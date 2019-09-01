package com.pravesh.oppomobile.retrofit;

import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;


public interface ApiInterface {


    //@Headers({"Content-Type: application/json","companycode: 0001","username: R35","password: R35","employeecode: R35"})
    //@Headers({"Content-Type: application/json","mobile: 9928100718","password: 123456"})

    @GET("funds")
    Call<JsonObject> getResult();
}
