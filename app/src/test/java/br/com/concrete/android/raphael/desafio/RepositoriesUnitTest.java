package br.com.concrete.android.raphael.desafio;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import br.com.concrete.android.raphael.desafio.common.api.ApiService;
import br.com.concrete.android.raphael.desafio.common.api.response.Repositories;
import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


public class RepositoriesUnitTest {

    private static final String REPOSITORIES_MOCK_JSON = "repositories_mock.json";
    private static final String HTTP_ERROR = "http_error";

    @Mock
    private ApiService apiService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetRepositories() throws Exception {
        when(apiService.getRepositories(getQueryMap()))
                .then(invocation -> createRespose());

        TestSubscriber<Repositories> testSubscriber = new TestSubscriber<>();
        Flowable<Repositories> flowable = apiService.getRepositories(getQueryMap());
        testSubscriber.assertNoErrors();

        Repositories repositories = flowable.blockingFirst();
        assertEquals(repositories, getRepositories());
    }

    @Test
    public void shouldFailToSignInWithUsernameAndPassword() {
        when(apiService.getRepositories(getQueryMap()))
                .then(invocation -> createFailureForGetRepositories());

        TestSubscriber<Repositories> testSubscriber = new TestSubscriber<>();
        Flowable<Repositories> flowable = apiService.getRepositories(getQueryMap());
        flowable.subscribe(testSubscriber);
        testSubscriber.assertError(Exception.class);

        List<Throwable> errors = testSubscriber.errors();
        Throwable throwable = errors.get(0);
        assertEquals(throwable.getMessage(), HTTP_ERROR);
    }

    private Flowable<Repositories> createRespose() throws IOException {
        return Flowable.just(getRepositories());
    }

    private Flowable<Repositories> createFailureForGetRepositories() {
        return Flowable.error(new Exception(HTTP_ERROR));
    }

    private Repositories getRepositories() throws IOException {
        String json = loadJSONFromAsset();
        return new Gson().fromJson(json, Repositories.class);
    }

    private HashMap<String, String> getQueryMap() {
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put("q", "language:Java");
        queryMap.put("sort", "stars");
        queryMap.put("page", "1");
        return queryMap;
    }

    private String loadJSONFromAsset() throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(REPOSITORIES_MOCK_JSON);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder json = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            json.append(line);
        }
        is.close();
        return json.toString();
    }
}