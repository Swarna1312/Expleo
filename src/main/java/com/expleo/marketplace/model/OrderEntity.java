package com.expleo.marketplace.model;

import javax.persistence.*;

@Entity
@Table(name="TBL_ORDERS")
public class OrderEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="orderer_name")
    private String ordererName;	
    
    @Column(name="order_type")
    private String orderType;
    
    @Column(name="order_price")
    private Long orderPrice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrdererName() {
		return ordererName;
	}

	public void setOrdererName(String ordererName) {
		this.ordererName = ordererName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Long getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Long orderPrice) {
		this.orderPrice = orderPrice;
	}

	@Override
	public String toString() {
		return "OrderEntity [id=" + id + ", ordererName=" + ordererName + ", orderType=" + orderType + ", orderPrice="
				+ orderPrice + "]";
	}
}
