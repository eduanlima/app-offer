package com.example.appoffer01.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.appoffer01.adapter.AdapterBannerOffer;
import com.example.appoffer01.api.model.BannerOffer;
import com.example.appoffer01.api.model.Sector;
import com.example.appoffer01.api.model.Store;
import com.example.appoffer01.util.PicassoTrustAll;
import com.example.appoffer01.util.RecyclerItemClickListener;
import com.example.appoffer10.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BannerOfferActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOffers;
    private Store store;
    private List<BannerOffer> bannersOffer = new ArrayList<>();
    private int sector_id = 0;
    private String address;
    private ImageView imageViewBrand;
    private TextView textViewStore;
    private TextView textViewAddress;
    private final String URL_API = "https://api-offers.herokuapp.com/v01/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

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
        TaskGetBannersOffer taskGetBannersOffer = new TaskGetBannersOffer();
        taskGetBannersOffer.execute(store.getId());
    }

    class TaskGetBannersOffer extends AsyncTask<Integer, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... integers) {
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;
            StringBuffer stringBuffer = null;

            try{
                //Create an url
                URL url = new URL(URL_API + "banners/" + integers[0]);

                //Open a connection with object url
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                //Get connection dates as bytes
                inputStream = httpURLConnection.getInputStream();

                //Convert inputStream from bytes to characters
                inputStreamReader = new InputStreamReader(inputStream);

                //Create buffer for read characters
                bufferedReader = new BufferedReader(inputStreamReader);

                stringBuffer = new StringBuffer();

                String line = "";

                while ((line = bufferedReader.readLine()) != null){
                    stringBuffer.append(line);
                }

            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }

            return stringBuffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            BannerOffer bannerOffer = null;
            Sector sector = null;
            Store store = null;

            try{
                JSONObject jsonObjectBanner = null;
                JSONObject jsonObjectSector = null;
                JSONObject jsonObjectStore = null;

                JSONArray jsonArray = new JSONArray(s);

                for (int i = 0; i < jsonArray.length(); i++){
                    jsonObjectBanner = jsonArray.getJSONObject(i);

                    bannerOffer = new BannerOffer();
                    bannerOffer.setDateInitial(jsonObjectBanner.getString("dateInitial"));
                    bannerOffer.setDateLimit(jsonObjectBanner.getString("dateLimit"));
                    bannerOffer.setImage(jsonObjectBanner.getString("image"));

                    jsonObjectSector = jsonObjectBanner.getJSONObject("sector");
                    sector = new Sector();
                    sector.setId(jsonObjectSector.getInt("id"));
                    bannerOffer.setSector(sector);

                    bannerOffer.setStatus(jsonObjectBanner.getBoolean("status"));

                    jsonObjectStore = jsonObjectBanner.getJSONObject("store");
                    store = new Store();
                    store.setId(jsonObjectStore.getInt("id"));
                    bannerOffer.setStore(store);

                    bannersOffer.add(bannerOffer);
                }

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

            }catch (JSONException e){
                e.printStackTrace();
            }
        }
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
