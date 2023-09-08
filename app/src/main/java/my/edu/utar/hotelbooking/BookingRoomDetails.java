package my.edu.utar.hotelbooking;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.Arrays;
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
    String selectedRoomType;
    TextView numberOfRoomsTextView;
    TextView numberOfNightsTextView;
    TextView adminFeeTextView;
    TextView totalTextView;
    private EditText numberOfRoomsEditText;
    private EditText numberOfNightsEditText;
    private double grandTotal;
    int selectedIndex;
    Hotel selectedHotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_room_details);

        //Retrieve data from DetailActivity
        Intent intent = getIntent();
        selectedIndex = intent.getIntExtra("selectedIndex", -1);

        // Initialize UI elements
        TextView roomPriceTextView = findViewById(R.id.roomPriceTextView);

        Spinner roomTypeSpinner = findViewById(R.id.roomTypeSpinner);

        int selectedHotelIndex = 0; // Change this index as needed

        numberOfRoomsEditText = findViewById(R.id.numberOfRoomsEditText);
        numberOfNightsEditText = findViewById(R.id.numberOfNightsEditText);

        ArrayAdapter<CharSequence> roomTypeAdapter = ArrayAdapter.createFromResource(this, R.array.room_types, android.R.layout.simple_spinner_item);
        roomTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        roomTypeSpinner.setAdapter(roomTypeAdapter);

        numberOfRoomsTextView = findViewById(R.id.numberOfRoomsTextView);
        numberOfNightsTextView = findViewById(R.id.numberOfNightsTextView);
        adminFeeTextView = findViewById(R.id.adminFeeTextView);
        totalTextView = findViewById(R.id.totalTextView);


        Button bookNowButton = findViewById(R.id.bookNowButton);

        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize the database helper in your activity
                DatabaseHelper dbHelper = new DatabaseHelper(BookingRoomDetails.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_ROOM_TYPE, selectedRoomType);
                values.put(DatabaseHelper.COLUMN_CHECK_IN_DATE, arrivalDateButton.getText().toString());
                values.put(DatabaseHelper.COLUMN_CHECK_OUT_DATE, departureDateButton.getText().toString());
                values.put(DatabaseHelper.COLUMN_NUM_ADULTS, Integer.parseInt(adultsSpinner.getSelectedItem().toString()));
                values.put(DatabaseHelper.COLUMN_NUM_CHILDREN, Integer.parseInt(childrenSpinner.getSelectedItem().toString()));
                values.put(DatabaseHelper.COLUMN_NUM_ROOMS, numberOfRoomsEditText.getText().toString());
                values.put(DatabaseHelper.COLUMN_NUM_NIGHTS, numberOfNightsEditText.getText().toString());


                long newRowId = db.insert(DatabaseHelper.TABLE_BOOKINGS, null, values);

                if (newRowId != -1) {
                    // Insertion successful
                    // You can add code here to show a success message or navigate to a confirmation screen.
                } else {
                    // Insertion failed
                    // You can add code here to show an error message or handle the failure.
                }

                db.close();

                updatePaymentSummary();
            }
        });

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

        roomTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedRoomType = parentView.getItemAtPosition(position).toString();

                double roomPrice = getRoomPrice(selectedRoomType);
                String priceText = "Price: RM" + roomPrice;

                roomPriceTextView.setText(priceText);

                TextView roomTypeSummaryTextView = findViewById(R.id.roomTypeSummary);
                roomTypeSummaryTextView.setText("Room Type: " + selectedRoomType);


                updatePaymentSummary();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        arrivalDateButton = findViewById(R.id.arrivalDateButton);
        departureDateButton = findViewById(R.id.departureDateButton);

        calendar = Calendar.getInstance();

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

        Button payButton = findViewById(R.id.payButton);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent transactionIntent = new Intent(BookingRoomDetails.this, PaymentActivity.class);

                transactionIntent.putExtra("checkInDate", arrivalDateButton.getText().toString());
                transactionIntent.putExtra("checkOutDate", departureDateButton.getText().toString());
                transactionIntent.putExtra("grandTotal", grandTotal);

                startActivity(transactionIntent);
            }
        });

        // Initialize UI elements (Participants section)
        participantsLabel = findViewById(R.id.participantsLabel);
        adultsLabel = findViewById(R.id.adultsLabel);
        childrenLabel = findViewById(R.id.childrenLabel);
        adultsSpinner = findViewById(R.id.adultsSpinner);
        childrenSpinner = findViewById(R.id.childrenSpinner);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for (int i = 0; i <= 10; i++) {
            spinnerAdapter.add(String.valueOf(i));
        }

        adultsSpinner.setAdapter(spinnerAdapter);
        childrenSpinner.setAdapter(spinnerAdapter);

        hotelNameTextView = findViewById(R.id.hotelNameTextView);
        hotelAddressTextView = findViewById(R.id.hotelAddressTextView);

        // Retrieve the selected hotel from your data source (e.g., HotelData)

        switch (selectedIndex) {
            case 0:
                selectedHotel = HotelData.getSelectedHotel();
                break;
            case 1:
                selectedHotel = HotelData.getSelectedHotel1();
                break;
            case 2:
                selectedHotel = HotelData.getSelectedHotel2();
                break;
        }


        hotelNameTextView.setText(selectedHotel.getName());
        hotelAddressTextView.setText(selectedHotel.getAddress());

        arrivalDateButton = findViewById(R.id.arrivalDateButton);
        departureDateButton = findViewById(R.id.departureDateButton);

        calendar = Calendar.getInstance();

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


    private void updatePaymentSummary() {
        int numberOfRooms = parseEditTextValue(numberOfRoomsEditText);
        int numberOfNights = parseEditTextValue(numberOfNightsEditText);

        double roomPrice = getRoomPrice(selectedRoomType);
        double totalRoomCost = roomPrice * numberOfRooms * numberOfNights;

        double adminFee = 40;
        grandTotal = totalRoomCost + adminFee;

        TextView numberOfRoomsTextView = findViewById(R.id.numberOfRoomsTextView);
        TextView numberOfNightsTextView = findViewById(R.id.numberOfNightsTextView);
        TextView adminFeeTextView = findViewById(R.id.adminFeeTextView);
        TextView grandTotalTextView = findViewById(R.id.totalTextView);

        numberOfRoomsTextView.setText(" " + numberOfRooms);
        numberOfNightsTextView.setText(" " + numberOfNights);
        adminFeeTextView.setText("RM" + adminFee);
        grandTotalTextView.setText("RM" + grandTotal);
    }

    private double calculateTotal(double roomPrice) {
        int numberOfRooms = parseEditTextValue(numberOfRoomsEditText);
        int numberOfNights = parseEditTextValue(numberOfNightsEditText);
        return roomPrice * numberOfRooms * numberOfNights;
    }


    private double getRoomPrice(String roomType) {
        // Define a variable to store room prices array
        double[] roomPrices;
        // Retrieve the selected hotel from your data source (e.g., HotelData)
        switch (selectedIndex) {
            case 0:
                roomPrices = HotelData.hotel1RoomPrices;
                break;
            case 1:
                roomPrices = HotelData.hotel2RoomPrices;
                break;
            case 2:
                roomPrices = HotelData.hotel3RoomPrices;
                break;
            default:
                roomPrices = new double[]{0.0, 0.0, 0.0, 0.0};
        }


        // Assuming your room types correspond to the positions in the array
        // Adjust the index accordingly
        int roomTypeIndex = Arrays.asList(getResources().getStringArray(R.array.room_types)).indexOf(roomType);

//         Check if the room type index is valid
        if (roomTypeIndex >= 0 && roomTypeIndex < roomPrices.length) {
            return roomPrices[roomTypeIndex];
        }

        return 0.0; // Return a default price for unknown room types
    }


    private void updateTotal() {
        int numberOfRooms = parseEditTextValue(numberOfRoomsEditText);
        int numberOfNights = parseEditTextValue(numberOfNightsEditText);

        double roomPrice = getRoomPrice(selectedRoomType);
        double total = roomPrice * numberOfRooms * numberOfNights;

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String totalText = "Total: RM" + decimalFormat.format(total);
        totalTextView.setText(totalText);
    }

    private int parseEditTextValue(EditText editText) {
        try {
            return Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void showDatePicker(final Button button) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                BookingRoomDetails.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int dayOfMonth) {
                        button.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}
