package edu.brown.cs.student;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import edu.brown.cs.student.main.Authorization.FirebaseInitialize;
import edu.brown.cs.student.main.server.RegistrationHandler;
import edu.brown.cs.student.main.server.RegistrationResponse;
import edu.brown.cs.student.main.server.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Spark;
import okio.Buffer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.testng.AssertJUnit.assertEquals;

public class RegistrationIntegrationTest {


    @BeforeEach
    public void setup() throws Exception {
        Spark.port(0);
        // Re-initialize state, etc. for _every_ test method run
        Server server = new Server();

        // In fact, restart the entire Spark server for every test!
        try {
            FirebaseInitialize initialize = new FirebaseInitialize();
            FirebaseInitialize.initialize();
        } catch (Exception e) {
            System.err.println("Could not connect to database: " + e.getMessage());
        }
        Spark.get("registerUser", new RegistrationHandler());
        Spark.init();
        Spark.awaitInitialization(); // don't continue until the server is listening
        Logger.getLogger("").setLevel(Level.WARNING);

    }

    public void tearDown() {
        // Gracefully stop Spark listening on both endpoints
        // Spark.unmap("loadCSV");
        Spark.unmap("registerUser");
        Spark.awaitStop(); // don't proceed until the server is stopped
    }

    private HttpURLConnection tryRequest(String apiCall) throws Exception {
        // Configure the connection (but don't actually send a request yet)

        URL requestURL = new URL("http://localhost:"+Spark.port()+"/"+apiCall);
        HttpURLConnection clientConnection = (HttpURLConnection) requestURL.openConnection();
        // The request body contains a Json object
        clientConnection.setRequestProperty("Content-Type", "application/json");
        // We're expecting a Json object in the response body
        clientConnection.setRequestProperty("Accept", "application/json");

        clientConnection.connect();
        return clientConnection;
    }



    @Test
    public void testRegistration() throws Exception {

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<RegistrationResponse> adapter = moshi.adapter(RegistrationResponse.class);

        /////////// LOAD DATASOURCE ///////////
        // Set up the request, make the request
        HttpURLConnection loadConnection = tryRequest("registerUser?" + "email=test@brown.edu"+"&"+"password=12345passwordcheck");
        // Get an OK response (the *connection* worked, the *API* provides an error response)
        assertEquals(200, loadConnection.getResponseCode());
        //Get the expected response: a success
        RegistrationResponse body = adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
        //showDetailsIfError(body);
        System.out.println(body);
        assertEquals("Success", body.result());
        assertEquals("test@brown.edu", body.emailAddress());
        assertEquals("Registration Complete", body.message());


    }
}
