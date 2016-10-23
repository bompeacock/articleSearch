package com.example.cong.articlesearch.api;

import com.example.cong.articlesearch.model.SearchResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Cong on 23/10/2016.
 */

public interface ArticleApi {
    @GET("articlesearch.json")
    Call<SearchResult> search(@QueryMap(encoded = true)Map<String,String> option);

}
