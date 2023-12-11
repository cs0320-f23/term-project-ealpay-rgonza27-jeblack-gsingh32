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
                   List<String> tags, String uid) implements User{



    public void updateUserName(String name) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionRef = db.collection("meiks");
        DocumentReference userDocRef = collectionRef.document(uid);

        System.out.println(userDocRef.update("name",name).get());

    }

    public void updateUserConcentration(String concentration) throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionRef = db.collection("meiks");
        DocumentReference userDocRef = collectionRef.document(uid);
        System.out.println(userDocRef.update("concentration",concentration).get());

    }

    public void updateUserLocation(String location) throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionRef = db.collection("meiks");
        DocumentReference userDocRef = collectionRef.document(uid);
        System.out.println(userDocRef.update("location",location).get());

    }

    public void updateUserText(String text) throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionRef = db.collection("meiks");
        DocumentReference userDocRef = collectionRef.document(uid);
        System.out.println(userDocRef.update("text",text).get());

    }

    public void updateUserEmail(String email) throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionRef = db.collection("meiks");
        DocumentReference userDocRef = collectionRef.document(uid);
        System.out.println(userDocRef.update("email",email).get());

    }

    public void addUserTags(String tag) throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionRef = db.collection("meiks");
        DocumentReference userDocRef = collectionRef.document(uid);
        if(!this.tags.contains(tag)) {
            this.tags.add(tag);
        }

        System.out.println(userDocRef.update("tag",this.tags).get());

    }

    public void removeUserTags(String tag) throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionRef = db.collection("meiks");
        DocumentReference userDocRef = collectionRef.document(uid);
        if(this.tags.contains(tag)) {
            this.tags.remove(tag);
        }

        System.out.println(userDocRef.update("tag",this.tags).get());

    }


    @Override
    public boolean isMeik() {
        return true;
    }
}

