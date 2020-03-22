package com.example.appoffer01.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appoffer01.api.model.Offer;
import com.example.appoffer01.api.model.Store;
import com.example.appoffer10.R;
import com.example.appoffer01.util.PicassoTrustAll;

import java.util.List;
import java.util.Map;

public class AdapterStoreOffer extends RecyclerView.Adapter<AdapterStoreOffer.MyViewHolder> {
    private List<Offer> offers;
    private Map<Integer, Store> mapStores;
    private Map<Integer, String> mapAdresses;
    private Context context;

    public AdapterStoreOffer(List<Offer> offers, Map<Integer, Store> mapStores, Map<Integer, String> mapAdresses, Context context){
        this.offers = offers;
        this.mapStores = mapStores;
        this.mapAdresses = mapAdresses;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_store_offer, parent, false);

        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Offer offer = offers.get(position);

        PicassoTrustAll.getInstance(context).load(offer.getImage()).into(holder.imageViewOffer);
        holder.textViewDescOffer.setText(offer.getProduct().getDescription());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("R$ ");
        holder.textViewPriceOffer.setText(stringBuilder.append(offer.getPrice()).toString().replace(".",","));

        holder.textViewStore.setText(mapStores.get(offer.getStore().getId()).getName());
        holder.textViewAddressStore.setText(mapAdresses.get(offer.getStore().getId()));
        PicassoTrustAll.getInstance(context).load(mapStores.get(offer.getStore().getId()).getImage()).into(holder.imageViewStore);
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewOffer;
        TextView textViewDescOffer;
        TextView textViewPriceOffer;

        TextView textViewStore;
        TextView textViewAddressStore;
        ImageView imageViewStore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewOffer = itemView.findViewById(R.id.imageViewOffer);
            textViewDescOffer = itemView.findViewById(R.id.textViewDescOffer);
            textViewPriceOffer = itemView.findViewById(R.id.textViewPriceOffer);

            textViewStore = itemView.findViewById(R.id.textViewStore);
            textViewAddressStore = itemView.findViewById(R.id.textViewAddressStore);
            imageViewStore = itemView.findViewById(R.id.imageViewStore);
        }
    }

}
