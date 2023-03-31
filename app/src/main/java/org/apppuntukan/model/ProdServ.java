package org.apppuntukan.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public class ProdServ {

    private static ProdServ instance;

    private static List<Product> products;
    private final List<Product> cartProducts;

    public ProdServ(List<Product> products) {
        ProdServ.products = products;
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
        ProdServ.instance().getProducts().add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProductToCart(Product product) {
        product.setQuantity(1);
        Objects.requireNonNull(product, "Product Cannot be null");
        ProdServ.instance().getCartProducts().add(product);
    }

    public List<Product> getCartProducts() {
        return cartProducts;
    }

    public Product removeProductFromCart(Product product) {
        Objects.requireNonNull(product, "Product Cannot be null");
        int size = ProdServ.instance()
                .getCartProducts()
                .size();
        for (int i = 0; i < size; i++) {
            if (ProdServ.instance().getCartProducts().get(i).getId() == product.getId()) {
                return ProdServ.instance().getCartProducts().remove(i);
            }
        }
        return null; // code smell
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public List<Product> searchProduct(String term) {
        for (Product product : ProdServ.instance().getProducts()) {
            if (product.getProductName().equalsIgnoreCase(term) || product.getProductName().contains(term)) {
                ProdServ.instance().getProducts().add(product);
            }
        }
        return ProdServ.instance().getProducts();
    }

    public String computeTotal() {
        double price = 0.0;
        for (int i = 0; i < ProdServ.instance().getCartProducts().size(); i++) {
            int quantity = ProdServ.instance().getCartProducts().get(i).getQuantity();
            if (quantity > 1){
                price += ProdServ.instance().getCartProducts().get(i).getPrice() * quantity;
            }
            else {
                price += ProdServ.instance().getCartProducts().get(i).getPrice();
            }

        }
        return String.valueOf(price);
    }

    public String getProductQuantityInCart(Product product) {
        int count = 1;
        for (int i = 0; i < ProdServ.instance().getCartProducts().size(); i++) {
            if (ProdServ.instance().getCartProducts().get(i).getId() == product.getId()) {
                count += ProdServ.instance().getCartProducts().get(i).getQuantity();
                break;
            }
        }
        return String.valueOf(count);
    }

    public void addProductQuantity(Product product) {
        product.setQuantity(product.getQuantity() + 1);
        ProdServ.instance().getCartProducts().get(getI(product)).setQuantity(product.getQuantity());
    }

    private int getI(Product product) {
        for (int i = 0; i < cartProducts.size(); i++) {
            if (ProdServ.instance().getCartProducts().get(i).getId() == product.getId()) {
                return i;
            }
        }
        return 0;
    }

    public boolean removeProductQuantityOrRemove(Product product) {
        product.setQuantity(product.getQuantity() - 1);
        if (product.getQuantity() <= 0) return true;
        ProdServ.instance().getCartProducts().get(getI(product)).setQuantity(product.getQuantity());
        return false;
    }

    public static synchronized ProdServ instance() {
        if (instance == null) {
            List<Product> copy = new ArrayList<>();
            Random random = new Random();
            for (int i = 1; i <= 10; i++) {
                copy.add(new Product(i, String.format("Example Product %s", i), Double.parseDouble(String.valueOf(random.nextInt(1000) + 1))));
            }
            instance = new ProdServ(copy);
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
        List<Product> cartProducts = ProdServ.instance().getCartProducts();
        for (int i = 0; i < cartProducts.size(); i++) {
            Product cartProduct = cartProducts.get(i);
            if (cartProduct.getId() == product.getId()) {
                return true;
            }
        }
        return false;
    }

    public int getProductIndexFromCart(Product product) {
        boolean contains = ProdServ.instance().getCartProducts().contains(product);
        if (contains) {
            return ProdServ.instance().getCartProducts().indexOf(product);
        }
        return -1;
    }
}
