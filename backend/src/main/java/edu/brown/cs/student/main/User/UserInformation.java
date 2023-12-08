package edu.brown.cs.student.main.User;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs.student.main.Authorization.FirebaseInitialize;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UserInformation {
    public void getUser(String userId) throws ExecutionException, InterruptedException, IOException {

        try {
            FirebaseInitialize initialize = new FirebaseInitialize();
            FirebaseInitialize.initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        Firestore db = FirestoreClient.getFirestore();
        CollectionReference firstYears = db.collection("FirstYears");
        DocumentReference userDocRef = firstYears.document("z8jCW6mJLxQaQswrOuQs");
        DocumentSnapshot document = userDocRef.get().get();


        if (document.exists()) {
            System.out.println("Successfully retrieved user: " + document.getId());

            // Get all contents of the document as a Map
            Map<String, Object> userData = document.getData();
           // System.out.println(userData);
            Moshi moshi = new Moshi.Builder().build();
            Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
            JsonAdapter<Map<String, Object>> stringObjMapAdapter = moshi.adapter(mapStringObject);
            JsonAdapter<User> userJsonAdapter = moshi.adapter(User.class);
            String text = stringObjMapAdapter.toJson(userData);
            String text2;
            text2 = new String(text);
            System.out.println(text2);


            userJsonAdapter.fromJson(text2);
           // User gurpartap = userJsonAdapter.fromJson(stringObjMapAdapter.toJson(userData).toString());


        }

    }

}
