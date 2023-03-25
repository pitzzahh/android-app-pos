package org.apppuntukan.views.adapter;

import android.content.Context;
import android.view.View;
import org.apppuntukan.R;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import org.apppuntukan.viewmodel.ICard;
import org.apppuntukan.model.ProductService;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;

public class MainCustomerAdapter extends RecyclerView.Adapter<MainCustomerAdapter.CardHolder> {

    private final Context context;
    private final ICard iCard;

    public MainCustomerAdapter(Context context, ICard iCard) {
        this.context = context;
        this.iCard = iCard;
    }

    @NonNull
    @Override
    public MainCustomerAdapter.CardHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View from = LayoutInflater.from(this.context)
                .inflate(R.layout.product_card, viewGroup, false);

        return new CardHolder(from, iCard);
    }

    @Override
    public void onBindViewHolder(@NonNull MainCustomerAdapter.CardHolder holder, int position) {
        holder.addToCart.setOnClickListener(view -> {
            int index = ProductService.getInstance().getProductIndexFromCart(ProductService.getInstance().getProducts().get(position));
            System.out.println("index = " + index);
            if (index != -1 &&
                    ProductService.getInstance().getCartProducts().get(index).getStock() <=
                            Integer.parseInt(ProductService.getInstance().getProductQuantityInCart(ProductService.getInstance().getCartProducts().get(index)))
            ) {
                Snackbar.make(view, "Product out of stock", Snackbar.LENGTH_SHORT)
                        .show();
            } else {
                if (ProductService.getInstance().isAlreadyInCart(ProductService.getInstance().getProducts().get(position))) {
                    ProductService.getInstance().addProductQuantity(ProductService.getInstance().getProducts().get(position));
                } else {
                    ProductService.getInstance().addProductToCart(ProductService.getInstance().getProducts().get(position));
                }
                Snackbar.make(view, "Product added to cart", Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
        if (ProductService.getInstance().getProducts().get(position).getStock() >= 1) {
            holder.getImageView().setImageResource(R.drawable.product_icon);
        } else {
            holder.getImageView().setImageResource(R.drawable.no_product_icon);
        }
        holder.getProductName().setText(ProductService.getInstance().getProducts().get(position).getProductName());
        holder.getPrice().setText(String.format("$%s", ProductService.getInstance().getProducts().get(position).getPrice()));
        holder.getAvailableStock().setText(String.format("Available stock: %s", ProductService.getInstance().getProducts().get(position).getStock()));
    }

    @Override
    public int getItemCount() {
        return ProductService.getInstance().getProducts().size();
    }

    public static class CardHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView productName;
        private final TextView price;
        private final TextView availableStock;
        private final Button addToCart;

        public CardHolder(@NonNull View itemView, ICard iCard) {

            super(itemView);
            imageView = itemView.findViewById(R.id.productImage);
            productName = (TextView) itemView.findViewById(R.id.productName);
            price = (TextView) itemView.findViewById(R.id.productPrice);
            availableStock = (TextView) itemView.findViewById(R.id.availableStock);
            addToCart = itemView.findViewById(R.id.addToCart);

            itemView.setOnClickListener(v -> {
                if (iCard != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        iCard.onClickCard(position);
                    }
                }
            });
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getProductName() {
            return productName;
        }

        public TextView getPrice() {
            return price;
        }

        public TextView getAvailableStock() {
            return availableStock;
        }
    }
}
