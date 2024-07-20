package edu.brown.cs.student;

import edu.brown.cs.student.main.Authorization.FirebaseInitialize;
import edu.brown.cs.student.main.User.FirstYear;
import edu.brown.cs.student.main.User.UserCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.testng.AssertJUnit.assertTrue;

public class CreateUserTest {

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
    public void testCreatUser() throws ExecutionException, InterruptedException {
        List<String> tags = new ArrayList<>();
        tags.add("Dogs");
        tags.add("Lizards");
        List<String> concentration = new ArrayList<>();
        concentration.add("Math");
        concentration.add("APMA");
        concentration.add("English");
        FirstYear firstYear = new FirstYear("Tarp2",concentration,"Mars",tags,"" +
                "crazyTown@mail.org",new HashMap<String,String>());
        UserCreator uc = new UserCreator();
        uc.createFirstYear(firstYear);
    }
}
