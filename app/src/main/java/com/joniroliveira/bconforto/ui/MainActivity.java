package com.joniroliveira.bconforto.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.joniroliveira.bconforto.R;
import com.joniroliveira.bconforto.data.model.Cloth;
import com.joniroliveira.bconforto.data.preferences.Settings;
import com.joniroliveira.bconforto.service.CalculatePrice;
import com.joniroliveira.bconforto.ui.adapters.ClothListAdapter;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.clothSpinner)
    AppCompatSpinner clothSpinner;

    @BindView(R.id.hours)
    AppCompatEditText hours;

    @BindView(R.id.clothMeters)
    AppCompatEditText clothMeters;

    @BindView(R.id.discount)
    AppCompatEditText discount;

    @BindView(R.id.priceWithTax)
    TextView priceWithTax;

    @BindView(R.id.priceWithoutTax)
    TextView priceWithoutTax;

    Realm realm;

    private ArrayList clothList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        realm = Realm.getDefaultInstance();
        clothList = new ArrayList(realm.where(Cloth.class).findAll());
        Timber.i("asdf %s", clothList.size());
        SpinnerAdapter adapter = new ClothListAdapter(this, android.R.layout.simple_spinner_item, clothList);
        clothSpinner.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        realm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SettingsActivity.start(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.calc_price)
    public void calcPrice(){

        if (hours.getText().toString().isEmpty() || clothMeters.getText().toString().isEmpty()){
            Toast.makeText(this, "Os campos Horas e Metros são obrigatórios", Toast.LENGTH_LONG).show();
        }else{
            int discountValue = 0;
            if (!discount.getText().toString().isEmpty()){
                discountValue = Integer.parseInt(discount.getText().toString());
            }

            float hoursPrice = Settings.getPrice(this);

            if (hoursPrice == 0){
                Toast.makeText(this, "O preço das horas não está definido", Toast.LENGTH_LONG).show();
            }else{

                CalculatePrice.Price calculate = CalculatePrice.calculate(
                        Double.parseDouble(hours.getText().toString()),
                        hoursPrice,
                        ((Cloth) clothList.get(clothSpinner.getSelectedItemPosition())).getPrice(),
                        Double.parseDouble(clothMeters.getText().toString()),
                        discountValue);

                DecimalFormat df = new DecimalFormat("#.####");
                df.setRoundingMode(RoundingMode.CEILING);

                priceWithTax.setText(df.format(calculate.getPriceWithTax()));
                priceWithoutTax.setText(df.format(calculate.getPriceWithoutTax()));
            }


        }
    }
}
