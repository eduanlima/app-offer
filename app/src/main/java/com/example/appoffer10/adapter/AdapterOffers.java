package com.example.appoffer10.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appoffer10.R;
import com.example.appoffer10.api.model.Offer;
import com.example.appoffer10.util.PicassoTrustAll;

import java.util.List;

public class AdapterOffers extends RecyclerView.Adapter<AdapterOffers.MyViewHolder> {

    private List<Offer> offers;
    private Context context;

    public AdapterOffers(List<Offer> offers, Context context){
        this.offers = offers;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.offer_details, parent, false);

        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Offer offer = offers.get(position);

        PicassoTrustAll.getInstance(context).load(offer.getImage()).into(holder.imageViewOffer);

        holder.textViewDescription.setText(offer.getProduct().getDescription());

        String price = offer.getPrice().toString();
        StringBuilder priceBuilder = new StringBuilder();
        priceBuilder.append("R$ ");
        priceBuilder.append(price.replace(".",","));
        holder.textViewPrice.setText(priceBuilder);
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageViewOffer;
        private TextView textViewDescription;
        private TextView textViewPrice;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewOffer = itemView.findViewById(R.id.imageViewOffer);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);

        }
    }
}
