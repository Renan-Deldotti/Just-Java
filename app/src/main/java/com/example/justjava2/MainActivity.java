package com.example.justjava2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void submitOrder(View view) {
        int numOfCoffee = 2;
        int coffeePrice = 5;
        display(numOfCoffee);
        displayPrice(numOfCoffee * coffeePrice);
    }
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    private void displayPrice(int i){
        TextView priceTxtView = findViewById(R.id.price_text_view);
        priceTxtView.setText(NumberFormat.getCurrencyInstance().format(i));
    }
}
