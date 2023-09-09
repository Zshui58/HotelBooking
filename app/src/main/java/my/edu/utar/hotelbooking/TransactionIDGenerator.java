package my.edu.utar.hotelbooking;

import java.util.Random;

public class    TransactionIDGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int ID_LENGTH = 8;

    public static String generateId() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();

        for (int i = 0; i < ID_LENGTH; i++) {
            char randomChar = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
            id.append(randomChar);
        }

        return id.toString();
    }
}
