package com.joniroliveira.bconforto.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.joniroliveira.bconforto.R;
import com.joniroliveira.bconforto.data.model.Cloth;
import com.joniroliveira.bconforto.data.preferences.Settings;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class SettingsActivity extends AppCompatActivity {

    Realm realm;

    @BindView(R.id.hour_price) AppCompatEditText hourPrice;

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
        float price = Settings.getPrice(this);
        hourPrice.setText(String.valueOf(price));
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
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                insertInDb(((AppCompatEditText)alertView.findViewById(R.id.cloth_name)).getText().toString(), ((AppCompatEditText)alertView.findViewById(R.id.cloth_price)).getText().toString());
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    @OnClick(R.id.save_hour_button)
    public void saveHourPrice(){
        Settings.writePrice(this, Float.parseFloat(hourPrice.getText().toString()));
        Toast.makeText(SettingsActivity.this, "Preço gravado", Toast.LENGTH_SHORT).show();
    }

    private void insertInDb(final String name, final String price) {


        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Cloth cloth = new Cloth(name, Float.parseFloat(price));
                realm.insertOrUpdate(cloth);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(SettingsActivity.this, "Pano adicionado", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(SettingsActivity.this, "Erro ao Adicionar o pano", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
