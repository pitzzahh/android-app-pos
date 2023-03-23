package org.apppuntukan.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import org.apppuntukan.R;
import org.apppuntukan.model.ProductService;
import org.apppuntukan.views.adapter.MainCustomerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        MainCustomerAdapter mainCustomerAdapter = new MainCustomerAdapter(ProductService.getInstance().getProducts(), this);

        recyclerView.setAdapter(mainCustomerAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        Button button = findViewById(R.id.btn_open_cart);

        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        });
    }
}
