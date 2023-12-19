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
    public User getUserFromId(String userId, String collection) throws ExecutionException, InterruptedException, IOException {

        User user = null;

        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionRef = db.collection(collection);
        DocumentReference userDocRef = collectionRef.document(userId);
        DocumentSnapshot document = userDocRef.get().get();

        user = this.getUserFromDocRef(document,collection);


        return user;

    }

    public User getUserFromDocRef(DocumentSnapshot doc,String collection) throws ExecutionException, InterruptedException, IOException {

        User user = null;


        if (doc.exists()) {
            System.out.println("Successfully retrieved user: " + doc.getId());

            // Get all contents of the document as a Map

            String email = doc.get("email",java.lang.String.class);
            String name = doc.get("name",java.lang.String.class);
            String location =  doc.get("location",java.lang.String.class);
            List<String> tags = (List<String>)doc.get("tags");

            if(collection.equals("FirstYears")){
                //List<String> concentration = (List<String>) doc.get("concentration");
                List<String> concentration = new ArrayList<>();

                //Issue of typecasting
                Map<String,Integer> search = (Map<String,Integer>) doc.get("search");
                user = new FirstYear(name,concentration,location, tags, email, search);

            }

            if(collection.equals("meiks")){

                String concentration = doc.get("concentration",java.lang.String.class);
                String text = doc.get("text",java.lang.String.class);
                String year =  doc.get("year",java.lang.String.class);
                user = new Meik(name,email,location,year,text,tags,concentration);


            }
            GenericTypeIndicator<List<String>> tag = new GenericTypeIndicator<List<String>>(){};
        }

        return user;

    }

}
