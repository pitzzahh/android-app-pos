package org.apppuntukan.viewmodel;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import org.apppuntukan.model.ProdServ;
import org.apppuntukan.views.CartActivity;

import java.util.Locale;

public class ProductsActivityViewModel extends ViewModelBase {

    static ProductsActivityViewModel model;

    public MutableLiveData<Boolean> CartEnabled = new MutableLiveData<>();
    public MutableLiveData<String> cartCount = new MutableLiveData<>();

    public ProductsActivityViewModel() {
        updateCartProductCount();
    }

    public void openCart(View view){
        view.getContext()
                .startActivity(new Intent(view.getContext(), CartActivity.class));
    }

    public void updateCartProductCount(){
        CartEnabled.setValue(ProdServ.instance().getCartProducts().size() > 0);
        cartCount.setValue(String.format(Locale.getDefault(),"Cart(%d)", ProdServ.instance().getCartProducts().size()));
    }

    public static synchronized ProductsActivityViewModel instance() {
        if (model == null) {
            model = new ProductsActivityViewModel();
        }
        return model;
    }
}
