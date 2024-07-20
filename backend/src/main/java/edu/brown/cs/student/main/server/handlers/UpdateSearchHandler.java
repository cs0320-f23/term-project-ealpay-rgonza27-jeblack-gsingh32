package edu.brown.cs.student.main.server.handlers;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import edu.brown.cs.student.main.User.User;
import edu.brown.cs.student.main.User.UserInformation;
import edu.brown.cs.student.main.server.responses.UserUpdateResponse;
import edu.brown.cs.student.main.utils.ImageCacheService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateSearchHandler implements Route {
    /**
     * Invoked when a request is made on this route's corresponding path e.g. '/hello'
     *
     * @param request  The request object providing information about the HTTP request
     * @param response The response object providing functionality for modifying the response
     * @return The content to be set in the response
     * @throws Exception implementation can choose to throw exception
     */


    @Override
    public Object handle(Request request, Response response) throws Exception {
        Map<String,Object> result = new HashMap<>();
        String searched = request.queryParams("searched");
        String uid = request.queryParams("uid");
        //error checking
        if(searched == null){
            result.put("bad_rqst","searched values are null");
            UserUpdateResponse response_error = new UserUpdateResponse(uid,result,"failure");

        }
        if(uid == null){
            result.put("bad_rqst","uid is null");
            UserUpdateResponse response_error = new UserUpdateResponse(null,result,"failure");
        }
        //Add the searched values to the existing hashmap
        try {
            List<String> searchedStrings = List.of(searched.split(","));
            UserInformation userInformation = new UserInformation();
            User fy = userInformation.getUserFromId(uid, "FirstYears");
            Map<String,String> searches =  fy.getSearch(uid);
            System.out.println("Searches:"+searches);

            for(String search: searchedStrings){
                if(searches.containsKey(search)){
                    Integer val = Integer.parseInt(searches.get(search));
                    val =val +1;
                    Integer put = val;
                    searches.put(search,put.toString());
                }else{
                    searches.put(search,"1");
                }
            }
            System.out.println("SearchesB:"+searches);
            Firestore db = FirestoreClient.getFirestore();
            CollectionReference collectionRef = db.collection("FirstYears");
            DocumentReference ref =  collectionRef.document(uid);
            ref.update("search",searches).get();
            result.put("no_error","true");
            result.put("result","success");
            result.put("new_search",searches);
        }
        catch (Exception e){
            result.put("error",e.getMessage());
            UserUpdateResponse response_error = new UserUpdateResponse(uid,result,"failure");
            return response_error.serialize();
        }

        UserUpdateResponse response1 = new UserUpdateResponse(uid,result,"success");
        return response1.serialize();
    }
}
