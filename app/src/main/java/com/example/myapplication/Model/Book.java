package com.example.myapplication.Model;

public class Book {
    private int id;
    private String image,title,type;
    private int price;
    public Book() {
    }

    public Book(int id, String image, String title, String type, int price) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
