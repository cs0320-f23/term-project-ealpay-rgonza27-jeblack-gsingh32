package edu.brown.cs.student;

import edu.brown.cs.student.main.User.UserCreator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class TestTools {


    @Test
    public void testStringToList(){
        UserCreator userCreator = new UserCreator();
        List<String> actual = userCreator.convertStringToList("hi,I,am");
        List<String> expected = new ArrayList<>();
        expected.add("hi");
        expected.add("I");
        expected.add("am");
        assertEquals(actual,expected);
    }
}
