package edu.brown.cs.student.main.User;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.GenericTypeIndicator;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs.student.main.Authorization.FirebaseInitialize;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class UserInformation {
    public User getUser(String userId, String collection) throws ExecutionException, InterruptedException, IOException {



        User user = null;


        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionRef = db.collection(collection);
        DocumentReference userDocRef = collectionRef.document(userId);
        DocumentSnapshot document = userDocRef.get().get();



        if (document.exists()) {
            System.out.println("Successfully retrieved user: " + document.getId());

            // Get all contents of the document as a Map
            Map<String, Object> userData = document.getData();
           // System.out.println(userData);
            String email = document.get("email",java.lang.String.class);
            String name = document.get("name",java.lang.String.class);
            String location =  document.get("location",java.lang.String.class);
            String concentration =  document.get("concentration",java.lang.String.class);
            List<String> tags = (List<String>)document.get("tags");

            if(collection.equals("FirstYears")){
                //Issue of typecasting
                Map<String,String> search = (Map<String,String>) document.get("search");
                user = new FirstYear(name,concentration,location, tags, email, search);
            }

            //GenericTypeIndicator<Map<String, Integer>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Integer>>() {};
            if(collection.equals("meiks")){
                String text = document.get("text",java.lang.String.class);
                String year =  document.get("year",java.lang.String.class);
                user = new Meik(name,email,location,year,text,tags,userId);
            }
            //GenericTypeIndicator<List<String>> tag = new GenericTypeIndicator<List<String>>(){};
        }

        return user;

    }

}
