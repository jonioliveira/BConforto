package com.joniroliveira.bconforto;

import com.joniroliveira.bconforto.service.CalculatePrice;

import org.junit.Test;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void service_without_discount_isCorrect(){
        CalculatePrice.Price calculate = CalculatePrice.calculate(1, 15, 1, 1, 10, 10, 20);
        double price = ((1 * 15) + (1*1));
        assertEquals(calculate.getPriceWithTax(), price * 1.23 , 0);
        assertEquals(calculate.getPriceWithoutTax(), price, 0);
    }

    @Test
    public void service_with_discount_isCorrect(){
        CalculatePrice.Price calculate = CalculatePrice.calculate(1, 15, 1, 1, 10, 30, 0);
        double price = 16 - 1.6;
        assertEquals(calculate.getPriceWithTax(), price * 1.23 , 0.05);
        assertEquals(calculate.getPriceWithoutTax(), price, 0.05);
    }
}