package br.com.concrete.android.raphael.desafio.repositories;

import br.com.concrete.android.raphael.desafio.common.api.response.Repositories;
import io.reactivex.Flowable;


public interface RepositoriesContract {

    @SuppressWarnings("EmptyMethod")
    interface View {

        void onRequestFailure();

        void dismissProgressDialog();

        void showProgress();

        void showPullRequests(Repositories response);
    }

    interface Presenter {
        void requestData();

        void loadMore();

        void bindView(RepositoriesContract.View view);

        void unbindView();

        boolean hasNextPage();

        Repositories getCachedResponse();
    }

    interface Api {

        Flowable<Repositories> getRepositories(String page);
    }
}
