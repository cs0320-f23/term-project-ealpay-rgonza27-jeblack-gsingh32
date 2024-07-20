package edu.brown.cs.student.main.Matching;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import edu.brown.cs.student.main.User.User;
import edu.brown.cs.student.main.User.UserInformation;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * This class is used to delegate the algorithm used to match. Ranking gets all the data needed to run our algorithm,
 * and use that data to rank.
 *
 */
public class Ranking {


    /**
     *
     * @param userToRankID The first year ID we are determining ranks for.
     * @return Return a map with the  new ranks for this user, in string to double format.
     * @throws IOException Exceptions from reading in our firebase credentials.
     * @throws ExecutionException Exceptions from fetching data from firebase.
     * @throws InterruptedException Exceptions from fetching data from firebase.
     */
    public Map<String,Double> rankOnSearch(String userToRankID)
            throws IOException, ExecutionException, InterruptedException {

        Map<String,Map<String,Double>> userRatings = new HashMap<>();
        Map<String,Double> missingRanks = new HashMap<>();
        UserInformation userInformation = new UserInformation();
        //Get information
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
                   user = userInformation.getUserFromDocRef(documentReference,"FirstYears");
                   userRatings.put(documentSnapshot.getId(), user.getTagRankings(documentSnapshot.getId()));
                }
            }

            //Call on our collaborative filter algorithm
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