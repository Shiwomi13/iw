package com.example.fdelivery.Class;

import java.util.List;

public class OrderFirebase {
    private String phone,name,adress,total,status;
    private List<Order> foods;

    public OrderFirebase(){
    }

    public OrderFirebase(String phone, String name, String adress, String total,String status, List<Order> foods) {
        this.phone = phone;
        this.name = name;
        this.adress = adress;
        this.total = total;
        this.foods = foods;
        this.status ="0";
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
