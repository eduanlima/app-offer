package com.example.appoffer01.api.model.services;

import com.example.appoffer01.api.model.Offer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OfferService {

    @GET("offers/{id_store}")
    Call<List<Offer>> searchOffers(@Path("id_store") Integer id_store);
}
