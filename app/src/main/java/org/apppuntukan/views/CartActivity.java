package org.apppuntukan.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import org.apppuntukan.R;
import org.apppuntukan.model.ProductService;
import org.apppuntukan.views.adapter.CartProductAdapter;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Button button = findViewById(R.id.btn_open_checkout);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, CheckoutActivity.class);
            startActivity(intent);
        });

        TextView price = findViewById(R.id.label_price);
        price.setText(String.format("$%s", ProductService.getInstance().getTotalCartPrice()));
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        CartProductAdapter cartProductAdapter = new CartProductAdapter();

        recyclerView.setAdapter(cartProductAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }
}