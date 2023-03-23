package org.apppuntukan.views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;

import org.apppuntukan.R;
import org.apppuntukan.model.Product;
import org.apppuntukan.model.ProductService;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Button button = findViewById(R.id.btn_open_products);

        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        TextView price = findViewById(R.id.lbl_price);
        price.setText(String.format("$%s", ProductService.getInstance().getTotal()));
        TextView change = findViewById(R.id.lbl_change);
        change.setText(String.format("$%s", ProductService.getInstance().getChange()));

        for (int i = 0; i < ProductService.getInstance().getCartProducts().size(); i++) {
            Product product = ProductService.getInstance().getCartProducts().get(i);
            if (product.getStock() > 0) product.setStock(product.getStock() - 1);
            if (ProductService.getInstance().getProducts().get(i).getId() == product.getId()) {
                ProductService.getInstance().getProducts().get(i).setStock(product.getStock());
                Log.w(String.format("product: %d", product.getId()), String.valueOf(ProductService.getInstance().getProducts().get(i).getStock()));
            }
        }
        ProductService.getInstance().getCartProducts().clear();
    }
}