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
        int qntOfCoffee;
        double coffeePrice = 5.25;
        RadioGroup cupBtn = (RadioGroup) findViewById(R.id.cupButtons);
        //int selectRadio = cupBtn.getCheckedRadioButtonId();
        //RadioButton selectedRadBtn = (RadioButton) findViewById(selectRadio);
        RadioButton selectedRadBtn = (RadioButton) findViewById(cupBtn.getCheckedRadioButtonId());
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        if (valNumCoffee(quantityTextView) == 0 || valNumCoffee(quantityTextView) == 1) {
            qntOfCoffee = Integer.parseInt(quantityTextView.getText().toString().trim());
        } else {
            quantityTextView.setText(0);
            qntOfCoffee = 0;
        }
        String radioBtnIdText = selectedRadBtn.getText().toString();
        if (radioBtnIdText.equals("I need a new cup")) {
            displayPrice(qntOfCoffee * coffeePrice);
        } else if (radioBtnIdText.equals("I got a paper cup")) {
            coffeePrice = 3.00;
            displayPrice(qntOfCoffee * coffeePrice);
        }
    }

    private int valNumCoffee(TextView textView) {
        int i = Integer.parseInt(textView.getText().toString().trim());
        if (i < 0) {
            return -1;
        } else if (i == 0) {
            return 0;
        } else if (i > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    private void displayPrice(double i) {
        TextView priceTxtView = findViewById(R.id.price_text_view);
        priceTxtView.setText(NumberFormat.getCurrencyInstance().format(i));
    }

    public void increaseQuantity(View view) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        if (valNumCoffee(quantityTextView) == 0 || valNumCoffee(quantityTextView) == 1) {
            int qntOfCoffee = Integer.parseInt(quantityTextView.getText().toString().trim());
            qntOfCoffee++;
            quantityTextView.setText("" + qntOfCoffee);
        } else {
            quantityTextView.setText("0");
        }
    }

    public void decreaseQuantity(View view) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        if (valNumCoffee(quantityTextView) == 1) {
            int qntOfCoffee = Integer.parseInt(quantityTextView.getText().toString().trim());
            qntOfCoffee--;
            quantityTextView.setText("" + qntOfCoffee);
        } else {
            quantityTextView.setText("0");
        }
    }
}
