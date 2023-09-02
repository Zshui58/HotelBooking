package my.edu.utar.hotelbooking;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseHelper {
    private DatabaseReference databaseReference;

    public FirebaseHelper() {
        databaseReference = FirebaseDatabase.getInstance().getReference("hotels");
    }

    public void getHotels(final ValueEventListener valueEventListener) {
        // Attach a ValueEventListener to retrieve all hotels
        databaseReference.addValueEventListener(valueEventListener);
    }
}

