package edu.brown.cs.student.main.User;


import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public record FirstYear(String name, List<String> concentrations, String location,
                        List<String> tags, String email, Map<String,String> search)
        implements User {

    @Override
    public void updateUserText(String text,String uid,String collection) throws ExecutionException, InterruptedException {

    }

    @Override
    public void updateUserLocation(String location,String uid,String collection) throws ExecutionException, InterruptedException {
        DocumentReference userDocRef = this.doc(uid,collection);
        System.out.println(userDocRef.update("location",location).get());
    }

    @Override
    public void updateUserConcentration(String concentration,String uid,String collection) throws ExecutionException, InterruptedException {
        DocumentReference userDocRef = this.doc(uid,collection);
        List<String> concentrations = List.of(concentration.split(","));
        System.out.println(userDocRef.update("concentration",concentrations).get());
    }

    @Override
    public void updateUserName(String name,String uid,String collection) throws ExecutionException, InterruptedException {
        DocumentReference userDocRef = this.doc(uid,collection);
        System.out.println(userDocRef.update("name",name).get());
    }

    @Override
    public void updateUserEmail(String email,String uid,String collection) throws ExecutionException, InterruptedException {
        DocumentReference userDocRef = this.doc(uid,collection);
        System.out.println(userDocRef.update("email",email).get());
    }

    @Override
    public void updateUserYear(String year,String uid,String collection) throws ExecutionException, InterruptedException {
        DocumentReference userDocRef = this.doc(uid,collection);
        System.out.println(userDocRef.update("year",year).get());
    }

    @Override
    public void updateUserTags(List<String> tags,String uid,String collection) throws ExecutionException, InterruptedException {
        DocumentReference userDocRef = this.doc(uid,collection);
        System.out.println(userDocRef.update("tags",tags).get());
    }

    @Override
    public DocumentReference doc(String uid,String collection) {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionRef = db.collection(collection);
        return collectionRef.document(uid);
    }
}
