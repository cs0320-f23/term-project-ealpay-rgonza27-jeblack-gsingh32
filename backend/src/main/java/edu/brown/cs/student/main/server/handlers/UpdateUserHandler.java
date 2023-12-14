package edu.brown.cs.student.main.server.handlers;

import edu.brown.cs.student.main.User.User;
import edu.brown.cs.student.main.User.UserInformation;
import edu.brown.cs.student.main.server.responses.UserUpdateResponse;
import java.util.List;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class UpdateUserHandler implements Route {

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
        String collection = request.queryParams("collection");

        if(userId == null){
            results.put("err_bad_request","Missing UID");
            UserUpdateResponse response1 = new UserUpdateResponse("N/A",null,"Failure");
            return response1.serialize();
        }
        if(collection == null){
            results.put("err_bad_request","Missing Collection");
            UserUpdateResponse response1 = new UserUpdateResponse("N/A",null,"Failure");
            return response1.serialize();
        }


        String location = request.queryParams("location");
        String concentration = request.queryParams("concentration");
        String year = request.queryParams("year");
        String name = request.queryParams("name");
        String tag = request.queryParams("tag");
        String text = request.queryParams("text");
        User user = null;

        try {
            UserInformation userInformation = new UserInformation();
            user = userInformation.getUser(userId, collection);

            if (!(location == null)) {
                user.updateUserLocation(location,userId,collection);
                results.put("location_status","Success");
            }
            if (!(tag == null)) {
                user.updateUserTags(List.of(tag.split(",")),userId,collection);
                results.put("tags_status","Success");
            }
            if (!(text == null)) {
                user.updateUserText(text,userId,collection);
                results.put("text_status","Success");
            }

            if (!(name == null)) {
                user.updateUserName(name,userId,collection);
                results.put("name_status","Success");

            }
            if (!(concentration == null)) {
                user.updateUserConcentration(concentration,userId,collection);
                results.put("concentration_status","Success");

            }
            if (!(year == null)) {
                user.updateUserYear(year,userId,collection);
                results.put("year_status","Success");

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
