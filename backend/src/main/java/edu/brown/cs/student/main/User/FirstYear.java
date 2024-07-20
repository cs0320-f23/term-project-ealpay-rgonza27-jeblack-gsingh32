package edu.brown.cs.student.main.User;



import java.util.ArrayList;
import java.util.HashMap;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/***
 * This record is used to represent our First Years.
 * @param name First Year's Name
 * @param concentrations First Year's concentration
 * @param location First Year's location
 * @param tags First Year's tags
 * @param email First Year's email
 * @param search First Year's searches
 */
public record FirstYear(String name, List<String> concentrations, String location,
                        List<String> tags, String email, Map<String,String> search)
        implements User {


    /**
     * This method allows us to update our first year's location
     * @param location First Year's location
     * @param uid First years' UID
     * @param collection Collection from firestore
     * @throws ExecutionException Exceptions thrown by firestore
     * @throws InterruptedException Exceptions thrown by firestore
     */

    @Override
    public void updateUserLocation(String location,String uid,String collection) throws ExecutionException, InterruptedException {
        DocumentReference userDocRef = this.doc(uid,collection);
        System.out.println(userDocRef.update("location",location).get());
    }

    /**
     * This method allows us to update our first year's Concentrations
     * @param concentration This is a list of strings we later split representing the concentrations
     * @param uid First years' UID
     * @param collection Collection from firestore
     * @throws ExecutionException Exceptions thrown by firestore
     * @throws InterruptedException Exceptions thrown by firestore
     */
    @Override
    public void updateUserConcentration(String concentration,String uid,String collection) throws ExecutionException, InterruptedException {
        DocumentReference userDocRef = this.doc(uid,collection);
        List<String> concentrations = List.of(concentration.split(","));
        System.out.println(userDocRef.update("concentration",concentrations).get());
    }
    /**
     * This method allows us to update our first year's name
     * @param name First year's new name.
     * @param uid First years' UID
     * @param collection Collection from firestore
     * @throws ExecutionException Exceptions thrown by firestore
     * @throws InterruptedException Exceptions thrown by firestore
     */

    @Override
    public void updateUserName(String name,String uid,String collection) throws ExecutionException, InterruptedException {
        DocumentReference userDocRef = this.doc(uid,collection);
        System.out.println(userDocRef.update("name",name).get());
    }
    /**
     * This method allows us to update our first year's email
     * @param email First year's email
     * @param uid First years' UID
     * @param collection Collection from firestore
     * @throws ExecutionException Exceptions thrown by firestore
     * @throws InterruptedException Exceptions thrown by firestore
     */
    @Override
    public void updateUserEmail(String email,String uid,String collection) throws ExecutionException, InterruptedException {
        DocumentReference userDocRef = this.doc(uid,collection);
        System.out.println(userDocRef.update("email",email).get());
    }
    /**
     * This method allows us to update our first year's, though we will probably scratch this method.
     * @param year First year's year
     * @param uid First years' UID
     * @param collection Collection from firestore
     * @throws ExecutionException Exceptions thrown by firestore
     * @throws InterruptedException Exceptions thrown by firestore
     */
    @Override
    public void updateUserYear(String year,String uid,String collection) throws ExecutionException, InterruptedException {
        DocumentReference userDocRef = this.doc(uid,collection);
        System.out.println(userDocRef.update("year",year).get());
    }
    /**
     * This method allows us to update our first year's Tags
     * @param tags List of tags.
     * @param uid First years' UID
     * @param collection Collection from firestore
     * @throws ExecutionException Exceptions thrown by firestore
     * @throws InterruptedException Exceptions thrown by firestore
     */
    @Override
    public void updateUserTags(List<String> tags,String uid,String collection) throws ExecutionException, InterruptedException {
        DocumentReference userDocRef = this.doc(uid,collection);
        System.out.println(userDocRef.update("tags",tags).get());
    }

    /**
     * This is used to get the relevant document user for our user.
     * @param uid UID that belongs to our first year.
     * @param collection Collection from firestore
     * @return Document Reference connected to our first year
     */
    @Override
    public DocumentReference doc(String uid,String collection) {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionRef = db.collection(collection);
        return collectionRef.document(uid);
    }

    /**
     * Get the first years concentrations
     * @return Concentrations formatted into a list of strings
     */
    @Override
    public List<String> getConcentration() {
        return this.concentrations;
    }

    /**
     * Return tags for user
     * @return list of string, with each string representing a tag.
     */
    @Override
    public List<String> getTags() {
        return this.tags;
    }

    /**
     * Retrieve the first years search data which holds how many times a user searches certain tags.
     * @param uid First year's
     * @return A Map<String,String> with each key being what is searched and the value a string number of how
     * many times it was searched.
     * @throws Exception Exceptions from firebase.
     */
    @Override
    public Map<String, String> getSearch(String uid) throws Exception {

return this.search;

    }

    /**
     * This method is used for meiks.
     * @return
     * @throws Exception
     */
    @Override
    public String getID() throws Exception {
        throw new Exception("For meiks");
    }

    /**
     * This begins our ranking algorithm by given everything in the tag list a default rating value of 5.
     * @throws Exception Working with data structures.
     */
    @Override
    public void initializeTagsBuckets() throws Exception {
        Map<String,Double> tagRating = new HashMap<>();
        for (String tag: this.tags){
            if(!tagRating.containsKey(tag)){
                tagRating.put(tag,5.0);
            }
        }
    }



    /**
     * This begins our ranking algorithm by given everything in the concentration list a default rating value of 5.
     * @throws Exception Working with data structures.
     */
    @Override
    public void initializeConcentrationBuckets() throws Exception {
        Map<String,Double> concentrationRating = new HashMap<>();
        for (String tag: this.concentrations){
            if(!concentrationRating.containsKey(tag)){
                concentrationRating.put(tag,5.0);
            }
        }
    }

    /**
     * Set everything in tag with a default value of 5
     * @return A Map<String, Double> with the string being the tag, and double being the value given.
     */
    @Override
    public Map<String,Double> setTagRankings() {
        Map<String,Double> ratings = new HashMap<>();

        List<String> officialTags = new ArrayList<>();
        officialTags.add("Music");
        officialTags.add("Sports");
        officialTags.add("International");
        officialTags.add("On Campus Job");
        officialTags.add("UFLI");
        officialTags.add("Pre-Med");
        officialTags.add("Pre-Law");
        officialTags.add("LGBTIQA2S+");
        officialTags.add("Dance");
        officialTags.add("Arts");
        officialTags.add("Sprint/Utra");
        officialTags.add("Languages");

        for(String tag: this.tags){
            if(!ratings.containsKey(tag)){
                ratings.put(tag,5.0);
            }
        }


        return ratings;

    }


    /**
     * This method allows us to update our first year's text
     * @param text New Text
     * @param uid uid for fist year
     * @param collection firestore collection
     * @throws ExecutionException Firestore exception
     * @throws InterruptedException Firestore exception
     */
    @Override
    public void updateUserText(String text, String uid, String collection) throws ExecutionException, InterruptedException {
        DocumentReference userDocRef = this.doc(uid,collection);
        System.out.println(userDocRef.update("text",text).get());
    }

    /**
     * This is a part of our ranking algorithm that gives rankings based off of how much we search something.
     * @param uid First year's uid.
     * @return Map of String,Double with rankings for values searched.
     * @throws ExecutionException Firestore exception
     * @throws InterruptedException Firestore exception
     */
    @Override
    public Map<String, Double> getTagRankings(String uid) throws ExecutionException, InterruptedException {
        //list of official tag
        List<String> officialTags = new ArrayList<>();

        officialTags.add("Music");
        officialTags.add("Sports");
        officialTags.add("International");
        officialTags.add("On Campus Job");
        officialTags.add("UFLI");
        officialTags.add("Pre-Med");
        officialTags.add("Pre-Law");
        officialTags.add("LGBTIQA2S+");
        officialTags.add("Dance");
        officialTags.add("Arts");
        officialTags.add("Sprint/Utra");
        officialTags.add("Languages");

        DocumentReference reference= this.doc(uid,"FirstYears");
        DocumentSnapshot snapshot = reference.get().get();

        Map<String,Double> rankings = this.setTagRankings();
        if(rankings == null){
            rankings = new HashMap<>();
        }

        Map<String,String> searched = (Map<String, String>) snapshot.get("search");
        //Give rankings based off of how much something is searched.
        if(!(searched == null) ) {
            for (String search : searched.keySet()) {
                if (officialTags.contains(search)) {
                    double val = Double.parseDouble( searched.get(search)) * 0.75;
                    if (!rankings.containsKey(search)) {
                       rankings.put(search, val);
                    } else {
                        val = rankings.get(search) + Double.parseDouble(searched.get(search)) * 0.75;
                        rankings.put(search, val);
                    }
                }
            }
        }

        return rankings;

    }


}
