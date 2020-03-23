package com.example.appoffer01.api.model.services;

import com.example.appoffer01.api.model.BannerOffer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BannerOfferService {

    @GET("banners/{id_store}")
    Call<List<BannerOffer>> searchBanners(@Path("id_store") Integer id_store);
}
