package org.apppuntukan.viewmodel;

import android.view.View;
import android.content.Intent;
import org.apppuntukan.model.ProdServ;
import androidx.lifecycle.MutableLiveData;
import org.apppuntukan.views.ConfirmationActivity;
import com.google.android.material.snackbar.Snackbar;

public class CheckoutActivityViewModel extends ViewModelBase {

    private static CheckoutActivityViewModel deez;
    public final MutableLiveData<String> payable = new MutableLiveData<>();
    public final MutableLiveData<String> amount = new MutableLiveData<>();

    public CheckoutActivityViewModel() {
        updateData(new String[] {ProdServ.instance().computeTotal()});
    }

    public void onConfirmCheckout(View view) { // FIXME: 24/03/2023 might be wrong, don't know how to use.
        String payment = amount.getValue() == null ? "" : amount.getValue();
        if (payment.matches("\\d|\\d+")) {
            double total = Double.parseDouble(ProdServ.instance().computeTotal());
            if (Double.parseDouble(payment) >= total) {
                ProdServ.instance().setTotal(total);
                ProdServ.instance().setChange((Double.parseDouble(payment) - total));
                view.getContext()
                        .startActivity(new Intent(view.getContext(), ConfirmationActivity.class));
            } else {
                Snackbar.make(view, "Insufficient cash", Snackbar.LENGTH_SHORT).show();
            }
        } else {
            Snackbar.make(view, "Invalid cash", Snackbar.LENGTH_SHORT).show();
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
