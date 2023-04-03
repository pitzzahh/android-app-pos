package org.apppuntukan.viewmodel;

import android.view.View;
import java.util.Objects;
import android.content.Intent;
import org.apppuntukan.model.ProdServ;
import androidx.lifecycle.MutableLiveData;
import org.apppuntukan.views.MainActivity;

public class ConfirmationActivityViewModel extends ViewModelBase {

    private static ConfirmationActivityViewModel deez;

    public final MutableLiveData<String> payable = new MutableLiveData<>();
    public final MutableLiveData<String> change = new MutableLiveData<>();

    public ConfirmationActivityViewModel() {
        updateData(new Object[]{
                ProdServ.instance().computeTotal(),
                Double.parseDouble(ProdServ.instance().computeTotal()) - Double.parseDouble(Objects.requireNonNull(CheckoutActivityViewModel.instance().amount.getValue()))
        });
    }

    public void orderAgain(View view) {
        for (int i = 0; i < ProdServ.instance().getCartProducts().size(); i++) {
            ProdServ.instance()
                    .getCartProductsRepository()
                    .remove(ProdServ.instance().getCartProducts().get(i));
        }
        ProdServ.instance()
                .getCartProducts()
                .clear();
        view.getContext()
                .startActivity(new Intent(view.getContext(), MainActivity.class));
    }

    @Override
    public <T> void updateData(T[] newData) {
        payable.setValue(String.valueOf(newData[0]));
        change.setValue(String.valueOf(newData[1]));
    }

    public static synchronized ConfirmationActivityViewModel instance() {
        if (deez == null) {
            deez = new ConfirmationActivityViewModel();
        }
        return deez;
    }
}
