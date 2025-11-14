package com.example.demo2;

public interface ProductObserver {
    void onProductExpired(Product product);

    void onProductOutOfStock(Product product);


}
