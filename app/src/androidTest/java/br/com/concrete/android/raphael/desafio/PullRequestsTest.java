package br.com.concrete.android.raphael.desafio;

import android.content.Intent;
import android.os.Bundle;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import br.com.concrete.android.raphael.desafio.common.NetworkModule;
import br.com.concrete.android.raphael.desafio.pullrequests.PullRequestsActivity;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class PullRequestsTest {

    private final static String PULL_REQUESTS_MOCK_JSON_RXJAVA = "pull_requests_rxjava.json";
    private final static String ARG_LOGIN_VALUE = "ReactiveX";
    private final static String ARG_REPO_VALUE = "RxJava2";
    private final static String ARG_LOGIN = "login";
    private final static String ARG_REPO = "repo";
    private final static int JSON_EMPTY_ARRAY_SIZE = 0;
    private static final int SERVER_PORT = 1234;
    private final static int JSON_SIZE = 1;
    @Rule
    public ActivityTestRule<PullRequestsActivity> mActivityRule = new ActivityTestRule<>(PullRequestsActivity.class, true, false);
    private MockWebServer server;

    @Before
    public void setUp() throws IOException {
        NetworkModule.BASE_URL = BuildConfig.TEST_BASE_URL;
        server = new MockWebServer();
        server.start(SERVER_PORT);
        server.url("/");
        InstrumentationRegistry.registerInstance(InstrumentationRegistry.getInstrumentation(), new Bundle());
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void listShouldLoad() throws Exception {
        server.enqueue(new MockResponse().setBody(loadJSON(PULL_REQUESTS_MOCK_JSON_RXJAVA)));
        Intent intent = new Intent();
        intent.putExtra(ARG_LOGIN, ARG_LOGIN_VALUE);
        intent.putExtra(ARG_REPO, ARG_REPO_VALUE);
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.pull_request_recycler)).check(new RecyclerViewItemCountAssertion(JSON_SIZE));
    }

    @Test
    public void listShouldNotLoad() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST));
        Intent intent = new Intent();
        intent.putExtra(ARG_LOGIN, ARG_LOGIN_VALUE);
        intent.putExtra(ARG_REPO, ARG_REPO_VALUE);
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.pull_request_recycler)).check(new RecyclerViewItemCountAssertion(JSON_EMPTY_ARRAY_SIZE));
    }

    private String loadJSON(String JSON_FILE_PATH) throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(JSON_FILE_PATH);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder json = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            json.append(line);
        }
        is.close();
        return json.toString();
    }


    class RecyclerViewItemCountAssertion implements ViewAssertion {

        private final int expectedCount;


        RecyclerViewItemCountAssertion(int expectedCount) {
            this.expectedCount = expectedCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assertEquals(adapter.getItemCount(), expectedCount);
        }

    }

}
