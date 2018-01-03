package br.com.concrete.android.raphael.desafio.pullrequests;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class PullRequestsModule {

    @Provides
    PullRequestsPresenter provideRepositoriesPresenter(CompositeDisposable compositeDisposable,
                                                       PullRequestsContract.Api api) {
        return new PullRequestsPresenter(compositeDisposable, api);
    }

    @Provides
    PullRequestsAdapter provideRepositoriesRecycleAdapter(Application application) {
        return new PullRequestsAdapter(application);
    }

}
