package com.example.justjava2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void submitOrder(View view) {
        RadioGroup cupBtn = (RadioGroup) findViewById(R.id.cupButtons);
        int selectRadio = cupBtn.getCheckedRadioButtonId();
        RadioButton selectedRadBtn = (RadioButton) findViewById(selectRadio);
        //RadioButton selectedRadBtn = (RadioButton) findViewById(cupBtn.getCheckedRadioButtonId());
        int numOfCoffee = 2;
        double coffeePrice = 5.25;
        String radioBtnIdText = selectedRadBtn.getText().toString();
        if (radioBtnIdText.equals("I need a new cup")){
            display(numOfCoffee);
            displayPrice(numOfCoffee * coffeePrice);
        }else if (radioBtnIdText.equals("I got a paper cup")){
            coffeePrice = 3.00;
            display(numOfCoffee);
            displayPrice(numOfCoffee * coffeePrice);
        }
    }
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    private void displayPrice(double i){
        TextView priceTxtView = findViewById(R.id.price_text_view);
        priceTxtView.setText(NumberFormat.getCurrencyInstance().format(i));
    }
}
