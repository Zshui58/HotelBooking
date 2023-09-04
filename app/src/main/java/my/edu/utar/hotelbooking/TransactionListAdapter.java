package my.edu.utar.hotelbooking;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import java.util.List;

public class TransactionListAdapter extends ArrayAdapter<Transaction> {
    private Context context;
    private int resource;
    private List<Transaction> transactions;

    public TransactionListAdapter(Context context, int resource, List<Transaction> transactions) {
        super(context, resource, transactions);
        this.context = context;
        this.resource = resource;
        this.transactions = transactions;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        // Get the transaction at the current position
        final Transaction transaction = getItem(position);

        // Bind transaction data to the custom row layout
        TextView bookingIdTextView = convertView.findViewById(R.id.bookingIdTextView);
        TextView transactionIdTextView = convertView.findViewById(R.id.transactionIdTextView);
        TextView checkInTextView = convertView.findViewById(R.id.checkInTextView);
        TextView checkOutTextView = convertView.findViewById(R.id.checkOutTextView);
        TextView amountTextView = convertView.findViewById(R.id.amountTextView);
        TextView statusTextView = convertView.findViewById(R.id.statusTextView);
        Button cancelButton = convertView.findViewById(R.id.cancelButton);

        if (transaction != null) {
            bookingIdTextView.setText("Booking ID: " + transaction.getBookingId());
            transactionIdTextView.setText("Transaction ID: " + transaction.getTransactionId());
            checkInTextView.setText("Check-In: " + transaction.getCheckIn());
            checkOutTextView.setText("Check-Out: " + transaction.getCheckOut());
            amountTextView.setText("Amount: " + transaction.getAmount());
            statusTextView.setText("Status: " + (transaction.isPaymentStatus() ? "Paid" : "Unpaid"));

            // Handle cancel button click
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Show a confirmation dialog for cancellation
                    showConfirmationDialog(position);
                    Context context = v.getContext();

                    Intent intent = new Intent(context, CancelBooking.class);
                    context.startActivity(intent);
                }
            });
        }

        return convertView;
    }

    private void showConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Cancel Booking");
        builder.setMessage("Are you sure you want to cancel this booking?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User confirmed cancellation, handle cancellation logic here

                // Remove the transaction from the list
                transactions.remove(position);

                // Notify the adapter that the data set has changed
                notifyDataSetChanged();

                // Dismiss the dialog
                dialog.dismiss();

                // Show a confirmation message
                Toast.makeText(context, "Booking cancelled.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User canceled the confirmation dialog, do nothing
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
