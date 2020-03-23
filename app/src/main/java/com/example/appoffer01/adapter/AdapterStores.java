package com.example.appoffer01.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appoffer10.R;
import com.example.appoffer01.api.model.Store;
import com.example.appoffer01.util.PicassoTrustAll;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AdapterStores extends RecyclerView.Adapter<AdapterStores.MyViewHolder>{

    private List<Store> stores;
    private Map<Integer, String> mapAdresses;
    private Context context;

    public AdapterStores(List<Store> stores, Map<Integer, String> mapAdresses, Context context) {

        this.stores = stores;
        this.mapAdresses = mapAdresses;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_stores, parent, false);
        itemList.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        itemList.requestLayout();
        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Store store = stores.get(position);
        holder.name.setText(store.getName());
        holder.address.setText(mapAdresses.get(store.getId()));
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
