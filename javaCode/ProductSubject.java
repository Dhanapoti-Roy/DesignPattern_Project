package com.example.demo2;

import java.util.ArrayList;
import java.util.List;

public class ProductSubject {
    private List<ProductObserver> observers = new ArrayList<>();

    public void addObserver(ProductObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ProductObserver observer) {
        observers.remove(observer);
    }

    public void checkProduct(Product product) {
        if (product.isExpired()) {
            for (ProductObserver observer : observers) {
                observer.onProductExpired(product);
            }
        }
        if (product.getQuantity() == 0) {
            for (ProductObserver observer : observers) {
                observer.onProductOutOfStock(product);
            }
        }
    }
}