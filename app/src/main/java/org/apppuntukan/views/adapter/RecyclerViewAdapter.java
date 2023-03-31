package org.apppuntukan.views.adapter;

import java.util.List;
import android.view.View;
import android.app.Activity;
import android.view.ViewGroup;
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

public class RecyclerViewAdapter<T extends ViewDataBinding> extends RecyclerView.Adapter<RecyclerViewAdapter.CardHolder<T>> {

    private final Activity activity;
    private final int layoutId;
    private final List<Product> productList;

    private final RecyclerViewAdapter<T> adapter;

    public RecyclerViewAdapter(Activity activity, int layoutId, List<Product> productList) {
        this.activity = activity;
        this.layoutId = layoutId;
        this.productList = productList;
        this.adapter = this;
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
                ), adapter);
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

        private final RecyclerViewAdapter<T> adapter;
        private final T binding;

        public CardHolder(T binding, RecyclerViewAdapter<T> adapter) {
            super(binding.getRoot());
            this.binding = binding;
            this.adapter = adapter;
        }

        void bindCard(Product product) {
            if (binding instanceof ProductCardBinding) { // can't use pattern matching
                ((ProductCardBinding) binding).setProduct(product);
                ((ProductCardBinding) binding).setHandler(this);
            }
            if (binding instanceof CartProductCardBinding) {
                ((CartProductCardBinding) binding).setProduct(product);
                ((CartProductCardBinding) binding).setHandler(this);
            }
            binding.executePendingBindings();
        }

        @Override
        public void onClickCard(View v) {
            Snackbar.make(v, "Card clicked", Snackbar.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onIncreaseQuantity(View v) {
            int position = super.getLayoutPosition();
            ProductService.getInstance().addProductQuantity(ProductService.getInstance().getProducts().get(position));
            adapter.notifyItemChanged(position);
        }

        @Override
        public void onDecreaseQuantity(View v) {
            int position = super.getLayoutPosition(); // get the position of the card
            Product product = ProductService.getInstance().getProducts().get(position);
            boolean noQuantityLeft = ProductService.getInstance().removeProductQuantityOrRemove(product);
            if (noQuantityLeft) {
                ProductService.getInstance().removeProductFromCart(product);
                adapter.notifyItemRemoved(position);
            }
            adapter.notifyItemChanged(position);
        }

        @Override
        public void onClickProductImage(View v) {
            Snackbar.make(v, "Product Image Clicked", Snackbar.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onAddToCart(View view) {
            int pos = super.getLayoutPosition(); // get the position of the card
            if (ProductService.getInstance().isAlreadyInCart(ProductService.getInstance().getProducts().get(pos))) {
                ProductService.getInstance().addProductQuantity(ProductService.getInstance().getProducts().get(pos));
            } else {
                ProductService.getInstance().addProductToCart(ProductService.getInstance().getProducts().get(pos));
            }
            Snackbar.make(view, "Product added to cart", Snackbar.LENGTH_SHORT)
                    .show();
            adapter.notifyItemChanged(pos);
        }
    }
}
