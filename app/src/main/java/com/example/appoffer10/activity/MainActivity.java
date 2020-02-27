package com.example.appoffer10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appoffer10.activity.OfferActivity;
import com.example.appoffer10.adapter.AdapterStores;
import com.example.appoffer10.api.model.Store;
import com.example.appoffer10.api.model.StoreService;
import com.example.appoffer10.util.RecyclerItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAllStores;
    private Button buttonSubmit;
    private TextView textViewResult;
    private Retrofit retrofit;
    private List<Store> stores;
    private List<Store> storeSearch;
    private EditText editTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSubmit = findViewById(R.id.buttonSubmit);
        editTextSearch = findViewById(R.id.editTextSearch);

        retrofit = new Retrofit.Builder().
                baseUrl("https://app-offers-one.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

         buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchStoreAPI();
            }
        });

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                Toast.makeText(getApplicationContext(), "Before text change",  Toast.LENGTH_SHORT).show();

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

    public List<Store> getStores(){
        return stores;
    }

    public void setStores(List<Store> stores){
        this.stores = stores;
    }

    public List<Store> getStoreSearch(){ return storeSearch; }

    public void setStoreSearch(List<Store> storeSearch){ this.storeSearch = storeSearch; }

    private void searchStoreAPI(){
        StoreService storeService = retrofit.create(StoreService.class);
        Call<List<Store>> call = storeService.searchStore();

        call.enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                if (response.isSuccessful()){
                    stores = response.body();
                    setStores(stores);
                    createRecycleView(getStores());
                }
            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {

            }
        });
    }

    private void searchStore(String textedit){

    }

    private void createRecycleView(List<Store> stores){
        recyclerViewAllStores = findViewById(R.id.recyclerViewAllStores);

        //Configure adapter
        AdapterStores adapterStores =  new AdapterStores(stores, this);

        //Configure Recycleview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewAllStores.setLayoutManager(layoutManager);
        recyclerViewAllStores.setHasFixedSize(true);
        recyclerViewAllStores.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerViewAllStores.setAdapter(adapterStores);

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
}
