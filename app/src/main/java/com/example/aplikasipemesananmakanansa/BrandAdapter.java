package com.example.aplikasipemesananmakanansa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {

    private List<Brand> brandList;
    private OnBrandClickListener onBrandClickListener;

    public interface OnBrandClickListener {
        void onBrandClick(Brand brand);
    }

    public BrandAdapter(List<Brand> brandList, OnBrandClickListener onBrandClickListener) {
        this.brandList = brandList;
        this.onBrandClickListener = onBrandClickListener;
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        Brand brand = brandList.get(position);
        holder.bind(brand);

        // Menambahkan click listener untuk membuka ItemMenuActivity
        holder.itemView.setOnClickListener(v -> {
            onBrandClickListener.onBrandClick(brand);
        });
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    class BrandViewHolder extends RecyclerView.ViewHolder {

        private TextView brandName;
        private ImageView brandImage;

        public BrandViewHolder(@NonNull View itemView) {
            super(itemView);
            brandName = itemView.findViewById(R.id.brandName);
            brandImage = itemView.findViewById(R.id.brandImage);
        }

        public void bind(Brand brand) {
            brandName.setText(brand.getName());
            brandImage.setImageResource(brand.getImageResId());
        }
    }
}
