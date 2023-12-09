package edu.brown.cs.student;

import edu.brown.cs.student.main.Authorization.FirebaseInitialize;
import edu.brown.cs.student.main.User.UserInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class GetUserInfoTests {


    @Test
    public void testUserClass() throws ExecutionException, InterruptedException, IOException {
        UserInformation user = new UserInformation();
        user.getUser("a","a");

    }
}
