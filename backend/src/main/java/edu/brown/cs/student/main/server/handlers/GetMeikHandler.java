package edu.brown.cs.student.main.server.handlers;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.brown.cs.student.main.server.responses.MeikDataResponse;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class GetMeikHandler implements Route {

  @Override
  public Object handle(Request request, Response response) throws Exception {
    response.type("application/json");
    String userId = request.queryParams("id");

    // Return the user data as JSON
    return getUserById(userId);
  }

  /**
   * This returns meik information from a given uid and serializes it into json format.
   * @param userId
   * @return
   */

  private String getUserById(String userId) {
    try {
      Firestore db = FirestoreClient.getFirestore();

      // Specify the collection to query
      CollectionReference meiksCollection = db.collection("meiks");

      // Example query: Get the document with the specified userId
      DocumentReference userDocRef = meiksCollection.document(userId);
      DocumentSnapshot document = userDocRef.get().get();

      if (document.exists()) {
        System.out.println("Successfully retrieved user: " + document.getId());

        // Get all contents of the document as a Map
        Map<String, Object> userData = document.getData();
        //Create a record of the retrieved data
        MeikDataResponse response = new MeikDataResponse(userId,userData,"Successfully retrieved data");
        return response.serialize();
      } else {
        System.err.println("User not found for ID: " + userId);
        //Create record for response where UID is not found.
        MeikDataResponse response = new MeikDataResponse(userId,null,"User not found for ID");
        return response.serialize();
      }

    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      //Create record for failures in retrieving data.
      MeikDataResponse response = new MeikDataResponse(userId,null,e.getMessage());
      return response.serialize();
    }
  }
}


