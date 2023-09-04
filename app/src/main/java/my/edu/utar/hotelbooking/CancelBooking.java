package my.edu.utar.hotelbooking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class CancelBooking extends AppCompatActivity {

    private Button cancelButton;
    private RadioGroup reasonRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_booking);

        // Initialize UI elements
        cancelButton = findViewById(R.id.cancelButton);
        reasonRadioGroup = findViewById(R.id.reasonRadioGroup);

        // Initially, disable the "Yes, cancel" button
        cancelButton.setEnabled(false);

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
