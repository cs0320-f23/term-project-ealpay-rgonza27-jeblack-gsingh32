package edu.brown.cs.student;

import edu.brown.cs.student.main.Matching.UserBasedCollaborativeFiltering;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class TestAlgorithm {


    @Test
    public void testAlgo(){
        UserBasedCollaborativeFiltering ubc = new UserBasedCollaborativeFiltering();
        ubc.addUserRating("User1", "Item1", 4.0);
        ubc.addUserRating("User1", "Item2", 5.0);
        ubc.addUserRating("User1", "Item3", 4.0);
        ubc.addUserRating("User2", "Item1", 3.0);
        ubc.addUserRating("User2", "Item3", 4.0);
        ubc.addUserRating("User1", "Item5", 4.0);


        Map<String, Double> recommendations = ubc.getRecommendations("User2");
        System.out.println(recommendations);



    }
}
