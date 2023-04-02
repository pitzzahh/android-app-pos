package org.apppuntukan.viewmodel;

import android.view.View;
import android.content.Intent;
import org.apppuntukan.views.CartActivity;
import androidx.lifecycle.MutableLiveData;

public class ProductsActivityViewModel extends ViewModelBase {

    static ProductsActivityViewModel model;

    public MutableLiveData<Integer> cartCount = new MutableLiveData<>();

    public void openCart(View view){
        view.getContext()
                .startActivity(new Intent(view.getContext(), CartActivity.class));
    }

    public static synchronized ProductsActivityViewModel instance() {
        if (model == null) {
            model = new ProductsActivityViewModel();
        }
        return model;
    }
}
