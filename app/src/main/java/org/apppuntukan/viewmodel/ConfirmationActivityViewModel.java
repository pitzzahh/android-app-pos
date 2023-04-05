package org.apppuntukan.viewmodel;

import android.view.View;
import java.util.List;
import java.util.Objects;
import android.content.Intent;
import org.apppuntukan.model.ProdServ;
import androidx.lifecycle.MutableLiveData;
import org.apppuntukan.model.Product;
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

    public void orderAgain(View view) { // FIXME: 05/04/2023 Fix stocks and cart products
        List<Product> cartProducts = ProdServ.instance().getCartProducts();

        for (int i = 0; i < cartProducts.size(); i++) {
            Product product = cartProducts.get(i);
            product.setStock(product.getStock() - product.getQuantity());
            ProdServ.instance()
                    .getCartProductsRepository()
                    .remove(ProdServ.instance().getCartProducts().get(i));
            ProdServ.instance()
                    .getProductRepository()
                    .update(product);
        }

        int size = ProdServ.instance().getCartProducts().size();
        CartActivityViewModel.getInstance()
                .updateData(new Object[]{
                        size,
                        ProdServ.instance().computeTotal()
                });
        ProductsActivityViewModel.instance()
                .updateData(new Object[]{size});

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
