package org.apppuntukan.views.adapter;

import java.util.List;
import android.view.View;
import android.app.Activity;
import android.widget.Toast;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import org.apppuntukan.model.Product;
import org.apppuntukan.viewmodel.ICard;
import org.apppuntukan.model.ProdServ;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import org.apppuntukan.databinding.ProductCardBinding;
import org.apppuntukan.databinding.CartProductCardBinding;
import org.apppuntukan.viewmodel.ProductsActivityViewModel;

public class RecyclerViewAdapter<T extends ViewDataBinding> extends RecyclerView.Adapter<RecyclerViewAdapter.CardHolder<T>> {

    private final Activity activity;
    private final int layoutId;
    private final List<Product> products;
    private final RecyclerViewAdapter<T> adapter;

    public RecyclerViewAdapter(Activity activity, int layoutId, List<Product> products) {
        this.activity = activity;
        this.layoutId = layoutId;
        this.products = products;
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
                ), adapter, products);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.CardHolder<T> holder, int position) {
        holder.bindCard(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class CardHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder implements ICard {

        private final RecyclerViewAdapter<T> adapter;
        private final List<Product> products;
        private final T binding;

        public CardHolder(T binding, RecyclerViewAdapter<T> adapter, List<Product> products) {
            super(binding.getRoot());
            this.binding = binding;
            this.adapter = adapter;
            this.products = products;
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
            Toast.makeText(v.getContext(), "Card clicked", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onIncreaseQuantity(View v) {
            int position = super.getLayoutPosition();
            ProdServ.instance().addProductQuantity(products.get(position));
            adapter.notifyItemChanged(position);
        }

        @Override
        public void onDecreaseQuantity(View v) { // FIXME: 02/04/2023 Cart still shows removed product.
            int position = super.getLayoutPosition();
            Product product = products.get(position);
            boolean toBeRemoved = ProdServ.instance().updateProductQuantityOrRemoveToCart(product);
            if (toBeRemoved) {
                products.remove(product);
                Product removedProduct = ProdServ.instance().removeProductFromCart(product);
                if (removedProduct != null) {
                    adapter.notifyItemChanged(position);
                    adapter.notifyItemRangeRemoved(position, products.size());
                }
            } else adapter.notifyItemChanged(position);
        }

        @Override
        public void onClickProductImage(View v) {
            Toast.makeText(v.getContext(), "Product Image Clicked", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAddToCart(View v) {
            int pos = super.getLayoutPosition(); // get the position of the card
            if (ProdServ.instance().isAlreadyInCart(products.get(pos)))
                ProdServ.instance().addProductQuantity(products.get(pos));
            else ProdServ.instance().addProductToCart(products.get(pos));

            ProductsActivityViewModel
                    .instance()
                    .cartCount
                    .set(ProdServ.instance().getCartProducts().size());
            Toast.makeText(v.getContext(), "Product added to cart", Toast.LENGTH_SHORT).show();
        }
    }
}
