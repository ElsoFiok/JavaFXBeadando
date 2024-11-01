package com.beadando.javafxbeadando;

public class Helyszin {
    private int id;         // Location ID
    private String name;    // Location name
    private int megyeId;    // County ID (foreign key)

    public Helyszin() {
    }

    // Constructor
    public Helyszin(int id, String name, int megyeId) {
        this.id = id;
        this.name = name;
        this.megyeId = megyeId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMegyeId() {
        return megyeId;
    }

    public void setMegyeId(int megyeId) {
        this.megyeId = megyeId;
    }

    @Override
    public String toString() {
        return "Helyszin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", megyeId=" + megyeId +
                '}';
    }
}
