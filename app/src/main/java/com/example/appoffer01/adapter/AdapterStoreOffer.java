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
    private Map<Integer, Double> mapDistance;
    private Context context;

    public AdapterStoreOffer(List<Offer> offers, Map<Integer, Store> mapStores, Map<Integer, Double> mapDistance, Context context){
        this.offers = offers;
        this.mapStores = mapStores;
        this.mapDistance = mapDistance;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_store_offer, parent, false);
                itemList.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                itemList.requestLayout();
               // itemList.setBackgroundColor(Color.parseColor("#bebebe"));
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
        holder.textViewDate.setText("Válida até "+offer.getDateLimit());

        holder.textViewStore.setText(mapStores.get(offer.getStore().getId()).getName());
        holder.textViewDistance.setText(String.format("%.2f",mapDistance.get(offer.getStore().getId())).replace(".",",") + " km");
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
        TextView textViewDate;

        TextView textViewStore;
        TextView textViewDistance;
        ImageView imageViewStore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewOffer = itemView.findViewById(R.id.imageViewOffer);
            textViewDescOffer = itemView.findViewById(R.id.textViewDescOffer);
            textViewPriceOffer = itemView.findViewById(R.id.textViewPriceOffer);
            textViewDate = itemView.findViewById(R.id.textViewDate);

            textViewStore = itemView.findViewById(R.id.textViewStore);
            textViewDistance = itemView.findViewById(R.id.textViewDistance);
            imageViewStore = itemView.findViewById(R.id.imageViewStore);
        }
    }

}
