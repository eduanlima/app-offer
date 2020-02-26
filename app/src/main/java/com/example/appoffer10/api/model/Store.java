package com.example.appoffer10.api.model;

import java.io.Serializable;
import java.util.List;

public class Store implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name_desc;
	private String image;
	private TypeStore typeStore;
	private String address;
	private Double latitude;
	private Double longitude;
	private List<Offer> offers;
	
	public Store() {}

	public Store(Integer id, String name_desc, String image, TypeStore type, String address, Double latitude, Double longitude, List<Offer> offerrs) {
		this.id = id;
		this.name_desc = name_desc;
		this.image = image;
		this.typeStore = type;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.offers = offerrs;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName_desc() {
		return name_desc;
	}

	public void setName_desc(String name_desc) {
		this.name_desc = name_desc;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public TypeStore getType() {
		return typeStore;
	}

	public void setType(TypeStore type) {
		this.typeStore = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offerrs) {
		this.offers = offerrs;
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
		Store other = (Store) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
