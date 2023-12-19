package edu.brown.cs.student.main.User;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UserCreator {



    public void createFirstYear(FirstYear firstYear) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionRef = db.collection("FirstYears");
        Map<String,Object> data = new HashMap<>();
        data.put("name",firstYear.name());
        data.put("concentration",firstYear.concentrations());
        data.put("search",firstYear.search());
        data.put("tags",firstYear.tags());
        data.put("email",firstYear.email());
        data.put("location",firstYear.location());
        data.put("rankings",firstYear.setTagRankings());
        System.out.println("hi");
        collectionRef.add(data).get().get();
        collectionRef.getId();

    }


}
