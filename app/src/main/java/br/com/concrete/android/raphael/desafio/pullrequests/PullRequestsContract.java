package br.com.concrete.android.raphael.desafio.pullrequests;

import java.util.List;

import br.com.concrete.android.raphael.desafio.common.api.response.PullRequest;
import io.reactivex.Flowable;


public interface PullRequestsContract {

    @SuppressWarnings("EmptyMethod")
    interface View {

        void onRequestFailure();

        void dismissProgressDialog();

        void showProgressDialog();

        void showPullRequests(List<PullRequest> pullRequests);

        void showEmptyView();
    }

    interface Presenter {

        void requestData(String login, String repo);

        void bindView(View view);

        void unbindView();

        List<PullRequest> getCachedResponse(String key);
    }

    interface Api {

        Flowable<List<PullRequest>> getPullRequests(String login, String repo);
    }
}
