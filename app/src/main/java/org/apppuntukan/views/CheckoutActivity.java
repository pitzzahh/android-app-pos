package org.apppuntukan.views;

import org.apppuntukan.R;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import org.apppuntukan.databinding.ActivityCheckoutBinding;
import org.apppuntukan.viewmodel.CheckoutActivityViewModel;

public class CheckoutActivity extends AppCompatActivity {

    static CheckoutActivity deez;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCheckoutBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_checkout);
        binding.setCheckoutViewModel(CheckoutActivityViewModel.instance());
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());
        binding.amountToPay
                .addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.toString().matches("\\d|\\d+")) {
                            CheckoutActivityViewModel.instance().checkEnabled.setValue(true);
                        } else {
                            CheckoutActivityViewModel.instance().checkEnabled.setValue(false);
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        CheckoutActivityViewModel.instance().amount.setValue(s.toString());
                    }
                });
    }

    public static synchronized CheckoutActivity instance() {
        if (deez == null) {
            return new CheckoutActivity();
        }
        return deez;
    }
}