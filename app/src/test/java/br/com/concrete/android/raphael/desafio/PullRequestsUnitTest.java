package br.com.concrete.android.raphael.desafio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import br.com.concrete.android.raphael.desafio.common.api.ApiService;
import br.com.concrete.android.raphael.desafio.common.api.response.PullRequest;
import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


public class PullRequestsUnitTest {

    private static final String PULL_REQUESTS_JSON_MOCK = "pull_requests_rxjava.json";
    private static final String HTTP_ERROR = "http_error";
    private static final String OWNER = "ReactiveX";
    private static final String REPO_NAME = "JavaRx2";


    @Mock
    private ApiService apiService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetPullRequests() throws Exception {
        when(apiService.getPullRequests(OWNER, REPO_NAME))
                .then(invocation -> createRespose());

        TestSubscriber<List<br.com.concrete.android.raphael.desafio.common.api.response.PullRequest>> testSubscriber = new TestSubscriber<>();
        Flowable<List<br.com.concrete.android.raphael.desafio.common.api.response.PullRequest>> flowable = apiService.getPullRequests(OWNER, REPO_NAME);
        testSubscriber.assertNoErrors();

        List<PullRequest> repositories = flowable.blockingFirst();
        assertEquals(repositories, getPullRequests());
    }

    @Test
    public void shouldFailPullRequests() {
        when(apiService.getPullRequests(OWNER, REPO_NAME))
                .then(invocation -> createFailureForGetPullRequests());

        TestSubscriber<List<PullRequest>> testSubscriber = new TestSubscriber<>();
        Flowable<List<PullRequest>> flowable = apiService.getPullRequests(OWNER, REPO_NAME);
        flowable.subscribe(testSubscriber);
        testSubscriber.assertError(Exception.class);

        List<Throwable> errors = testSubscriber.errors();
        Throwable throwable = errors.get(0);
        assertEquals(throwable.getMessage(), HTTP_ERROR);
    }

    private Flowable<List<PullRequest>> createRespose() throws IOException {
        return Flowable.just(getPullRequests());
    }

    private Flowable<List<PullRequest>> createFailureForGetPullRequests() {
        return Flowable.error(new Exception(HTTP_ERROR));
    }

    private List<PullRequest> getPullRequests() throws IOException {
        String json = loadJSONFromAsset();
        return new Gson().fromJson(json, new TypeToken<List<PullRequest>>() {
        }.getType());
    }

    private String loadJSONFromAsset() throws IOException {
        String json;
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(PULL_REQUESTS_JSON_MOCK);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, "UTF-8");
        return json;
    }
}