package edu.brown.cs.student.main.server.handlers;

import edu.brown.cs.student.main.User.Meik;
import edu.brown.cs.student.main.User.User;
import edu.brown.cs.student.main.User.UserInformation;
import edu.brown.cs.student.main.server.responses.UserDataResponse;
import edu.brown.cs.student.main.server.responses.UserUpdateResponse;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class UpdateMeikHandler implements Route {

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

        Map<String,Object> results = new HashMap<>();
        String userId = request.queryParams("id");

        if(userId == null){
            results.put("err_bad_request","Missing UID");
            UserUpdateResponse response1 = new UserUpdateResponse("N/A",null,"Failure");
            return response1.serialize();
        }

        String location = request.queryParams("location");
        String email = request.queryParams("email");
        String text = request.queryParams("text");
        //String year = request.queryParams("year");
        String name = request.queryParams("name");
        String tag = request.queryParams("tag");
        String tagAction = request.queryParams("action");
        User user = null;

        try {
            UserInformation userInformation = new UserInformation();
            user = userInformation.getUser(userId, "meiks");

            if (!(location == null)) {
                user.updateUserLocation(location);
                results.put("location_status","Success");
            }
            if (!(email == null)) {
                user.updateUserEmail(email);
                results.put("email_status","Success");
            }
            if (!(text == null)) {
                user.updateUserText(text);
                results.put("text_status","Success");

            }
            if (!(name == null)) {
                user.updateUserName(name);
                results.put("name_status","Success");

            }
            if (!(tag == null)) {
                if (!(tagAction == null)) {
                    if (tagAction.equals("add")) {
                        user.addUserTags(tag);
                        results.put("tag_add","Success");

                    }
                    if (tagAction.equals("remove")) {
                        user.removeUserTags(tag);
                        results.put("tag_remove","Success");

                    }
                }
            }
        }
        catch (Exception e){
            results.put("error",e.getMessage());
            e.printStackTrace();
        }

        UserUpdateResponse response1 = new UserUpdateResponse(userId,results,"Success");
        return  response1.serialize();
    }
}
