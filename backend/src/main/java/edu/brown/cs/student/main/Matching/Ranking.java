package edu.brown.cs.student.main.Matching;

import com.google.api.client.util.ArrayMap;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import edu.brown.cs.student.main.User.User;
import edu.brown.cs.student.main.User.UserInformation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Ranking {



    public Map<String,Double> rankOnSearch(String userToRankID) throws IOException, ExecutionException, InterruptedException {
        Map<String,Map<String,Double>> userRatings = new HashMap<>();
        Map<String,Double> missingRanks = new HashMap<>();
        UserInformation userInformation = new UserInformation();
        User userThis = userInformation.getUserFromId(userToRankID,"FirstYears");

        try {

            Firestore db = FirestoreClient.getFirestore();
            Map<String, Double> userTagRankings = userThis.getTagRankings(userToRankID);
            // Specify the collection to query
            CollectionReference firstYearsCollection = db.collection("FirstYears");
            // Get all references to documents in the collection
            Iterable<DocumentReference> documentReferences = firstYearsCollection.listDocuments();
            for (DocumentReference documentReference : documentReferences) {
                // Get the DocumentSnapshot for each reference
                DocumentSnapshot documentSnapshot = documentReference.get().get();
                User user;
                if (documentSnapshot.exists()) {
                   user = userInformation.getUserFromDocRef(documentSnapshot,"FirstYears");
                   userRatings.put(documentSnapshot.getId(), user.getTagRankings(documentSnapshot.getId()));
                }
            }


            UserBasedCollaborativeFiltering collaborativeFiltering = new UserBasedCollaborativeFiltering();
            missingRanks =
                    collaborativeFiltering.getRecommendations(userToRankID,userRatings);

            for(String tag : missingRanks.keySet()){
                userTagRankings.put(tag,missingRanks.get(tag));
            }
            return userTagRankings;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }



}