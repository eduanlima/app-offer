package com.example.appoffer01.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.appoffer01.adapter.AdapterStoreOffer;
import com.example.appoffer01.api.model.Address;
import com.example.appoffer01.api.model.Offer;
import com.example.appoffer01.api.model.Product;
import com.example.appoffer01.api.model.Sector;
import com.example.appoffer01.api.model.TypeStore;
import com.example.appoffer01.util.FilterAddress;
import com.example.appoffer01.util.OrderOffers;
import com.example.appoffer10.R;
import com.example.appoffer01.adapter.AdapterStores;
import com.example.appoffer01.api.model.Store;
import com.example.appoffer01.util.RecyclerItemClickListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Address> adresses;
    private List<Integer> allIdStores = new ArrayList<>();
    private List<Store> stores;
    private List<Offer> allOffers  = new ArrayList<>();
    private List<Offer> offerSearch = new ArrayList<>();
    private Map<Integer, Store> mapStores = new TreeMap<>();
    private Map<Integer, String> mapAdresses = new TreeMap<>();
    private Map<Integer, Double> mapDistance = new TreeMap<>();
    private EditText editTextSearch;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    private Double latitude;
    private Double longitude;
    private Byte checkAdapter;
    private ProgressDialog progressDialog;

    private Button buttonOption;
    private Dialog dialogOption;
    private Integer searchMode; //{0 - product, 1 - store, 2 - location}
    private TextView textViewModeSearch;

    /*Constant url api*/
    private final String URL_API = "https://api-offers.herokuapp.com/v01/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Get location*/
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        /*Initialize progress dialog*/
        progressDialog = new ProgressDialog(MainActivity.this);

        /*Edit text to search offers and stores*/
        editTextSearch = findViewById(R.id.editTextSearch);

        /**/
        dialogOption = new Dialog(MainActivity.this);

        searchMode = 0;

        textViewModeSearch = findViewById(R.id.textViewModeSearch);

        /*Set action on text change in edit text*/
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                /*The search will be work from three characters*/
                if (editTextSearch.length() > 2) {

                    if (searchMode == 0){
                        /*This list contains all items was returned from method "orderByPrice"*/
                        offerSearch = OrderOffers.orderByPrice(editTextSearch.getText().toString(), allOffers);

                        if (offerSearch != null){
                            /*Show all offers into 'recycleView'*/
                            createRecycleView(1);
                        }
                        else{
                            /*If there is not any offer found*/
                            Toast.makeText(getApplicationContext(), "Produto não encontrado",  Toast.LENGTH_SHORT).show();
                            createRecycleView(0);
                        }
                    }

                }
                else if (editTextSearch.length()  <= 2){
                    createRecycleView(0);
                }
            }
        });

        //Button show search options
        buttonOption = findViewById(R.id.buttonOption);

        //Set action on click button show options search */
        buttonOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOption.show();
                /*Set content*/
                dialogOption.setContentView(R.layout.option_search);
                /*Set transparent background*/
                dialogOption.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
            }
        });
    }

    /*Use radio*/
    public void onRadioOptionClicked(View view){
        //Is the button option now clicked?
        boolean checked = ((RadioButton) view).isChecked();

        //Checked which radio button was clicked
        switch (view.getId()){
            case R.id.radioButtonOffer:
                if (checked) {
                    searchMode = 0;
                    textViewModeSearch.setText("Pesquisa por ofertas.");
                }
                break;
            case R.id.radioButtonStore:
                if (checked) {
                    searchMode = 1;
                    textViewModeSearch.setText("Pesquisa por lojas.");
                }
                break;
            case R.id.radioButtonLocals:
                if (checked) {
                    searchMode = 2;
                    textViewModeSearch.setText("Pesquisa a partir de 'Meus locais'");
                }
                break;
        }
    }

    public void confirmOption(View view){
        dialogOption.dismiss();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
        /*Is list stores is full, it is possible show all stores*/
        if (stores != null){
            editTextSearch.setText("");
            offerSearch = new ArrayList<>();
            createRecycleView(0);
        }
    }

    /*Get location: begin*/
    private boolean checkPermissions(){
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

                                    showProgressDialog();

                                    TaskGetAdresses taskGetAdresses = new TaskGetAdresses();
                                    taskGetAdresses.execute("adresses");
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
    /*Get location: end*/

    private void createRecycleView(int type){

        recyclerView = findViewById(R.id.recyclerViewAllStores);

        /*Configure adapter*/
        AdapterStores adapterStores =  new AdapterStores(stores, mapAdresses, mapDistance, this);
        AdapterStoreOffer adapterStoreOffer = new AdapterStoreOffer(offerSearch, mapStores, mapDistance, this);

        /*Configure Recycleview*/
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(null);

        /*The 'type' variable defines the way the listing will be displayed,
         whether by stores (type == 0) or offers (type == 1)*/
        if (type == 0){
            recyclerView.setAdapter(adapterStores);
            /*The 'checkAdapter' variable defines what the action will be when
            clicking on the 'recyclerView' element*/
            checkAdapter = 0;
        }
        else{
           recyclerView.setAdapter(adapterStoreOffer);
            checkAdapter = 1;
        }

        /*Set event click into each element 'recyclerView'*/
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Store store = null;
                                Offer offer;
                                /*This variable defines what the first banner offer in the next activity
                                 for sector_id == 0, display banners on standard order*/
                                int sector_id = 0;

                                /*Open next activity on click store*/
                                if (checkAdapter == 0){
                                    store = stores.get(position);
                                }
                                /*Open next activity on click offer*/
                                else if (checkAdapter == 1){
                                    offer = offerSearch.get(position);
                                    store = mapStores.get(offer.getStore().getId());
                                    sector_id = offer.getProduct().getSector().getId();
                                }

                                /*Set new Activity*/
                                Intent intent = new Intent(getApplicationContext(), BannerOfferActivity.class);

                                /*Send object to next Activity*/
                                intent.putExtra("store", store);
                                intent.putExtra("address", mapAdresses.get(store.getId()));
                                intent.putExtra("sector", sector_id);

                                /*Open next Activity*/
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {}

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {}
                        }
                )
         );
    }

    public void showProgressDialog(){
        progressDialog.show();
        /*Set content*/
        progressDialog.setContentView(R.layout.progress_dialog);
        /*Set transparent background*/
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }

    public void closeProgressDialog(){
        progressDialog.dismiss();
    }

    /*This async task return a list of all adresses from api*/
    class TaskGetAdresses extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;
            StringBuffer stringBuffer = null;

            try {
                /*Create an url*/
                URL url = new URL(URL_API + strings[0]);

                /*Open a connection with object url*/
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                /*Get response dates as bytes*/
                inputStream = httpURLConnection.getInputStream();

                /*Convert inputStream from bytes to characters*/
                inputStreamReader = new InputStreamReader(inputStream);

                /*Create buffer for read characters*/
                bufferedReader = new BufferedReader(inputStreamReader);

                stringBuffer = new StringBuffer();

                String line;

                while ((line = bufferedReader.readLine()) != null){
                    stringBuffer.append(line);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }

            return stringBuffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            List<Address> adressesSearch = new ArrayList<>();
            Address address = null;

            try {
                JSONObject jsonObject = null;
                JSONArray jsonArray = new JSONArray(s);

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    address = new Address();
                    address.setId(jsonObject.getInt("id"));
                    address.setAddress(jsonObject.getString("address"));
                    address.setLatitude(jsonObject.getDouble("latitude"));
                    address.setLongitude(jsonObject.getDouble("longitude"));
                    adressesSearch.add(address);
                }

                adresses = adressesSearch;
                adresses = FilterAddress.filterAdresses(adresses, latitude, longitude);

                for (Address addr : adresses){
                    mapAdresses.put(addr.getId(), addr.getAddress());
                    mapDistance.put(addr.getId(), addr.getDistance());
                    TaskGetStore taskGetStore = new TaskGetStore();
                    taskGetStore.execute(addr.getId());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    /*This async task return one store from api through id*/
    class TaskGetStore extends AsyncTask<Integer, Void, String>{
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
                /*Create URL*/
                URL url = new URL(URL_API + "stores/" + integers[0]);

                /*Open a connection with object url*/
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                /*Get response dates as bytes*/
                inputStream = httpURLConnection.getInputStream();

                /*Convert inputStream from bytes to characters*/
                inputStreamReader = new InputStreamReader(inputStream);

                /*Create buffer for read characters*/
                bufferedReader = new BufferedReader(inputStreamReader);

                stringBuffer = new StringBuffer();

                String line;

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

            Store store = new Store();
            TypeStore typeStore = null;

            if (stores == null){
                stores = new ArrayList<>();
            }

            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONObject jsonObjectType = jsonObject.getJSONObject("type");

                store = new Store();
                store.setId(jsonObject.getInt("id"));
                store.setImage(jsonObject.getString("image"));
                store.setName(jsonObject.getString("name"));
                store.setOffers(null);
                typeStore = new TypeStore();
                typeStore.setId(jsonObjectType.getInt("id"));
                store.setType(typeStore);

                stores.add(store);

                mapStores.put(store.getId(), store);

                if (stores.size() == mapAdresses.size()){
                    createRecycleView(0);

                    if (mapAdresses.size() != allIdStores.size()){
                        for (Store storeT: stores){
                            TaskGetOffers taskGetOffers = new TaskGetOffers();
                            taskGetOffers.execute(storeT.getId());
                        }
                    }
                }

            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }

    /*This async task return a list of offers from api through id store */
    class TaskGetOffers extends AsyncTask<Integer, Void, String>{
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
                /*Create an url*/
                URL url = new URL(URL_API + "offers/" + integers[0]);

                /*Open a connection with object url*/
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                /*Get response dates as bytes*/
                inputStream = httpURLConnection.getInputStream();

                /*Convert inputStream from bytes to characters*/
                inputStreamReader = new InputStreamReader(inputStream);

                /*Create buffer for read characters*/
                bufferedReader = new BufferedReader(inputStreamReader);

                stringBuffer = new StringBuffer();

                String line;

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

            Store store = null;
            Sector sector = null;
            Product product = null;
            Offer offer = null;
            List<Offer> offers = new ArrayList<>();

            try{
                JSONObject jsonObjectStore = null;
                JSONObject jsonObjectSector = null;
                JSONObject jsonObjectProduct = null;
                JSONObject jsonObjectOffer = null;

                JSONArray jsonArray = new JSONArray(s);

                /*Add items offer into list offer*/
                for (int i = 0; i < jsonArray.length(); i++){
                    jsonObjectOffer = jsonArray.getJSONObject(i);

                    offer = new Offer();
                    offer.setDateInitial(jsonObjectOffer.getString("dateInitial"));
                    offer.setDateLimit(jsonObjectOffer.getString("dateLimit"));
                    offer.setImage(jsonObjectOffer.getString("image"));
                    offer.setPrice(jsonObjectOffer.getDouble("price"));

                    jsonObjectProduct = jsonObjectOffer.getJSONObject("product");
                    product = new Product();
                    product.setDescription(jsonObjectProduct.getString("description"));
                    product.setId(jsonObjectProduct.getInt("id"));

                    jsonObjectSector = jsonObjectProduct.getJSONObject("sector");
                    sector = new Sector();
                    sector.setId(jsonObjectSector.getInt("id"));

                    product.setSector(sector);

                    offer.setProduct(product);

                    offer.setStatus(jsonObjectOffer.getBoolean("status"));

                    jsonObjectStore = jsonObjectOffer.getJSONObject("store");
                    store = new Store();
                    store.setId(jsonObjectStore.getInt("id"));

                    offer.setStore(store);

                    offers.add(offer);
                }

                /*If there will be offers, this offers will be add in another list with all offers
                 independent stores*/
                if (offers != null) {
                    int aux = 0;

                    /*The array 'allIdStores' contains all id of stores that have already had their
                    offers added to the list of 'allOffers'*/
                    for (Integer id_store : allIdStores) {
                        if (id_store == offers.get(0).getStore().getId()) {
                            aux++;
                            break;
                        }
                    }

                    /*If the store has not yet had its offers added to the list 'allOffers' and
                    its id added to the array 'allIdStores'*/
                    if (aux == 0) {
                        allIdStores.add(offers.get(0).getStore().getId());
                    }

                    for (Offer offerAll: offers){
                        allOffers.add(offerAll);
                    }
                }

                /*If all offers already added into 'allOffers'*/
                if (mapAdresses.size() == allIdStores.size()){
                    //Close progress dialog
                    closeProgressDialog();
                }

            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
}
