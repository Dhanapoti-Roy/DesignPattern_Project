package com.example.demo2;



public class Customer {
    private int id;
    private String name, phone, address, nid;

    public Customer(int id, String name, String phone, String address, String nid) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.nid = nid;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getNid() { return nid; }
}

