package br.com.concrete.android.raphael.desafio.common.api;

import java.util.HashMap;

import javax.inject.Inject;

import br.com.concrete.android.raphael.desafio.common.api.response.Repositories;
import br.com.concrete.android.raphael.desafio.repositories.RepositoriesContract;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("WeakerAccess")
public class ApiServiceRepositories implements RepositoriesContract.Api {

    private final String PARAM_Q = "q";
    private final String PARAM_Q_VALUE = "language:Java";
    private final String PARAM_SORT = "sort";
    private final String PARAM_SORT_VALUE = "stars";
    private final String PARAM_PAGE = "page";

    private ApiService apiService;

    @Inject
    ApiServiceRepositories(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Flowable<Repositories> getRepositories(String page) {
        return apiService.getRepositories(getQueryMap(page))
                .cache()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private HashMap<String, String> getQueryMap(String page) {
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put(PARAM_Q, PARAM_Q_VALUE);
        queryMap.put(PARAM_SORT, PARAM_SORT_VALUE);
        queryMap.put(PARAM_PAGE, String.valueOf(page));
        return queryMap;
    }
}
