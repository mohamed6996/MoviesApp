package com.example.lenovo.myapplication.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lenovo.myapplication.R;

public class DetailActivity extends AppCompatActivity {
   /* TextView overView, releaseDate, voteAvg;
    ImageView image_path;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.activity_detail, new DetailFragment()).commit();

        }

    }
}
