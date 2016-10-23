package com.example.cong.articlesearch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Cong on 23/10/2016.
 */

public class SearchResult {
    @SerializedName("docs")
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }
}
