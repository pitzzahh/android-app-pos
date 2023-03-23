package org.apppuntukan.views.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import org.apppuntukan.R;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import org.apppuntukan.model.Product;
import org.apppuntukan.model.ProductService;
import androidx.recyclerview.widget.RecyclerView;

public class MainCustomerAdapter extends RecyclerView.Adapter<MainCustomerAdapter.CardHolder> {

    private final Context context;

    private final List<Product> products;

    public MainCustomerAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public MainCustomerAdapter.CardHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View from = LayoutInflater.from(this.context)
                .inflate(R.layout.product_card, viewGroup, false);

        return new CardHolder(from);
    }

    @Override
    public void onBindViewHolder(@NonNull MainCustomerAdapter.CardHolder holder, int position) {

        holder.addToCart.setOnClickListener(view -> {
            if (ProductService.getInstance().getCartProducts().contains(products.get(position))) {
                ProductService.getInstance().addProductQuantity(products.get(position));
            } else {
                ProductService.getInstance().addProductToCart(products.get(position));
            }
        });

        holder.getImageView().setImageResource(R.drawable.product_icon);
        holder.getProductName().setText(products.get(position).getProductName());
        holder.getPrice().setText(String.format("$%s", products.get(position).getPrice()));
        holder.getAvailableStock().setText(String.format("Available stock: %s", products.get(position).getStock()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class CardHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView productName;
        private final TextView price;
        private final TextView availableStock;
        private final Button addToCart;

        public CardHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.productImage);
            productName = (TextView) itemView.findViewById(R.id.productName);
            price = (TextView) itemView.findViewById(R.id.productPrice);
            availableStock = (TextView) itemView.findViewById(R.id.availableStock);
            addToCart = itemView.findViewById(R.id.addToCart);

            Log.d("imageView", String.valueOf(imageView));
            Log.d("productName", String.valueOf(productName));
            Log.d("price", String.valueOf(price));
            Log.d("availableStock", String.valueOf(availableStock));
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
