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

import androidx.core.util.Pair;
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
    List<HotelDetail>detailList=new ArrayList<>();
    HotelAddedCallback callback;
    private HotelAdapter hotelAdapter;
    FirebaseHelper firebaseHelper;
    DatabaseReference newHotelReference;

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
                    // Filter the hotel items and details based on the search query
                    Pair<List<HotelItem>, List<HotelDetail>> filteredLists = filterHotels(itemList, detailList, newText);

                    // Update the adapter with the filtered lists
                    hotelAdapter.setItemList(filteredLists.first);
                    hotelAdapter.setDetailList(filteredLists.second);
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
        hotelAdapter = new HotelAdapter(this, itemList,detailList);


        String hotelTitle = "Mughal Gardens, Srinagar";
        addHotelToDatabase(hotelTitle, 4.91f, 510, R.drawable.image_one, new HotelAddedCallback() {
            @Override
            public void onHotelAdded(String hotelId) {
                addHotelDetailToDatabase(hotelId,hotelTitle,"4.91","RM200/night","Mughal Gardens which have truly changed the face of the Mughal Empire is one of the most popular and the most visited tourist attractions of Srinagar.","88 reviews",R.drawable.image_one,R.drawable.image_one, R.drawable.image_two, R.drawable.image_three);
                hotelAdapter.notifyDataSetChanged();
            }
        });

        String hotelTitle1="Sunway Putra Hotel Kuala Lumpur";
        addHotelToDatabase(hotelTitle1, 4.75f, 0, R.drawable.sunway1, new HotelAddedCallback() {
            @Override
            public void onHotelAdded(String hotelId) {
                addHotelDetailToDatabase(hotelId,hotelTitle1,"4.75","RM340/night","Sunway Putra Hotel Kuala Lumpur is located opposite the World Trade Centre Kuala Lumpur (formerly known as PWTC),","34 reviews",R.drawable.sunway1,R.drawable.sunway2, R.drawable.sunway3, R.drawable.sunway4);
                hotelAdapter.notifyDataSetChanged();
            }
        });

        fetchAndPopulateData();
        recyclerView.setAdapter(hotelAdapter);
    }

    private void fetchAndPopulateData() {
        // Attach a ValueEventListener to fetch data from Firebase
        firebaseHelper.getHotels(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //List<HotelItem> hotels = new ArrayList<>();
                //List<HotelDetail> details = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Parse hotel data and create HotelItem objects
                    DataSnapshot listSnapshot = snapshot.child("list");
                    DataSnapshot informationSnapshot = snapshot.child("information");
                    HotelItem hotel = listSnapshot.getValue(HotelItem.class);
                    HotelDetail detail = informationSnapshot.getValue(HotelDetail.class);
                    itemList.add(hotel);
                    detailList.add(detail);
                }

                // Set the fetched data to the RecyclerView adapter
                hotelAdapter.setItemList(itemList);
                hotelAdapter.setDetailList(detailList);

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
        hotelAdapter.setDetailList(detailList);
    }

    // Helper method to filter hotel items
    private Pair<List<HotelItem>, List<HotelDetail>> filterHotels(List<HotelItem> items, List<HotelDetail> details, String query) {
        int size = Math.min(items.size(), details.size());
        // Filter the hotel items and details based on the search query
        List<HotelItem> filteredItems = new ArrayList<>();
        List<HotelDetail> filteredDetails = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            HotelItem item = items.get(i);
            HotelDetail detail = details.get(i);

            if (item.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredItems.add(item);
                filteredDetails.add(detail);
            }
        }

        return new Pair<>(filteredItems, filteredDetails);
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
    public void addHotelToDatabase(String title, float rating, int price, int imageResId, HotelAddedCallback callback) {
        // Check if the hotel with the same title already exists in the database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelsReference = databaseReference.child("hotels");

        hotelsReference.orderByChild("list/title").equalTo(title).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // A hotel with the same title already exists in the database
                    // You can handle this case, e.g., show a message to the user
                    Toast.makeText(MainActivity.this, "Hotel already exists in the database", Toast.LENGTH_SHORT).show();
                } else {
                    // The hotel does not exist, so add it to the database
                    HotelItem hotel = new HotelItem(itemList.size() + 1, title, rating, price, imageResId);

                    String hotelId = hotelsReference.push().getKey();
                    hotelsReference.child(hotelId).child("list").setValue(hotel); // Set the data under "list"

                    // Notify the adapter that the data has changed
                    hotelAdapter.notifyDataSetChanged();

                    callback.onHotelAdded(hotelId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that may occur during the database query
                Toast.makeText(MainActivity.this, "Database query error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addHotelDetailToDatabase(String hotelId, String title, String rate, String price,String summary,String review, int imageResource1,int imageResource2,int imageResource3,int imageResource4) {
        // Check if the hotel with the same title already exists in the database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelsReference = databaseReference.child("hotels");

        HotelDetail hotelDetail = new HotelDetail(detailList.size() + 1, title, rate, price, summary,review,imageResource1,imageResource2,imageResource3,imageResource4);

        hotelsReference.child(hotelId).child("information").setValue(hotelDetail);

        // Add the hotel detail item to the list
        detailList.add(hotelDetail);
        // Notify the adapter that the data has changed
        hotelAdapter.notifyDataSetChanged();
    }


}
