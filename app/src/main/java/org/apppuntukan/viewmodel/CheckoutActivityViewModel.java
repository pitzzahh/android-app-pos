package org.apppuntukan.viewmodel;

import org.apppuntukan.R;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import org.apppuntukan.model.ProductService;
import org.apppuntukan.views.ConfirmationActivity;
import com.google.android.material.snackbar.Snackbar;

public class CheckoutActivityViewModel extends ViewModelBase {

    public void onConfirmCheckout(View view) { // FIXME: 24/03/2023 might be wrong, don't know how to use.
        Intent intent = new Intent(view.getContext(), ConfirmationActivity.class);

        String payment = ((EditText) view.findViewById(R.id.amount_to_pay)).getText().toString();
        String totalCartPrice = ProductService.getInstance().computeTotal();

        if (payment.matches("\\d|\\d+")) {
            double total = Double.parseDouble(totalCartPrice);
            if (Double.parseDouble(payment) >= total) {
                ProductService.getInstance().setTotal(total);
                ProductService.getInstance().setChange((Double.parseDouble(payment) - total));
                view.getContext()
                        .startActivity(intent);
            } else {
                Snackbar.make(view, "Insufficient cash", Snackbar.LENGTH_SHORT).show();
            }
        } else {
            Snackbar.make(view, "Invalid cash", Snackbar.LENGTH_SHORT).show();
        }

        TextView textView = view.findViewById(R.id.label_price);
        textView.setText(String.format("$%s", totalCartPrice));
    }
}
