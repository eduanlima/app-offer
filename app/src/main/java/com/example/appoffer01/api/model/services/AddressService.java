package com.example.appoffer01.api.model.services;

import com.example.appoffer01.api.model.Address;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AddressService {

    @GET("adresses")
    Call<List<Address>> searchAdresses();

}
