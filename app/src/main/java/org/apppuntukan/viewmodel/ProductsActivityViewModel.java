package org.apppuntukan.viewmodel;

import android.view.View;
import android.content.Intent;
import org.apppuntukan.views.CartActivity;
import androidx.databinding.ObservableField;

public class ProductsActivityViewModel extends ViewModelBase {

    static ProductsActivityViewModel model;

    public ObservableField<Integer> cartCount = new ObservableField<>();

    public void openCart(View view){
        view.getContext()
                .startActivity(new Intent(view.getContext(), CartActivity.class));
    }

    public static ProductsActivityViewModel instance() {
        if (model == null) {
            return new ProductsActivityViewModel();
        }
        return model;
    }
}
