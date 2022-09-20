package com.example.myapplication.Model;

import java.util.List;

public class Order {
    private String receivename;
    private List<BorrowBook> borrowbook;
    private String days;
    private String hoursreceive;
    private String id;
    private String iduser;
    private String note;
    private String type;

    public Order() {
    }

    public String getReceivename() {
        return receivename;
    }

    public void setReceivename(String receivename) {
        this.receivename = receivename;
    }

    public List<BorrowBook> getBorrowbook() {
        return borrowbook;
    }

    public void setBorrowbook(List<BorrowBook> borrowbook) {
        this.borrowbook = borrowbook;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getHoursreceive() {
        return hoursreceive;
    }

    public void setHoursreceive(String hoursreceive) {
        this.hoursreceive = hoursreceive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
