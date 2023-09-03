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

import java.util.ArrayList;
import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {

    private List<HotelItem> itemList;
    private List<HotelDetail> detailList;
    private Context context;

    public HotelAdapter(Context context, List<HotelItem> itemList,List<HotelDetail> detailList) {
        this.context = context;
        this.itemList = itemList;
        this.detailList = detailList;
        //this.itemList = new ArrayList<>();
    }

    public HotelAdapter(MainActivity context) {
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
        holder.bind(item); // Pass the HotelItem to the ViewHolder to set data
    }

    @Override
    public int getItemCount() {

        return itemList.size();
    }

    public void setItemList(List<HotelItem> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public void setDetailList(List<HotelDetail> detailList) {
        this.detailList = detailList;
        notifyDataSetChanged();
    }

    public List<HotelItem> getItemList() {
        return itemList;
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

            // Set click listener for the specific CardView
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        HotelItem clickedItem = itemList.get(position);

                        // Create an intent to start the DetailActivity
                        Intent detailIntent = new Intent(context, DetailActivity.class);

                        // Pass the item's identifier to DetailActivity
                        detailIntent.putExtra("hotelItemId", clickedItem.getId());

                        // Pass the detailList and selectedItemIndex
                        detailIntent.putParcelableArrayListExtra("detailList", new ArrayList<>(detailList));
                        detailIntent.putExtra("selectedItemIndex", position);  // Pass the selected item index

                        // Start the detail activity
                        context.startActivity(detailIntent);
                    }
                }
            });
        }

        public void bind(HotelItem item) {
            if (item != null) {
                titleTextView.setText(item.getTitle());
                ratingBar.setRating(item.getRating());
                ratingTextView.setText(String.valueOf(item.getRating()));
                reviewCountTextView.setText("(" + item.getReviewCount() + ")");
                imageView.setImageResource(item.getImageResId());
            }
        }


    }

}

