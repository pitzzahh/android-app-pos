package org.apppuntukan.model;

import android.util.Log;

import org.apppuntukan.views.MainActivity;
import org.dizitart.no2.WriteResult;
import org.dizitart.no2.objects.ObjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ProdServ {

    private static ProdServ instance;
    private final List<Product> filteredSearchedProducts;
    private static ObjectRepository<Product> productRepository;
    private static ObjectRepository<Product> cartProductsRepository;

    public ProdServ() {
        filteredSearchedProducts = new ArrayList<>();
        productRepository = MainActivity.getDb().getRepository("products", Product.class);
        cartProductsRepository = MainActivity.getDb().getRepository("cartProducts", Product.class);
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
        ProdServ.instance().getProductRepository().insert(product);
    }

    public void addProductToCart(Product product) {
        product.setQuantity(1);
        Objects.requireNonNull(product, "Product Cannot be null");
        ProdServ.instance().getCartProducts().add(product);
        ProdServ.instance().getCartProductsRepository().insert(product);
    }

    public List<Product> getProducts() {
        return ProdServ.instance().getProductRepository().find().toList();
    }

    public List<Product> getCartProducts() {
        return ProdServ.instance().getCartProductsRepository().find().toList();
    }

    public Product removeProductFromCart(Product product) {
        Objects.requireNonNull(product, "Product Cannot be null");
        Product remove = ProdServ.instance().getCartProducts().remove(getI(product));
        WriteResult result = ProdServ.instance().getCartProductsRepository().remove(remove);
        return result.getAffectedCount() == 1 ? remove : null;
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
                filteredSearchedProducts.add(product);
            }
        }
        return filteredSearchedProducts;
    }

    public String computeTotal() {
        double price = 0.0;
        for (int i = 0; i < ProdServ.instance().getCartProducts().size(); i++) {
            int quantity = ProdServ.instance().getCartProducts().get(i).getQuantity();
            if (quantity > 1) {
                price += ProdServ.instance().getCartProducts().get(i).getPrice() * quantity;
            } else {
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
        WriteResult update = ProdServ.instance()
                .getCartProductsRepository()
                .update(product);
        Log.d("update_quantity", String.valueOf(update.getAffectedCount()));
    }

    public int getI(Product product) {
        for (int i = 0; i < ProdServ.instance().getCartProducts().size(); i++) {
            if (ProdServ.instance().getCartProducts().get(i).getId() == product.getId()) {
                return i;
            }
        }
        return 0;
    }

    public boolean updateProductQuantityOrRemoveToCart(Product product) {
        product.setQuantity(product.getQuantity() - 1);
        if (product.getQuantity() <= 0) {
            return true;
        }
        ProdServ.instance().getCartProducts().get(getI(product)).setQuantity(product.getQuantity());
        ProdServ.instance()
                .getProductRepository()
                .update(product);
        return false;
    }

    public static synchronized ProdServ instance() {
        if (instance == null) {
            Random random = new Random();
            instance = new ProdServ();
            if (productRepository.find().toList().isEmpty()) {
                for (int i = 1; i <= 10; i++) {
                    productRepository.insert(new Product(i, String.format("Example Product %s", i), Double.parseDouble(String.valueOf(random.nextInt(1000) + 1))));
                }
            }
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

    public ObjectRepository<Product> getProductRepository() {
        return productRepository;
    }

    public ObjectRepository<Product> getCartProductsRepository() {
        return cartProductsRepository;
    }
}
