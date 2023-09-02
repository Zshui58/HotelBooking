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

        // Retrieve the item's identifier passed from the clicked item's intent
        int hotelItemId = getIntent().getIntExtra("hotelItemId", -1); // -1 is a default value if not found

        // Get the appropriate HotelItem based on the identifier (assuming itemList is your list of items)
        //HotelItem selectedHotelItem = findHotelItemById(hotelItemId);

        // Create an array of DetailView objects
        List<DetailView> detailViews = new ArrayList<>();

        // Assuming you've already initialized your rootView
        View rootView = findViewById(R.id.detailLayout);

        //if (selectedHotelItem != null) {
        // Retrieve the views from the template layout
        ImageView imageView = findViewById(R.id.imageView3);
        TextView titleTextView = findViewById(R.id.textTitle);
        TextView priceTextView = findViewById(R.id.textPrice);
        TextView rateTextView = findViewById(R.id.rate);
        TextView reviewTextView = findViewById(R.id.review);
        TextView sumDetail = findViewById(R.id.textSumDetail);
        ImageView imageView8 = findViewById(R.id.imageView8);
        ImageView imageView9 = findViewById(R.id.imageView9);
        ImageView imageView10 = findViewById(R.id.imageView10);


        // Create a DetailView object to set data based on the selected content
        DetailView detailView = new DetailView(rootView);

        // Example 1: Populate with data for content detail 1
        DetailView detailView1 = new DetailView(rootView);
        detailView1.setImage(imageView, R.drawable.image_one);
        detailView1.setTitle(titleTextView, "Mughal Gardens, Srinagar");
        detailView1.setPrice(priceTextView, "RM200/night");
        detailView1.setRate(rateTextView, "4.91");
        detailView1.setReview(reviewTextView, "88 reviews");
        detailView1.setSumDetail(sumDetail, "Mughal Gardens which have truly changed the face of the Mughal Empire is one of the most popular and the most visited tourist attractions of Srinagar.");
        detailView1.setImages(imageView8, imageView9, imageView10, R.drawable.image_one, R.drawable.image_one, R.drawable.image_one);
        detailViews.add(detailView1);

        // Example 2: Populate with data for content detail 2
        DetailView detailView2 = new DetailView(rootView);
        detailView2.setImage(imageView, R.drawable.sunway1);
        detailView2.setTitle(titleTextView, "Sunway Putra Hotel Kuala Lumpur");
        detailView2.setPrice(priceTextView, "RM340/night");
        detailView2.setRate(rateTextView, "4.75");
        detailView2.setReview(reviewTextView, "34 reviews");
        detailView2.setSumDetail(sumDetail, "Sunway Putra Hotel Kuala Lumpur is located opposite the World Trade Centre Kuala Lumpur (formerly known as PWTC), nestled within Kuala Lumpur's Diamond Triangle, one of Kuala Lumpur’s most known districts with local and trendy cafes and bars, bustling markets and landmarks – a pot of Asian cultures and traditions; all within the city’s main commercial district.");
        detailView2.setImages(imageView8, imageView9, imageView10, R.drawable.sunway2, R.drawable.sunway3, R.drawable.sunway4);
        detailViews.add(detailView2);

        //
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


