package edu.brown.cs.student.main.User;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public record Meik(String name, String email, String location, String year, String text,
                   List<String> tags, String concentration) implements User{



    public void updateUserName(String name,String uid,String collection) throws ExecutionException, InterruptedException {
        DocumentReference userDocRef = this.doc(uid,collection);
        System.out.println(userDocRef.update("name",name).get());

    }

    public void updateUserConcentration(String concentration,String uid,String collection) throws ExecutionException, InterruptedException {

        DocumentReference userDocRef = this.doc(uid,collection);
        System.out.println(userDocRef.update("concentration",concentration).get());

    }
    public void updateUserTags(List<String> tags,String uid,String collection) throws ExecutionException, InterruptedException {

        DocumentReference userDocRef = this.doc(uid,collection);
        System.out.println(userDocRef.update("tags",tags).get());

    }

    public void updateUserYear(String year,String uid,String collection) throws ExecutionException, InterruptedException {

        DocumentReference userDocRef = this.doc(uid,collection);
        System.out.println(userDocRef.update("year",year).get());

    }

    public void updateUserLocation(String location,String uid,String collection) throws ExecutionException, InterruptedException {

        DocumentReference userDocRef = this.doc(uid,collection);
        System.out.println(userDocRef.update("location",location).get());

    }

    public void updateUserText(String text,String uid,String collection) throws ExecutionException, InterruptedException {

        DocumentReference userDocRef = this.doc(uid,collection);
        System.out.println(userDocRef.update("text",text).get());

    }

    public void updateUserEmail(String email,String uid,String collection) throws ExecutionException, InterruptedException {

        DocumentReference userDocRef = this.doc(uid,collection);
        System.out.println(userDocRef.update("email",email).get());

    }


    public DocumentReference doc(String uid,String collection){
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionRef = db.collection(collection);
        return collectionRef.document(uid);

    }
}

