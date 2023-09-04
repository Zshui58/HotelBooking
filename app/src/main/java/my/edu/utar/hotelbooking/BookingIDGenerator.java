package my.edu.utar.hotelbooking;

import android.content.Intent;

import java.util.Random;

public class BookingIDGenerator<cancelBookingIntent> {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int ID_LENGTH = 8;

    public static String generateBookingId() {
        Random random = new Random();
        StringBuilder bookingId = new StringBuilder();

        for (int i = 0; i < ID_LENGTH; i++) {
            char randomChar = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
            bookingId.append(randomChar);
        }

        return bookingId.toString();
    }
}
