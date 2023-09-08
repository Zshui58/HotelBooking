package my.edu.utar.hotelbooking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CancelBooking extends AppCompatActivity {

    private Button cancelButton;
    private RadioGroup reasonRadioGroup;
    private TextView totalEligiblePriceTextView;
    double grandTotal;

    private double roomPrice; // Room price from the booking details
    private double taxes = 50.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_booking);

        TextView refundRoom = findViewById(R.id.totalRoomPrices);

        // Retrieve the grandTotal from the intent extras
        Intent intent = getIntent();
        double[] roomPrices = getIntent().getDoubleArrayExtra("roomPrices");
        if (intent != null) {
            grandTotal = intent.getDoubleExtra("grandTotal",0.0);

        }
        String strGrandTotal = Double.toString(grandTotal);
        refundRoom.setText(strGrandTotal);

        // Initialize UI elements
        cancelButton = findViewById(R.id.cancelButton);
        reasonRadioGroup = findViewById(R.id.reasonRadioGroup);
        totalEligiblePriceTextView = findViewById(R.id.totalPriceTextView);

        // Initially, disable the "Yes, cancel" button
        cancelButton.setEnabled(false);

        // Retrieve booking data from the previous activity (e.g., BookingRoomDetails)
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            roomPrice = extras.getDouble("roomPrice"); // Replace with your actual key
        }

        // Set a click listener for the "Cancel" button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Display the confirmation dialog
                showCancelConfirmationDialog();
            }
        });

        // Set a listener for the RadioGroup to enable/disable the button based on selection
        reasonRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                // Enable the button if a reason is selected, otherwise disable it
                cancelButton.setEnabled(checkedId != -1);
            }
        });

        // Calculate and display the total eligible price
        calculateTotalEligiblePrice();
    }

    // Method to calculate and display the total eligible price
    private void calculateTotalEligiblePrice() {
        double totalEligiblePrice = grandTotal - taxes;
        // You can add more fees or deductions here as needed

        // Display the total eligible price to the user
        totalEligiblePriceTextView.setText("RM " + totalEligiblePrice);
    }

    // Method to show the confirmation dialog
    private void showCancelConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to cancel your booking?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked "Yes," take action (e.g., redirect to cancellation page)
                        redirectToCancellationPage();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked "No," do nothing (dismiss the dialog)
                        dialog.dismiss();
                    }
                });
        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Method to show a cancellation success message (you can replace this with your own logic)
    private void showCancellationSuccessMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Booking canceled successfully!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked "OK," redirect to the main activity
                        redirectToMainActivity();
                    }
                });
        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Method to redirect to the main activity
    private void redirectToMainActivity() {
        // Create an Intent to start the main activity
        Intent intent = new Intent(CancelBooking.this, MainActivity.class);
        // Start the main activity
        startActivity(intent);
        // Finish the current activity (CancelBooking)
        finish();
    }

    // Method to redirect to the cancellation page (replace with your actual code)
    private void redirectToCancellationPage() {
        // Assuming the cancellation was successful, show a success message
        showCancellationSuccessMessage();
    }
}
