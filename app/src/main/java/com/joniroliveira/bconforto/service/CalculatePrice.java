package com.joniroliveira.bconforto.service;

import android.content.Context;

import com.joniroliveira.bconforto.data.preferences.Settings;

/**
 * Created by jonioliveira on 18/01/18.
 */

public class CalculatePrice {

    public static Price calculate(double hours, float hoursPrice, double clothPrice, double quantity, float foamPrice, float margin,  int discount){
        float marginCal = 1;
        if (margin != 0){
            marginCal = 1 + ( margin /100);
        }
        double price = (hours * hoursPrice) + (clothPrice * quantity) + foamPrice * marginCal;
        float aux = (float) (discount/100.0);
        double discountValue = price * aux;
        price = price - discountValue;
        return new Price(price* 1.23, price);
    }

    public static class Price{

        double priceWithTax;
        double priceWithoutTax;

        public Price(double priceWithTax, double priceWithoutTax) {
            this.priceWithTax = priceWithTax;
            this.priceWithoutTax = priceWithoutTax;
        }

        public double getPriceWithTax() {
            return priceWithTax;
        }

        public double getPriceWithoutTax() {
            return priceWithoutTax;
        }
    }
}
