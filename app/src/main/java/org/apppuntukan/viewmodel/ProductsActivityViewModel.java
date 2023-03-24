package org.apppuntukan.viewmodel;

import android.view.View;
import android.content.Intent;
import org.apppuntukan.views.CartActivity;
import androidx.databinding.ObservableField;

public class ProductsActivityViewModel extends ViewModelBase {

    public ObservableField<String> cartCount;

    public void openCart(View view){
        view.getContext()
                .startActivity(new Intent(view.getContext(), CartActivity.class));
    }
}
