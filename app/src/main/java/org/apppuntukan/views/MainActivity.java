package org.apppuntukan.views;

import org.apppuntukan.R;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import org.apppuntukan.model.ProductService;
import org.apppuntukan.viewmodel.ICard;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.GridLayoutManager;
import org.apppuntukan.databinding.ActivityMainBinding;
import org.apppuntukan.views.adapter.MainCustomerAdapter;
import org.apppuntukan.viewmodel.ProductsActivityViewModel;
import org.apppuntukan.model.abstractions.NoActionBarActivity;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;

public class MainActivity extends NoActionBarActivity implements ICard {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setProductsViewModel(new ViewModelProvider(this).get(ProductsActivityViewModel.class));

        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new MainCustomerAdapter(ProductService.getInstance().getProducts(), this, this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    @Override
    public void onClickCard(int position) {
        Snackbar.make(recyclerView, "Card clicked", Snackbar.LENGTH_LONG)
                .show();
    }

}
