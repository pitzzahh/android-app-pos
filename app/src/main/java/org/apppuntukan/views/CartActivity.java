package org.apppuntukan.views;

import android.os.Bundle;
import org.apppuntukan.R;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import org.apppuntukan.databinding.ActivityCartBinding;
import org.apppuntukan.databinding.CartProductCardBinding;
import org.apppuntukan.model.ProductService;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.apppuntukan.viewmodel.CartActivityViewModel;
import org.apppuntukan.model.abstractions.NoActionBarActivity;
import org.apppuntukan.views.adapter.RecyclerViewAdapter;

public class CartActivity extends NoActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCartBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        binding.setCartProductsViewModel(new ViewModelProvider(this).get(CartActivityViewModel.class));

        setContentView(binding.getRoot());

        TextView price = findViewById(R.id.label_price);
        price.setText(String.format("$%s", ProductService.getInstance().computeTotal()));
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setAdapter(new RecyclerViewAdapter<CartProductCardBinding>(this, R.layout.cart_product_card, ProductService.getInstance().getCartProducts()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}