package org.apppuntukan.viewmodel;

import androidx.lifecycle.MutableLiveData;

public class CartIconViewModel extends ViewModelBase {

    public final MutableLiveData<Integer> cartCount = new MutableLiveData<>(0);

    private final int MAX_CART_SHOW = 99;

    public CartIconViewModel() {
        updateData(new Object[] {});
    }

    @Override
    public <T> void updateData(T[] newData) {

    }
}
