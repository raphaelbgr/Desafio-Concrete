package br.com.concrete.android.raphael.desafio.repositories;

import android.app.Application;

import javax.inject.Singleton;

import br.com.concrete.android.raphael.desafio.common.api.ApiServiceRepositories;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class RepositoriesModule {

    @Provides
    RepositoriesPresenter provideRepositoriesPresenter(CompositeDisposable compositeDisposable,
                                                       ApiServiceRepositories api) {
        return new RepositoriesPresenter(compositeDisposable, api);
    }

    @Provides
    @Singleton
    RepositoriesAdapter provideRepositoriesRecycleAdapter(Application context) {
        return new RepositoriesAdapter(context);
    }

}
