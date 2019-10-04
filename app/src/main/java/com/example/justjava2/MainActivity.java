package com.example.justjava2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText mEditText = findViewById(R.id.usernameTextView);
        mEditText.setFilters(new InputFilter[]{getEditTextFilter()});
    }

    /**
     * Cria um filtro para receber apenas letras pelo textfield.
     * @return retorna a string formatada.
     */
    public static InputFilter getEditTextFilter() {
        return new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                boolean keepOriginal = true;
                StringBuilder sb = new StringBuilder(end - start);
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (isCharAllowed(c)) // put your condition here
                        sb.append(c);
                    else
                        keepOriginal = false;
                }
                if (keepOriginal)
                    return null;
                else {
                    if (source instanceof Spanned) {
                        SpannableString sp = new SpannableString(sb);
                        TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                        return sp;
                    } else {
                        return sb;
                    }
                }
            }

            private boolean isCharAllowed(char c) {
                Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
                Matcher ms = ps.matcher(String.valueOf(c));
                return ms.matches();
            }
        };
    }

    public void submitOrder(View view) {
        int qntOfCoffee = checkQntView();
        double coffeePrice = getCoffeePrice(checkCup());
        String textToShow;
        String username = checkForUsername();
        ArrayList<String> adt = new ArrayList<>();
        if (qntOfCoffee != 0) {
            if (coffeePrice != 0) {
                adt = checkAdditional();
                textToShow = "Your ordered is being prepared :D\n\tThank you.";
                showSummary(username,adt,true, qntOfCoffee, coffeePrice);
            } else {
                showSummary("",adt,false, 0, 0);
                textToShow = "Try Again";
            }
        } else if (qntOfCoffee == 0) {
            showSummary("",adt,false, 0, 0);
            textToShow = "Your cart is empty.";
        } else {
            showSummary("",adt,false, 0, 0);
            textToShow = "Try again.";
        }
        Toast.makeText(this, textToShow, Toast.LENGTH_LONG).show();
    }

    private String checkForUsername() {
        EditText usernameTxtView = findViewById(R.id.usernameTextView);
        return usernameTxtView.getText().toString();
    }

    private ArrayList<String> checkAdditional() {
        ArrayList<String> adts = new ArrayList<>();
        CheckBox adt = findViewById(R.id.whippedCream);
        if (adt.isChecked())
            adts.add("Whipped cream");
        adt = findViewById(R.id.chocolate);
        if (adt.isChecked())
            adts.add("Chocolate");
        return adts;
    }

    /**
     * Cria o summario e insere o valor na view summaryTextView e seta ela como visivel
     * @param userName nome do usuario, pego pelo metodo checkForUsername()
     * @param adt ArrayList de adicionais do café
     * @param b se tudo ocorreu corretamente, o valor deve ser verdadeiro, caso contrario,
     *         nada é mostrado.
     * @param i Quantidade de copos de café
     * @param d Valor de cada café
     */
    private void showSummary(String userName,ArrayList<String> adt, boolean b, int i, double d) {
        TextView summaryTextView = findViewById(R.id.summaryTextView);
        String textToShow = "";
        double addtionalPrice = 0;
        if (b) {
            textToShow = "Name: "+ userName + "\nQuantity: " + i;
            if(adt.size() > 0) {
                textToShow += "\nAditionals:";
                for (String adtional : adt) {
                    addtionalPrice += 1.50;
                    textToShow += "\n\t" + adtional;
                }
            }
            textToShow += "\nTotal: " + NumberFormat.getCurrencyInstance().format((i * d)+addtionalPrice);
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
        ArrayList<String> arrayList = checkAdditional();
        coffeePrice += (arrayList.size() * 1.50 );
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
