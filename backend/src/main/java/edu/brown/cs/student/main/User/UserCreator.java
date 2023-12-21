package edu.brown.cs.student.main.User;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * This class holds all methods necessary to create new users.
 */
public class UserCreator {


    /**
     * Create a new first yaer and adds that to our database.
     * @param firstYear Which has the information of our new first year.
     * @throws ExecutionException Firebase exception
     * @throws InterruptedException Firebase exception
     */
    public void createFirstYear(FirstYear firstYear) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionRef = db.collection("FirstYears");
        //Create hashmap with all relevant information
        Map<String,Object> data = new HashMap<>();
        data.put("name",firstYear.name());
        data.put("concentration",firstYear.concentrations());
        data.put("search",firstYear.search());
        data.put("tags",firstYear.tags());
        data.put("email",firstYear.email());
        data.put("location",firstYear.location());
        data.put("rankings",firstYear.setTagRankings());
        //Send that hashmap to our database.
        collectionRef.add(data).get().get();
        collectionRef.getId();

    }


}
