package org.apppuntukan.views;

import android.os.Bundle;
import org.apppuntukan.R;
import org.apppuntukan.model.ProdServ;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import org.apppuntukan.databinding.ActivityCartBinding;
import org.apppuntukan.viewmodel.CartActivityViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import org.apppuntukan.views.adapter.RecyclerViewAdapter;
import org.apppuntukan.databinding.CartProductCardBinding;
import org.apppuntukan.model.abstractions.NoActionBarActivity;

public class CartActivity extends NoActionBarActivity {

    static CartActivity cartActivity;
    private RecyclerViewAdapter<CartProductCardBinding> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCartBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        binding.setCartProductsViewModel(CartActivityViewModel.getInstance());
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        adapter = new RecyclerViewAdapter<>(this, R.layout.cart_product_card, ProdServ.instance().getCartProducts());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public static synchronized CartActivity instance() {
        if (cartActivity == null) {
            return new CartActivity();
        }
        return cartActivity;
    }

    public RecyclerViewAdapter<CartProductCardBinding> getAdapter() {
        return adapter;
    }
}