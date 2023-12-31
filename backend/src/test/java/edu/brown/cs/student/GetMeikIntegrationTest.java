package edu.brown.cs.student;


import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import edu.brown.cs.student.main.Authorization.FirebaseInitialize;
import edu.brown.cs.student.main.server.Server;
import edu.brown.cs.student.main.server.handlers.GetMeikHandler;
import edu.brown.cs.student.main.server.responses.MeikDataResponse;
import edu.brown.cs.student.main.utils.ImageCacheService;
import okio.Buffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Spark;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.testng.AssertJUnit.assertEquals;

public class GetMeikIntegrationTest {

  @BeforeEach
  public void intitializeFirebase() {
    try {
      FirebaseInitialize initialize = new FirebaseInitialize();
      FirebaseInitialize.initialize();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

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
    Spark.get("getMeikById", new GetMeikHandler(new ImageCacheService()));
    Spark.init();
    Spark.awaitInitialization(); // don't continue until the server is listening
    Logger.getLogger("").setLevel(Level.WARNING);

  }

  public void tearDown() {
    // Gracefully stop Spark listening on both endpoints
    Spark.unmap("getMeikById");
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
  public void testGetMeikById() throws Exception {

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<MeikDataResponse> adapter = moshi.adapter(MeikDataResponse.class);

    /////////// LOAD DATASOURCE ///////////
    // Set up the request, make the request
    HttpURLConnection loadConnection = tryRequest("getMeikById?id=6KihrwCTtOO3vA7yAU3Q9K3j6gu1");
    // Get an OK response (the *connection* worked, the *API* provides an error response)
    assertEquals(200, loadConnection.getResponseCode());
    //Get the expected response: a success
    MeikDataResponse body = adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));

    assertEquals("6KihrwCTtOO3vA7yAU3Q9K3j6gu1", body.meikID());


  }

  //Test bad id parameter
  @Test
  public void testBadRequestParams() throws Exception {

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<MeikDataResponse> adapter = moshi.adapter(MeikDataResponse.class);

    /////////// LOAD DATASOURCE ///////////
    // Set up the request, make the request
    HttpURLConnection loadConnection = tryRequest("getMeikById");
    // Get an OK response (the *connection* worked, the *API* provides an error response)
    assertEquals(200, loadConnection.getResponseCode());
    //Get the expected response: a success
    MeikDataResponse body = adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
    assertEquals("Missing ID", body.data().get("err_bad_request"));


  }

  @Test
  public void badUserId() throws Exception {

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<MeikDataResponse> adapter = moshi.adapter(MeikDataResponse.class);

    /////////// LOAD DATASOURCE ///////////
    // Set up the request, make the request
    HttpURLConnection loadConnection = tryRequest("getMeikById?id=6KihrwCTt");
    // Get an OK response (the *connection* worked, the *API* provides an error response)
    assertEquals(200, loadConnection.getResponseCode());
    //Get the expected response: a success
    MeikDataResponse body = adapter.fromJson(
        new Buffer().readFrom(loadConnection.getInputStream()));

    assertEquals("failure", body.data().get("result"));



  }
}
