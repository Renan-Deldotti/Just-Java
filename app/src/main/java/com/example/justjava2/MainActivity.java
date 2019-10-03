package com.example.justjava2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {
        int qntOfCoffee = checkQntView();
        double coffeePrice = getCoffeePrice(checkCup());
        String textToShow;
        if (qntOfCoffee != 0) {
            if (coffeePrice != 0) {
                textToShow = "Your ordered is being prepared :D\n\tThank you.";
                showSummary(true, qntOfCoffee,coffeePrice);
            }else {
                showSummary(false,0,0);
                textToShow = "Error";
            }
        }else {
            showSummary(false,0,0);
            textToShow = "Error";
        }
        Toast.makeText(this, textToShow, Toast.LENGTH_LONG).show();
    }

    private void showSummary(boolean b, int i,double d) {
        TextView summaryTextView = findViewById(R.id.summaryTextView);
        String textToShow = "";
        if(b) {
            textToShow =
                "Name: Username Here"
                + "\nQuantity: " + i
                + "\nTotal: " + NumberFormat.getCurrencyInstance().format(i * d);
            summaryTextView.setText(textToShow);
        }else{
            summaryTextView.setText(textToShow);
        }
    }

    private int checkQntView() {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        if (Integer.parseInt(quantityTextView.getText().toString().trim()) >= 0) {
            return Integer.parseInt(quantityTextView.getText().toString().trim());
        }
        return 0;
    }

    private short checkCup() {
        RadioGroup cupBtn = findViewById(R.id.cupButtons);
        switch (cupBtn.getCheckedRadioButtonId()) {
            case R.id.radioBtOne:
                return 1;
            case R.id.radioBtTwo:
                return 2;
            default:
                return 0;
        }
    }

    private double getCoffeePrice(short s) {
        double price;
        if (s == 1) price = 5.00;
        else if (s == 2) price = 3.50;
        else price = 0;
        return price;
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

    private void displayPrice() {
        TextView priceTxtView = findViewById(R.id.price_text_view);
        int qntOfCoffee = checkQntView();
        double coffeePrice = getCoffeePrice(checkCup());
        String textToShow = NumberFormat.getCurrencyInstance().format(qntOfCoffee * coffeePrice);
        priceTxtView.setText(textToShow);
    }

    public void displayPrice(View view) {
        displayPrice();
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
        displayPrice();
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
        displayPrice();
    }
}
