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

public record FirstYear(String name, List<String> concentrations, String location,
                        List<String> tags, String email, Map<String,Integer> search)
        implements User {







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

    @Override
    public List<String> getConcentration() {
        return this.concentrations;
    }

    @Override
    public List<String> getTags() {
        return null;
    }

    @Override
    public void initializeTagsBuckets() throws Exception {
        Map<String,Double> tagRating = new HashMap<>();
        for (String tag: this.tags){
            if(!tagRating.containsKey(tag)){
                tagRating.put(tag,5.0);
            }
        }
    }

    @Override
    public void updateTagBucket() {

    }


    @Override
    public void initializeConcentrationBuckets() throws Exception {
        Map<String,Double> concentrationRating = new HashMap<>();
        for (String tag: this.concentrations){
            if(!concentrationRating.containsKey(tag)){
                concentrationRating.put(tag,5.0);
            }
        }
    }
    @Override
    public String getName() {
        return name;
    }

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



    @Override
    public void updateUserText(String text, String uid, String collection) throws ExecutionException, InterruptedException {

    }


    @Override
    public Map<String, Double> getTagRankings(String uid) throws ExecutionException, InterruptedException {

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
