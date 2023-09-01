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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    CardView cardView;
    Button buttonStart;
    Button buttonEnd;
    RecyclerView recyclerView;
    HotelAdapter adapter;
    FirebaseAuth auth;
    Button btn;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        btn = findViewById(R.id.logoutButton);
        user = auth.getCurrentUser();
        if(user == null){
            Intent intent = new Intent(this,Login.class);
            startActivity(intent);
            finish();
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                finish();
            }
        });


        recyclerView = findViewById(R.id.recyclerView);

        buttonStart = (Button) findViewById(R.id.startDateBt);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePicker = DatePickerFragment.newInstance(R.id.startDateBt);
                datePicker.show(getSupportFragmentManager(), "start_date_picker");
            }
        });

        buttonEnd = (Button) findViewById(R.id.endDateBt);
        buttonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePicker = DatePickerFragment.newInstance(R.id.endDateBt);
                datePicker.show(getSupportFragmentManager(), "end_date_picker");
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<HotelItem> itemList = new ArrayList<>();
        itemList.add(new HotelItem("The START Hotel, Casino & SkyPod", 4.91f, 510, R.drawable.image_one));
        itemList.add(new HotelItem("Sky Bar at Waldorf Astoria", 4.75f, 0, R.drawable.sunway1));

        adapter = new HotelAdapter(this, itemList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = (TextView) findViewById(R.id.startdate);
        textView.setText(currentDateString);

        TextView textView1 = (TextView) findViewById(R.id.endDate);
        textView1.setText(currentDateString);
    }

}