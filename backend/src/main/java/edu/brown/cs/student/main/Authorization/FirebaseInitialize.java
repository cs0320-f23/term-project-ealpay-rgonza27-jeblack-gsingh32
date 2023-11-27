package edu.brown.cs.student.main.Authorization;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseInitialize {

  public static void initialize() throws IOException {
    try {
      FileInputStream serviceAccount =
          new FileInputStream(
              "src/main/java/edu/brown/cs/student/main/Authorization/meikdatabase-firebase-adminsdk-5r9bn-600aa7ae4f.json");
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
