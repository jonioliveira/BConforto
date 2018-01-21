package com.joniroliveira.bconforto.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jonioliveira on 18/01/18.
 */
public class Settings {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String PRICE = "priceKey";

    public static void writePrice(Context context, float value){
        SharedPreferences.Editor editor = context.getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
        editor.putFloat(PRICE, value);
        editor.commit();
    }

    public static float getPrice(Context context){
        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        return preferences.getFloat(PRICE, 0);
    }

}
