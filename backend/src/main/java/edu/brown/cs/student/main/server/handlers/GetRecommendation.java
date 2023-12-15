package edu.brown.cs.student.main.server.handlers;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import edu.brown.cs.student.main.User.User;
import edu.brown.cs.student.main.User.UserInformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class GetRecommendation {



    public Map<String, Map<String,Double>> getUsersRatings() throws ExecutionException, InterruptedException {

        Map<String, Map<String,Double>> ratings = new HashMap<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionRef = db.collection("FirstYears");
        Iterable<DocumentReference> documentReferences = collectionRef.listDocuments();
        UserInformation userInfo = new UserInformation();
        List<User> userList = new ArrayList<>();

        try {
            for(DocumentReference doc : documentReferences ){

                DocumentSnapshot documentSnapshot = doc.get().get();
                User user = userInfo.getUserFromDocRef(documentSnapshot,"FirstYears");
                userList.add(user);
                user.initializeTagsBuckets();
                ratings.put(user.getName(),user.getTagRankings());
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
