package com.example.lenovo.myapplication.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lenovo.myapplication.R;

public class MainActivity extends AppCompatActivity {

    public static boolean mTWO_PANE = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.detail_container) != null) {

            mTWO_PANE = true;
        }

    }
}
