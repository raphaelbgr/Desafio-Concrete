package br.com.concrete.android.raphael.desafio;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matcher;
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
import br.com.concrete.android.raphael.desafio.repositories.RepositoriesActivity;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;


@RunWith(AndroidJUnit4.class)
public class RepositoriesTest {

    private static final String REPOSITORIES_MOCK_JSON_PAGE_1 = "repositories_mock_page_1.json";
    private static final String REPOSITORIES_MOCK_JSON_PAGE_2 = "repositories_mock_page_2.json";
    private static final String REPOSITORIES_MOCK_JSON_PAGE_3 = "repositories_mock_page_3.json";
    private static final int JSON_EMPTY_ARRAY_SIZE = 0;
    private static final int JSON_ARRAY_SIZE = 30;
    private static final int SERVER_PORT = 1234;
    @Rule
    public ActivityTestRule<RepositoriesActivity> mActivityRule = new ActivityTestRule<>(RepositoriesActivity.class, true, false);
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
    public void listShouldLoadMore() throws Exception {
        server.enqueue(new MockResponse().setBody(loadJSON(REPOSITORIES_MOCK_JSON_PAGE_1)));
        server.enqueue(new MockResponse().setBody(loadJSON(REPOSITORIES_MOCK_JSON_PAGE_2)));
        server.enqueue(new MockResponse().setBody(loadJSON(REPOSITORIES_MOCK_JSON_PAGE_3)));
        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.repo_recycler)).perform(ViewActions.swipeUp());
        onView(withId(R.id.repo_recycler)).perform(ViewActions.swipeUp());
        Thread.sleep(1000);
        onView(withId(R.id.repo_recycler)).perform(ViewActions.swipeUp());
        onView(withId(R.id.repo_recycler)).perform(ViewActions.swipeUp());
        Thread.sleep(1000);
        onView(withId(R.id.repo_recycler)).perform(ViewActions.swipeUp());
        onView(withId(R.id.repo_recycler)).perform(ViewActions.swipeUp());
        Thread.sleep(1000);
        onView(withId(R.id.repo_recycler)).perform(ViewActions.swipeUp());
        onView(withId(R.id.repo_recycler)).perform(ViewActions.swipeUp());
        Thread.sleep(1000);
        onView(withId(R.id.repo_recycler)).perform(ViewActions.swipeUp());
        onView(withId(R.id.repo_recycler)).perform(ViewActions.swipeUp());
        Thread.sleep(1000);
        onView(withId(R.id.repo_recycler)).perform(ViewActions.swipeUp());
        onView(withId(R.id.repo_recycler)).perform(ViewActions.swipeUp());
        Thread.sleep(1000);
        onView(withId(R.id.repo_recycler)).check(new RecyclerViewItemCountAssertion(greaterThan(JSON_ARRAY_SIZE)));
    }

    @Test
    public void listShouldLoad() throws Exception {
        server.enqueue(new MockResponse().setBody(loadJSON(REPOSITORIES_MOCK_JSON_PAGE_1)));
        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.repo_recycler)).check(new RecyclerViewItemCountAssertion(greaterThanOrEqualTo(JSON_ARRAY_SIZE)));
    }

    @Test
    public void listShouldNotLoad() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST));
        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.repo_recycler)).check(new RecyclerViewItemCountAssertion(lessThanOrEqualTo(JSON_EMPTY_ARRAY_SIZE)));
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

        private final Matcher<Integer> matcher;


        RecyclerViewItemCountAssertion(Matcher<Integer> matcher) {
            this.matcher = matcher;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assertThat(adapter.getItemCount(), matcher);
        }

    }

}
