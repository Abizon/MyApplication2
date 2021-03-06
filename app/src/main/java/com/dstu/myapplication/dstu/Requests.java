package com.dstu.myapplication.dstu;

import com.dstu.myapplication.models.Event;
import com.dstu.myapplication.models.News;
import com.dstu.myapplication.models.Specialty;
import com.dstu.myapplication.models.Facultie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface Requests {
    @GET("/site/ci/api/training/getFaculties")
    Call<Facultie.Answer> getFaculties();

    @GET("/site/ci/api/training/getSpecialties/{id}") Call<Specialty.Array> getSpecialties(@Path("id") int ID);

    @GET("/site/ci/api/base/getEvents/{smeshenie}") Call<Event.Array> getEvents(@Path("smeshenie") int smeshenie);

    @GET("/site/ci/api/base/getNews/{smeshenie}") Call<News.Array> getNews(@Path("smeshenie") int smeshenie);
}
