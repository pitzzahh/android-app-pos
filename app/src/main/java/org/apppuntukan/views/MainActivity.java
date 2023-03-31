package org.apppuntukan.views;

import org.apppuntukan.R;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import org.apppuntukan.model.ProdServ;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;
import org.apppuntukan.databinding.ActivityMainBinding;
import org.apppuntukan.databinding.ProductCardBinding;
import com.google.android.flexbox.FlexboxLayoutManager;
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
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter<ProductCardBinding> adapter = new RecyclerViewAdapter<>(this, R.layout.product_card, ProdServ.instance().getProducts());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new FlexboxLayoutManager(this));
    }

    public static MainActivity getInstance() {
        if (mainActivity == null) {
            return new MainActivity();
        }
        return mainActivity;
    }

}
