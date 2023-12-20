package edu.brown.cs.student.main.User;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public record Meik(String name, String email, String location, String year, String text,
                   List<String> tags, String concentration,String uid) implements User {


    @Override
    public void updateUserName(String name, String uid, String collection) throws ExecutionException, InterruptedException {
        DocumentReference userDocRef = this.doc(uid, collection);
        System.out.println(userDocRef.update("name", name).get());

    }
    @Override
    public void updateUserConcentration(String concentration, String uid, String collection) throws ExecutionException, InterruptedException {

        DocumentReference userDocRef = this.doc(uid, collection);
        System.out.println(userDocRef.update("concentration", concentration).get());

    }
    @Override
    public void updateUserTags(List<String> tags, String uid, String collection) throws ExecutionException, InterruptedException {

        DocumentReference userDocRef = this.doc(uid, collection);
        System.out.println(userDocRef.update("tags", tags).get());

    }


    @Override
    public void initializeTagsBuckets() throws Exception {
        throw new Exception("This method is meant for First Years");
    }

    @Override
    public void updateTagBucket() {

    }

    @Override
    public void initializeConcentrationBuckets() throws Exception {
        throw new Exception("This method is meant for First Years");

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Map<String, Double> setTagRankings() throws Exception {
        throw new Exception("This method is meant for First Years");
    }

    @Override
    public Map<String, Double> getTagRankings(String uid) {
        return null;
    }

    public void updateUserYear(String year, String uid, String collection) throws ExecutionException, InterruptedException {


        DocumentReference userDocRef = this.doc(uid, collection);
        System.out.println(userDocRef.update("year", year).get());

    }

    @Override
    public void updateUserLocation(String location, String uid, String collection) throws ExecutionException, InterruptedException {

        DocumentReference userDocRef = this.doc(uid, collection);
        System.out.println(userDocRef.update("location", location).get());

    }

    @Override
    public void updateUserText(String text, String uid, String collection) throws ExecutionException, InterruptedException {

        DocumentReference userDocRef = this.doc(uid, collection);
        System.out.println(userDocRef.update("text", text).get());

    }
    @Override
    public void updateUserEmail(String email, String uid, String collection) throws ExecutionException, InterruptedException {

        DocumentReference userDocRef = this.doc(uid, collection);
        System.out.println(userDocRef.update("email", email).get());

    }

    @Override
    public DocumentReference doc(String uid, String collection) {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionRef = db.collection(collection);
        return collectionRef.document(uid);

    }

    @Override
    public List<String> getConcentration() {
        List<String> concentrations = new ArrayList<>();
        concentrations.add(this.concentration);
        return concentrations;
    }

    @Override
    public List<String> getTags() {
        return this.tags;
    }

    @Override
    public Map<String, String> getSearch(String uid) throws Exception {
        throw new Exception("Meant for first Years");
    }

    @Override
    public String getID() {
        return this.uid;
    }


}