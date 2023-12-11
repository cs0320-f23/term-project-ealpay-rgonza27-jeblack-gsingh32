package edu.brown.cs.student;

import edu.brown.cs.student.main.Authorization.FirebaseInitialize;
import edu.brown.cs.student.main.User.FirstYear;
import edu.brown.cs.student.main.User.Meik;
import edu.brown.cs.student.main.User.User;
import edu.brown.cs.student.main.User.UserInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class GetUserInfoTests {

    @BeforeEach
    public void setup(){
        try {
            FirebaseInitialize initialize = new FirebaseInitialize();
            FirebaseInitialize.initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testUserClass() throws ExecutionException, InterruptedException, IOException {
        UserInformation user = new UserInformation();
        User user1= user.getUser("BDKRZm8WKJP98ocTbh8MtwRhnQZ2","meiks");
        System.out.println(user);
        User user2 = user.getUser("z8jCW6mJLxQaQswrOuQs","FirstYears");

    }
}
