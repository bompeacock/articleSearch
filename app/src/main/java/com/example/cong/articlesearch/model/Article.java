package com.example.cong.articlesearch.model;

import com.example.cong.articlesearch.utils.Constant;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Cong on 22/10/2016.
 */

public class Article {
    @SerializedName("snippet")
    private String snippet;
    @SerializedName("web_url")
    private String web_url;
    @SerializedName("multimedia")
    private List<Media> multimedia;

    public class Media{
        private String url;
        private String type;
        private int width;
        private int height;

        public String getUrl() {
            return  Constant.APPEAR_IMAGE+url;
        }

        public String getType() {
            return type;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    public String getWeb_url() {
        return web_url;
    }

    public String getSnippet() {
        return snippet;
    }

    public List<Media> getMutimedia() {
        return multimedia;
    }
}
