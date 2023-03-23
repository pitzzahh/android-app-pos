package org.apppuntukan.model;

import java.util.Objects;

public class Product {
    private int id;
    private String productName;
    private double price;
    private double tax;
    private int stock;
    private int quantity;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product(int id, String productName, double price) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.stock = 20;
        this.tax = 0.5;
    }

    public Product(String productName, double price, int stock) {
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.tax = 0.5;
    }

    public Product(String productName, double price, int stock, int quantity) {
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.quantity = quantity;
        this.tax = 0.5;
    }

    public Product(int id, String productName, double price, double tax, int stock, int quantity) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.tax = tax;
        this.stock = stock;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && Objects.equals(productName, product.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, price);
    }
}

