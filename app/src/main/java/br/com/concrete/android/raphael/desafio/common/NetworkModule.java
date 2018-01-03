package br.com.concrete.android.raphael.desafio.common;


import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import br.com.concrete.android.raphael.desafio.BuildConfig;
import br.com.concrete.android.raphael.desafio.common.api.ApiService;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    final private static int CACHE_SIZE_10_MB = 10 * 1024 * 1024;
    final private static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static String BASE_URL = BuildConfig.BASE_URL;


    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        return new Cache(application.getCacheDir(), CACHE_SIZE_10_MB);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat(DATE_FORMAT)
                .create();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    @Provides
    @Singleton
    okhttp3.OkHttpClient provideOkHttpClient(okhttp3.Cache cache,
                                             HttpLoggingInterceptor loggingInterceptor) {
        return new okhttp3.OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(GsonConverterFactory gsonConverterFactory,
                             okhttp3.OkHttpClient okHttpClient,
                             RxJava2CallAdapterFactory rxJavaCallAdapterFactory) {
        return new Retrofit.Builder()
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .client(okHttpClient)
                .baseUrl(NetworkModule.BASE_URL)
                .build();
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit restAdapter) {
        return restAdapter.create(ApiService.class);
    }

    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    RxJava2CallAdapterFactory provideRxJavaCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }
}
