package com.example.lenovo.myapplication.UI;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {
    TextView review;

    public ReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        String value = getArguments().getString("reviewKey");

        review = (TextView) view.findViewById(R.id.review_fragment);
        review.setText(value);
        return view;
    }

}
