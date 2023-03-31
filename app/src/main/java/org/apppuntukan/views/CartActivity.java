package org.apppuntukan.views;

import android.os.Bundle;
import org.apppuntukan.R;
import androidx.databinding.DataBindingUtil;
import org.apppuntukan.databinding.ActivityCartBinding;
import org.apppuntukan.databinding.CartProductCardBinding;
import org.apppuntukan.model.ProdServ;
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

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        RecyclerViewAdapter<CartProductCardBinding> adapter = new RecyclerViewAdapter<>(this, R.layout.cart_product_card, ProdServ.instance().getCartProducts());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}