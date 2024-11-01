package com.beadando.javafxbeadando;

public class Torony {
    private int id;
    private int darab;
    private int teljesitmeny;
    private int kezdev;
    private int helyszinid;

    // Constructor
    public Torony(int id, int darab, int teljesitmeny, int kezdev, int helyszinid) {
        this.id = id;
        this.darab = darab;
        this.teljesitmeny = teljesitmeny;
        this.kezdev = kezdev;
        this.helyszinid = helyszinid;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getDarab() {
        return darab;
    }

    public int getTeljesitmeny() {
        return teljesitmeny;
    }

    public int getKezdev() {
        return kezdev;
    }

    public int getHelyszinid() {
        return helyszinid;
    }
}
