package edu.brown.cs.student.main.server.handlers;

import com.google.api.services.storage.Storage.Buckets.Get;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import edu.brown.cs.student.main.server.responses.AllMeikDataResponse;
import edu.brown.cs.student.main.utils.ImageCacheService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class GetAllMeikHandler implements Route {

  private ImageCacheService imageCacheService;

  public GetAllMeikHandler(ImageCacheService imageCacheService) {
    this.imageCacheService = imageCacheService;
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    response.type("application/json");

    // Return all users in the collection as a list of JSON

    return getAllUsers();
  }

  /***
   * Gets all meik data and serializes it into a
   * @return
   */

  private String getAllUsers() {
    List<Map<String,Object>> userList = new ArrayList<>();
    try {

      Firestore db = FirestoreClient.getFirestore();

      // Specify the collection to query
      CollectionReference meiksCollection = db.collection("meiks");
      System.out.println("getting all meiks");
      // Get all references to documents in the collection
      Iterable<DocumentReference> documentReferences = meiksCollection.listDocuments();

      for (DocumentReference documentReference : documentReferences) {
        // Get the DocumentSnapshot for each reference
        DocumentSnapshot documentSnapshot = documentReference.get().get();

        if (documentSnapshot.exists()) {
          //System.out.println("Successfully retrieved user: " + documentSnapshot.getId());
          // Get all contents of the document as a Map
          Map<String, Object> userData = documentSnapshot.getData();
          String userId = (String)userData.get("id");
          if(userId != null){
            String image = imageCacheService.getImage(userId);
            userData.put("image", image);
          }

          // Convert the Map to JSON and add to the list
          userList.add(userData);
        } else {
          System.err.println("Document not found for reference: " + documentReference.getPath());
          //Create a failed response
          AllMeikDataResponse response = new AllMeikDataResponse("Document not found for reference: " +
                  documentReference.getPath(),
                  null);
          return response.serialize();

        }
      }

    } catch (InterruptedException | ExecutionException e) {

      e.printStackTrace();
      //Create a failed response
      AllMeikDataResponse response = new AllMeikDataResponse("Error retrieving data: "+e.getMessage(),
              null);
      return response.serialize();

    }

    //Create a success response
    AllMeikDataResponse response = new AllMeikDataResponse("Successfully retrieved data",userList);
    return response.serialize();

  }

}

