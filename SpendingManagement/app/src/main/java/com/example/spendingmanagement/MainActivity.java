package com.example.spendingmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    Button btn_plus, btn_minus;
    EditText date, amount, tag;
    EditText history;
    TextView balance_text;
    double balance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date = findViewById(R.id.date);
        amount = findViewById(R.id.amount);
        tag = findViewById(R.id.tag);
        history = findViewById(R.id.history);
        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        balance_text = findViewById(R.id.balance);

        SharedPreferences sharepre = getSharedPreferences("data", MODE_PRIVATE);
        balance = sharepre.getFloat("balance", 0);
        balance_text.setText("Current Balance:$" + balance);
        history.setText(sharepre.getString("history", ""));

        final SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = history.getText() + "Added $" + new DecimalFormat("0.00").format(Double.valueOf(amount.getText().toString())) + " on " + date.getText() + " from " + tag.getText() + "\n";
                history.setText(text);
                balance += Double.valueOf(amount.getText().toString());
                balance_text.setText("Current Balance:$" + new DecimalFormat("0.00").format(balance));
                editor.putFloat("balance", (float) balance);
                editor.putString("history", history.getText().toString());
                editor.commit();
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = history.getText() + "Spent $" + new DecimalFormat("0.00").format(Double.valueOf(amount.getText().toString())) + " on " + date.getText() + " from " + tag.getText() + "\n";
                history.setText(text);
                balance -= Double.valueOf(amount.getText().toString());
                balance_text.setText("Current Balance:$" + new DecimalFormat("0.00").format(balance));
                editor.putFloat("balance", (float) balance);
                editor.putString("history", history.getText().toString());
                editor.commit();
            }
        });


    }
}
