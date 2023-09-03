package my.edu.utar.hotelbooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class TransactionListAdapter extends ArrayAdapter<Transaction> {
    private Context context;
    private int resource;

    public TransactionListAdapter(Context context, int resource, List<Transaction> transactions) {
        super(context, resource, transactions);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        // Get the transaction at the current position
        Transaction transaction = getItem(position);

        // Bind transaction data to the custom row layout
        TextView bookingIdTextView = convertView.findViewById(R.id.bookingIdTextView);
        TextView transactionIdTextView = convertView.findViewById(R.id.transactionIdTextView);
        TextView checkInTextView = convertView.findViewById(R.id.checkInTextView);
        TextView checkOutTextView = convertView.findViewById(R.id.checkOutTextView);
        TextView amountTextView = convertView.findViewById(R.id.amountTextView);
        TextView statusTextView = convertView.findViewById(R.id.statusTextView);

        if (transaction != null) {
            bookingIdTextView.setText("Booking ID: " + transaction.getBookingId());
            transactionIdTextView.setText("Transaction ID: " + transaction.getTransactionId());
            checkInTextView.setText("Check-In: " + transaction.getCheckIn());
            checkOutTextView.setText("Check-Out: " + transaction.getCheckOut());
            amountTextView.setText("Amount: " + transaction.getAmount());
            statusTextView.setText("Status: " + (transaction.isPaymentStatus() ? "Paid" : "Unpaid"));
        }

        // Handle cancel button click here
        Button cancelButton = convertView.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the cancellation logic here
                // You can access the transaction associated with this row using the 'transaction' variable
                // For example, you can remove it from the list and update the adapter
            }
        });

        return convertView;
    }
}
