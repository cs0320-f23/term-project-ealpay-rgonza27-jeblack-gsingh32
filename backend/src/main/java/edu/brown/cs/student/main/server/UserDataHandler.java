package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.User.UserInformation;
import spark.Request;
import spark.Response;
import spark.Route;

public class UserDataHandler implements Route {
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
        UserInformation info = new UserInformation();
        return null;
    }
}
