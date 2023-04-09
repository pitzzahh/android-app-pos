package org.apppuntukan.views;

import org.apppuntukan.R;
import android.os.Bundle;
import org.dizitart.no2.Nitrite;
import org.apppuntukan.model.ProdServ;
import android.annotation.SuppressLint;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;
import org.apppuntukan.databinding.ProductCardBinding;
import com.google.android.flexbox.FlexboxLayoutManager;
import org.apppuntukan.databinding.ActivityMainBinding;
import org.apppuntukan.views.adapter.RecyclerViewAdapter;
import org.apppuntukan.viewmodel.ProductsActivityViewModel;
import org.apppuntukan.model.abstractions.NoActionBarActivity;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;

public class MainActivity extends NoActionBarActivity {

    private RecyclerViewAdapter<ProductCardBinding> adapter;
    private static Nitrite db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);

        try {
            /*
                Need i try kasi pag nag rotate maga create ng new instance ng db. maga throw ng error
             */
            db = Nitrite.builder()
                    .compressed()
                    .filePath(getFilesDir().getPath() + "/test.db")
                    .openOrCreate("user", "password");
        } catch (Exception ignored) {
            db = getDb(); // if so, just get the current instance.
        }

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setProductsViewModel(ProductsActivityViewModel.instance());
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());

        // should be here?
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            @SuppressLint("NotifyDataSetChanged")
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new RecyclerViewAdapter<>(this, R.layout.product_card, ProdServ.instance().getProducts());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new FlexboxLayoutManager(this));

        Runtime.getRuntime()
                .addShutdownHook(new Thread(() -> getDb().close()));
    }

    public static synchronized Nitrite getDb() {
        return db;
    }


}
