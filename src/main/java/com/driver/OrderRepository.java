package com.driver;


import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

    HashMap<String,Order> orderDb = new HashMap<>();
    HashMap<String,DeliveryPartner> partnerDb= new HashMap<>();
    HashMap<String,List<String>> orderpartner= new HashMap<>();
    HashMap<String,Order> unassigned= new HashMap<>();

    public OrderRepository() {
    }

    public void addOrder(Order order) {
        orderDb.put(order.getId(),order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner partner = new DeliveryPartner(partnerId);
        partnerDb.put(partnerId,partner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        if(orderpartner.get(partnerId)==null)
            orderpartner.put(partnerId,new ArrayList<>());
        orderpartner.get(partnerId).add(orderId);
    }

    public Order getOrderById(String orderId) {
      //  System.out.println(orderDb.size() +" "+orderDb.get(orderId).toString());
        return orderDb.getOrDefault(orderId,null);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return partnerDb.getOrDefault(partnerId,null);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return orderpartner.get(partnerId)==null?0:orderpartner.get(partnerId).size();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderpartner.getOrDefault(partnerId,new ArrayList<>());
    }

    public List<String> getAllOrders() {
        List<String> list = new ArrayList<>();
        for(String orderId:orderDb.keySet())
            list.add(orderId);
        return list;
    }

    public Integer getCountOfUnassignedOrders() {
        return unassigned.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        Integer ans = 0;
        int timeGiven = Integer.valueOf(time.substring(0,2))*60+Integer.valueOf(4);
        List<String> list = orderpartner.get(partnerId);
        for(int i=0;i< list.size();i++){
            Order order = orderDb.get(list.get(i));
            if(order.getDeliveryTime()>timeGiven)
                ans++;
        }
        return ans;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        List<String> list = orderpartner.get(partnerId);
        int max = 0;
        for(int i=0;i< list.size();i++){
            Order order = orderDb.get(list.get(i));
            max = Math.max(max,order.getDeliveryTime());
        }
        String res = "";
        res+=max/60+":"+max%60;
        return res;
    }

    public void deletePartnerById(String partnerId) {
        List<String> list = orderpartner.getOrDefault(partnerId,new ArrayList<>());
        for(String s:list){
            unassigned.put(s,orderDb.get(s));
        }
        orderpartner.remove(partnerId);
    }

    public void deleteOrderById(String orderId) {
        for(String s:orderpartner.keySet()){
            List<String> orderlist = orderpartner.get(s);
            if(orderlist.contains(orderId)){
                orderlist.remove(orderId);
                break;
            }
        }
        orderDb.remove(orderId);
    }
}
