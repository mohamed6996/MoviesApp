package com.example.lenovo.myapplication.UI;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lenovo.myapplication.Constants.Constants;
import com.example.lenovo.myapplication.adapter.ItemAdapter;
import com.example.lenovo.myapplication.model.ItemModel;
import com.example.lenovo.myapplication.singleton.MySingleton;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.db.MovieHelper;
import com.example.lenovo.myapplication.db.MoviesContract;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ItemFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ItemModel> mDataSet;
    public static int type;

    public ItemFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mDataSet = new ArrayList<>();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.PREF_SELECTION, getActivity().MODE_PRIVATE);
        sharedPreferences.getInt(Constants.KEY_POPULAR, 0);
        sharedPreferences.getInt(Constants.KEY_TOP_RATED, 0);

        switch (type) {
            case 1:
                initDataset(Constants.POPULAR);
                break;
            case 2:
                initDataset(Constants.TOP_RATED);
                break;
            default:
                initDataset(Constants.POPULAR);

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_item, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.PREF_SELECTION, getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int id = item.getItemId();
        if (id == R.id.popular) {
            type = 1;
            editor.putInt(Constants.KEY_POPULAR, 1);
            if (mDataSet != null) mDataSet.clear();
            initDataset(Constants.POPULAR);
            return true;
        }
        if (id == R.id.top_rated) {
            type = 2;
            editor.putInt(Constants.KEY_TOP_RATED, 2);
            if (mDataSet != null) mDataSet.clear();
            initDataset(Constants.TOP_RATED);
            return true;
        }
        if (R.id.favorits == id) {

            Gson gson = new Gson();
            MovieHelper movieHelper = new MovieHelper(getContext());
            SQLiteDatabase db = movieHelper.getReadableDatabase();

            Cursor cursor = db.query(MoviesContract.MovieEntry.TABLE_NAME, null, null, null, null, null, null);
            if (mDataSet != null) mDataSet.clear();

            try {
                while (cursor.moveToNext()) {
                    String jsonString = cursor.getString(1);
                    ItemModel model = gson.fromJson(jsonString, ItemModel.class);
                    mDataSet.add(model);
                    mAdapter.notifyDataSetChanged();

                }
            } finally {
                cursor.close();
                return true;
            }
        }

        editor.apply();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);


        if (MainActivity.mTWO_PANE) {
            mLayoutManager = new GridLayoutManager(getActivity(), 3);
        } else {
            mLayoutManager = new GridLayoutManager(getActivity(), 2);
        }


        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new ItemAdapter(mDataSet, getContext());
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }


    private void initDataset(String url) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String poster_path = jsonObject1.getString("poster_path");
                                String backdrop_path = jsonObject1.getString("backdrop_path");
                                String original_title = jsonObject1.getString("original_title");
                                String over_view = jsonObject1.getString("overview");
                                String release_date = jsonObject1.getString("release_date");
                                String vote_average = jsonObject1.getString("vote_average");
                                String id = jsonObject1.getString("id");

                                ItemModel model = new ItemModel(poster_path, backdrop_path, original_title, over_view, release_date, vote_average, id);
                                mDataSet.add(model);
                                mAdapter.notifyDataSetChanged();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        MySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);


    }


}
