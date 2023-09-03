package my.edu.utar.hotelbooking;

public class HotelItem {
    private int id;
    private String title;
    private float rating;
    private int reviewCount;
    private int imageResId;

    public HotelItem() {

    }

    public HotelItem(int id, String title, float rating, int reviewCount, int imageResId) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.imageResId = imageResId;
    }

    public int getId() {
        return id;
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

