package my.edu.utar.hotelbooking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CancelBooking extends AppCompatActivity {

    private RadioGroup reasonRadioGroup;
    private RadioButton selectedReason;
    private Button cancelButton;

    // Assuming you have a DatabaseHelper class for database operations
    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_booking);

        // Initialize UI elements
        reasonRadioGroup = findViewById(R.id.reasonRadioGroup);
        cancelButton = findViewById(R.id.cancelButton);

        // Set a click listener for the "Yes, cancel" button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if a reason is selected
                int selectedReasonId = reasonRadioGroup.getCheckedRadioButtonId();

                if (selectedReasonId != -1) {
                    // A reason is selected
                    selectedReason = findViewById(selectedReasonId);
                    String reason = selectedReason.getText().toString();

                    // Create an instance of your DatabaseHelper
                    DatabaseHelper dbHelper = new DatabaseHelper(CancelBooking.this);

                    // Show a confirmation message
                    Toast.makeText(CancelBooking.this, "Booking canceled. Reason: " + reason, Toast.LENGTH_SHORT).show();
                } else {
                    // No reason is selected
                    Toast.makeText(CancelBooking.this, "Please select a reason", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
