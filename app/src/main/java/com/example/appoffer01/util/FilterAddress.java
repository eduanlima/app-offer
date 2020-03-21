package com.example.appoffer01.util;

import com.example.appoffer01.api.model.Address;

import java.util.List;

import static com.example.appoffer01.util.CalculatesDistance.distance;

public class FilterAddress {

    public static List<Address> filterAdresses(List<Address> adresses, double latitude, double longitude){

        for (Address address: adresses){
            if (distance(address.getLatitude(), latitude, address.getLongitude(), longitude, 0,0) > 8000){
                adresses.remove(address);
            }
        }
        return adresses;
    }
}
