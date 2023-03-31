package org.apppuntukan.viewmodel;

import android.view.View;

public interface ICard {
    void onClickCard(View v);

    default void onClickProductImage(View v) {}

    default void onAddToCart(View v) {}
}