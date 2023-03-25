package org.apppuntukan.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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
            int quantity = ProductService.getInstance().getCartProducts().get(i).getQuantity();
            if (quantity > 1){
                price += ProductService.getInstance().getCartProducts().get(i).getPrice() * quantity;
            }
            else {
                price += ProductService.getInstance().getCartProducts().get(i).getPrice();
            }

        }
        return String.valueOf(price);
    }

    public String getProductQuantityInCart(Product product) {
        int count = 1;
        for (int i = 0; i < ProductService.getInstance().getCartProducts().size(); i++) {
            if (ProductService.getInstance().getCartProducts().get(i).getId() == product.getId()) {
                count += ProductService.getInstance().getCartProducts().get(i).getQuantity();
                break;
            }
        }
        return String.valueOf(count);
    }

    public void addProductQuantity(Product product) {
        product.setQuantity(product.getQuantity() + 1);
        int i = ProductService.getInstance().getCartProducts().indexOf(product);
        ProductService.getInstance().getCartProducts().get(i).setQuantity(product.getQuantity());
    }

    public static synchronized ProductService getInstance() {
        if (instance == null) {
            List<Product> copy = new ArrayList<>();
            Random random = new Random();
            for (int i = 1; i <= 10; i++) {
                copy.add(new Product(i, String.format("Example Product %s", i), Double.parseDouble(String.valueOf(random.nextInt(1000) + 1))));
            }
            instance = new ProductService(copy);
        }
        return instance;
    }

    public void executeIfProductExists(List<Product> products, Product product, Runnable runnable) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == product.getId()) {
                runnable.run();
                break;
            }
        }
    }

    public boolean isAlreadyInCart(Product product) {
        List<Product> cartProducts = ProductService.getInstance().getCartProducts();
        for (int i = 0; i < cartProducts.size(); i++) {
            Product cartProduct = cartProducts.get(i);
            if (cartProduct.getId() == product.getId()) {
                return true;
            }
        }
        return false;
    }

    public int getProductIndexFromCart(Product product) {
        boolean contains = ProductService.getInstance().getCartProducts().contains(product);
        if (contains) {
            return ProductService.getInstance().getCartProducts().indexOf(product);
        }
        return -1;
    }
}
