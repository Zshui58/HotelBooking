package my.edu.utar.hotelbooking;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button buttonStart;
    Button buttonEnd;
    RecyclerView recyclerView;
    HotelAdapter adapter;
    FirebaseAuth auth;
    Button btn;
    FirebaseUser user;
    List<HotelItem> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase (you can also do this in your Application class)
        FirebaseApp.initializeApp(this);

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
                Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);

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
        adapter = new HotelAdapter(this, itemList);
        recyclerView.setAdapter(adapter);

        // Add hotel items to the database and the list
        addHotelToDatabase("The START Hotel, Casino & SkyPod", 4.91f, 510, R.drawable.image_one);
        addHotelToDatabase("Sunway Putra Hotel Kuala Lumpur", 4.75f, 0, R.drawable.sunway1);
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

    // Define a method to add a hotel item to the list and database
    private void addHotelToDatabase(String name, float rating, int price, int imageResource) {
        HotelItem hotel = new HotelItem(itemList.size() + 1, name, rating, price, imageResource);

        // Add the hotel item to the list
        itemList.add(hotel);

        // Add the hotel item to the Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelsReference = databaseReference.child("hotels");
        DatabaseReference newHotelReference = hotelsReference.push();
        newHotelReference.setValue(hotel);

        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged();
    }
}
