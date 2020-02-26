package com.example.appoffer10.api.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StoreService {

    @GET("stories")
    Call<List<Store>> searchStore();
}
