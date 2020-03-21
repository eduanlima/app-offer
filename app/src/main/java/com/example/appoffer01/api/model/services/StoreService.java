package com.example.appoffer01.api.model.services;

import com.example.appoffer01.api.model.Store;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StoreService {

    @GET("stores/{id_store}")
    Call<Store> searchStore(@Path("id_store") Integer id_store);
}
