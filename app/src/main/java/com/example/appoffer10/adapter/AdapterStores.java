package com.example.appoffer10.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appoffer10.R;
import com.example.appoffer10.api.model.Store;
import com.example.appoffer10.util.PicassoTrustAll;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterStores extends RecyclerView.Adapter<AdapterStores.MyViewHolder>{

    private List<Store> stores;
    private Context context;

    public AdapterStores(List<Store> stores, Context context) {

        this.stores = stores;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_stores, parent, false);
        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Store store = stores.get(position);
        holder.name.setText(store.getName_desc());
        holder.address.setText(store.getAddress());
        PicassoTrustAll.getInstance(context).load(store.getImage()).into(holder.imageViewBrand);
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView address;
        ImageView imageViewBrand;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textViewName);
            address = itemView.findViewById(R.id.textViewAddress);
            imageViewBrand = itemView.findViewById(R.id.imageViewBrand);
        }
    }

}
