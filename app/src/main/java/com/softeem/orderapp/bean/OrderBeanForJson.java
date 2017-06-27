package com.softeem.orderapp.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aliez on 2017/6/26.
 */

public class OrderBeanForJson {
    public int orderId;
    public double totalPrice;
    public double cutPrice;
    public int tableNumber = 1;  // 1
    public String orderTime;
    public List<OrderItemBeanForJson> orderItemBeanForJsonList = new ArrayList<OrderItemBeanForJson>();
    public OrderBeanForJson(OrderBean orderBean) {
        this.orderId = orderBean.getOrderId();
        this.totalPrice = orderBean.getTotalPrice();
        this.cutPrice = orderBean.getCutPrice();
        this.tableNumber = orderBean.getTableNumber();
        this.orderTime = orderBean.getOrderTime();
        for(OrderItemBean oib : orderBean.getOrderItemBeanList()) {
            this.orderItemBeanForJsonList.add(new OrderItemBeanForJson(oib));
        }

    }
}
