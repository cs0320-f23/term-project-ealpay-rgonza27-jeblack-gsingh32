package edu.brown.cs.student.main.server.handlers;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.brown.cs.student.main.User.User;
import edu.brown.cs.student.main.User.UserInformation;
import edu.brown.cs.student.main.server.responses.UserDataResponse;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.util.HashMap;
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
    Map<String,Object> userResponse = new HashMap<>();
    if(userId == null){
      userResponse.put("err_bad_rqst","Missing ID");
      UserDataResponse response1 = new UserDataResponse("Null",userResponse);
      return response1.serialize();
    }

    try {
      UserInformation userInformation = new UserInformation();
      User user1 = userInformation.getUser(userId,"meiks");
      userResponse.put("user",user1);
      userResponse.put("result","success");

    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      //Create record for failures in retrieving data.
      userResponse.put("result","Failure");
      userResponse.put("message",e.getMessage());

      UserDataResponse response = new UserDataResponse(userId,userResponse);
      return response.serialize();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    UserDataResponse response = new UserDataResponse(userId,userResponse);
    return response.serialize();

  }
}

