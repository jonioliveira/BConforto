package com.joniroliveira.bconforto.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jonioliveira on 18/01/18.
 */
public class Settings {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String PRICE_RESALE = "resalePriceKey";
    public static final String PRICE_CONSUMER = "consumerPriceKey";
    public static final String PRICE_FOAM = "foamKey";
    public static final String MARGIN_RESALE = "resaleMarginKey";
    public static final String MARGIN_CONSUMER = "resaleMarginConsumer";

    public static void writePriceResale(Context context, float value){
        SharedPreferences.Editor editor = context.getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
        editor.putFloat(PRICE_RESALE, value);
        editor.apply();
    }

    public static float getPriceResale(Context context){
        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        return preferences.getFloat(PRICE_RESALE, 0);
    }

    public static void writePriceConsumer(Context context, float value){
        SharedPreferences.Editor editor = context.getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
        editor.putFloat(PRICE_CONSUMER, value);
        editor.apply();
    }

    public static float getPriceConsumer(Context context){
        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        return preferences.getFloat(PRICE_CONSUMER, 0);
    }

    public static void writePriceFoam(Context context,  float value){
        SharedPreferences.Editor editor = context.getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
        editor.putFloat(PRICE_FOAM, value);
        editor.apply();
    }

    public static float getPriceFoam(Context context){
        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        return preferences.getFloat(PRICE_FOAM, 0);
    }

    public static void writeMarginResale(Context context,  int value){
        SharedPreferences.Editor editor = context.getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
        editor.putFloat(PRICE_FOAM, value);
        editor.apply();
    }

    public static int getMarginResale(Context context){
        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        return preferences.getInt(MARGIN_RESALE, 0);
    }

    public static void writeMarginConsumer(Context context,  int value){
        SharedPreferences.Editor editor = context.getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
        editor.putFloat(PRICE_FOAM, value);
        editor.apply();
    }

    public static int getMarginConsumer(Context context){
        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        return preferences.getInt(MARGIN_CONSUMER, 0);
    }

}
