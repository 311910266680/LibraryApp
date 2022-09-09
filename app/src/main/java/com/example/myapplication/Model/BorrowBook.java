package com.example.myapplication.Model;

public class BorrowBook {
    private int id;
    private String img;
    private String title;
    private int count;
    private String datestart;
    private String expirationdate;
    private int price;
    private long duration;

    public BorrowBook() {
    }

    public BorrowBook(int id,String img, String title, int count, String datestart, String expirationdate, int price,long duration) {
        this.id = id;
        this.img = img;
        this.title = title;
        this.count = count;
        this.datestart = datestart;
        this.expirationdate = expirationdate;
        this.price = price;
        this.duration = duration;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
