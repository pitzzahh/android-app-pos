package org.apppuntukan.viewmodel;

import android.view.View;

public interface ICard {
    default void onClickCard(View v) {}

    default void onClickProductImage(View v) {}

    default void onAddToCart(View v) {}
}