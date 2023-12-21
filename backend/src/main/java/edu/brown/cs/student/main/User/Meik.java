package edu.brown.cs.student.main.User;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * This record represents our meiks
 * @param name Meik's name
 * @param email Meik's email
 * @param location Meik's location
 * @param year Meik's location
 * @param text Meik's text
 * @param tags Meik's tags
 * @param concentration Meik's concentration
 * @param uid Meik's UID
 */
public record Meik(String name, String email, String location, String year, String text,
                   List<String> tags, String concentration,String uid) implements User {

    /**
     * Update meiks user name
     * @param name New name
     * @param uid Meik's uid
     * @param collection firestore collection
     * @throws ExecutionException Firestore exception.
     * @throws InterruptedException Firestore exception.
     */
    @Override
    public void updateUserName(String name, String uid, String collection) throws ExecutionException, InterruptedException {
        DocumentReference userDocRef = this.doc(uid, collection);
        System.out.println(userDocRef.update("name", name).get());

    }
    /**
     * Update meiks user concentrations
     * @param concentration New concentrations
     * @param uid Meik's uid
     * @param collection firestore collection
     * @throws ExecutionException Firestore exception.
     * @throws InterruptedException Firestore exception.
     */
    @Override
    public void updateUserConcentration(String concentration, String uid, String collection) throws ExecutionException, InterruptedException {

        DocumentReference userDocRef = this.doc(uid, collection);
        System.out.println(userDocRef.update("concentration", concentration).get());

    }
    /**
     * Update meiks user tags
     * @param tags New tags
     * @param uid Meik's uid
     * @param collection firestore collection
     * @throws ExecutionException Firestore exception.
     * @throws InterruptedException Firestore exception.
     */
    @Override
    public void updateUserTags(List<String> tags, String uid, String collection) throws ExecutionException, InterruptedException {

        DocumentReference userDocRef = this.doc(uid, collection);
        System.out.println(userDocRef.update("tags", tags).get());

    }

    /**
     * This is meant for first years
     */
    @Override
    public void initializeTagsBuckets() throws Exception {
        throw new Exception("This method is meant for First Years");
    }


    /**
     * This is meant for first years
     */
    @Override
    public void initializeConcentrationBuckets() throws Exception {
        throw new Exception("This method is meant for First Years");

    }

    /**
     * This is meant for first years
     */
    @Override
    public Map<String, Double> setTagRankings() throws Exception {
        throw new Exception("This method is meant for First Years");
    }

    /**
     * This is meant for first years
     */
    @Override
    public Map<String, Double> getTagRankings(String uid) {
        return null;
    }

    /**
     * This is meant for first years
     */
    public void updateUserYear(String year, String uid, String collection) throws ExecutionException, InterruptedException {


        DocumentReference userDocRef = this.doc(uid, collection);
        System.out.println(userDocRef.update("year", year).get());

    }

    /**
     * Update meiks user location.
     * @param location New location
     * @param uid Meik's uid
     * @param collection firestore collection
     * @throws ExecutionException Firestore exception.
     * @throws InterruptedException Firestore exception.
     */
    @Override
    public void updateUserLocation(String location, String uid, String collection) throws ExecutionException, InterruptedException {

        DocumentReference userDocRef = this.doc(uid, collection);
        System.out.println(userDocRef.update("location", location).get());

    }

    /**
     * Update meiks user text
     * @param text New concentrations
     * @param uid Meik's uid
     * @param collection firestore collection
     * @throws ExecutionException Firestore exception.
     * @throws InterruptedException Firestore exception.
     */
    @Override
    public void updateUserText(String text, String uid, String collection) throws ExecutionException, InterruptedException {

        DocumentReference userDocRef = this.doc(uid, collection);
        System.out.println(userDocRef.update("text", text).get());

    }

    /**
     * Update meiks user email
     * @param email New concentrations
     * @param uid Meik's uid
     * @param collection firestore collection
     * @throws ExecutionException Firestore exception.
     * @throws InterruptedException Firestore exception.
     */
    @Override
    public void updateUserEmail(String email, String uid, String collection) throws ExecutionException, InterruptedException {

        DocumentReference userDocRef = this.doc(uid, collection);
        System.out.println(userDocRef.update("email", email).get());

    }

    /**
     * Returns the relevant document reference that has our meiks information, or to be used to update our meik.
     * @param uid Meiks uid
     * @param collection Reference to which Firestore collection
     * @return Meik's document reference.
     */
    @Override
    public DocumentReference doc(String uid, String collection) {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionRef = db.collection(collection);
        return collectionRef.document(uid);

    }

    /**
     * Return the meiks concentrations
     * @return concentrations
     */
    @Override
    public List<String> getConcentration() {
        List<String> concentrations = new ArrayList<>();
        concentrations.add(this.concentration);
        return concentrations;
    }

    /**
     * Return tags.
     * @return Tags in list of string format.
     */
    @Override
    public List<String> getTags() {
        return this.tags;
    }

    /**
     * This is meant for first years.
     */
    @Override
    public Map<String, String> getSearch(String uid) throws Exception {
        throw new Exception("Meant for first Years");
    }

    /**
     * Return the meiks uid
     * @return uid
     */
    @Override
    public String getID() {
        return this.uid;
    }


}