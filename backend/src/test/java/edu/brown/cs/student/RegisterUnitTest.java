package edu.brown.cs.student;

import edu.brown.cs.student.main.Authorization.FirebaseInitialize;
import edu.brown.cs.student.main.Authorization.RegisterUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.testng.AssertJUnit.assertEquals;

public class RegisterUnitTest {

    @BeforeEach
    public void intitializeFirebase(){
        try {
            FirebaseInitialize initialize = new FirebaseInitialize();
            FirebaseInitialize.initialize();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetProvider(){
        RegisterUser registerUser = new RegisterUser();
        assertEquals("brown.edu",registerUser.getEmailProvider("hi@brown.edu"));
        assertEquals("brown.edu",registerUser.getEmailProvider("gurp_par_tapa214@brown.edu"));
        assertEquals("gmail.com",registerUser.getEmailProvider("gurp@gmail.com"));

    }

    @Test
    public void testRegistrationWithoutBrownEmail(){
        RegisterUser registerUser = new RegisterUser();
        try{
            registerUser.saverUser("gurpartap@gmail.com","password");
        }
        catch (Exception e){
            assertEquals("Please provide your Brown email",e.getMessage());
        }
    }

    @Test
    public void testRegistrationWithBrownEmail(){
        RegisterUser registerUser = new RegisterUser();
        try{
            boolean result = registerUser.saverUser("gurpartap@brown.edu","password");
            assert(result);
        }
        catch (Exception e){
            assertEquals("The user with the provided email already exists (EMAIL_EXISTS).",e.getMessage());
        }
    }
}
