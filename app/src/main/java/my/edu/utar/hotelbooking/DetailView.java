package my.edu.utar.hotelbooking;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailView {

    private ImageView imageView;
    private TextView titleTextView;
    private TextView priceTextView;
    private TextView rateTextView;
    private TextView reviewTextView;
    private TextView sumDetail;
    private ImageView imageView8;
    private ImageView imageView9;
    private ImageView imageView10;

    public DetailView(View rootView) {
        // Initialize views from the provided rootView
        imageView = rootView.findViewById(R.id.imageView3);
        titleTextView = rootView.findViewById(R.id.textTitle);
        priceTextView = rootView.findViewById(R.id.textPrice);
        rateTextView = rootView.findViewById(R.id.rate);
        reviewTextView = rootView.findViewById(R.id.review);
        sumDetail = rootView.findViewById(R.id.textSumDetail);
        imageView8 = rootView.findViewById(R.id.imageView8);
        imageView9 = rootView.findViewById(R.id.imageView9);
        imageView10 = rootView.findViewById(R.id.imageView10);
    }

    public void setImage(ImageView imageView, int resourceId) {
        this.imageView.setImageResource(resourceId);
    }

    public void setTitle(TextView titleTextView, String title) {
        this.titleTextView.setText(title);
    }

    public void setPrice(TextView priceTextView, String price) {
        this.priceTextView.setText(price);
    }

    public void setRate(TextView rateTextView, String rate) {
        this.rateTextView.setText(rate);
    }

    public void setReview(TextView reviewTextView, String review) {
        this.reviewTextView.setText(review);
    }

    public void setSumDetail(TextView sumDetail, String detail) {
        this.sumDetail.setText(detail);
    }

    public void setImages(int image1, int image2, int image3) {
        imageView8.setImageResource(image1);
        imageView9.setImageResource(image2);
        imageView10.setImageResource(image3);
    }

    public void setImages(ImageView imageView8, ImageView imageView9, ImageView imageView10, int image_one, int image_one1, int image_one2) {
    }
}
