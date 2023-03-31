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
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import org.apppuntukan.databinding.ProductCardBinding;
import org.apppuntukan.databinding.CartProductCardBinding;

import java.util.List;

public class RecyclerViewAdapter<T extends ViewDataBinding> extends RecyclerView.Adapter<RecyclerViewAdapter.CardHolder<T>> {

    private final Activity activity;
    private final int layoutId;
    private final List<Product> productList;


    public RecyclerViewAdapter(Activity activity, int layoutId, List<Product> productList) {
        this.activity = activity;
        this.layoutId = layoutId;
        this.productList = productList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.CardHolder<T> onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new CardHolder<>(DataBindingUtil
                .inflate(
                        activity.getLayoutInflater(),
                        layoutId,
                        viewGroup,
                        false
                ), layoutId);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.CardHolder<T> holder, int position) {
        holder.bindCard(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class CardHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder implements ICard {

        private final ImageView imageView;
        private final T binding;

        public CardHolder(T binding, int layoutId) {
            super(binding.getRoot());
            this.imageView = itemView.findViewById(layoutId == R.layout.product_card ? R.id.productImage : R.id.cartProductImage);
            this.binding = binding;
        }

        void bindCard(Product product) {
            if (binding instanceof ProductCardBinding) {
                ((ProductCardBinding) binding).setProduct(product);
                ((ProductCardBinding) binding).setHandler(this);
            }
            if (binding instanceof CartProductCardBinding) {
                ((CartProductCardBinding) binding).setProduct(product);
                ((CartProductCardBinding) binding).setHandler(this);
            }

            binding.executePendingBindings();
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
