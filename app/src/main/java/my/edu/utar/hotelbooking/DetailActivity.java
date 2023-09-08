package my.edu.utar.hotelbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private HotelAdapter hotelAdapter;
    private HotelDetail selectedHotelDetail;
    private HotelItem selectedHotelItem;
    private int selectedItemIndex;
    private List<HotelItem> itemList;
    private List<HotelDetail> detailList;
    private GoogleMap googleMap;
    private MapView mapView;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Retrieve data passed from MainActivity
        Intent intent = getIntent();

        detailList = intent.getParcelableArrayListExtra("detailList");
        selectedItemIndex = intent.getIntExtra("selectedItemIndex", -1);

        if (selectedItemIndex != -1 && detailList != null && selectedItemIndex < detailList.size()) {
            selectedHotelDetail = detailList.get(selectedItemIndex);
            ImageView imageView = findViewById(R.id.imageView3);
            TextView titleTextView = findViewById(R.id.textTitle);
            TextView priceTextView = findViewById(R.id.textPrice);
            TextView rateTextView = findViewById(R.id.rate);
            TextView reviewTextView = findViewById(R.id.review);
            TextView sumDetail = findViewById(R.id.textSumDetail);
            ImageView imageView8 = findViewById(R.id.imageView8);
            ImageView imageView9 = findViewById(R.id.imageView9);
            ImageView imageView10 = findViewById(R.id.imageView10);
            latitude = selectedHotelDetail.getLatitude();
            longitude = selectedHotelDetail.getLongitude();
            Log.d("DetailActivity", "Latitude: " + latitude + ", Longitude: " + longitude);

            // Populate views with data
            imageView.setImageResource(selectedHotelDetail.getImageResId1());
            imageView8.setImageResource(selectedHotelDetail.getImageResId2());
            imageView9.setImageResource(selectedHotelDetail.getImageResId3());
            imageView10.setImageResource(selectedHotelDetail.getImageResId4());
            titleTextView.setText(selectedHotelDetail.getTitle());
            rateTextView.setText(selectedHotelDetail.getRate());
            priceTextView.setText(selectedHotelDetail.getPrice());
            sumDetail.setText(selectedHotelDetail.getSummary());
            reviewTextView.setText(selectedHotelDetail.getReview());
        }

        mapView = findViewById(R.id.mapView);
        mapView. onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        ImageView backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event here, e.g., navigate back to MainActivity
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //
        TextView reviewTextView = findViewById(R.id.review);
        reviewTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity here
                Intent intent = new Intent(DetailActivity.this, ReviewActivity.class);
                startActivity(intent);
            }
        });

        Button startBookingButton = findViewById(R.id.button);
        startBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the booking page
                Intent bookingIntent = new Intent(DetailActivity.this, BookingRoomDetails.class);
                startActivity(bookingIntent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        // Create a LatLng object with the retrieved coordinates
        LatLng locationLatLng = new LatLng(latitude, longitude);

        // Add a marker to the map at the specified location
        googleMap.addMarker(new MarkerOptions().position(locationLatLng).title("Hotel Location"));

        // Move the camera to the marker's position
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 15)); // You can adjust the zoom level as needed
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}







