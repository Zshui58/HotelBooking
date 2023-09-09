package my.edu.utar.hotelbooking;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PaymentActivity extends Activity {

    private ImageButton btnExit;
    private EditText editCardNumber, editExpiration, editCVV, editZipCode;
    private Spinner spinnerCountry;
    private Button btnSubmit;
    private Button viewTransactionsButton;
    private CheckBox checkBoxSaveForFutureUse;
    private SharedPreferences sharedPreferences;
    private List<Transaction> transactionHistory = new ArrayList<>();
    private String checkInDate;
    private String checkOutDate;
    private double grandTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        btnExit = findViewById(R.id.btnExit);
        editCardNumber = findViewById(R.id.editCardNumber);
        editExpiration = findViewById(R.id.editExpiration);
        editCVV = findViewById(R.id.editCVC);
        editZipCode = findViewById(R.id.editZipCode);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        checkBoxSaveForFutureUse = findViewById(R.id.checkBoxSaveForFutureUse);
        btnSubmit = findViewById(R.id.btnSubmit);
        viewTransactionsButton = findViewById(R.id.viewTransactionsButton);
        sharedPreferences = getSharedPreferences("PaymentData", MODE_PRIVATE);



        //Retrieve data from intent
        Intent intentReceive = getIntent();
        if (intentReceive != null) {
            checkInDate = intentReceive.getStringExtra("checkInDate");
            checkOutDate = intentReceive.getStringExtra("checkOutDate");
            grandTotal = intentReceive.getDoubleExtra("grandTotal", 0.0);
        }

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish the current activity and go back
            }
        });

        editExpiration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Insert '/' after two digits
                if (charSequence.length() == 2 && i2 == 1) {
                    int month = Integer.parseInt(charSequence.toString());
                    if (month > 12) {
                        Toast.makeText(PaymentActivity.this,
                                "Invalid month. Please enter a value between 01 and 12.",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    editExpiration.setText(charSequence + "/");
                    editExpiration.setSelection(editExpiration.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Populate spinner with country options
        String[] countries = {"United States", "Canada", "Malaysia", "Singapore"
                , "HongKong", "Japan", "Thailand", "Germany", "China", "Australia", "Others"};
        Arrays.sort(countries);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(adapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitClick(v);
            }
        });


        viewTransactionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Create an Intent to start the TransactionCheckingActivity
                Intent intent = new Intent(PaymentActivity.this, TransactionCheckingActivity.class);

                // Pass the transactionHistory list and the previous activity data to the TransactionCheckingActivity
                intent.putExtra("transactionChecking", (Serializable) transactionHistory);
                intent.putExtra("checkInDate", checkInDate);
                intent.putExtra("checkOutDate", checkOutDate);
                intent.putExtra("grandTotal", grandTotal);



                // Start the TransactionCheckingActivity
                startActivity(intent);
            }
        });
    }

    public void onSubmitClick(View view) {
        String cardNumber = editCardNumber.getText().toString().trim();
        String expiration = editExpiration.getText().toString().trim();
        String cvv = editCVV.getText().toString().trim();
        String selectedCountry = spinnerCountry.getSelectedItem().toString();

        if (cardNumber.isEmpty() || expiration.isEmpty() || cvv.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (cardNumber.length() < 16) {
            Toast.makeText(this, "Invalid card number.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (expiration.length() != 5 || !expiration.matches("\\d{2}/\\d{2}")) {
            Toast.makeText(this, "Invalid expiration date.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (cvv.length() != 3) {
            Toast.makeText(this, "Invalid CVV (use xxx).", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean paymentSuccess = simulatePaymentProcessing(cardNumber, expiration, cvv);

        if (paymentSuccess) {
            // Create a transaction record
            Transaction transaction = new Transaction();
            transaction.setCardNumber(cardNumber);
            transaction.setExpiration(expiration);
            transaction.setCvv(cvv);
            transaction.setCountry(selectedCountry);
            transaction.setZipCode(editZipCode.getText().toString().trim());
            transaction.setPaymentStatus(true);
            transaction.setPaymentDate(new Date());

            // Add the transaction to the history
            transactionHistory.add(transaction);

            // Display a success message
            Toast.makeText(this, "Payment successful!", Toast.LENGTH_SHORT).show();
        } else {
            // Display a failure message
            Toast.makeText(this, "Payment failed. Please try again.", Toast.LENGTH_SHORT).show();
        }

        if (checkBoxSaveForFutureUse.isChecked()) {
            savePaymentData(cardNumber, expiration, cvv, selectedCountry);
        }
    }

    private void savePaymentData(String cardNumber, String expiration, String cvv, String country) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CardNumber", cardNumber);
        editor.putString("Expiration", expiration);
        editor.putString("CVV", cvv);
        editor.putString("Country", country);
        editor.apply();
        Toast.makeText(this, "Payment information saved for future use.",
                Toast.LENGTH_SHORT).show();
    }

    private boolean simulatePaymentProcessing(String cardNumber, String expiration, String cvv) {
        return Math.random() < 0.8;
    }
}