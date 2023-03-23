package org.apppuntukan.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductService {

    private static ProductService instance;

    private static List<Product> products;
    private final List<Product> cartProducts;

    public ProductService(List<Product> products) {
        ProductService.products = products;
        cartProducts = new ArrayList<>();
    }

    private String searchTerm;

    private double total;
    private double change;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public void addProduct(Product product) {
        Objects.requireNonNull(product, "Product Cannot be null");
        ProductService.getInstance().getProducts().add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProductToCart(Product product) {
        Objects.requireNonNull(product, "Product Cannot be null");
        ProductService.getInstance().getCartProducts().add(product);
    }

    public List<Product> getCartProducts() {
        return cartProducts;
    }

    public boolean removeProductFromCart(Product product) {
        Objects.requireNonNull(product, "Product Cannot be null");
        return ProductService.getInstance().getCartProducts().remove(product);
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public List<Product> searchProduct(String term) {
        for (Product product : ProductService.getInstance().getProducts()) {
            if (product.getProductName().equalsIgnoreCase(term) || product.getProductName().contains(term)) {
                ProductService.getInstance().getProducts().add(product);
            }
        }
        return ProductService.getInstance().getProducts();
    }

    public String computeTotal() {
        double price = 0.0;
        for (int i = 0; i < ProductService.getInstance().getCartProducts().size(); i++) {
            price += ProductService.getInstance().getCartProducts().get(i).getPrice();
        }
        return String.valueOf(price);
    }

    public String getProductQuantityInCart(Product product) {
        int count = 0;
        for (int i = 0; i < ProductService.getInstance().getCartProducts().size(); i++) {
            if (ProductService.getInstance().getCartProducts().get(i).equals(product)) count++;
        }
        return String.valueOf(count);
    }

    public String getTotalCartPrice() {
        double price = 0.0;
        for (int i = 0; i < ProductService.getInstance().getCartProducts().size(); i++) {
            if (ProductService.getInstance().getCartProducts().get(i).getQuantity() > 1){
                price += ProductService.getInstance().getCartProducts().get(i).getPrice() * ProductService.getInstance().getCartProducts().get(i).getQuantity();
            }
            else {
                price += ProductService.getInstance().getCartProducts().get(i).getPrice();
            }
        }
        return String.valueOf(price);
    }

    public void addProductQuantity(Product product) {
        product.setQuantity(product.getQuantity() + 1);
        int i = ProductService.getInstance().getCartProducts().indexOf(product);
        ProductService.getInstance().getCartProducts().get(i).setQuantity(product.getQuantity());
    }

    public static synchronized ProductService getInstance() {
        if (instance == null) {
            List<Product> copy = new ArrayList<>();
            copy.add(new Product(1, "Name 1", 123));
            copy.add(new Product(2, "Name 2", 123));
            copy.add(new Product(3, "Name 3", 123));
            copy.add(new Product(4, "Name 4", 123));
            instance = new ProductService(copy);
        }
        return instance;
    }
}
