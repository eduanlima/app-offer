package com.example.appoffer01.util;

import com.example.appoffer01.api.model.Offer;
import com.example.appoffer01.api.model.Store;
import com.example.appoffer01.model.OfferSearch;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderOffers {

    public static List<Store> orderByPrice(String text, List<Store> stores){

        List<Offer> listOfferNOrder = new ArrayList<Offer>();
        List<Offer> listOfferOrder = new ArrayList<Offer>();

        List<Store> listStoresOrder = new ArrayList<>();

        //Getting offer each element into list of store
        if (stores.size() > 0) {
            for (Store store: stores) {

                for (Offer offerNOrder : store.getOffers())
                    listOfferNOrder.add(offerNOrder);
            }
        }

        //Searching for text description product into list offer without order (NOrder) and instance a list order according search
        for (Offer offerSearch: listOfferNOrder) {
            System.out.println(text.toUpperCase());
            int aux = offerSearch.getProduct().getDescription().toUpperCase().indexOf(text.toUpperCase());

            if (aux != -1) {
                listOfferOrder.add(offerSearch);
            }
        }

        if (listOfferOrder.size() > 0) {
            Collections.sort(listOfferOrder);

            //Creating a new list of stores ordered
            Store storeOrder;
            List<Offer> listSearchOffer;

            for (Offer offerOrder: listOfferOrder) {

                storeOrder = new Store();
                storeOrder.setId(offerOrder.getStore().getId());
                storeOrder.setName(offerOrder.getStore().getName());

                listSearchOffer = new ArrayList<>();
                listSearchOffer.add(offerOrder);
                storeOrder.setOffers(listSearchOffer);

                listStoresOrder.add(storeOrder);
            }
        }

        return listStoresOrder;
    }
}
