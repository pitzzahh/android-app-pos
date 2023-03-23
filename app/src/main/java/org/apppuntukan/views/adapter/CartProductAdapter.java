package org.apppuntukan.views.adapter;

import org.apppuntukan.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import org.apppuntukan.model.ProductService;
import androidx.recyclerview.widget.RecyclerView;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.CardHolder> {

    @NonNull
    @Override
    public CartProductAdapter.CardHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View from = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cart_product_card, viewGroup, false);
        return new CardHolder(from);
    }

    @Override
    public void onBindViewHolder(@NonNull CartProductAdapter.CardHolder holder, int position) {
        holder.getProductName().setText(ProductService.getInstance().getCartProducts().get(position).getProductName());
        holder.getPrice().setText(String.format("$%s", ProductService.getInstance().getCartProducts().get(position).getPrice()));
        holder.getQuantity().setText(String.format("Qty: %s", ProductService.getInstance().getProductQuantityInCart(ProductService.getInstance().getCartProducts().get(position))));
    }

    @Override
    public int getItemCount() {
        return ProductService.getInstance().getCartProducts().size();
    }

    public static class CardHolder extends RecyclerView.ViewHolder {

        private final TextView productName;
        private final TextView price;
        private final TextView quantity;

        public CardHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.cartProductName);
            price = itemView.findViewById(R.id.cartProductPrice);
            quantity = itemView.findViewById(R.id.cartQuantity);
        }

        public TextView getProductName() {
            return productName;
        }

        public TextView getPrice() {
            return price;
        }

        public TextView getQuantity() {
            return quantity;
        }
    }
}
