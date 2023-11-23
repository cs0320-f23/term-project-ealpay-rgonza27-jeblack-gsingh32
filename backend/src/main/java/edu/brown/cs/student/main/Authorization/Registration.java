package edu.brown.cs.student.main.Authorization;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;

public class Registration {

    private FirebaseApp registration = FirebaseApp.initializeApp();

    public class FirebaseInitializer {
        public static void initialize() throws IOException {
            FileInputStream serviceAccount = new FileInputStream("***");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://****.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
        }
    }

    public void registerUser(){



    }

}
