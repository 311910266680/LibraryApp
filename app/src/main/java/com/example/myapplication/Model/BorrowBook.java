package com.example.myapplication.Model;

public class BorrowBook {
    private String idbookborrow;
    private int bookid;
    private Book book;
    private int count;
    private String datestart;
    private String expirationdate;
    private int pricetotal;
    private long duration;

    public BorrowBook(String idbookborrow, int bookid, Book book, int count, String datestart, String expirationdate, int pricetotal, long duration) {
        this.idbookborrow = idbookborrow;
        this.book = book;
        this.bookid = bookid;
        this.count = count;
        this.datestart = datestart;
        this.expirationdate = expirationdate;
        this.pricetotal = pricetotal;
        this.duration = duration;
    }
    public BorrowBook() {
    }

    public String getIdbookborrow() {
        return idbookborrow;
    }

    public void setIdbookborrow(String idbookborrow) {
        this.idbookborrow = idbookborrow;
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

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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
}
