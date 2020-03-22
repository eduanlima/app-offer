package com.example.appoffer01.util;

import com.example.appoffer01.api.model.Offer;
import com.example.appoffer01.api.model.Store;
import com.example.appoffer01.model.OfferSearch;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderOffers {

    public static List<Offer> orderByPrice(String text, List<Offer> allOffers){

        List<Offer> listOfferOrder = new ArrayList<>();

        //Searching for text description product into list offer without order (NOrder) and instance a list order according search
        for (Offer offerSearch: allOffers) {
            System.out.println(text.toUpperCase());
            int aux = offerSearch.getProduct().getDescription().toUpperCase().indexOf(text.toUpperCase());

            if (aux != -1) {
                listOfferOrder.add(offerSearch);
            }
        }

        if (listOfferOrder.size() > 0) {
            Collections.sort(listOfferOrder);
        }
        else{
            listOfferOrder = null;
        }

        return listOfferOrder;
    }
}
