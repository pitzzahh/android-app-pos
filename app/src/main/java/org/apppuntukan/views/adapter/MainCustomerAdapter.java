package org.apppuntukan.views.adapter;

import org.apppuntukan.R;
import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import org.apppuntukan.model.Product;
import org.apppuntukan.model.ProductService;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
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

    public static class CardHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final ProductCardBinding productCardBinding;

        public CardHolder(ProductCardBinding productCardBinding) {
            super(productCardBinding.getRoot());
            this.imageView = itemView.findViewById(R.id.productImage);
            this.productCardBinding = productCardBinding;
        }

        void bindCard(Product product) {
            productCardBinding.setProduct(product);
            productCardBinding.executePendingBindings();
            if (product.getStock() >= 1) {
                imageView.setImageResource(R.drawable.product_icon);
            } else {
                imageView.setImageResource(R.drawable.no_product_icon);
            }
        }
    }
}
