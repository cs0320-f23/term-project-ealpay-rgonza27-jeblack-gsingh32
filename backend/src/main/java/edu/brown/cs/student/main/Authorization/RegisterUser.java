package edu.brown.cs.student.main.Authorization;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterUser {

  public boolean saverUser(String userEmail, String password)
      throws FirebaseAuthException, IOException {

    if (!this.getEmailProvider(userEmail).equals("brown.edu")) {
      throw new IllegalArgumentException("Please provide your Brown email");
    }
    UserRecord.CreateRequest request =
        new UserRecord.CreateRequest()
            .setEmail(userEmail)
            // .setEmailVerified(false)
            .setPassword(password)
            // .setPhoneNumber("+11234567890")
            // .setDisplayName("John Doe")
            // .setPhotoUrl("http://www.example.com/12345678/photo.png")
            .setDisabled(false);

    UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
    System.out.println("Successfully created new user: " + userRecord.getUid());
    return true;
  }

  /***
   * This method returns the email provider
   * @param email - this is the email entered, example: example@school.edu
   * @return the prover is returned, in our example it would return: "school.edu"
   */
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
