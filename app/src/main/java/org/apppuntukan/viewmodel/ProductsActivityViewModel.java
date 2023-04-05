package org.apppuntukan.viewmodel;

import android.content.Intent;
import android.view.View;
import org.apppuntukan.model.ProdServ;
import org.apppuntukan.views.CartActivity;
import androidx.databinding.ObservableField;

public class ProductsActivityViewModel extends ViewModelBase {

    protected static ProductsActivityViewModel model;
    public ObservableField<String> cartCount = new ObservableField<>("0");

    public ProductsActivityViewModel() {
        updateData(new Object[]{ProdServ.instance().getCartProducts().size()});
    }

    public void openCart(View view){
        view.getContext()
                .startActivity(new Intent(view.getContext(), CartActivity.class));
    }

    @Override
    public <T> void updateData(T[] newData) {
        final int MAX_CART_SHOW = 99;
        final int toShow = Math.min(Integer.parseInt(String.valueOf(newData[0])), MAX_CART_SHOW);
        cartCount.set(String.valueOf(toShow));
    }

    public static synchronized ProductsActivityViewModel instance() {
        if (model == null) {
            model = new ProductsActivityViewModel();
        }
        return model;
    }
}
