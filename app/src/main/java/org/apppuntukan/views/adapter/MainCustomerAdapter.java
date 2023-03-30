package org.apppuntukan.views.adapter;

import org.apppuntukan.R;
import android.view.View;
import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import org.apppuntukan.model.Product;
import org.apppuntukan.viewmodel.ICard;
import org.apppuntukan.model.ProductService;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import org.apppuntukan.databinding.ProductCardBinding;

public class MainCustomerAdapter extends RecyclerView.Adapter<MainCustomerAdapter.CardHolder> {

    private final Activity activity;

    public MainCustomerAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public MainCustomerAdapter.CardHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new CardHolder(DataBindingUtil
                .inflate(
                        activity.getLayoutInflater(),
                        R.layout.product_card,
                        viewGroup,
                        false
                ));
    }

    @Override
    public void onBindViewHolder(@NonNull MainCustomerAdapter.CardHolder holder, int position) {
        Product product = ProductService.getInstance().getProducts().get(position);
        holder.bindCard(product);
    }

    @Override
    public int getItemCount() {
        return ProductService.getInstance().getProducts().size();
    }

    public static class CardHolder extends RecyclerView.ViewHolder implements ICard {

        private final ImageView imageView;
        private final ProductCardBinding productCardBinding;

        public CardHolder(ProductCardBinding productCardBinding) {
            super(productCardBinding.getRoot());
            this.imageView = itemView.findViewById(R.id.productImage);
            this.productCardBinding = productCardBinding;
        }

        void bindCard(Product product) {
            productCardBinding.setProduct(product);
            productCardBinding.setHandler(this);
            productCardBinding.executePendingBindings();
            if (product.getStock() >= 1) {
                imageView.setImageResource(R.drawable.product_icon);
            } else {
                imageView.setImageResource(R.drawable.no_product_icon);
            }
        }

        @Override
        public void onClickCard(View v) {
            Snackbar.make(v, "Card clicked", Snackbar.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onAddToCart(View view) {
            int pos = super.getLayoutPosition(); // get the position of the card
            System.out.println("position: " + pos);
            if (ProductService.getInstance().isAlreadyInCart(ProductService.getInstance().getProducts().get(pos))) {
                ProductService.getInstance().addProductQuantity(ProductService.getInstance().getProducts().get(pos));
            } else {
                ProductService.getInstance().addProductToCart(ProductService.getInstance().getProducts().get(pos));
            }
            Snackbar.make(view, "Product added to cart", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }
}
