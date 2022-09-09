package com.example.myapplication.Model;

public class Discount {
    private String code;
    private int id;
    private int percent;

    public Discount() {
    }

    public Discount(String code, int id, int percent) {
        this.code = code;
        this.id = id;
        this.percent = percent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
