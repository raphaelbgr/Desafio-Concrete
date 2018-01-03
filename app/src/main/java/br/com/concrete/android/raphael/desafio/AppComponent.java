package br.com.concrete.android.raphael.desafio;


import android.app.Application;

import javax.inject.Singleton;

import br.com.concrete.android.raphael.desafio.common.NetworkModule;
import br.com.concrete.android.raphael.desafio.common.api.ApiModule;
import br.com.concrete.android.raphael.desafio.common.api.ApiServicePullRequests;
import br.com.concrete.android.raphael.desafio.common.api.ApiServiceRepositories;
import br.com.concrete.android.raphael.desafio.pullrequests.PullRequestsActivity;
import br.com.concrete.android.raphael.desafio.pullrequests.PullRequestsModule;
import br.com.concrete.android.raphael.desafio.pullrequests.PullRequestsPresenter;
import br.com.concrete.android.raphael.desafio.repositories.RepositoriesActivity;
import br.com.concrete.android.raphael.desafio.repositories.RepositoriesModule;
import br.com.concrete.android.raphael.desafio.repositories.RepositoriesPresenter;
import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {
        ApiModule.class,
        NetworkModule.class,
        RepositoriesModule.class,
        PullRequestsModule.class
})
public interface AppComponent {

    void inject(RepositoriesActivity target);

    void inject(RepositoriesPresenter target);

    void inject(ApiServiceRepositories target);

    void inject(PullRequestsActivity target);

    void inject(PullRequestsPresenter target);

    void inject(ApiServicePullRequests target);

    @Component.Builder
    interface Builder {
        AppComponent build();
        @BindsInstance Builder application(Application application);
    }
}
