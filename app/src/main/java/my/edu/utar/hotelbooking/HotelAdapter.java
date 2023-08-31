package my.edu.utar.hotelbooking;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {

    private List<HotelItem> itemList;
    private Context context;

    public HotelAdapter(Context context, List<HotelItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HotelItem item = itemList.get(position);
        holder.titleTextView.setText(item.getTitle());
        holder.ratingBar.setRating(item.getRating());
        holder.ratingTextView.setText(String.valueOf(item.getRating()));
        holder.reviewCountTextView.setText("(" + item.getReviewCount() + ")");
        holder.imageView.setImageResource(item.getImageResId());

        // Set click listener for the card view
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event, e.g., start the detail activity
                Intent detailIntent = new Intent(context, DetailActivity.class);
                context.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        RatingBar ratingBar;
        TextView ratingTextView;
        TextView reviewCountTextView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.hotelImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            ratingBar = itemView.findViewById(R.id.hotelRatingBar);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            reviewCountTextView = itemView.findViewById(R.id.reviewCountTextView);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}

