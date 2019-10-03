package com.example.justjava2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;

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
        ArrayList<String> adt = new ArrayList<>();
        if (qntOfCoffee != 0) {
            if (coffeePrice != 0) {
                adt = checkAdditional();
                textToShow = "Your ordered is being prepared :D\n\tThank you.";
                showSummary(adt,true, qntOfCoffee, coffeePrice);
            } else {
                showSummary(adt,false, 0, 0);
                textToShow = "Try Again";
            }
        } else if (qntOfCoffee == 0) {
            showSummary(adt,false, 0, 0);
            textToShow = "Your cart is empty.";
        } else {
            showSummary(adt,false, 0, 0);
            textToShow = "Try again.";
        }
        Toast.makeText(this, textToShow, Toast.LENGTH_LONG).show();
    }

    private ArrayList<String> checkAdditional() {
        ArrayList<String> adts = new ArrayList<String>();
        CheckBox adt = findViewById(R.id.whippedCream);
        if (adt.isChecked())
            adts.add("Whipped cream");
        adt = findViewById(R.id.chocolate);
        if (adt.isChecked())
            adts.add("Chocolate");
        return adts;
    }

    private void showSummary(ArrayList<String> adt, boolean b, int i, double d) {
        TextView summaryTextView = findViewById(R.id.summaryTextView);
        String textToShow = "";
        if (b) {
            textToShow = "Name: Username Here" + "\nQuantity: " + i;
            if(adt.size() > 0) {
                textToShow += "\nAditionals:";
                for (String adtional : adt) {
                    textToShow += "\n\t" + adtional;
                }
            }
            textToShow += "\nTotal: " + NumberFormat.getCurrencyInstance().format(i * d);
        }else {
            summaryTextView.setText(textToShow);
        }
        summaryTextView.setText(textToShow);
        summaryTextView.setVisibility(View.VISIBLE);
    }

    private int checkQntView() {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        if (Integer.parseInt(quantityTextView.getText().toString().trim()) >= 0) {
            return Integer.parseInt(quantityTextView.getText().toString().trim());
        }
        return -1;
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
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        if (checkQntView() != -1) {
            quantityTextView.setText(String.valueOf(checkQntView() + 1));
        } else {
            quantityTextView.setText("0");
        }
        displayPrice();
    }

    public void decreaseQuantity(View view) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        if (checkQntView() != -1 && checkQntView() > 0) {
            quantityTextView.setText(String.valueOf(checkQntView() - 1));
        } else {
            quantityTextView.setText("0");
        }
        displayPrice();
    }
}
