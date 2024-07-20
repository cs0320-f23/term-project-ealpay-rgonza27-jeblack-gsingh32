package edu.brown.cs.student.main.server.handlers;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs.student.main.User.FirstYear;
import edu.brown.cs.student.main.User.UserCreator;
import edu.brown.cs.student.main.server.responses.MeikDataResponse;
import spark.Request;
import spark.Response;
import spark.Route;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateFirstYearHandler implements Route {
    /**
     *  This handler creates a first year and adds them to our firestore database.
     * @param request  The request object providing information about the HTTP request
     * @param response The response object providing functionality for modifying the response
     * @return The content to be set in the response
     * @throws Exception implementation can choose to throw exception
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {

        Moshi moshi = new Moshi.Builder().build();
        Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);
        //Get information
        Map<String,Object> results = new HashMap<>();
        String name = request.queryParams("name"); ;
        String concentration = request.queryParams("concentration");
        String location = request.queryParams("location");
        String tag = request.queryParams("tags");
        String email = request.queryParams("email");
        Map<String,String> search = new HashMap<>();

        try {
            List<String> concentrations = List.of(concentration.split(","));
            List<String> tags = List.of(tag.split(","));
            FirstYear newFirstYear = new FirstYear(name, concentrations, location, tags, email, search);
            UserCreator userCreator = new UserCreator();
            //Create First Year
            userCreator.createFirstYear(newFirstYear);
            results.put("result","no_error");

        } catch (Exception e){
            results.put("result","error");
            results.put("error",e.getMessage());
        }

        return adapter.toJson(results);
    }
}
