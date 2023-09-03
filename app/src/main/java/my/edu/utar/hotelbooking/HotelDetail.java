package my.edu.utar.hotelbooking;

import android.os.Parcel;
import android.os.Parcelable;

public class HotelDetail implements Parcelable {

    private int id;
    private String title;

    public HotelDetail() {
    }

    private String price;
    private String review;
    private String rate;
    private String summary;
    private int imageResId1;
    private int imageResId2;
    private int imageResId3;
    private int imageResId4;

    public HotelDetail(int id, String title, String rate, String price, String summary, String review, int imageResId1, int imageResId2, int imageResId3, int imageResId4) {
        this.id = id;
        this.title=title;
        this.rate=rate;
        this.price=price;
        this.summary=summary;
        this.review=review;
        this.imageResId1 = imageResId1;
        this.imageResId2 = imageResId2;
        this.imageResId3 = imageResId3;
        this.imageResId4 = imageResId4;

    }


    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getReview() {
        return review;
    }

    public String getRate() {
        return rate;
    }

    public String getSummary() {
        return summary;
    }

    public int getImageResId1() {
        return imageResId1;
    }

    public int getImageResId2() {
        return imageResId2;
    }

    public int getImageResId3() {
        return imageResId3;
    }

    public int getImageResId4() {
        return imageResId4;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(title);
        dest.writeString(rate);
        dest.writeString(price);
        dest.writeString(summary);
        dest.writeString(review);
        dest.writeInt(imageResId1);
        dest.writeInt(imageResId2);
        dest.writeInt(imageResId3);
        dest.writeInt(imageResId4);
    }

    public static final Parcelable.Creator<HotelDetail> CREATOR = new Parcelable.Creator<HotelDetail>() {
        public HotelDetail createFromParcel(Parcel in) {
            return new HotelDetail(in);
        }

        public HotelDetail[] newArray(int size) {
            return new HotelDetail[size];
        }
    };

    private HotelDetail(Parcel in) {

        title = in.readString();
        rate = in.readString();
        price = in.readString();
        summary = in.readString();
        review = in.readString();
        imageResId1 = in.readInt();
        imageResId2 = in.readInt();
        imageResId3 = in.readInt();
        imageResId4 = in.readInt();
    }
}
