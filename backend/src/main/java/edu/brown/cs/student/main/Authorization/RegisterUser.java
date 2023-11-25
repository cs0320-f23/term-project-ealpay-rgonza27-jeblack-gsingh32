package edu.brown.cs.student.main.Authorization;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import java.io.IOException;

public class RegisterUser {


    public String saverUser(String userEmail,String password) throws FirebaseAuthException, IOException {

        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(userEmail)
                //.setEmailVerified(false)
                .setPassword(password)
                //.setPhoneNumber("+11234567890")
                //.setDisplayName("John Doe")
                //.setPhotoUrl("http://www.example.com/12345678/photo.png")
                .setDisabled(false);

        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
        System.out.println("Successfully created new user: " + userRecord.getUid());
        return userRecord.toString();
    }


}
