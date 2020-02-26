package com.example.appoffer10.api.model;

import java.io.Serializable;
import java.util.Date;

public class Offer implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Store store;
	private Product product;
	private Double price;
	private Date dateInitial;
	private Date dateLimit;
	private Boolean status;
	private String image;
	
	public Offer() {}
	
	public Offer(Integer id, Store store, Product product, Double price, Date dateInitial, Date dateLimit, Boolean status, String image) {
		this.id = id;
		this.store = store;
		this.product = product;
		this.price = price;
		this.dateInitial = dateInitial;
		this.dateLimit = dateLimit;
		this.status = status;
		this.image = image;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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
		Offer other = (Offer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
