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
        String email = request.queryParams("email");
        String password = request.queryParams("password");
        if(email == null){
            return new RegistrationResponse("Failure","none",false,
                    "Please provide a brown affiliated email.");
        }
        if(password == null){
            return new RegistrationResponse("Failure","none",false,
                    "Please provide a password.");
        }
        try {
            RegisterUser registerUser = new RegisterUser();
            boolean result = registerUser.saverUser(email,password);
            return new RegistrationResponse("Success",email,result,
                    "Registration Complete");
        } catch (Exception e) {
            return new RegistrationResponse("Failure",email,false, e.getMessage());
        }
    }



}
