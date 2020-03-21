package com.example.appoffer01.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appoffer01.adapter.AdapterOffers;
import com.example.appoffer01.api.model.Offer;
import com.example.appoffer01.api.model.Store;
import com.example.appoffer01.util.PicassoTrustAll;
import com.example.appoffer10.R;

import java.util.List;

public class OfferActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOffers;
    private ImageView imageViewBrand;
    private TextView textViewStore;
    private TextView textViewAddress;
    private List<Offer> offers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        //Get object from previous Activity
        Bundle storeBundle = getIntent().getExtras();

        //Get object from Bundle
        Store store = (Store) storeBundle.getSerializable("store");

        imageViewBrand = findViewById(R.id.imageViewBrand);
        PicassoTrustAll.getInstance(this).load(store.getImage()).into(imageViewBrand);

        textViewStore = findViewById(R.id.textViewStore);
        textViewStore.setText(store.getName());

        textViewAddress = findViewById(R.id.textViewAddress);
        //textViewAddress.setText(store.getAddress());

        setOffers(store.getOffers());

        recyclerViewOffers = findViewById(R.id.recycleViewOffers);

        //Define layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewOffers.setLayoutManager(layoutManager);

        //Define adapter
        //AdapterOffers adapterOffers = new AdapterOffers(getOffers(), this);
        //recyclerViewOffers.setAdapter(adapterOffers);


    }

    public List<Offer> getOffers(){
        return offers;
    }
    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
