package my.edu.utar.hotelbooking;

import android.os.Parcel;
import android.os.Parcelable;

public class HotelItem implements Parcelable {
    private int id;
    private String title;
    private Float rating;
    private int reviewCount;
    private int imageResId;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    private boolean isAddedToWishList;

    public HotelItem() {

    }

    public HotelItem(int id, String title, Float rating, int reviewCount, int imageResId) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.imageResId = imageResId;
    }

    protected HotelItem(Parcel in) {
        id = in.readInt();
        title = in.readString();
        rating = in.readFloat();
        reviewCount = in.readInt();
        imageResId = in.readInt();
        isAddedToWishList = in.readByte() != 0;
    }

    public static final Creator<HotelItem> CREATOR = new Creator<HotelItem>() {
        @Override
        public HotelItem createFromParcel(Parcel in) {
            return new HotelItem(in);
        }

        @Override
        public HotelItem[] newArray(int size) {
            return new HotelItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Float getRating() {
        return rating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public int getImageResId() {
        return imageResId;
    }

    public boolean isAddedToWishList() {
        return isAddedToWishList;
    }

    public void setAddedToWishList(boolean addedToWishList) {
        isAddedToWishList = addedToWishList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(String.valueOf(rating));
        dest.writeInt(reviewCount);
        dest.writeInt(imageResId);
        dest.writeByte((byte) (isAddedToWishList ? 1 : 0));
    }
}

