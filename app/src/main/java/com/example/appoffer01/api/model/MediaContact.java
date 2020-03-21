package com.example.appoffer01.api.model;

import java.io.Serializable;

public class MediaContact implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String detail;
	private String icon;
	private Store store;
	private TypeMediaContact typeMediaContact;
	
	public MediaContact() {}
	
	public MediaContact(String detail, String icon, Store store, TypeMediaContact typeMediaContact) {
		super();
		this.detail = detail;
		this.icon = icon;
		this.store = store;
		this.typeMediaContact = typeMediaContact;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public TypeMediaContact getTypeMediaContact() {
		return typeMediaContact;
	}

	public void setTypeMediaContact(TypeMediaContact typeMediaContact) {
		this.typeMediaContact = typeMediaContact;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((detail == null) ? 0 : detail.hashCode());
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
		MediaContact other = (MediaContact) obj;
		if (detail == null) {
			if (other.detail != null)
				return false;
		} else if (!detail.equals(other.detail))
			return false;
		return true;
	}
	
}
