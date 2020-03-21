package com.example.appoffer01.model;

import java.io.Serializable;

public class OfferSearch implements Serializable, Comparable<OfferSearch> {

    private Integer id;
    private Double price;
    private Integer store;

    public OfferSearch(Integer id, Double price, Integer store) {
        this.id = id;
        this.price = price;
        this.store = store;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OfferSearch other = (OfferSearch) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "OfferSearch [id=" + id + ", price=" + price + ", store=" + store + "]";
    }

    @Override
    public int compareTo(OfferSearch o) {
        return price.compareTo(o.price);
    }
}

