package com.example.fdelivery.Class;

public class Desc {
    private String name,desc,gram,image,jir,ugl,kkal,bel;

    public Desc(){}

    public Desc(String name, String desc, String gram, String image, String jir, String ugl, String kkal, String bel) {
        this.name = name;
        this.desc = desc;
        this.gram = gram;
        this.image = image;
        this.jir = jir;
        this.ugl = ugl;
        this.kkal = kkal;
        this.bel = bel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGram() {
        return gram;
    }

    public void setGram(String gram) {
        this.gram = gram;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getJir() {
        return jir;
    }

    public void setJir(String jir) {
        this.jir = jir;
    }

    public String getUgl() {
        return ugl;
    }

    public void setUgl(String ugl) {
        this.ugl = ugl;
    }

    public String getKkal() {
        return kkal;
    }

    public void setKkal(String kkal) {
        this.kkal = kkal;
    }

    public String getBel() {
        return bel;
    }

    public void setBel(String bel) {
        this.bel = bel;
    }
}
