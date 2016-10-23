package com.example.cong.articlesearch.utils;

import com.example.cong.articlesearch.model.ApiRespone;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Cong on 22/10/2016.
 */

public class RetrofitUtils {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public  static final Gson GSON = new Gson();

    public static Retrofit get(){
        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(client())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient client() {
        return new OkHttpClient.Builder()
                .addInterceptor(apiKeyIntercepter())
                .addInterceptor(reponseIntercepter())
                .build();
    }
    private static Interceptor reponseIntercepter(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                ResponseBody body = response.body();
                ApiRespone apiRespone = GSON.fromJson(body.string(),ApiRespone.class);
                body.close();
                response = response.newBuilder()
                        .body(ResponseBody.create(JSON,apiRespone.getResponse().toString()))
                        .build();
                return response;
            }
        };
    }
    private static Interceptor apiKeyIntercepter() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url()
                        .newBuilder()
                        .addQueryParameter("api_key",Constant.API_KEY)
                        .build();
                request = request.newBuilder()
                        .url(url)
                        .build();
                return chain.proceed(request);
            }
        };
    }
}
