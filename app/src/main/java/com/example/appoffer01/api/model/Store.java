package com.example.appoffer01.api.model;

import java.io.Serializable;
import java.util.List;

public class Store implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String image;
	private TypeStore typeStore;
	private List<Offer> offers;
	
	public Store() {}

	public Store(Integer id, String name, String image, TypeStore type, List<Offer> offerrs) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.typeStore = type;
		this.offers = offerrs;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
