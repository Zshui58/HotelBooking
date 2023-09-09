package my.edu.utar.hotelbooking;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
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

public class MainActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener,
        NavigationView.OnNavigationItemSelectedListener {

    SearchView searchView;
    Button buttonStart;
    Button buttonEnd;
    RecyclerView recyclerView;
    private RecyclerView wishlistRecyclerView;
    FirebaseAuth auth;
    Button btn;
    FirebaseUser user;
    List<HotelItem> itemList = new ArrayList<>();
    List<HotelDetail>detailList=new ArrayList<>();
    List<HotelDetail> selectedItems = new ArrayList<>();
    HotelAddedCallback callback;
    private HotelAdapter hotelAdapter;
    FirebaseHelper firebaseHelper;
    DatabaseReference newHotelReference;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        //Google
        // Check if the user is signed in with Google
        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        ImageView userImage = headerView.findViewById(R.id.userImage);
        TextView userName = headerView.findViewById(R.id.userName);
        TextView userEmail = headerView.findViewById(R.id.email);

        //Clear the information
        GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut();

        if (googleAccount != null) {
            String username = googleAccount.getDisplayName(); // Get the user's display name
            String email = googleAccount.getEmail(); // Get the user's email
            String photoUrl = googleAccount.getPhotoUrl().toString(); // Get the URL of the user's profile photo

            userName.setText(username);
            userEmail.setText(email);
            Glide.with(this)
                    .load(photoUrl)
                    .into(userImage);
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseApp.initializeApp(this);
        firebaseHelper = new FirebaseHelper();

        auth = FirebaseAuth.getInstance();
        btn = findViewById(R.id.nav_logout);
        user = auth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }

        String hotelTitle = "Mughal Gardens, Srinagar";
        addHotelToDatabase(1, hotelTitle, 4.91F, 510, R.drawable.image_one, new HotelAddedCallback() {
            @Override
            public void onHotelAdded(String hotelId) {
                addHotelDetailToDatabase(1, hotelId,hotelTitle,"4.91","RM200/night","Mughal Gardens which have truly changed the face of the Mughal Empire is one of the most popular and the most visited tourist attractions of Srinagar.","88 reviews",R.drawable.image_one,R.drawable.image_one, R.drawable.image_two, R.drawable.image_three,34.1504402354116, 74.87279684778184);
                hotelAdapter.notifyDataSetChanged();
            }
        });

        String hotelTitle1="Sunway Putra Hotel Kuala Lumpur";
        addHotelToDatabase(2,hotelTitle1, 4.75F, 0, R.drawable.sunway1, new HotelAddedCallback() {
            @Override
            public void onHotelAdded(String hotelId) {
                addHotelDetailToDatabase(2,hotelId,hotelTitle1,"4.75","RM340/night","Sunway Putra Hotel Kuala Lumpur is located opposite the World Trade Centre Kuala Lumpur (formerly known as PWTC),","34 reviews",R.drawable.sunway1,R.drawable.sunway2, R.drawable.sunway3, R.drawable.sunway4,3.166630851897795, 101.69241721596963);
                hotelAdapter.notifyDataSetChanged();
            }
        });

        String hotelTitle3="The Platinum Kuala Lumpur by Cozy White";
        addHotelToDatabase(3,hotelTitle3, 4.80f, 28, R.drawable.klcc1, new HotelAddedCallback() {
            @Override
            public void onHotelAdded(String hotelId) {
                addHotelDetailToDatabase(3,hotelId,hotelTitle3,"4.80","RM540/night","Set in Kuala Lumpur, 1.2 km from Petronas Twin Towers and 2 km from the centre, The Platinum Kuala Lumpur by Cozy White offers air-conditioned accommodation with free WiFi, and a rooftop pool.","28 reviews",R.drawable.klcc1,R.drawable.klcc2, R.drawable.klcc3, R.drawable.klcc4,3.1589032506998134, 101.70399049140613);
                hotelAdapter.notifyDataSetChanged();
            }
        });

        fetchAndPopulateData();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hotelAdapter = new HotelAdapter(this, itemList,detailList);
        recyclerView.setAdapter(hotelAdapter);

        searchView = findViewById(R.id.searchView);
        if (searchView != null) {
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
        }

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
    public void addHotelToDatabase(int id,String title, Float rating, int price, int imageResId, HotelAddedCallback callback) {
        // Check if the hotel with the same title already exists in the database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelsReference = databaseReference.child("hotels");

        hotelsReference.orderByChild("list/title").equalTo(title).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // A hotel with the same title already exists in the database
                    // You can handle this case, e.g., show a message to the user
                    Toast.makeText(MainActivity.this, "Welcome to Hotel Booking", Toast.LENGTH_SHORT).show();
                } else {
                    // The hotel does not exist, so add it to the database
                    HotelItem hotel = new HotelItem(id, title, rating, price, imageResId);

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

    private void addHotelDetailToDatabase(int id, String hotelId, String title, String rate, String price, String summary, String review, int imageResource1, int imageResource2, int imageResource3, int imageResource4, double latitude, double longitude) {
        // Check if the hotel with the same title already exists in the database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelsReference = databaseReference.child("hotels");

        HotelDetail hotelDetail = new HotelDetail(id, title, rate, price, summary,review,imageResource1,imageResource2,imageResource3,imageResource4,latitude,longitude);

        hotelsReference.child(hotelId).child("information").setValue(hotelDetail);

        // Add the hotel detail item to the list
        detailList.add(hotelDetail);
        // Notify the adapter that the data has changed
        hotelAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
