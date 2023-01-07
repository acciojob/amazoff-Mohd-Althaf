package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        int h = Integer.valueOf(deliveryTime.substring(0,2));
        int m = Integer.valueOf(deliveryTime.substring(3));
        this.deliveryTime=h*60+m;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
