package my.edu.utar.hotelbooking;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.Calendar;

public class BookingRoomDetails extends AppCompatActivity {

    private EditText checkInEditText;
    private Calendar calendar;
    Button arrivalDateButton;
    Button departureDateButton;
    TextView hotelNameTextView;
    TextView hotelAddressTextView;
    TextView participantsLabel;
    TextView adultsLabel;
    TextView childrenLabel;
    Spinner adultsSpinner;
    Spinner childrenSpinner;
    TextView roomPriceTextView;
    String selectedRoomType;
    TextView numberOfRoomsTextView;
    TextView numberOfNightsTextView;
    TextView adminFeeTextView;
    TextView totalTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_room_details);

        // Initialize UI elements
        roomPriceTextView = findViewById(R.id.roomPriceTextView);

        // Initialize the roomTypeSpinner
        Spinner roomTypeSpinner = findViewById(R.id.roomTypeSpinner);

        // Create ArrayAdapter for room type Spinner
        ArrayAdapter<CharSequence> roomTypeAdapter = ArrayAdapter.createFromResource(this, R.array.room_types, android.R.layout.simple_spinner_item);
        roomTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter to the room type Spinner
        roomTypeSpinner.setAdapter(roomTypeAdapter);


        numberOfRoomsTextView = findViewById(R.id.numberOfRoomsTextView);
        numberOfNightsTextView = findViewById(R.id.numberOfNightsTextView);
        adminFeeTextView = findViewById(R.id.adminFeeTextView);
        totalTextView = findViewById(R.id.totalTextView);


        // Set up a selection listener for the room type Spinner
        roomTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Retrieve the selected room type
                selectedRoomType = parentView.getItemAtPosition(position).toString();

                // Update the room price based on the selected room type (replace with your logic)
                double roomPrice = getRoomPrice(selectedRoomType);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle the case when nothing is selected (if needed)
            }

        });

        // Set up a selection listener for the room type Spinner
        roomTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Retrieve the selected room type
                selectedRoomType = parentView.getItemAtPosition(position).toString();

                // Update the room price based on the selected room type (replace with your logic)
                double roomPrice = getRoomPrice(selectedRoomType); // Replace with your price calculation logic
                String priceText = "Price: RM" + roomPrice; // Format the price

                // Set the updated price in the TextView
                roomPriceTextView.setText(priceText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle the case when nothing is selected (if needed)
            }

        });

        // Set up a selection listener for the room type Spinner
        roomTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Retrieve the selected room type
                String selectedRoomType = parentView.getItemAtPosition(position).toString();

                // Update the room price based on the selected room type (replace with your logic)
                double roomPrice = getRoomPrice(selectedRoomType); // Replace with your price calculation logic
                String priceText = "Price: RM" + roomPrice; // Format the price

                // Set the updated price in the TextView
                roomPriceTextView.setText(priceText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle the case when nothing is selected (if needed)
            }

            // You can implement your own logic to retrieve room prices based on room types.
            private double getRoomPrice(String roomType) {
                // Replace with your logic to fetch room prices based on room types
                // For this example, we'll use a simple mapping.
                if ("Standard Single Room".equals(roomType)) {
                    return 166.0; // Replace with the actual price for the Standard room
                } else if ("Standard Double Room".equals(roomType)) {
                    return 340.0; // Replace with the actual price for the Deluxe room
                } else if ("Deluxe Twin".equals(roomType)) {
                    return 328.0; // Replace with the actual price for the Deluxe room
                } else if ("Deluxe Family".equals(roomType)) {
                    return 448.0; // Replace with the actual price for the Deluxe room
                } else if ("Executive Suite".equals(roomType)) {
                    return 999.0; // Replace with the actual price for the Suite
                } else if ("Superior Suite".equals(roomType)) {
                    return 1388.0; // Replace with the actual price for the Suite
                } else if ("Deluxe Suite".equals(roomType)) {
                    return 1688.0; // Replace with the actual price for the Suite
                } else if ("Premier Suite".equals(roomType)) {
                    return 1888.0; // Replace with the actual price for the Suite
                }
                return 0.0; // Return a default price for unknown room types
            }



        });

        // Initialize UI elements (Participants section)
        participantsLabel = findViewById(R.id.participantsLabel);
        adultsLabel = findViewById(R.id.adultsLabel);
        childrenLabel = findViewById(R.id.childrenLabel);
        adultsSpinner = findViewById(R.id.adultsSpinner);
        childrenSpinner = findViewById(R.id.childrenSpinner);

        // Create ArrayAdapter for Spinners
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Populate the Spinners with options (e.g., 0 to 10)
        for (int i = 0; i <= 10; i++) {
            spinnerAdapter.add(String.valueOf(i));
        }

        // Set the ArrayAdapter to the Spinners
        adultsSpinner.setAdapter(spinnerAdapter);
        childrenSpinner.setAdapter(spinnerAdapter);

        // Initialize TextViews (Hotel details)
        hotelNameTextView = findViewById(R.id.hotelNameTextView);
        hotelAddressTextView = findViewById(R.id.hotelAddressTextView);

        // Replace with the actual method to retrieve selected hotel information
        Hotel selectedHotel = HotelData.getSelectedHotel();
        Hotel selectedHotel1 = HotelData.getSelectedHotel1();

        // Set the text of the TextViews with the selected hotel information
        hotelNameTextView.setText(selectedHotel.getName());
        hotelAddressTextView.setText(selectedHotel.getAddress());
        hotelNameTextView.setText(selectedHotel1.getName());
        hotelAddressTextView.setText(selectedHotel1.getAddress());

        arrivalDateButton = findViewById(R.id.arrivalDateButton);
        departureDateButton = findViewById(R.id.departureDateButton);

        calendar = Calendar.getInstance();

        // Set click listeners for date buttons
        arrivalDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(arrivalDateButton);
            }
        });

        departureDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(departureDateButton);
            }
        });
    }


    private double getRoomPrice(String selectedRoomType) {
        return 0;
    }

    private void showDatePicker(final Button button) {
        // Get current year, month, and day
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Show date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                BookingRoomDetails.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int dayOfMonth) {
                        // Update the selected date on the button
                        button.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}
