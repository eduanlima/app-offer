package com.example.appoffer01.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appoffer01.adapter.AdapterBannerOffer;
import com.example.appoffer01.api.model.Address;
import com.example.appoffer01.api.model.BannerOffer;
import com.example.appoffer01.api.model.Store;
import com.example.appoffer01.api.model.services.BannerOfferService;
import com.example.appoffer01.util.PicassoTrustAll;
import com.example.appoffer10.R;

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
        Bundle storeBundle = getIntent().getExtras();

        //Get object from Bundle
        store = (Store) storeBundle.getSerializable("store");
        address = storeBundle.getString("address");

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
        BannerOfferService bannerOfferService = retrofit.create(BannerOfferService.class);
        Call<List<BannerOffer>> call = bannerOfferService.searchBanners(store.getId());

        call.enqueue(new Callback<List<BannerOffer>>() {
            @Override
            public void onResponse(Call<List<BannerOffer>> call, Response<List<BannerOffer>> response) {
                if (response.isSuccessful()){
                    bannersOffer = response.body();
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
    }
}
