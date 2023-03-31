package org.apppuntukan.views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;

import org.apppuntukan.R;
import org.apppuntukan.model.ProdServ;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Button button = findViewById(R.id.btn_open_confirmation);

        String totalCartPrice = ProdServ.instance().computeTotal();

        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, ConfirmationActivity.class);
            String payment = ((EditText)findViewById(R.id.amount_to_pay)).getText().toString();
            if (payment.matches("\\d|\\d+")) {
                double total = Double.parseDouble(totalCartPrice);
                if (Double.parseDouble(payment) >= total) {
                    ProdServ.instance().setTotal(total);
                    ProdServ.instance().setChange((Double.parseDouble(payment) - total));
                    startActivity(intent);
                } else {
                    Snackbar.make(view, "Insufficient cash", Snackbar.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(view, "Invalid cash", Snackbar.LENGTH_SHORT).show();
            }
        });

        TextView textView = findViewById(R.id.label_price);
        textView.setText(String.format("$%s", totalCartPrice));

    }
}