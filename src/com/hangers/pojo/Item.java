package com.hangers.pojo;

import java.sql.Date;

public class Item {
	private String itemCode;
	private String itemType;
	private String brand;
	private float priceIn;
	private float priceOut;
	private int quantity;
	private String size;
	private Date dateIn;
	private Date dateOut;
	private String transactionId;
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Item(String itemCode, String itemType, String brand, float priceIn,
			float priceOut, int quantity, String size, Date dateIn,
			Date dateOut, String transactionId) {
		super();
		this.itemCode = itemCode;
		this.itemType = itemType;
		this.brand = brand;
		this.priceIn = priceIn;
		this.priceOut = priceOut;
		this.quantity = quantity;
		this.size = size;
		this.dateIn = dateIn;
		this.dateOut = dateOut;
		this.transactionId = transactionId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public float getPriceIn() {
		return priceIn;
	}
	public void setPriceIn(float priceIn) {
		this.priceIn = priceIn;
	}
	public float getPriceOut() {
		return priceOut;
	}
	public void setPriceOut(float priceOut) {
		this.priceOut = priceOut;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Date getDateIn() {
		return dateIn;
	}
	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}
	public Date getDateOut() {
		return dateOut;
	}
	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	@Override
	public String toString() {
		return "Item [itemCode=" + itemCode + ", itemType=" + itemType
				+ ", brand=" + brand + ", priceIn=" + priceIn + ", priceOut="
				+ priceOut + ", quantity=" + quantity + ", size=" + size
				+ ", dateIn=" + dateIn + ", dateOut=" + dateOut
				+ ", transactionId=" + transactionId + "]";
	}
	
	}
