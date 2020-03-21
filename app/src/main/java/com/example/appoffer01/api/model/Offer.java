package com.example.appoffer01.api.model;

import java.io.Serializable;
import java.util.Date;

public class Offer implements Serializable, Comparable<Offer>{

	private static final long serialVersionUID = 1L;
	private Store store;
	private Product product;
	private Double price;
	private Date dateInitial;
	private Date dateLimit;
	private String image;
	private Boolean status;
	
	public Offer() {}

	public Offer(Store store, Product product, Double price, Date dateInitial, Date dateLimit, String image,
			Boolean status) {
		super();
		this.store = store;
		this.product = product;
		this.price = price;
		this.dateInitial = dateInitial;
		this.dateLimit = dateLimit;
		this.image = image;
		this.status = status;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getDateInitial() {
		return dateInitial;
	}

	public void setDateInitial(Date dateInitial) {
		this.dateInitial = dateInitial;
	}

	public Date getDateLimit() {
		return dateLimit;
	}

	public void setDateLimit(Date dateLimit) {
		this.dateLimit = dateLimit;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Offer other = (Offer) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public int compareTo(Offer o) {
		return price.compareTo(o.getPrice());
	}
	
}
