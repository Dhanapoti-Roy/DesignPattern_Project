package com.example.demo2;

import java.time.LocalDate;

public class Product {
    private int id;
    private String name, type, color;
    private final int quantity;
    private double price;
    private LocalDate expiryDate ;

    // Add expiryDate to constructor and getter
    public Product(int id, String name, String type, String color, int quantity, double price, LocalDate expiry) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.color = color;
        this.quantity = quantity;
        this.price = price;
        this.expiryDate = expiry;
    }

    public int getQuantity() { return quantity; }
    public LocalDate getExpiryDate() { return expiryDate; }

    public boolean isExpired() {
        return expiryDate != null && expiryDate.isBefore(LocalDate.now());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}