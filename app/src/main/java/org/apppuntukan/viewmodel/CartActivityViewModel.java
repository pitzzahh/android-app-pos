package org.apppuntukan.viewmodel;

import android.view.View;
import android.content.Intent;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.apppuntukan.model.ProdServ;
import org.apppuntukan.views.CheckoutActivity;

public class CartActivityViewModel extends ViewModelBase {

    private static CartActivityViewModel _instance;

    public MutableLiveData<String> Total = new MutableLiveData<>();
    public MutableLiveData<Boolean> CheckoutEnabled = new MutableLiveData<>();

    public CartActivityViewModel() {
        updateCartTotal();
    }
    public void openCheckout(View view) {
        if(ProdServ.instance().getCartProducts().size() > 0)
            view.getContext().startActivity(new Intent(view.getContext(), CheckoutActivity.class));
    }

    public void updateCartTotal(){
        CheckoutEnabled.setValue(ProdServ.instance().getCartProducts().size() > 0);
        Total.setValue(String.format("Total: $%s", ProdServ.instance().computeTotal()));
    }

    public static synchronized CartActivityViewModel getInstance()
    {
        if (_instance == null)
            _instance = new CartActivityViewModel();
        return _instance;
    }
}
