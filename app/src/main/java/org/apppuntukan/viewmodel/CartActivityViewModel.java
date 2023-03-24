package org.apppuntukan.viewmodel;

import android.view.View;
import android.content.Intent;
import org.apppuntukan.views.CheckoutActivity;

public class CartActivityViewModel extends ViewModelBase {

    public void openCheckout(View view) {
        view.getContext()
                .startActivity(new Intent(view.getContext(), CheckoutActivity.class));
    }
}
