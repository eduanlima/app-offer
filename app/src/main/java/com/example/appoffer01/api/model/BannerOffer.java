package com.example.appoffer01.api.model;

import java.io.Serializable;

public class BannerOffer implements Serializable{

	private static final long serialVersionUID = 1L;
	private Store store;
	private Sector sector;
	private String dateInitial;
	private String dateLimit;
	private String image;
	private Boolean status;
	
	public BannerOffer() {}
	
	public BannerOffer(Store store, Sector sector, String dateInitial, String dateLimit, String image, Boolean status) {
		super();
		this.store = store;
		this.sector = sector;
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

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public String getDateInitial() {
		return dateInitial;
	}

	public void setDateInitial(String dateInitial) {
		this.dateInitial = dateInitial;
	}

	public String getDateLimit() {
		return dateLimit;
	}

	public void setDateLimit(String dateLimit) {
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
		result = prime * result + ((sector == null) ? 0 : sector.hashCode());
		result = prime * result + ((store == null) ? 0 : store.hashCode());
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
		BannerOffer other = (BannerOffer) obj;
		if (sector == null) {
			if (other.sector != null)
				return false;
		} else if (!sector.equals(other.sector))
			return false;
		if (store == null) {
			if (other.store != null)
				return false;
		} else if (!store.equals(other.store))
			return false;
		return true;
	}
	
}
