package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.Authorization.RegisterUser;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationHandler implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        try {
            RegisterUser registerUser = new RegisterUser();
            String email = request.queryParams("email");
            if(!this.getEmailProvider(email).equals("brown.edu")){
                throw new IllegalArgumentException("Please provide your Brown email");
            }
            //String password = request.queryParams("password");

            String result = registerUser.saverUser("gurpartap_singh@brown.edu","testPassword1029-3");
            return result;
        } catch (Exception a) {
            return "oh oh :" + a.getMessage();
        }
    }


    public String getEmailProvider(String email) {
        // Regular expression pattern to match the domain part of the email
        String regex = "@([a-zA-Z0-9.-]+)$";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Create a Matcher object
        Matcher matcher = pattern.matcher(email);

        // Check if the pattern matches
        if (matcher.find()) {
            // Extract and return the matched email provider
            return matcher.group(1);
        } else {
            // Return null if no match is found
            return null;
        }
    }
}
