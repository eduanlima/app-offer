package com.example.appoffer10.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appoffer10.R;
import com.example.appoffer10.api.model.Store;

import java.util.List;

public class AdapterStoreOffer extends RecyclerView.Adapter<AdapterStoreOffer.MyViewHolder> {
    private List<Store> stores;
    private Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewOffer;
        TextView textViewDescOffer;
        TextView textViewPriceOffer;

        TextView textViewStore;
        TextView textViewAddressStore;
        ImageView imageViewBrand;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewOffer = itemView.findViewById(R.id.imageViewOffer);
            textViewDescOffer = itemView.findViewById(R.id.textViewDescOffer);
            textViewPriceOffer = itemView.findViewById(R.id.textViewPriceOffer);

            textViewStore = itemView.findViewById(R.id.textViewStore);
            textViewAddressStore = itemView.findViewById(R.id.textViewAddressStore);
            imageViewBrand = itemView.findViewById(R.id.imageViewBrand);
        }
    }

}
