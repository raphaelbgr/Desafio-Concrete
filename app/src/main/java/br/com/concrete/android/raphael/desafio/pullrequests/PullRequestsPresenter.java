package br.com.concrete.android.raphael.desafio.pullrequests;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import br.com.concrete.android.raphael.desafio.common.api.response.PullRequest;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@SuppressWarnings("CanBeFinal")
public class PullRequestsPresenter implements
        PullRequestsContract.Presenter {

    private HashMap<String, List<PullRequest>> pullRequests;
    private PullRequestsContract.View view;
    private PullRequestsContract.Api api;
    private CompositeDisposable disposable;

    @Inject
    PullRequestsPresenter(CompositeDisposable compositeDisposable, PullRequestsContract.Api api) {
        this.disposable = compositeDisposable;
        this.pullRequests = new HashMap<>();
        this.api = api;
    }

    @Override
    public void bindView(PullRequestsContract.View view) {
        this.view = view;
    }

    @Override
    public void unbindView() {
        disposable.clear();
        view = null;
    }

    @Override
    public void requestData(String login, String repo) {
        view.showProgressDialog();
        String key = login + repo;
        if (containsCachedResponse(key)) {
            List<PullRequest> cached = getCachedResponse(key);
            showContentOrEmptyView(cached);
            view.dismissProgressDialog();
        } else {
            callServer(login, repo);
        }
    }

    private void callServer(String login, String repo) {
        disposable.clear();
        Disposable subscribe = api.getPullRequests(login, repo)
                .subscribe(pullRequests -> {
                            String key = login + repo;
                            cacheResponse(key, pullRequests);
                            showContentOrEmptyView(pullRequests);
                            view.dismissProgressDialog();
                        },
                        throwable -> {
                            view.dismissProgressDialog();
                            view.onRequestFailure();
                        });
        disposable.add(subscribe);
    }

    private void showContentOrEmptyView(List<PullRequest> pullRequests) {
        if (pullRequests == null || pullRequests.size() == 0) {
            view.showEmptyView();
        } else {
            view.showPullRequests(pullRequests);
        }
    }

    private void cacheResponse(String key, List<PullRequest> pullRequests) {
        this.pullRequests.put(key, pullRequests);
    }

    @Override
    public List<PullRequest> getCachedResponse(String key) {
        return pullRequests.get(key);
    }

    private boolean containsCachedResponse(String key) {
        return pullRequests.containsKey(key);
    }
}
