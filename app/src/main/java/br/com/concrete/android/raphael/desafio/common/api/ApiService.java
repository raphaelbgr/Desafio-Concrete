package br.com.concrete.android.raphael.desafio.common.api;

import java.util.HashMap;
import java.util.List;

import br.com.concrete.android.raphael.desafio.common.api.response.PullRequest;
import br.com.concrete.android.raphael.desafio.common.api.response.Repositories;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;


public interface ApiService {

    @GET("/search/repositories")
    Flowable<Repositories> getRepositories(@QueryMap HashMap<String, String> queryMap);

    @GET("/repos/{owner}/{repo}/pulls")
    Flowable<List<PullRequest>> getPullRequests(@Path("owner") String owner, @Path("repo") String repo);
}
