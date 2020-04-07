package com.example.appoffer01.util;

import com.example.appoffer01.api.model.Offer;
import com.example.appoffer01.api.model.Store;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchStore {
    /*This method is used to search a offer from through a description product
    and return it into a ordained list*/
    public static List<Store> search(String textSearch, List<Store> allStores){

        /*Create list that will contain items in order of lowest price*/
        List<Store> stores = new ArrayList<>();

        /*Description product from list allOffers*/
        String name;

        /*Searching for text name product into list offer without order (allOffers) and
        instance a new list according search*/
        for (Store storeSearch: allStores) {

            name = storeSearch.getName().toUpperCase();

            /*Replace all special characters from name*/
            name = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

            /*Transform to upper case text used on search*/
            textSearch = textSearch.toUpperCase();

            /*Replace all special characters from text search*/
            textSearch = Normalizer.normalize(textSearch, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

            /*Looking for occurrence of the search text in the product name listed in the offer*/
            int aux = name.indexOf(textSearch.toUpperCase());

            if (aux != -1) {
                /*If occurrence is found, the offer is add into new list that will be ordained by price*/
                stores.add(storeSearch);
            }
        }

        if (stores.size() == 0){
            stores = null;
        }

        return stores;
    }
}
