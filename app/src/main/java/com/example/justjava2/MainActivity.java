package com.example.justjava2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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

    /**
     * faz o pedido
     * @param view recebe a view que chamou o metodo
     */
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

    private void sendByEmail(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.setPackage("com.whatsapp");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Encontra o nome do usuario do app
     * @return uma String contendo o nome
     */
    private String checkForUsername() {
        EditText usernameTxtView = findViewById(R.id.usernameTextView);
        return usernameTxtView.getText().toString();
    }

    /**
     * Verifica por adicionais na bebida
     * @return retorna um ArrayList de adicionais
     */
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
     * Cria o summario e insere o valor na view summaryTextView, seta ela como visivel e
     * chama o método que cria uma intent e envia o pedido por whatsapp
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
            //sendByEmail(textToShow + "\nYour order is beeing prepared.\nThank you.");
        }else {
            summaryTextView.setText(textToShow);
        }
        summaryTextView.setText(textToShow);
        summaryTextView.setVisibility(View.VISIBLE);
    }

    /**
     * Checa a quantidade de café
     * @return -1 se a quantidade de cafés for qualquer coisa senão um numero maior ou igual a 0
     * ou retorna o numero de pedidos de café
     */
    private int checkQntView() {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        if (Integer.parseInt(quantityTextView.getText().toString().trim()) >= 0) {
            return Integer.parseInt(quantityTextView.getText().toString().trim());
        }
        return -1;
    }

    /**
     * checa o tipo do copo
     * @return 1 se - O botao 1 foi clicado
     * 2 se - o botao 2 foi clicado
     * 0 se algo deu errado ou algum erro ocorreu
     */
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

    /**
     * Retorna o valor do café de acordo com o copo (papel ou normal)
     * @param s definiçao do tipo do copo
     * @return o valor do preço
     */
    private double getCoffeePrice(short s) {
        double price;
        if (s == 1) price = 5.00;
        else if (s == 2) price = 3.50;
        else price = 0;
        return price;
    }

    /**
     * Mostra o preço atualizado na view price_text_view
     */
    private void displayPrice() {
        TextView priceTxtView = findViewById(R.id.price_text_view);
        int qntOfCoffee = checkQntView();
        double coffeePrice = getCoffeePrice(checkCup());
        ArrayList<String> arrayList = checkAdditional();
        coffeePrice += (arrayList.size() * 1.50 );
        String textToShow = NumberFormat.getCurrencyInstance().format(qntOfCoffee * coffeePrice);
        priceTxtView.setText(textToShow);
    }

    /**
     * acão recebida pelo botao e chama outro metodo
     * @param view
     */
    public void displayPrice(View view) {
        displayPrice();
    }

    /**
     * aumenta a quantidade
     * @param view botao clicado
     */
    public void increaseQuantity(View view) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        if (checkQntView() != -1) {
            quantityTextView.setText(String.valueOf(checkQntView() + 1));
        } else {
            quantityTextView.setText("0");
        }
        displayPrice();
    }

    /**
     * Diminui a quantidade
     * @param view recebe o botão que foi clicado
     */
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
