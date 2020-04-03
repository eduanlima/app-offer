package com.example.appoffer01.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appoffer01.api.model.BannerOffer;
import com.example.appoffer10.R;
import com.example.appoffer01.util.PicassoTrustAll;

import java.util.List;

public class AdapterBannerOffer extends RecyclerView.Adapter<AdapterBannerOffer.MyViewHolder> {

    private List<BannerOffer> bannersOffer;
    private Context context;

    public AdapterBannerOffer(List<BannerOffer> bannersOffer, Context context){
        this.bannersOffer = bannersOffer;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_banner_offer, parent, false);

        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        BannerOffer bannerOffer = bannersOffer.get(position);

        PicassoTrustAll.getInstance(context).load(bannerOffer.getImage()).into(holder.imageViewBannerOffer);

        holder.textViewDate.setText("Válida até " + bannerOffer.getDateLimit());
        //Sector sector = bannerOffer.getSector();
        //holder.textViewSector.setText(sector.getId());
    }

    @Override
    public int getItemCount() {
        return bannersOffer.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageViewBannerOffer;
        private TextView textViewDate;
        private TextView textViewSector;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewBannerOffer = itemView.findViewById(R.id.imageViewOffer);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewSector = itemView.findViewById(R.id.textViewSector);

        }
    }
}
