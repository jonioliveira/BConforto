package com.joniroliveira.bconforto.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.joniroliveira.bconforto.R;
import com.joniroliveira.bconforto.data.model.Cloth;
import com.joniroliveira.bconforto.data.preferences.Settings;
import com.joniroliveira.bconforto.ui.adapters.ClothRecyclerViewAdapter;
import com.joniroliveira.bconforto.ui.controllers.SwipeController;
import com.joniroliveira.bconforto.ui.controllers.SwipeControllerActions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class SettingsActivity extends AppCompatActivity {

    Realm realm;

    @BindView(R.id.hour_resale_price) AppCompatEditText hourResalePrice;
    @BindView(R.id.hour_consumer_price) AppCompatEditText hourConsumerPrice;
    @BindView(R.id.clothList) RecyclerView clothRecyclerView;
    @BindView(R.id.foam_price) AppCompatEditText foamPrice;
    @BindView(R.id.margin_consumer) AppCompatEditText marginConsumer;
    @BindView(R.id.margin_resale) AppCompatEditText marginResale;

    private ClothRecyclerViewAdapter clothRecyclerViewAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingsActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        this.finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        float price = Settings.getPriceResale(this);
        hourResalePrice.setText(String.valueOf(price));
        price = Settings.getPriceConsumer(this);
        hourConsumerPrice.setText(String.valueOf(price));
        price = Settings.getPriceFoam(this);
        foamPrice.setText(String.valueOf(price));
        marginConsumer.setText(String.valueOf(Settings.getMarginConsumer(this)));
        marginResale.setText(String.valueOf(Settings.getMarginResale(this)));

        clothRecyclerViewAdapter = new ClothRecyclerViewAdapter(realm);
        clothRecyclerView.setAdapter(clothRecyclerViewAdapter);
        clothRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        final SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                clothRecyclerViewAdapter.removeData(position);
                super.onRightClicked(position);
            }

            @Override
            public void onLeftClicked(int position) {
                clothRecyclerViewAdapter.getItem(position);
                showUpdateDialog(clothRecyclerViewAdapter.getItem(position));
                clothRecyclerViewAdapter.updateData();
                super.onLeftClicked(position);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(clothRecyclerView);

        clothRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    private void showUpdateDialog(Cloth item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //you should edit this to fit your needs
        builder.setTitle("Atualizar Pano");
        LayoutInflater inflater = LayoutInflater.from(this);
        final View alertView = inflater.inflate(R.layout.add_cloth, null);
        ((AppCompatEditText)alertView.findViewById(R.id.cloth_name)).setText(item.getName());
        ((AppCompatEditText)alertView.findViewById(R.id.cloth_price)).setText(String.valueOf(item.getPrice()));
        builder.setView(alertView);
        // Set up the buttons
        builder.setPositiveButton("Ok", (dialog, whichButton) ->{

                    realm.executeTransaction(realm -> {
                        final RealmResults<Cloth> all = realm.where(Cloth.class).equalTo("name", item.getName()).and().equalTo("price", item.getPrice()).findAll();
                        all.deleteAllFromRealm();
                    });

                    insertInDb(((AppCompatEditText)alertView.findViewById(R.id.cloth_name)).getText().toString(),
                            ((AppCompatEditText)alertView.findViewById(R.id.cloth_price)).getText().toString());
                });

        builder.setNegativeButton("Cancelar", (dialog, whichButton) -> dialog.cancel());
        builder.show();
    }

    @Override
    protected void onPause() {
        realm.close();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.addCloth)
    public void addCloth(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //you should edit this to fit your needs
        builder.setTitle("Adicionar Pano");
        LayoutInflater inflater = LayoutInflater.from(this);
        final View alertView = inflater.inflate(R.layout.add_cloth, null);
        builder.setView(alertView);
        // Set up the buttons
        builder.setPositiveButton("Ok", (dialog, whichButton) -> insertInDb(((AppCompatEditText)alertView.findViewById(R.id.cloth_name)).getText().toString(), ((AppCompatEditText)alertView.findViewById(R.id.cloth_price)).getText().toString()));

        builder.setNegativeButton("Cancelar", (dialog, whichButton) -> dialog.cancel());
        builder.show();
    }

    @OnClick(R.id.save_button)
    public void save(){
        if (!hourConsumerPrice.getText().toString().isEmpty() &&
                !hourResalePrice.getText().toString().isEmpty() &&
                !foamPrice.getText().toString().isEmpty() &&
                !marginConsumer.getText().toString().isEmpty() &&
                !marginResale.getText().toString().isEmpty()){
            Settings.writePriceResale(this, Float.parseFloat(hourResalePrice.getText().toString()));
            Settings.writePriceConsumer(this, Float.parseFloat(hourConsumerPrice.getText().toString()));
            Settings.writePriceFoam(this, Float.parseFloat(foamPrice.getText().toString()));
            Settings.writeMarginConsumer(this, Integer.parseInt(marginConsumer.getText().toString()));
            Settings.writeMarginResale(this, Integer.parseInt(marginResale.getText().toString()));
            Toast.makeText(SettingsActivity.this, "Preços gravados", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(SettingsActivity.this, "Um dos preços está em branco", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertInDb(final String name, final String price) {
        realm.executeTransactionAsync(realm -> {
            Cloth cloth = new Cloth(name, Float.parseFloat(price));
            realm.insertOrUpdate(cloth);
        }, () -> {
            Toast.makeText(SettingsActivity.this, "Pano adicionado", Toast.LENGTH_SHORT).show();
            clothRecyclerViewAdapter.updateData();
        }, error -> Toast.makeText(SettingsActivity.this, "Erro ao Adicionar o pano", Toast.LENGTH_SHORT).show());
    }
}
