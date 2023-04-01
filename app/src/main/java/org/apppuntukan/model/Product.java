package org.apppuntukan.model;

import androidx.annotation.NonNull;
import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;
import java.io.Serializable;
import java.util.Objects;

@Indices({
        @Index(value = "productName", type = IndexType.NonUnique),
        @Index(value = "price", type = IndexType.NonUnique),
        @Index(value = "tax", type = IndexType.NonUnique),
        @Index(value = "stock", type = IndexType.NonUnique),
        @Index(value = "quantity", type = IndexType.NonUnique)
})
public class Product implements Serializable {

    @Id
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
        this.stock = 10;
        this.tax = 0.5;
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

    @NonNull
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", tax=" + tax +
                ", stock=" + stock +
                ", quantity=" + quantity +
                '}';
    }
}

