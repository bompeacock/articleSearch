package com.example.cong.articlesearch.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cong on 23/10/2016.
 */

public class SearchRequest implements Serializable{
    private int page =0;
    private String q;
    private String beginDate;
    private String sort = "newest";
    private boolean deskArts;
    private boolean deskFashionAndStyle;
    private boolean deskSports;

    public SearchRequest() {
    }

    protected SearchRequest(Parcel in) {
        page = in.readInt();
        q = in.readString();
        beginDate = in.readString();
        sort = in.readString();
        deskArts = in.readByte() != 0;
        deskFashionAndStyle = in.readByte() != 0;
        deskSports = in.readByte() != 0;
    }

    public int getPage() {
        return page;
    }
    public String getQ() {
        return q;
    }
    public String getBeginDate() {
        return beginDate;
    }
    public String getSort() {
        return sort;
    }
    public boolean isDeskArts() {
        return deskArts;
    }
    public boolean isDeskFashionAndStyle() {
        return deskFashionAndStyle;
    }
    public boolean isDeskSports() {
        return deskSports;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setDeskArts(boolean deskArts) {
        this.deskArts = deskArts;
    }

    public void setDeskFashionAndStyle(boolean deskFashionAndStyle) {
        this.deskFashionAndStyle = deskFashionAndStyle;
    }

    public void setDeskSports(boolean deskSports) {
        this.deskSports = deskSports;
    }

    public void resetPage(){
        page = 0;
    }
    public void addPage(){
        page +=1;
    }
    public Map<String,String> castToMap(){
        Map<String,String> map = new HashMap<>();
        if(q!=null) map.put("q",q);
        if(beginDate!=null) map.put("begin_date",beginDate);
        if(sort!=null) map.put("sort",sort.toLowerCase());
        if(getNewsDesk()!=null) map.put("fq", "news_desk:("+getNewsDesk()+")");
        map.put("page",page+"");

        return map;
    }
    public String getNewsDesk(){
        if(!deskArts&&!deskFashionAndStyle&&!deskSports) return null;
        String value="";
        if(deskArts) value+="\"Arts\" ";
        if(deskFashionAndStyle) value+="\"Fashion & Style\"";
        if(deskSports) value+="\"Sports\" ";
        return  value.trim();

    }


}
