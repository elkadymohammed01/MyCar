package com.great.mycar.model;

public class Comments {
    private String id,name,email,details,love;

    public Comments(String id, String name, String email, String details, String love ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.details = details;
        this.love = love;
    }

    public Comments() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

}
