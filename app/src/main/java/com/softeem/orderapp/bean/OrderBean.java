package com.softeem.orderapp.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 2017/6/22.
 */

public class OrderBean {
    public int orderId;
    public double totalPrice;
    public double cutPrice;
    public int tableNumber = 1;  // 1
    public String orderTime = "201706271646";

    public List<OrderItemBean> orderItemBeanList = new ArrayList<OrderItemBean>();





    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getCutPrice() {
        return cutPrice;
    }

    public void setCutPrice(double cutPrice) {
        this.cutPrice = cutPrice;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public List<OrderItemBean> getOrderItemBeanList() {
        return orderItemBeanList;
    }

    public void setOrderItemBeanList(List<OrderItemBean> orderItemBeanList) {
        this.orderItemBeanList = orderItemBeanList;
    }
}
