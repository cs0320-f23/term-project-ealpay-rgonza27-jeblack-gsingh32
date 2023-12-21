package edu.brown.cs.student.main.Matching;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import edu.brown.cs.student.main.User.Meik;
import edu.brown.cs.student.main.User.User;
import edu.brown.cs.student.main.User.UserInformation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/***
 *This is the matching class that is used to match meiks for first year users.
 *
 */
public class Matching {
    // List to keep all our meiks.
    private List<User> meiks;
    // Constructor that preserves our meiks so that we don't hav to call on firestore to get the meiks for every method.
    public Matching() throws IOException, ExecutionException, InterruptedException {
        // Initialize our meiks.
        this.meiks = this.getMeiks();
    }

    /***
     * Retrieves our meiks from the backend.
     * @return List of meiks.
     * @throws IOException Exceptions from reading in our firebase credentials
     * @throws ExecutionException Exceptions from fetching data from firebase
     * @throws InterruptedException Exceptions from fetching data from firebase
     */
    private List<User> getMeiks() throws IOException, ExecutionException, InterruptedException {
        List<User> meiks = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        // Specify the collection to query
        CollectionReference meiksCollection = db.collection("meiks");
        UserInformation userInformation = new UserInformation();
        // Get all references to documents in the collection
        Iterable<DocumentReference> documentReferences = meiksCollection.listDocuments();
        for (DocumentReference documentReference : documentReferences) {
            // Get the DocumentSnapshot for each reference
            DocumentSnapshot documentSnapshot = documentReference.get().get();

            if (documentSnapshot.exists()) {
                //add meik to list
                User meik = userInformation.getUserFromDocRef(documentSnapshot, "meiks");
                meiks.add(meik);
            }
        }
        return meiks;
    }

    /***
     * Returns the highest tags for our user.
     * @param uid which is the uid of our first year.
     * @return List of the highest tags, multiple tags can be the highest if they have the same ranking/rating
     * @throws IOException Exceptions from reading in our firebase credentials.
     * @throws ExecutionException Exceptions from fetching data from firebase.
     * @throws InterruptedException Exceptions from fetching data from firebase.
     */
    public List<String> getHighestTags(String uid) throws IOException, ExecutionException, InterruptedException {

        Ranking ranking = new Ranking(); // The class used to rank.
        Map<String, Double> ratings = ranking.rankOnSearch(uid);// Rank the tags based off of algorithms.

        String highest = null;
        double val = 0;
        for (String tag : ratings.keySet()) {
            double curr = ratings.get(tag);
            if (curr > val) { // Finds the highest tag.
                val = curr;
                highest = tag;
            }
        }
        List<String> highestTags = new ArrayList<>();
        highestTags.add(highest);

        double high = ratings.get(highest);
        //Make sure we do not leave out tags with the same ratings/value.
        for (String tag : ratings.keySet()) {
            double curr = ratings.get(tag);
            if (curr == high) {
                if(!highestTags.contains(tag)) highestTags.add(tag);
            }
        }

        return highestTags;

    }

    /**
     *  Returns the meiks that have the users highest tags in their tag-collection
     *  @param uid  which is the uid of our first year.
     *  @return Returns the meiks that have the users highest tags in their tag-collection
     *  @throws IOException Exceptions from reading in our firebase credentials.
     *  @throws ExecutionException Exceptions from fetching data from firebase.
     *  @throws InterruptedException Exceptions from fetching data from firebase.
     */
    public List<User> getMatchingMeiksByTag(String uid) throws ExecutionException, InterruptedException, IOException {
        List<String> highTags = this.getHighestTags(uid) ; //get the highest tags for the users.
        if(highTags.isEmpty()){
            return new ArrayList<>(); //if it is empty, we can not find a match so we have an empty array list.
        }
        List<User> reccMeiks = new ArrayList<>();//the list of matching meiks we will return.
        for(User user : this.meiks){

           List<String> tags =  user.getTags(); //the meiks tags
           for(String tag: tags){
               for(String highTag : highTags) {
                   if (tag.equals(highTag)) {
                       reccMeiks.add(user);// if the tags match, add the meik to the list.
                   }
               }
           }
        }
        return reccMeiks;
    }


    /**
     * This is not completed, but would factor in concentration when matching meiks.
     *  @param uid  which is the uid of our first year.
     *  @return Returns the meiks that have the users highest tags in their tag-collection
     *  @throws IOException Exceptions from reading in our firebase credentials.
     *  @throws ExecutionException Exceptions from fetching data from firebase.
     *  @throws InterruptedException Exceptions from fetching data from firebase.
     */
    public List<User> getMatchingMeiksConcentration(String uid) throws IOException, ExecutionException, InterruptedException {
        UserInformation userInformation = new UserInformation();
        User firstYear = userInformation.getUserFromId(uid,"FirstYears");
        String firstConc = firstYear.getConcentration().get(0);
        List<User> matched = new ArrayList<>();
        for(User user : this.meiks){
            List<String> concentrations =  user.getConcentration();
            for(String conc : concentrations){
                if(conc.equals(firstConc)){
                    matched.add(user);
                }
            }

        }

        return matched;
    }
}



