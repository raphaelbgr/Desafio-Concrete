package br.com.concrete.android.raphael.desafio;

import android.app.Application;


public class App extends Application {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = initDagger();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        appComponent = null;
    }

    private AppComponent initDagger() {
        return DaggerAppComponent.builder()
                .application(this)
                .build();
    }
}
