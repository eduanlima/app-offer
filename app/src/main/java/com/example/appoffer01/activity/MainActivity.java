package com.example.appoffer01.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.appoffer01.adapter.AdapterStoreOffer;
import com.example.appoffer01.api.model.Address;
import com.example.appoffer01.api.model.Offer;
import com.example.appoffer01.api.model.services.AddressService;
import com.example.appoffer01.api.model.services.OfferService;
import com.example.appoffer01.util.FilterAddress;
import com.example.appoffer01.util.OrderOffers;
import com.example.appoffer10.R;
import com.example.appoffer01.adapter.AdapterStores;
import com.example.appoffer01.api.model.Store;
import com.example.appoffer01.api.model.services.StoreService;
import com.example.appoffer01.util.RecyclerItemClickListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAllStores;
    private Retrofit retrofit;
    private Store store;
    private List<Address> adresses;
    private List<Integer> allIdStores = new ArrayList<>();
    private List<Store> stores = new ArrayList<>();
    private List<Offer> allOffers  = new ArrayList<>();
    private List<Offer> offerSearch = new ArrayList<>();
    private Map<Integer, Store> mapStores = new TreeMap<>();
    private Map<Integer, List<Offer>> mapOffers =  new TreeMap<>();
    private Map<Integer, String> mapAdresses = new TreeMap<>();
    private EditText editTextSearch;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    private Double latitude;
    private Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        retrofit = new Retrofit.Builder().
                baseUrl("https://api-offers.herokuapp.com/v01/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        editTextSearch = findViewById(R.id.editTextSearch);

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (editTextSearch.length() > 2) {

                    offerSearch = OrderOffers.orderByPrice(editTextSearch.getText().toString(), allOffers);

                    if (offerSearch != null){
                        createRecycleView(1);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Produto não encontrado",  Toast.LENGTH_SHORT).show();
                        createRecycleView(0);
                    }
                }
                else if (editTextSearch.length()  <= 2){
                    createRecycleView(0);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Toast.makeText(getApplicationContext(), "On text changed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
               // Toast.makeText(getApplicationContext(), "After changed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        searchAllAdressesAPI();
    }

    //Get location
    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
        }
    };

    public void searchAllAdressesAPI(){
        AddressService addressService = retrofit.create(AddressService.class);
        Call<List<Address>> call = addressService.searchAdresses();

        call.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                if (response.isSuccessful()){
                    adresses = response.body();

                    adresses = FilterAddress.filterAdresses(adresses, latitude, longitude);

                    for (Address address : adresses){
                        mapAdresses.put(address.getId(), address.getAddress());
                        searchStoreAPI(address.getId());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {}
        });
    }

    private void searchStoreAPI(Integer id_store){
        final int totalAdresses = adresses.size();

        StoreService storeService = retrofit.create(StoreService.class);
        Call<Store> call = storeService.searchStore(id_store);

        call.enqueue(new Callback<Store>() {
            @Override
            public void onResponse(Call<Store> call, Response<Store> response) {
                if (response.isSuccessful()){
                    store = response.body();
                    stores.add(store);
                    mapStores.put(store.getId(), store);

                    if (stores.size() == totalAdresses){
                        createRecycleView(0);

                        if (mapAdresses.size() != allIdStores.size()){
                            for (Store store: getStores()){
                                searchOffersAPI(store.getId());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Store> call, Throwable t) {}
        });
    }

    public void searchOffersAPI(Integer id_store){
        OfferService offerService = retrofit.create(OfferService.class);
        Call<List<Offer>> call = offerService.searchOffers(id_store);

        call.enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                List<Offer> offers;

                if (response.isSuccessful()) {
                    offers = response.body();

                    if (offers != null) {
                        int aux = 0;
                        for (Integer id_store : allIdStores) {
                            if (id_store == offers.get(0).getStore().getId()) {
                                aux++;
                            }
                        }

                        if (aux == 0) {
                            allIdStores.add(offers.get(0).getStore().getId());
                        }

                        for (Offer offer: offers){
                            allOffers.add(offer);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {}
        });
    }

    private void createRecycleView(int type){
        recyclerViewAllStores = findViewById(R.id.recyclerViewAllStores);

        //Configure adapter
        AdapterStores adapterStores =  new AdapterStores(stores, mapAdresses, this);
        AdapterStoreOffer adapterStoreOffer = new AdapterStoreOffer(offerSearch, mapStores, mapAdresses, this);

        //Configure Recycleview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewAllStores.setLayoutManager(layoutManager);
        recyclerViewAllStores.setHasFixedSize(true);
        recyclerViewAllStores.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        recyclerViewAllStores.setAdapter(null);

        if (type == 0){
            recyclerViewAllStores.setAdapter(adapterStores);
        }
        else{
           recyclerViewAllStores.setAdapter(adapterStoreOffer);
        }

        //event click
        recyclerViewAllStores.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerViewAllStores,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Store store = getStores().get(position);

                                //Set new Activity
                                Intent intent = new Intent(getApplicationContext(), OfferActivity.class);

                                //Send object to next Activity
                                intent.putExtra("store",store);

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

    public void setOffersStores(){
        List<Store> newStore = new ArrayList<>();
        for (Store store : stores){
            store.setOffers(mapOffers.get(store.getId()));
            newStore.add(store);
        }
        stores = newStore;
    }

    public List<Store> getStores(){
        return stores;
    }

    public void setStores(List<Store> stores){
        this.stores = stores;
    }
}
