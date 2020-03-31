package com.example.appoffer01.util;

import com.example.appoffer01.api.model.Address;

import java.util.ArrayList;
import java.util.List;

import static com.example.appoffer01.util.CalculatesDistance.distance;

public class FilterAddress {
    /*This method search all adresses in database and filter them by the current location of user,
     after return them into list adresses*/
    public static List<Address> filterAdresses(List<Address> adresses, double latitude, double longitude){
        /*List adresses selected*/
        List<Address> addressesAux = new ArrayList<>();

        for (Address address: adresses){
            /*Filter by current location user, it will be selected each address near the user until eight thousand meters*/
            if (distance(address.getLatitude(), latitude, address.getLongitude(), longitude, 0,0) <= 8000){
                addressesAux.add(address);
            }
        }
        return addressesAux;
    }
}
