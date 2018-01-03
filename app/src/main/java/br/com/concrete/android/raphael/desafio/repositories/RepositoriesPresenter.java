package br.com.concrete.android.raphael.desafio.repositories;

import android.annotation.SuppressLint;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import br.com.concrete.android.raphael.desafio.BuildConfig;
import br.com.concrete.android.raphael.desafio.common.api.model.Items;
import br.com.concrete.android.raphael.desafio.common.api.response.Repositories;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
@SuppressLint("StringFormatInvalid")
public class RepositoriesPresenter implements
        RepositoriesContract.Presenter {

    private static final int API_RESPONSE_PAGE_SIZE = 30;

    private Repositories cachedResponse;
    private RepositoriesContract.View view;
    private RepositoriesContract.Api api;
    private CompositeDisposable disposable;

    @Inject
    RepositoriesPresenter(CompositeDisposable compositeDisposable, RepositoriesContract.Api api) {
        this.disposable = compositeDisposable;
        this.api = api;
    }

    private void mergeResponses(final Repositories newResponse) {
        if (cachedResponse == null || cachedResponse.getItems() == null) {
            cachedResponse = newResponse;
        } else {
            cachedResponse.setItems(mergeItems(newResponse.getItems()));
            newResponse.setItems(cachedResponse.getItems());
        }
    }

    private List<Items> mergeItems(List<Items> incomingItems) {
        List<Items> mergedT = new ArrayList<>(cachedResponse.getItems());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mergedT.addAll(incomingItems.stream()
                    .filter(tIncoming -> !cachedResponse.getItems().contains(tIncoming))
                    .collect(Collectors.toList()));
        }
        return mergedT;
    }

    private int getNextPage() {
        int cachedSize = cachedResponse.getItems().size();
        return (cachedSize / RepositoriesPresenter.API_RESPONSE_PAGE_SIZE) + 1;
    }

    @Override
    public boolean hasNextPage() {
        if (getCachedResponse() != null) {
            int cachedSize = getCachedResponse().getItems().size();
            int totalSize = getCachedResponse().getTotal_count();
            return cachedSize < totalSize;
        } else {
            return true;
        }
    }

    @Override
    public void bindView(RepositoriesContract.View view) {
        this.view = view;
    }

    @Override
    public void unbindView() {
        disposable.clear();
        view = null;
    }

    public Repositories getCachedResponse() {
        return cachedResponse;
    }

    @Override
    public void loadMore() {
        view.showProgress();
        callServer(String.valueOf(getNextPage()));
    }

    @Override
    public void requestData() {
        view.showProgress();
        if (cachedResponse != null) {
            view.showPullRequests(cachedResponse);
            view.dismissProgressDialog();
        } else {
            callServer(String.valueOf(1));
        }
    }

    private void callServer(String page) {
        disposable.clear();
        Disposable subscribe = api.getRepositories(page)
                .subscribe(gitHubResponse -> {
                            mergeResponses(gitHubResponse);
                            view.showPullRequests(cachedResponse);
                            view.dismissProgressDialog();
                        },
                        throwable -> {
                            if (BuildConfig.DEBUG) {
                                throwable.printStackTrace();
                            }
                            view.dismissProgressDialog();
                            view.onRequestFailure();
                        });
        disposable.add(subscribe);
    }
}
