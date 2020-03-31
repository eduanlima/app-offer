package com.example.appoffer01.util;

import com.example.appoffer01.api.model.Offer;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderOffers {
    /*This method is used to search a offer from through a description product
    and return it into a ordained list*/
    public static List<Offer> orderByPrice(String textSearch, List<Offer> allOffers){

        /*Create list that will contain items in order of lowest price*/
        List<Offer> listOfferOrder = new ArrayList<>();

        /*Description product from list allOffers*/
        String description;

        /*Searching for text description product into list offer without order (allOffers) and
        instance a new list according search*/
        for (Offer offerSearch: allOffers) {

            description = offerSearch.getProduct().getDescription().toUpperCase();

            /*Replace all special characters from description*/
            description = Normalizer.normalize(description, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

            /*Transform to upper case text used on search*/
            textSearch = textSearch.toUpperCase();

            /*Replace all special characters from text search*/
            textSearch = Normalizer.normalize(textSearch, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

            /*Looking for occurrence of the search text in the product description listed in the offer*/
            int aux = description.indexOf(textSearch.toUpperCase());

            if (aux != -1) {
                /*If occurrence is found, the offer is add into new list that will be ordained by price*/
                listOfferOrder.add(offerSearch);
            }
        }

        if (listOfferOrder.size() > 0) {
            /*Ordering the list by price*/
            Collections.sort(listOfferOrder);
        }
        else{
            /*No offers were found through the searched text*/
            listOfferOrder = null;
        }

        return listOfferOrder;
    }
}
