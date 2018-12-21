package com.tollywood24.tollywoodcircle.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseDataManager {

    public static DatabaseReference getCategoriesRef(String language) {
        return FirebaseDatabase.getInstance().getReference().child("home").child("News").child("Languages").child(language).child("Categories");
    }
}
