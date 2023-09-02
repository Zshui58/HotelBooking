package my.edu.utar.hotelbooking;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    SearchView searchView;
    Button buttonStart;
    Button buttonEnd;
    RecyclerView recyclerView;
    FirebaseAuth auth;
    Button btn;
    FirebaseUser user;
    List<HotelItem> itemList = new ArrayList<>();
    private HotelAdapter hotelAdapter;
    FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase (you can also do this in your Application class)
        FirebaseApp.initializeApp(this);
        // Initialize FirebaseHelper
        firebaseHelper = new FirebaseHelper();

        auth = FirebaseAuth.getInstance();
        btn = findViewById(R.id.logoutButton);
        user = auth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerView);

        // Handle search query changes in the SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    // If the search query is empty, show all hotel items
                    loadAllHotelItems();
                } else {
                    // Filter the hotel items based on the search query
                    List<HotelItem> filteredList = filterHotels(hotelAdapter.getItemList(), newText);
                    hotelAdapter.setItemList(filteredList);
                }
                return true;
            }
        });

        buttonStart = findViewById(R.id.startDateBt);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePicker = DatePickerFragment.newInstance(R.id.startDateBt);
                datePicker.show(getSupportFragmentManager(), "start_date_picker");
            }
        });

        buttonEnd = findViewById(R.id.endDateBt);
        buttonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePicker = DatePickerFragment.newInstance(R.id.endDateBt);
                datePicker.show(getSupportFragmentManager(), "end_date_picker");
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize and set up the RecyclerView adapter
        hotelAdapter = new HotelAdapter(this, itemList);
        recyclerView.setAdapter(hotelAdapter);

        addHotelToDatabase("The START Hotel, Casino & SkyPod", 4.91f, 510, R.drawable.image_one);
        addHotelToDatabase("Sunway Putra Hotel Kuala Lumpur", 4.75f, 0, R.drawable.sunway1);
        // Fetch hotel data from Firebase and populate the RecyclerView
        fetchAndPopulateData();
    }

    private void fetchAndPopulateData() {
        // Attach a ValueEventListener to fetch data from Firebase
        firebaseHelper.getHotels(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<HotelItem> hotels = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Parse hotel data and create HotelItem objects
                    HotelItem hotel = snapshot.getValue(HotelItem.class);
                    hotels.add(hotel);
                }

                // Set the fetched data to the RecyclerView adapter
                hotelAdapter.setItemList(hotels);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(MainActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAllHotelItems() {
        // Populate the RecyclerView with all hotel items
        hotelAdapter.setItemList(itemList);
    }

    // Helper method to filter hotel items
    private List<HotelItem> filterHotels(List<HotelItem> itemList, String query) {
        query = query.toLowerCase();
        List<HotelItem> filteredList = new ArrayList<>();
        for (HotelItem item : itemList) {
            if (item.getTitle().toLowerCase().contains(query)) {
                filteredList.add(item);
            }
        }
        return filteredList;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = findViewById(R.id.startdate);
        textView.setText(currentDateString);

        TextView textView1 = findViewById(R.id.endDate);
        textView1.setText(currentDateString);
    }

    // Method to add a new hotel item to the Firebase Realtime Database, checking for duplicates
    private void addHotelToDatabase(String title, float rating, int price, int imageResource) {
        // Check if the hotel with the same title already exists in the database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelsReference = databaseReference.child("hotels");

        hotelsReference.orderByChild("title").equalTo(title).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // A hotel with the same title already exists in the database
                    // You can handle this case, e.g., show a message to the user
                    Toast.makeText(MainActivity.this, "Hotel already exists in the database", Toast.LENGTH_SHORT).show();
                } else {
                    // The hotel does not exist, so add it to the database
                    HotelItem hotel = new HotelItem(itemList.size() + 1, title, rating, price, imageResource);

                    DatabaseReference newHotelReference = hotelsReference.push();
                    newHotelReference.setValue(hotel);

                    // Add the hotel item to the list
                    itemList.add(hotel);

                    // Notify the adapter that the data has changed
                    hotelAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that may occur during the database query
                Toast.makeText(MainActivity.this, "Database query error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
