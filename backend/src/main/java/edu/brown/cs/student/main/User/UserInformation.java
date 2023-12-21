package edu.brown.cs.student.main.User;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.GenericTypeIndicator;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * This class represent the user information we can get from the database.
 */
public class UserInformation {
    /**
     *Gets the user from the database from a string id.
     * @param userId UID related to the user we are trying to receive
     * @param collection Collection we want to receive data from.
     * @return The user we receive
     * @throws ExecutionException Firestore exception
     * @throws InterruptedException Firestore exception
     * @throws IOException Exception reading firestore credentials
     */
    public User getUserFromId(String userId, String collection) throws ExecutionException, InterruptedException, IOException {

        User user = null;

        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionRef = db.collection(collection);
        DocumentReference userDocRef = collectionRef.document(userId);
        DocumentSnapshot document = userDocRef.get().get();

        user = this.getUserFromDocRef(document,collection);


        return user;

    }
    /**
     *Gets the user from the database from a string Documentsnapshot.
     * @param doc Get information from given Documentsnapshot.
     * @param collection Collection we want to receive data from.
     * @return The user we receive
     * @throws ExecutionException Firestore exception
     * @throws InterruptedException Firestore exception
     * @throws IOException Exception reading firestore credentials
     */

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
                String uid =  doc.get("id",java.lang.String.class);
                user = new Meik(name,email,location,year,text,tags,concentration,uid);


            }
            GenericTypeIndicator<List<String>> tag = new GenericTypeIndicator<List<String>>(){};
        }

        return user;

    }

}
