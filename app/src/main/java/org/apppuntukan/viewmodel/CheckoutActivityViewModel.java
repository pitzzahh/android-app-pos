package org.apppuntukan.viewmodel;

import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import org.apppuntukan.model.ProdServ;
import androidx.lifecycle.MutableLiveData;
import org.apppuntukan.views.ConfirmationActivity;

public class CheckoutActivityViewModel extends ViewModelBase {
    private static CheckoutActivityViewModel deez;
    public final MutableLiveData<String> payable = new MutableLiveData<>();
    public final MutableLiveData<String> amount = new MutableLiveData<>();
    public MutableLiveData<Boolean> checkEnabled = new MutableLiveData<>();

    public CheckoutActivityViewModel() {
        updateData(new Object[]{ProdServ.instance().computeTotal()});
    }

    public void onConfirmCheckout(View view) { // FIXME: 24/03/2023 might be wrong, don't know how to use.
        String payment = amount.getValue() == null ? "" : amount.getValue();
        double total = Double.parseDouble(ProdServ.instance().computeTotal());
        if (Double.parseDouble(payment) >= total) {
            view.getContext()
                    .startActivity(new Intent(view.getContext(), ConfirmationActivity.class));
        } else {
            Toast.makeText(view.getContext(), "Insufficient cash", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public final <T> void updateData(T[] newData) {
        payable.setValue(String.valueOf(newData[0]));
    }

    public static synchronized CheckoutActivityViewModel instance() {
        if (deez == null)
            deez = new CheckoutActivityViewModel();
        return deez;
    }
}
