package org.apppuntukan.views;

import org.apppuntukan.R;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;
import org.apppuntukan.databinding.ActivityMainBinding;
import org.apppuntukan.databinding.ProductCardBinding;
import org.apppuntukan.model.ProductService;
import org.apppuntukan.views.adapter.RecyclerViewAdapter;
import org.apppuntukan.viewmodel.ProductsActivityViewModel;
import org.apppuntukan.model.abstractions.NoActionBarActivity;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;

public class MainActivity extends NoActionBarActivity {

    static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setProductsViewModel(new ViewModelProvider(this).get(ProductsActivityViewModel.class));

        setContentView(binding.getRoot());
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new RecyclerViewAdapter<ProductCardBinding>(this, R.layout.product_card, ProductService.getInstance().getProducts()));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    public static MainActivity getInstance() {
        if (mainActivity == null) {
            return new MainActivity();
        }
        return mainActivity;
    }

}
