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

public class Matching {
    private List<User> meiks;
    public Matching() throws IOException, ExecutionException, InterruptedException {
        this.meiks = this.getMeiks();
    }

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
                User meik = userInformation.getUserFromDocRef(documentSnapshot, "meiks");
                meiks.add(meik);
            }
        }
        return meiks;
    }


    public List<String> getHighestTags(String uid) throws IOException, ExecutionException, InterruptedException {

        Ranking ranking = new Ranking();
        Map<String, Double> ratings = ranking.rankOnSearch(uid);

        String highest = null;
        double val = 0;
        for (String tag : ratings.keySet()) {
            double curr = ratings.get(tag);
            if (curr > val) {
                val = curr;
                highest = tag;
            }
        }
        List<String> highestTags = new ArrayList<>();
        highestTags.add(highest);

        double high = ratings.get(highest);

        for (String tag : ratings.keySet()) {
            double curr = ratings.get(tag);
            if (curr == high) {
                if(!highestTags.contains(tag)) highestTags.add(tag);
            }
        }

        return highestTags;

    }

    public List<User> getMatchingMeiksByTag(String uid) throws ExecutionException, InterruptedException, IOException {
        List<String> highTags = this.getHighestTags(uid) ;
        if(highTags.isEmpty()){
            return new ArrayList<>();
        }
        List<User> reccMeiks = new ArrayList<>();
        for(User user : this.meiks){
           List<String> tags =  user.getTags();
           for(String tag: tags){
               for(String highTag : highTags) {
                   if (tag.equals(highTag)) {
                       reccMeiks.add(user);
                   }
               }
           }
        }
        return reccMeiks;
    }


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



