package com.beadando.javafxbeadando;

public class Megye {
    private int id;          // County ID
    private String name;     // County name
    private String region;   // Region name

    public Megye() {
    }

    // Constructor
    public Megye(int id, String name, String region) {
        this.id = id;
        this.name = name;
        this.region = region;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Megye{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
