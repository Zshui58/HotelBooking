package my.edu.utar.hotelbooking;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        recyclerView = findViewById(R.id.reviewRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create sample review data
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review("User1","Jan 2023", "Great app!"));
        reviews.add(new Review("User2","April 2023", "Needs improvements."));
        // Add more reviews as needed

        adapter = new ReviewAdapter(reviews);
        recyclerView.setAdapter(adapter);
    }
}
