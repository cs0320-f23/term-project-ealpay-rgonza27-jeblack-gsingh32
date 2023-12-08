package edu.brown.cs.student.main.User;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs.student.main.Authorization.FirebaseInitialize;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public record User(String name, String concentrations, String location,
                   List<String> tags, String email,Map<String, Integer> search) {



    public String concentration(){
        return this.concentrations;
    }

    public List<String> getTags(){
        return this.tags;
    }

    public Map<String,Integer> getSearch(){
        return this.search;
    }

}
