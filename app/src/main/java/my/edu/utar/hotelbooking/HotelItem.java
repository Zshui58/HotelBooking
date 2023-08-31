package my.edu.utar.hotelbooking;

public class HotelItem {
    private String title;
    private float rating;
    private int reviewCount;
    private int imageResId;

    public HotelItem(String title, float rating, int reviewCount, int imageResId) {
        this.title = title;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public float getRating() {
        return rating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public int getImageResId() {
        return imageResId;
    }
}

