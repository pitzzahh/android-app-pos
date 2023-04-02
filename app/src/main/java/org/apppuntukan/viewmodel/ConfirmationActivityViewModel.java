package org.apppuntukan.viewmodel;

import android.view.View;
import android.content.Intent;
import org.apppuntukan.model.ProdServ;
import org.apppuntukan.views.MainActivity;

public class ConfirmationActivityViewModel extends ViewModelBase {

    public void orderAgain(View view) {
        view.getContext()
                .startActivity(new Intent(view.getContext(), MainActivity.class));
        ProdServ.instance()
                .getCartProducts()
                .clear();
        for (int i = 0; i < ProdServ.instance().getCartProducts().size(); i++) {
            ProdServ.instance()
                    .getCartProductsRepository()
                    .remove(ProdServ.instance().getCartProducts().get(i));
        }
    }
}
