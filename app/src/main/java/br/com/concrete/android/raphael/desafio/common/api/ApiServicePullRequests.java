package br.com.concrete.android.raphael.desafio.common.api;


import java.util.List;

import javax.inject.Inject;

import br.com.concrete.android.raphael.desafio.common.api.response.PullRequest;
import br.com.concrete.android.raphael.desafio.pullrequests.PullRequestsContract;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("WeakerAccess")
public class ApiServicePullRequests implements PullRequestsContract.Api {

    private ApiService apiService;

    @Inject
    ApiServicePullRequests(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Flowable<List<PullRequest>> getPullRequests(String login, String repo) {
        return apiService.getPullRequests(login, repo)
                .cache()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
