package edu.brown.cs.student;

import edu.brown.cs.student.main.Authorization.FirebaseInitialize;
import edu.brown.cs.student.main.User.Meik;
import edu.brown.cs.student.main.User.UserInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UpdataUsersTest {

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
    public void updateMeikTest() throws ExecutionException, InterruptedException {
        List<String> tags = new ArrayList<>();
        tags.add("Computers");
        tags.add("swagger");
        tags.add("jagger");

//        Meik testMeik = new Meik("gurpartap","gurpartap_singh@brown.edu","Queens, NY",
//                "24","instagram haha",tags,"6KihrwCTtOO3vA7yAU3Q9K3j6gu1");
//        testMeik.updateUserName("Gurpoptart");
    }

}
