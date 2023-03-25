package org.apppuntukan.viewmodel;

import android.view.View;
import android.content.Intent;
import org.apppuntukan.model.ProductService;
import org.apppuntukan.views.MainActivity;

public class ConfirmationActivityViewModel extends ViewModelBase {

    public void orderAgain(View view) {
        view.getContext()
                .startActivity(new Intent(view.getContext(), MainActivity.class));
        ProductService.getInstance().getCartProducts().clear();
    }
}
