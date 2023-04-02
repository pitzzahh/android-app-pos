package org.apppuntukan.viewmodel;

import android.view.View;
import android.content.Intent;
import org.apppuntukan.model.ProdServ;
import androidx.lifecycle.MutableLiveData;
import org.apppuntukan.views.CheckoutActivity;

public class CartActivityViewModel extends ViewModelBase {

    private static CartActivityViewModel _instance;
    public MutableLiveData<String> Total = new MutableLiveData<>();
    public MutableLiveData<Boolean> CheckoutEnabled = new MutableLiveData<>();
    public MutableLiveData<String> totalProducts = new MutableLiveData<>();

    public CartActivityViewModel() {
        updateData(new Object[]{
                ProdServ.instance().getCartProducts().size(),
                ProdServ.instance().computeTotal()
        });
    }

    public void openCheckout(View view) {
        view.getContext().startActivity(new Intent(view.getContext(), CheckoutActivity.class));

    }

    @Override
    public <T> void updateData(T[] newData) {
        totalProducts.setValue(String.valueOf(newData[0]));
        Total.setValue(String.valueOf(newData[1]));
        CheckoutEnabled.setValue(Integer.parseInt(String.valueOf(newData[0])) > 0);
        CheckoutActivityViewModel.instance().updateData(new Object[]{newData[1]});
    }

    public static synchronized CartActivityViewModel getInstance() {
        if (_instance == null)
            _instance = new CartActivityViewModel();
        return _instance;
    }
}
