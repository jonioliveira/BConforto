package com.joniroliveira.bconforto.ui.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joniroliveira.bconforto.R;
import com.joniroliveira.bconforto.data.model.Cloth;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by jonioliveira on 19/01/18.
 */

public class ClothRecyclerViewAdapter extends RecyclerView.Adapter<ClothRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Cloth> clothList;
    private Realm realm;


    public ClothRecyclerViewAdapter(Realm realm) {
        this.realm = realm;
        getData();
    }

    private void getData(){
        clothList = new ArrayList(realm.where(Cloth.class).findAll());
    }

    public void updateData(){
        getData();
        notifyDataSetChanged();
    }

    public Cloth getItem(int position){
        return clothList.get(position);
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
        holder.price.setText(String.valueOf(clothList.get(position).getPrice()) + " €");
    }

    @Override
    public int getItemCount() {
        return clothList.size();
    }

    public void removeData(int position) {
        final Cloth cloth = clothList.get(position);
        clothList.remove(position);
        RealmResults<Cloth> id = realm.where(Cloth.class).equalTo("id", cloth.getId()).findAll();
        realm.beginTransaction();
        id.deleteAllFromRealm();
        realm.commitTransaction();
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, clothList.size());
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
