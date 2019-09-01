package com.pravesh.oppomobile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("fundCode")
    @Expose
    private String fundCode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("3yrreturns")
    @Expose
    private String _3yrreturns;
    @SerializedName("nav")
    @Expose
    private String nav;
    @SerializedName("fundCategory")
    @Expose
    private String fundCategory;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("icon")
    @Expose
    private String icon;

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get3yrreturns() {
        return _3yrreturns;
    }

    public void set3yrreturns(String _3yrreturns) {
        this._3yrreturns = _3yrreturns;
    }

    public String getNav() {
        return nav;
    }

    public void setNav(String nav) {
        this.nav = nav;
    }

    public String getFundCategory() {
        return fundCategory;
    }

    public void setFundCategory(String fundCategory) {
        this.fundCategory = fundCategory;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
