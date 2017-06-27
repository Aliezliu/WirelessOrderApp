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
    public int tableId = 1;  // 1
    public String orderTime = "201706271643";
    public List<OrderItemBeanForJson> orderItemBeanForJsonList = new ArrayList<OrderItemBeanForJson>();
    public OrderBeanForJson(OrderBean orderBean) {
        this.orderId = orderBean.getOrderId();
        this.totalPrice = orderBean.getTotalPrice();
        this.cutPrice = orderBean.getCutPrice();
        this.tableId = orderBean.getTableNumber();
        this.orderTime = orderBean.getOrderTime();
        for(OrderItemBean oib : orderBean.getOrderItemBeanList()) {
            this.orderItemBeanForJsonList.add(new OrderItemBeanForJson(oib));
        }

    }
}
