package org.apppuntukan.viewmodel;

import java.util.Locale;
import android.view.View;
import android.content.Intent;
import org.apppuntukan.model.ProdServ;
import androidx.lifecycle.MutableLiveData;
import org.apppuntukan.views.CheckoutActivity;

public class CartActivityViewModel extends ViewModelBase {

    private static CartActivityViewModel _instance;

    public MutableLiveData<String> Total = new MutableLiveData<>();

    public MutableLiveData<String> totalProducts = new MutableLiveData<>();

    public CartActivityViewModel() {
        updateCartTotal();
        updateCartProductCount();
    }
    public void openCheckout(View view) {
        view.getContext()
                .startActivity(new Intent(view.getContext(), CheckoutActivity.class));
    }

    public void updateCartProductCount() {
        totalProducts.setValue(String.format(Locale.getDefault(),"Cart(%d)", ProdServ.instance().getCartProducts().size()));
    }

    public void updateCartTotal(){
        Total.setValue(String.format("Total: ₱%s", ProdServ.instance().computeTotal()));
    }

    public static synchronized CartActivityViewModel getInstance()
    {
        if (_instance == null)
            _instance = new CartActivityViewModel();
        return _instance;
    }
}
