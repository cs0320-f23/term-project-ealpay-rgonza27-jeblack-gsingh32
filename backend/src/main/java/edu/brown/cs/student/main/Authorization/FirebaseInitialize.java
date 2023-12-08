package edu.brown.cs.student.main.Authorization;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/***
 * Creates our firebase app.
 */
public class FirebaseInitialize {
  /***
   * This method is used to initialize our firebase app.
   * @throws IOException If file with service account information is not accessible.
   */
  public static void initialize() throws IOException {
    try {
      // Load the service account JSON file as a FileInputStream
      FileInputStream serviceAccount =
              new FileInputStream(
                      "src/main/resources/meikdatabase-firebase-adminsdk-5r9bn-be1c95c791.json");


      FirebaseOptions options =
              new FirebaseOptions.Builder()
                      .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                      .build();

      FirebaseApp.initializeApp(options);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

