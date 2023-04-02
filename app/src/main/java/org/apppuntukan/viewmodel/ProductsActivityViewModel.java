package org.apppuntukan.viewmodel;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import org.apppuntukan.model.ProdServ;
import org.apppuntukan.views.CartActivity;

import java.util.Locale;

public class ProductsActivityViewModel extends ViewModelBase {

    static ProductsActivityViewModel model;
    public MutableLiveData<String> cartCount = new MutableLiveData<>();

    public ProductsActivityViewModel() {
        updateData(new Object[]{ProdServ.instance().getCartProducts().size()});
    }

    public void openCart(View view){
        view.getContext()
                .startActivity(new Intent(view.getContext(), CartActivity.class));
    }

    @Override
    public <T> void updateData(T[] newData) {
        cartCount.setValue(String.format(Locale.getDefault(),"Cart(%d)", Integer.parseInt(String.valueOf(newData[0]))));
    }

    public static synchronized ProductsActivityViewModel instance() {
        if (model == null) {
            model = new ProductsActivityViewModel();
        }
        return model;
    }
}
