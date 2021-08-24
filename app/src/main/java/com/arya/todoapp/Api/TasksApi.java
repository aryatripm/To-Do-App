package com.arya.todoapp.Api;

import com.arya.todoapp.Entity.Note;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TasksApi {

    @GET("api/tasks")
    Call<List<Note>> getAll();

    @GET("api/tasks/{id}")
    Call<Note> getById(
            @Path("id") String id
    );

    @POST("api/tasks")
    Call<Note> addOneNote(
            @Body Note note
    );

    @FormUrlEncoded
    @POST("api/tasks")
    Call<Note> addOne(
            @Field("note_title") String title,
            @Field("note_desc") String desc
    );

    @PUT("api/tasks/{id}")
    Call<Note> updateCompleted(
            @Path("id") String id,
            @Body Note note
    );


    @DELETE("api/tasks/{id}")
    Call<Note> deleteById(
            @Path("id") String id
    );
}
