package com.example.fdelivery.Class;

public class Products {
    private String Name,Desc,Price,Image,MenuId,Kal,Gram,Jir,Ugl,Bel;

    public Products(){}

    public Products(String name, String desc, String price, String image, String menuId, String kal, String gram, String jir, String ugl, String bel) {
        Name = name;
        Desc = desc;
        Price = price;
        Image = image;
        MenuId = menuId;
        Kal = kal;
        Gram = gram;
        Jir = jir;
        Ugl = ugl;
        Bel = bel;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public String getKal() {
        return Kal;
    }

    public void setKal(String kal) {
        Kal = kal;
    }

    public String getGram() {
        return Gram;
    }

    public void setGram(String gram) {
        Gram = gram;
    }

    public String getJir() {
        return Jir;
    }

    public void setJir(String jir) {
        Jir = jir;
    }

    public String getUgl() {
        return Ugl;
    }

    public void setUgl(String ugl) {
        Ugl = ugl;
    }

    public String getBel() {
        return Bel;
    }

    public void setBel(String bel) {
        Bel = bel;
    }
}
