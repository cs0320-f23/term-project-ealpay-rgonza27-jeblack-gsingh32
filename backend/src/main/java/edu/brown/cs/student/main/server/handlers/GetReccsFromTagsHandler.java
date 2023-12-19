package edu.brown.cs.student.main.server.handlers;

import edu.brown.cs.student.main.Matching.Matching;
import edu.brown.cs.student.main.Matching.Ranking;
import edu.brown.cs.student.main.User.User;
import edu.brown.cs.student.main.server.responses.RecommendedMeiksResponse;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetReccsFromTagsHandler implements Route {


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


        Map<String ,Object> result = new HashMap<>();
        String uid = request.queryParams("uid");
        if(uid == null){
            result.put("bad_rqst","uid was not provided");
        }
        try {
            Matching matching = new Matching();
            List<User> meiks = matching.getMatchingMeiksByTag(uid);
            result.put("result","success");
            result.put("meiks",meiks);
        }
        catch (Exception e){
            result.put("error",e.getMessage());
        }
        RecommendedMeiksResponse response1 = new RecommendedMeiksResponse(uid,result);

        return response1.serialize();
    }
}
