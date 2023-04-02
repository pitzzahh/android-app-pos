package org.apppuntukan.viewmodel;

import java.util.Locale;
import android.view.View;
import android.content.Intent;
import org.apppuntukan.model.ProdServ;
import org.apppuntukan.views.CartActivity;
import androidx.lifecycle.MutableLiveData;

public class ProductsActivityViewModel extends ViewModelBase {

    static ProductsActivityViewModel model;

    public MutableLiveData<String> cartCount = new MutableLiveData<>();

    public ProductsActivityViewModel() {
        updateCartProductCount();
    }

    public void openCart(View view){
        view.getContext()
                .startActivity(new Intent(view.getContext(), CartActivity.class));
    }

    public void updateCartProductCount(){
        cartCount.setValue(String.format(Locale.getDefault(),"Cart(%d)", ProdServ.instance().getCartProducts().size()));
    }

    public static ProductsActivityViewModel instance() {
        if (model == null) {
            return new ProductsActivityViewModel();
        }
        return model;
    }
}
