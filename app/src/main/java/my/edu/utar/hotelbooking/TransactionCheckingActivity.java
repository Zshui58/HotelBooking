package my.edu.utar.hotelbooking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TransactionCheckingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_checking);

        // Retrieve the user's transaction history from the intent
        Intent intent = getIntent();
        if (intent != null) {
            List<Transaction> transactions = (List<Transaction>) intent.getSerializableExtra("transactionChecking");

            // Retrieve check-in and check-out dates
            String checkInDate = intent.getStringExtra("checkInDate");
            String checkOutDate = intent.getStringExtra("checkOutDate");
            Toast.makeText(this, "Check-in Date: " + checkInDate, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Check-out Date: " + checkOutDate, Toast.LENGTH_SHORT).show();


            // Generate booking IDs for transactions if they don't have one
            for (Transaction transaction : transactions) {
                if (transaction.getBookingId() == null || transaction.getBookingId().isEmpty()) {
                    String bookingId = BookingIDGenerator.generateBookingId();
                    transaction.setBookingId(bookingId);
                }

                // Generate transaction IDs for transactions if they don't have one
                if (transaction.getTransactionId() == null || transaction.getTransactionId().isEmpty()) {
                    String transactionId = TransactionIDGenerator.generateId();
                    transaction.setTransactionId(transactionId);
                }
                // Set check-in and check-out dates for the transaction
                transaction.setCheckIn(checkInDate);
                transaction.setCheckOut(checkOutDate);
            }

            // Initialize the custom adapter with your custom row layout
            TransactionListAdapter adapter = new TransactionListAdapter(this, R.layout.list_item_transaction, transactions);

            // Set the adapter for the ListView
            ListView listView = findViewById(R.id.transactionListView);
            listView.setAdapter(adapter);
        }
    }
}