package com.example.appoffer01.util;

import com.example.appoffer01.api.model.Address;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.appoffer01.util.CalculatesDistance.distance;

public class HandlerAddress {
    //This method search all adresses in database and filter them by the current location of user,
    //after return them into list adresses
    public static List<Address> filterAdresses(List<Address> adresses, double latitude, double longitude){
        //List adresses selected
        List<Address> addressesAux = new ArrayList<>();

        double distanceMt;

        for (Address address: adresses){
            //Get distance between location user and store
            distanceMt = distance(address.getLatitude(), latitude, address.getLongitude(), longitude, 0,0);

            //Filter by current location user, it will be selected each address near the user until eight thousand meters
            if (distanceMt <= 8000){
                distanceMt = distanceMt / 1000;
                address.setDistance(distanceMt);
                addressesAux.add(address);
            }
        }

        //Order address by distance to location user
        Collections.sort(addressesAux);

        return addressesAux;
    }

    public static List<Address> orderAdresses(List<Address> adresses, double latitude, double longitude){
        //List adresses selected
        List<Address> addressesAux = new ArrayList<>();

        double distanceMt;

        for (Address address: adresses){
            //Get distance between location user and store
            distanceMt = distance(address.getLatitude(), latitude, address.getLongitude(), longitude, 0,0);

            distanceMt = distanceMt / 1000;
            address.setDistance(distanceMt);
            addressesAux.add(address);
        }

        //Order address by distance to location user
        Collections.sort(addressesAux);

        return addressesAux;
    }
}
