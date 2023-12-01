package edu.brown.cs.student.main.server.handlers;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
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

        // Convert the Map to JSON
        return convertMapToJson(userData);
      } else {
        System.err.println("User not found for ID: " + userId);
        return "{\"error\":\"User not found\"}";
      }

    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      return "{\"error\":\"" + e.getMessage() + "\"}";
    }
  }

  private String convertMapToJson(Map<String, Object> data) {
    // Convert the Map to JSON (you can use a JSON library for better handling)
    StringBuilder jsonBuilder = new StringBuilder("{");
    for (Map.Entry<String, Object> entry : data.entrySet()) {
      jsonBuilder.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\",");
    }
    // Remove the trailing comma and add the closing brace
    if (jsonBuilder.length() > 1) {
      jsonBuilder.setLength(jsonBuilder.length() - 1);
    }
    jsonBuilder.append("}");
    return jsonBuilder.toString();
  }
}


