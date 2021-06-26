package com.great.mycar.model;

public class ser  {
    private String Title,id ,price ,details;

    public ser(String title, String id, String price, String details) {
        Title = title;
        this.id = id;
        this.price = price;
        this.details = details;
    }

    public ser() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
