package my.edu.utar.hotelbooking;

import static android.app.DownloadManager.COLUMN_STATUS;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HotelBooking.db";
    private static final int DATABASE_VERSION = 2;

    // Define your table and column names
    public static final String TABLE_BOOKINGS = "bookings";
    private static final String COLUMN_ID = "id";
    static final String COLUMN_ROOM_TYPE = "room_type";
    public static final String COLUMN_CHECK_IN_DATE = "check_in_date";
    public static final String COLUMN_CHECK_OUT_DATE = "check_out_date";
    public static final String COLUMN_NUM_ADULTS = "num_adults";
    public static final String COLUMN_NUM_CHILDREN = "num_children";
    public static final String COLUMN_NUM_NIGHTS = "num_nights";
    public static final String COLUMN_NUM_ROOMS = "num_rooms";

    // Define the create table SQL statement
    private static final String CREATE_BOOKINGS_TABLE =
            "CREATE TABLE " + TABLE_BOOKINGS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ROOM_TYPE + " TEXT, " +
                    COLUMN_NUM_NIGHTS + " INTEGER, " +
                    COLUMN_NUM_ROOMS + " INTEGER, " +
                    COLUMN_NUM_CHILDREN + " INTEGER, " +
                    COLUMN_NUM_ADULTS + " INTEGER, " +
                    COLUMN_CHECK_IN_DATE + " TEXT, " +
                    COLUMN_CHECK_OUT_DATE + " TEXT)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the bookings table with a status column
        String createTableQuery = "CREATE TABLE bookings (id INTEGER PRIMARY KEY AUTOINCREMENT, check_in_date TEXT, check_out_date TEXT, status TEXT);";
        db.execSQL(createTableQuery);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // Method to update booking status
    public boolean updateBookingStatus(String bookingId, String newStatus, String cancellationReason) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, newStatus);

        // Perform the update
        int numRowsAffected = db.update(TABLE_BOOKINGS, values, COLUMN_ID + " = ?", new String[]{bookingId});

        // Check if the update was successful
        if (numRowsAffected > 0) {
            // Optionally, you can also log the cancellation reason here
            return true;
        } else {
            return false;
        }
    }

    public void getBookingIdToCancelBasedOnCriteria() {
    }
}
