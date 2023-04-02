package org.apppuntukan.model;

import android.view.View;

public interface ICard {
    default void onClickCard(View v) {}

    default void onClickProductImage(View v) {}

    default void onAddToCart(View v) {}

    default void onIncreaseQuantity(View v) {}

    default void onDecreaseQuantity(View v) {}
}