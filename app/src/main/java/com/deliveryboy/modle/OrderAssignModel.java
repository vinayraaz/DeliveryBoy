package com.deliveryboy.modle;

public class OrderAssignModel {
    private String orderId,orderName,orderDetails,orderAdd;
    private String deliveryBid,deliveryBName,deliveryBMobile;
    private Integer orderStatus;

    public OrderAssignModel() {
    }

    public OrderAssignModel(String orderId, String orderName, String orderDetails, String orderAdd, String deliveryBid, String deliveryBName, String deliveryBMobile, Integer orderStatus) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.orderDetails = orderDetails;
        this.orderAdd = orderAdd;
        this.deliveryBid = deliveryBid;
        this.deliveryBName = deliveryBName;
        this.deliveryBMobile = deliveryBMobile;
        this.orderStatus = orderStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getOrderAdd() {
        return orderAdd;
    }

    public void setOrderAdd(String orderAdd) {
        this.orderAdd = orderAdd;
    }

    public String getDeliveryBid() {
        return deliveryBid;
    }

    public void setDeliveryBid(String deliveryBid) {
        this.deliveryBid = deliveryBid;
    }

    public String getDeliveryBName() {
        return deliveryBName;
    }

    public void setDeliveryBName(String deliveryBName) {
        this.deliveryBName = deliveryBName;
    }

    public String getDeliveryBMobile() {
        return deliveryBMobile;
    }

    public void setDeliveryBMobile(String deliveryBMobile) {
        this.deliveryBMobile = deliveryBMobile;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
