package my.edu.utar.hotelbooking;

import static my.edu.utar.hotelbooking.DatabaseHelper.COLUMN_NUM_NIGHTS;
import static my.edu.utar.hotelbooking.DatabaseHelper.COLUMN_NUM_ROOMS;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    double roomPriceTextView;
    String selectedRoomType;
    TextView numberOfRoomsTextView;
    TextView numberOfNightsTextView;
    TextView adminFeeTextView;
    TextView totalTextView;
    // Define variables to store the user's input
    private EditText numberOfRoomsEditText;
    private EditText numberOfNightsEditText;
    String numRooms;
    String numNights;
    private double grandTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_room_details);

        // Initialize UI elements
        TextView roomPriceTextView = findViewById(R.id.roomPriceTextView);

        // Initialize the roomTypeSpinner
        Spinner roomTypeSpinner = findViewById(R.id.roomTypeSpinner);


        // Initialize the numberOfRoomsEditText and numberOfNightsEditText
        numberOfRoomsEditText = findViewById(R.id.numberOfRoomsEditText);
        numberOfNightsEditText = findViewById(R.id.numberOfNightsEditText);

        // Create ArrayAdapter for room type Spinner
        ArrayAdapter<CharSequence> roomTypeAdapter = ArrayAdapter.createFromResource(this, R.array.room_types, android.R.layout.simple_spinner_item);
        roomTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter to the room type Spinner
        roomTypeSpinner.setAdapter(roomTypeAdapter);


        numberOfRoomsTextView = findViewById(R.id.numberOfRoomsTextView);
        numberOfNightsTextView = findViewById(R.id.numberOfNightsTextView);
        adminFeeTextView = findViewById(R.id.adminFeeTextView);
        totalTextView = findViewById(R.id.totalTextView);

        // Assuming you have a "Book Now" button with the id "bookNowButton"
        Button bookNowButton = findViewById(R.id.bookNowButton);

        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize the database helper in your activity
                DatabaseHelper dbHelper = new DatabaseHelper(BookingRoomDetails.this);

                // Get a writable database
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // Example: Insert a booking record
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_ROOM_TYPE, selectedRoomType);
                values.put(DatabaseHelper.COLUMN_CHECK_IN_DATE, arrivalDateButton.getText().toString());
                values.put(DatabaseHelper.COLUMN_CHECK_OUT_DATE, departureDateButton.getText().toString());
                values.put(DatabaseHelper.COLUMN_NUM_ADULTS, Integer.parseInt(adultsSpinner.getSelectedItem().toString()));
                values.put(DatabaseHelper.COLUMN_NUM_CHILDREN, Integer.parseInt(childrenSpinner.getSelectedItem().toString()));
                values.put(COLUMN_NUM_ROOMS, numRooms);
                values.put(COLUMN_NUM_NIGHTS, numNights);






                // Insert the values into the database
                long newRowId = db.insert(DatabaseHelper.TABLE_BOOKINGS, null, values);

                // Check if the insertion was successful
                if (newRowId != -1) {
                    // Insertion successful
                    // You can add code here to show a success message or navigate to a confirmation screen.
                } else {
                    // Insertion failed
                    // You can add code here to show an error message or handle the failure.
                }

                // Close the database connection when done
                db.close();

                // Display the payment summary
                updatePaymentSummary();
            }
        });

        // Set up TextChangedListeners to recalculate the total when the user changes inputs
        numberOfRoomsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateTotal();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        numberOfNightsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateTotal();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // Set up a selection listener for the room type Spinner
        roomTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Retrieve the selected room type
                selectedRoomType = parentView.getItemAtPosition(position).toString();

                // Update the room price based on the selected room type (replace with your logic)
                double roomPrice = getRoomPrice(selectedRoomType);
                String priceText = "Price: RM" + roomPrice;

                // Set the updated price in the TextView
                roomPriceTextView.setText(priceText);

                // Update the payment summary TextView for room type
                TextView roomTypeSummaryTextView = findViewById(R.id.roomTypeSummary);
                roomTypeSummaryTextView.setText("Room Type: " + selectedRoomType);

                // Calculate the total
                double total = calculateTotal(roomPrice);

                // Display the payment summary
                updatePaymentSummary();


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
        Hotel selectedHotel2 = HotelData.getSelectedHotel2();

        // Set the text of the TextViews with the selected hotel information
        hotelNameTextView.setText(selectedHotel.getName());
        hotelAddressTextView.setText(selectedHotel.getAddress());
        hotelNameTextView.setText(selectedHotel1.getName());
        hotelAddressTextView.setText(selectedHotel1.getAddress());
        hotelNameTextView.setText(selectedHotel2.getName());
        hotelAddressTextView.setText(selectedHotel2.getAddress());

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

        // Find the "Pay" button
        Button payButton = findViewById(R.id.payButton);

        // Set an OnClickListener for the "Pay" button
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create an explicit intent to start the PaymentActivity
                Intent transactionIntent = new Intent(BookingRoomDetails.this, PaymentActivity.class);

                // Add any extra data you want to pass to the PaymentActivity, if needed
                // paymentIntent.putExtra("key", "value");
                // Pass selectedRoomType, check-in, and check-out dates to TransactionCheckingActivity
                //transactionIntent.putExtra("selectedRoomType", selectedRoomType);
                transactionIntent.putExtra("checkInDate", arrivalDateButton.getText().toString());
                transactionIntent.putExtra("checkOutDate", departureDateButton.getText().toString());
                transactionIntent.putExtra("grandTotal", grandTotal);



                // Start the PaymentActivity
                startActivity(transactionIntent);
            }
        });
    }


    private void displayPaymentSummary(String selectedRoomType, double total) {
    }

    // Update the payment summary based on user selections
    private void updatePaymentSummary() {
        // Calculate the total cost based on user selections
        int numberOfRooms = parseEditTextValue(numberOfRoomsEditText);
        int numberOfNights = parseEditTextValue(numberOfNightsEditText);

        // Get the selected room type
        Spinner roomTypeSpinner = findViewById(R.id.roomTypeSpinner);
        String selectedRoomType = roomTypeSpinner.getSelectedItem().toString();

        // Define room prices for different room types
        double standardSingleRoomPrice = 166.0;
        double standardDoubleRoomPrice = 340.0;
        double deluxeTwinPrice = 328.0;
        double deluxeFamilyPrice = 448.0;
        double executiveSuitePrice = 999.0;
        double superiorSuitePrice = 1388.0;
        double deluxeSuitePrice = 1688.0;
        double premierSuitePrice = 1888.0;

        // Initialize the total room cost
        double totalRoomCost = 0.0;

        // Calculate the total room cost based on the selected room type
        switch (selectedRoomType) {
            case "Standard Single Room":
                totalRoomCost = standardSingleRoomPrice * (numberOfRooms * numberOfNights);
                break;
            case "Standard Double Room":
                totalRoomCost = standardDoubleRoomPrice * (numberOfRooms * numberOfNights);
                break;
            case "Deluxe Twin":
                totalRoomCost = deluxeTwinPrice * (numberOfRooms * numberOfNights);
                break;
            case "Deluxe Family":
                totalRoomCost = deluxeFamilyPrice * (numberOfRooms * numberOfNights);
                break;
            case "Executive Suite":
                totalRoomCost = executiveSuitePrice * (numberOfRooms * numberOfNights);
                break;
            case "Superior Suite":
                totalRoomCost = superiorSuitePrice * (numberOfRooms * numberOfNights);
                break;
            case "Deluxe Suite":
                totalRoomCost = deluxeSuitePrice * (numberOfRooms * numberOfNights);
                break;
            case "Premier Suite":
                totalRoomCost = premierSuitePrice * (numberOfRooms * numberOfNights);
                break;
            // Handle additional room types if needed
        }

        // Now parse the numeric value into a double
        double adminFee = 40;

        // Calculate the grand total
        grandTotal= 0;
        grandTotal = totalRoomCost + adminFee;

        // Format and update the TextViews with the calculated values
        TextView numberOfRoomsTextView = findViewById(R.id.numberOfRoomsTextView);
        TextView numberOfNightsTextView = findViewById(R.id.numberOfNightsTextView);
        TextView adminFeeTextView = findViewById(R.id.adminFeeTextView);
        TextView grandTotalTextView = findViewById(R.id.totalTextView);

        numberOfRoomsTextView.setText(" " + numberOfRooms);
        numberOfNightsTextView.setText(" " + numberOfNights);
        adminFeeTextView.setText("RM" + adminFee);
        grandTotalTextView.setText("RM" + grandTotal);
    }


    private String getRoomTypeName(int index) {
        // Define your list of room type names in the same order as roomPrices
        List<String> roomTypeNames = new ArrayList<>();
        roomTypeNames.add("Standard Single Room");
        roomTypeNames.add("Standard Double Room");
        roomTypeNames.add("Deluxe Twin");
        roomTypeNames.add("Deluxe Family");
        roomTypeNames.add("Executive Suite");
        roomTypeNames.add("Superior Suite");
        roomTypeNames.add("Deluxe Suite");
        roomTypeNames.add("Premier Suite");

        // Check if the index is within bounds
        if (index >= 0 && index < roomTypeNames.size()) {
            return roomTypeNames.get(index);
        } else {
            // Handle invalid index, return an empty string or an error message
            return "";
        }
    }


    // Calculate the total based on room price, number of rooms, and number of nights
    private double calculateTotal(double roomPrice) {
        int numberOfRooms = parseEditTextValue(numberOfRoomsEditText);
        int numberOfNights = parseEditTextValue(numberOfNightsEditText);
        return roomPrice * numberOfRooms * numberOfNights;
    }


    private double getRoomPrice(String selectedRoomType) {
        return 0;
    }

    // Modify the updateTotal method to use the user's input for number of nights and rooms
    private void updateTotal() {
        // Get the number of rooms and nights from the EditText fields
        int numberOfRooms = parseEditTextValue(numberOfRoomsEditText);
        int numberOfNights = parseEditTextValue(numberOfNightsEditText);

        // Calculate the total
        roomPriceTextView=getRoomPrice();
        double total = roomPriceTextView * numberOfRooms * numberOfNights;

        // Format the total and update the TextView
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String totalText = "Total: RM" + decimalFormat.format(total);
        totalTextView.setText(totalText);
    }


    private double getRoomPrice() {
        return 0;
    }

    // Helper method to parse EditText input
    private int parseEditTextValue(EditText editText) {
        try {
            return Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException e) {
            // Handle parsing error, e.g., when the input is not a valid number
            return 0; // Default value
        }
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