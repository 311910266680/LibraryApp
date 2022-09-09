package com.example.myapplication.Model;

public class BorrowBook {
    private int id;
    private String bookid;
    private Book book;

//    private String img;
//    private String title;
    private int count;
    private String datestart;
    private String expirationdate;
//    private int price;
    private int pricetotal;
    private long duration;

    public BorrowBook() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
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

    public int getPricetotal() {
        return pricetotal;
    }

    public void setPricetotal(int pricetotal) {
        this.pricetotal = pricetotal;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
