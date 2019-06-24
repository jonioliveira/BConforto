package com.joniroliveira.bconforto.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.joniroliveira.bconforto.data.model.Cloth;

import java.util.ArrayList;

/**
 * Created by jonioliveira on 18/01/18.
 */

public class ClothListAdapter extends ArrayAdapter<Cloth> {
    @NonNull
    private final Context context;
    private final ArrayList clothList;

    public ClothListAdapter(@NonNull Context context, int resource, ArrayList clothList) {
        super(context, resource);
        this.context = context;
        this.clothList = clothList;
    }

    @Override
    public int getCount() {
        return clothList.size();
    }

    @Nullable
    @Override
    public Cloth getItem(int position) {
        return (Cloth) clothList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(((Cloth) clothList.get(position)).getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(((Cloth) clothList.get(position)).getName());
        label.setPadding(25,25,25,25);
        return label;
    }
}
