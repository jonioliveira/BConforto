package com.joniroliveira.bconforto.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joniroliveira.bconforto.R;
import com.joniroliveira.bconforto.data.model.Cloth;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by jonioliveira on 19/01/18.
 */

public class ClothRecyclerViewAdapter extends RecyclerView.Adapter<ClothRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Cloth> clothList;

    public ClothRecyclerViewAdapter() {
        getData();
    }

    public void getData(){
        clothList = new ArrayList(Realm.getDefaultInstance().where(Cloth.class).findAll());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_cloth_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(clothList.get(position).getName());
        holder.price.setText(String.valueOf(clothList.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return clothList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView price;
        View view;


        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            name = itemView.findViewById(R.id.cloth_row_name);
            price = itemView.findViewById(R.id.cloth_row_price);
        }
    }


}
