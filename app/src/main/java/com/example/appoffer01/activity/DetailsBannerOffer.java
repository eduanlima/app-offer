package com.example.appoffer01.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.example.appoffer01.api.model.BannerOffer;
import com.example.appoffer01.util.PicassoTrustAll;
import com.example.appoffer10.R;
import com.github.chrisbanes.photoview.PhotoView;

public class DetailsBannerOffer extends AppCompatActivity {

    private BannerOffer bannerOffer;
    private ImageView imageViewBannerOffer;
    private PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_banner_offer);

        //Get object from previous Activity
        Bundle bundle = getIntent().getExtras();

        //Get object form Bundle
        bannerOffer = (BannerOffer) bundle.getSerializable("bannerOffer");
    }

    @Override
    protected void onStart(){
        super.onStart();

        //Set image
        photoView = findViewById(R.id.imageViewBannerOffer);
        PicassoTrustAll.getInstance(this).load(bannerOffer.getImage()).into(photoView);
    }
}
