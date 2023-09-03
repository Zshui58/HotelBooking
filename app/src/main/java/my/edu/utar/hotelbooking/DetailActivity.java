package my.edu.utar.hotelbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Retrieve data passed from MainActivity
        Intent intent = getIntent();
        //int hotelItemId = intent.getIntExtra("hotelItemId", -1); // Replace with the actual data type
        int selectedItemIndex = intent.getIntExtra("selectedItemIndex", -1);
        ArrayList<HotelDetail> detailList = intent.getParcelableArrayListExtra("detailList");

        if (selectedItemIndex != -1 && detailList != null && selectedItemIndex < detailList.size()) {
            HotelDetail selectedHotelDetail = detailList.get(selectedItemIndex);
            ImageView imageView = findViewById(R.id.imageView3);
            TextView titleTextView = findViewById(R.id.textTitle);
            TextView priceTextView = findViewById(R.id.textPrice);
            TextView rateTextView = findViewById(R.id.rate);
            TextView reviewTextView = findViewById(R.id.review);
            TextView sumDetail = findViewById(R.id.textSumDetail);
            ImageView imageView8 = findViewById(R.id.imageView8);
            ImageView imageView9 = findViewById(R.id.imageView9);
            ImageView imageView10 = findViewById(R.id.imageView10);

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
        TextView reviewTextView=findViewById(R.id.review);
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
}



