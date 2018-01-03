package br.com.concrete.android.raphael.desafio.common.api;


import br.com.concrete.android.raphael.desafio.pullrequests.PullRequestsContract;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ApiModule {

    @Provides
    ApiServiceRepositories provideRepoApiService(ApiService apiService) {
        return new ApiServiceRepositories(apiService);
    }

    @Provides
    PullRequestsContract.Api providePullRequestsApiService(ApiService apiService) {
        return new ApiServicePullRequests(apiService);
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}
