package com.example.lenovo.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.myapplication.Constants.Constants;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.UI.DetailActivity;
import com.example.lenovo.myapplication.UI.DetailFragment;
import com.example.lenovo.myapplication.UI.MainActivity;
import com.example.lenovo.myapplication.model.ItemModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 10/24/2016.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.VH> {
    List<ItemModel> mDataset;
    Context context;

    public ItemAdapter(List<ItemModel> mDataset, Context context) {
        this.mDataset = mDataset;
        this.context = context;
    }


    @Override

    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new VH(view, this.context, this.mDataset);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        ItemModel model = mDataset.get(position);

        String FULL_IMG = Constants.IMG_BASE + model.getImagePath();

        Glide.with(context).load(FULL_IMG).into(holder.imageView);
        holder.textView.setText(model.getFilm_name());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public static class VH extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;

        Context vhContext;
        List<ItemModel> vhDataSet = new ArrayList<>();

        public VH(View itemView, final Context context, final List<ItemModel> vhDataSet) {
            super(itemView);

            this.vhContext = context;
            this.vhDataSet = vhDataSet;
            textView = (TextView) itemView.findViewById(R.id.count);

            imageView = (ImageView) itemView.findViewById(R.id.img);
            imageView.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            ItemModel m = vhDataSet.get(position);

            Bundle b = new Bundle();

            Gson gson = new Gson();
            String jsonString = gson.toJson(m, ItemModel.class);
            b.putString(Constants.JSON_ITEM_KEY, jsonString);
            b.putString(Constants.FILM_NAME_KEY, m.getFilm_name());
            b.putString(Constants.OVER_VIEW_KEY, m.getOver_view());
            b.putString(Constants.RELESE_DAT_KEY, m.getRelease_date());
            b.putString(Constants.VOTE_AVG_KEY, m.getVote_average());
            b.putString(Constants.IMAGE_PATH_KEY, m.getImagePath());
            b.putString(Constants.BACK_IMG_PATH_KEY, m.getBackdrop_path());
            b.putString(Constants.ID_KEY, m.getId());

            if (MainActivity.mTWO_PANE) {
                DetailFragment detailFragment = new DetailFragment();
                detailFragment.setArguments(b);
                ((MainActivity) vhContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, detailFragment)
                        .addToBackStack(null)
                        .commit();
            } else {
                Intent intent = new Intent(this.vhContext, DetailActivity.class);
                intent.putExtras(b);
                this.vhContext.startActivity(intent);
            }

        }
    }
}
