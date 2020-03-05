package com.expleo.marketplace.model;

public class Matches {

	private String sellerName;
	
	private long sellerPrice;
	
	private String buyerName;
	
	private long buyerPrice;

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public long getSellerPrice() {
		return sellerPrice;
	}

	public void setSellerPrice(long sellerPrice) {
		this.sellerPrice = sellerPrice;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public long getBuyerPrice() {
		return buyerPrice;
	}

	public void setBuyerPrice(long buyerPrice) {
		this.buyerPrice = buyerPrice;
	}

}
