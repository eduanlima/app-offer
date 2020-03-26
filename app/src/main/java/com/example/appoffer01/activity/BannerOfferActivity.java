package com.example.appoffer01.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appoffer01.adapter.AdapterBannerOffer;
import com.example.appoffer01.api.model.Address;
import com.example.appoffer01.api.model.BannerOffer;
import com.example.appoffer01.api.model.Offer;
import com.example.appoffer01.api.model.Store;
import com.example.appoffer01.api.model.services.BannerOfferService;
import com.example.appoffer01.util.PicassoTrustAll;
import com.example.appoffer01.util.RecyclerItemClickListener;
import com.example.appoffer10.R;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BannerOfferActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOffers;
    private Retrofit retrofit;
    private Store store;
    private List<BannerOffer> bannersOffer;
    private int sector_id = 0;
    private String address;
    private ImageView imageViewBrand;
    private TextView textViewStore;
    private TextView textViewAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api-offers.herokuapp.com/v01/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Get object from previous Activity
        Bundle bundle = getIntent().getExtras();

        //Get object from Bundle
        store = (Store) bundle.getSerializable("store");
        address = bundle.getString("address");
        sector_id = bundle.getInt("sector");

        imageViewBrand = findViewById(R.id.imageViewBrand);
        PicassoTrustAll.getInstance(this).load(store.getImage()).into(imageViewBrand);

        textViewStore = findViewById(R.id.textViewStore);
        textViewStore.setText(store.getName());

        textViewAddress = findViewById(R.id.textViewAddress);
        textViewAddress.setText(address);

        recyclerViewOffers = findViewById(R.id.recycleViewOffers);

    }

    @Override
    public void onStart(){
        super.onStart();
        searchBannersOffer();
    }

    public void searchBannersOffer(){
        final BannerOfferService bannerOfferService = retrofit.create(BannerOfferService.class);
        Call<List<BannerOffer>> call = bannerOfferService.searchBanners(store.getId());

        call.enqueue(new Callback<List<BannerOffer>>() {
            @Override
            public void onResponse(Call<List<BannerOffer>> call, Response<List<BannerOffer>> response) {
                if (response.isSuccessful()){
                    bannersOffer = response.body();

                    int position = 0;

                    if (sector_id != 0){
                        for (int i = 0; i < bannersOffer.size(); i++){
                            if (bannersOffer.get(i).getSector().getId() == sector_id){
                                position = i;
                                break;
                            }
                        }

                        Collections.swap(bannersOffer, 0, position);
                    }
                    createRecycleView();
                }
            }

            @Override
            public void onFailure(Call<List<BannerOffer>> call, Throwable t) {}
        });
    }

    public void createRecycleView(){
        //Define layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewOffers.setLayoutManager(layoutManager);

        //Define adapter
        AdapterBannerOffer adapterBannersOffer = new AdapterBannerOffer(bannersOffer, this);
        recyclerViewOffers.setAdapter(adapterBannersOffer);

        //event click
        recyclerViewOffers.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerViewOffers,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Store store = null;
                                BannerOffer bannerOffer;

                                bannerOffer = bannersOffer.get(position);

                                //Set new Activity
                                Intent intent = new Intent(getApplicationContext(), DetailsBannerOffer.class);

                                //Send objects to next Activity
                                intent.putExtra("store", store);
                                intent.putExtra("address", address);
                                intent.putExtra("bannerOffer", bannerOffer);

                                //Open next Activity
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }
}
