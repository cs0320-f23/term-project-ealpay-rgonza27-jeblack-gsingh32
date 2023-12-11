package edu.brown.cs.student.main.server.handlers;

import edu.brown.cs.student.main.User.User;
import edu.brown.cs.student.main.User.UserInformation;
import edu.brown.cs.student.main.server.responses.UserDataResponse;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class GetUserHandler implements Route {
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
        Map<String,Object> userResponse = new HashMap<>();
        String userId = request.queryParams("id");
        if(userId == null){
            userResponse.put("err_bad_rqst","Missing ID");
            UserDataResponse response1 = new UserDataResponse("Null",userResponse);
            return response1.serialize();
        }
        else {

            try {
                UserInformation user = new UserInformation();
                User firstYear = user.getUser(userId, "FirstYears");
                userResponse.put("user", firstYear);
                System.out.println(firstYear);
                userResponse.put("result", "success");
                UserDataResponse response1 = new UserDataResponse(userId, userResponse);
                return response1.serialize();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Check error :" + e.getMessage());
                System.out.println();

                userResponse.put("message", e.getMessage());
                userResponse.put("result", "failure");
                UserDataResponse response1 = new UserDataResponse(userId, userResponse);
                return response1.serialize();

            }

        }
    }
}
