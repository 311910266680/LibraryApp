package com.example.myapplication.Model;

public class BorrowBook {
    private String img;
    private String title;
    private int count;
    private String datestart;
    private String expirationdate;
    private int price;

    public BorrowBook() {
    }

    public BorrowBook(String img, String title, int count, String datestart, String expirationdate, int price) {
        this.img = img;
        this.title = title;
        this.count = count;
        this.datestart = datestart;
        this.expirationdate = expirationdate;
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDatestart() {
        return datestart;
    }

    public void setDatestart(String datestart) {
        this.datestart = datestart;
    }

    public String getExpirationdate() {
        return expirationdate;
    }

    public void setExpirationdate(String expirationdate) {
        this.expirationdate = expirationdate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
