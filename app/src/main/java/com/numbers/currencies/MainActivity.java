package com.numbers.currencies;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.numbers.currencies.Database.CurrenciesDatabase;
import com.numbers.currencies.Network.CurrencyApi;
import com.numbers.currencies.Network.PairInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private RecyclerView currencies_rv;
    private Button add_button;
    private MainController controller;
    private CurrenciesAdapter adapter;
    private List<String> currencies =  Arrays.asList("USD", "AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD","AWG","AZN","BAM","BBD","BDT","BGN",
            "BHD","BIF","BMD","BND","BOB","BRL","BSD","BTN","BWP","BYN","BZD","CAD","CDF","CHF","CLP","CNY","COP","CRC","CUC","CUP",
            "CVE","CZK","DJF","DKK","DOP","DZD","EGP","ERN","ETB","EUR","FJD","FKP","FOK","GBP","GEL","GGP","GHS","GIP","GMD","GNF",
            "GTQ","GYD","HKD","HNL","HRK","HTG","HUF","IDR","ILS","IMP","INR","IQD","IRR","ISK","JMD","JOD","JPY","KES","KGS","KHR",
            "KID","KMF","KRW","KWD","KYD","KZT","LAK","LBP","LKR","LRD","LSL","LYD","MAD","MDL","MGA","MKD","MMK","MNT","MOP","MRU",
            "MUR","MVR","MWK","MXN","MYR","MZN","NAD","NGN","NIO","NOK","NPR","NZD","OMR","PAB","PEN","PGK","PHP","PKR","PLN","PYG",
            "QAR","RON","RSD","RUB","RWF","SAR","SBD","SCR","SDG","SEK","SGD","SHP","SLL","SOS","SRD","SSP","STN","SYP","SZL","THB",
            "TJS","TMT","TND","TOP","TRY","TTD","TVD","TWD","TZS","UAH","UGX","UYU","UZS","VES","VND","VUV","WST","XAF","XCD","XDR",
            "XOF","XPF","YER","ZAR","ZMW");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        controller = new MainController(this);

        init();
    }

    private void init(){
        // RecyclerView initialization
        currencies_rv = findViewById(R.id.currencies_rv);
        currencies_rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false ));
        adapter = new CurrenciesAdapter(this, controller);
        currencies_rv.setAdapter(adapter);

        // Add button initialization

        add_button = findViewById(R.id.add_currency_pair);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
                String report = " currency doesn't exist";
                new AlertDialog.Builder(MainActivity.this, R.style.MyDialogTheme)
                        .setView(dialogView)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText base_code_edit_text = dialogView.findViewById(R.id.base_currency_edit_text);
                                String base_code = base_code_edit_text.getText().toString();
                                EditText target_code_edit_text = dialogView.findViewById(R.id.target_currency_edit_text);
                                String target_code = target_code_edit_text.getText().toString();
                                if (!currencies.contains(base_code)){
                                    Toast.makeText(MainActivity.this, "'" + base_code + "'" + report, Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                    return;
                                }else if (!currencies.contains(target_code)){
                                    Toast.makeText(MainActivity.this, "'" + target_code + "'" + report, Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                    return;
                                }
                                controller.insertCurrency(base_code, target_code);
                                adapter.update();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

    }
}