package edu.brown.cs.student;

import edu.brown.cs.student.main.Authorization.FirebaseInitialize;
import edu.brown.cs.student.main.Matching.Matching;
import edu.brown.cs.student.main.Matching.Ranking;
import edu.brown.cs.student.main.Matching.UserBasedCollaborativeFiltering;
import edu.brown.cs.student.main.User.User;
import edu.brown.cs.student.main.User.UserInformation;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.testng.AssertJUnit.assertEquals;

public class TestAlgorithm {


    @Test
    public void testAlgo(){
        UserBasedCollaborativeFiltering ubc = new UserBasedCollaborativeFiltering();
        ubc.addUserRating("User1", "Item1", 4.0);
        ubc.addUserRating("User1", "Item2", 5.0);
        ubc.addUserRating("User1", "Item3", 4.0);

        Map<String,Double> user1 = new HashMap<>();
        user1.put("Item1",4.0);
        user1.put("Item2",5.0);
        user1.put("Item3",4.0);

        ubc.addUserRating("User2", "Item1", 3.0);
        ubc.addUserRating("User2", "Item3", 4.0);
        ubc.addUserRating("User1", "Item5", 4.0);
        Map<String,Double> user2 = new HashMap<>();
        user2.put("Item3",4.0);
        user2.put("Item1",3.0);
        user2.put( "Item5", 4.0);
        Map<String,Map<String,Double>> ratings = new HashMap<>();
        ratings.put("User1",user1);
        ratings.put("User2",user2);



        Map<String, Double> recommendations = ubc.getRecommendations("User2",ratings);
        System.out.println(recommendations);
        assertEquals(recommendations.get("Item2"),2.896000813244424);

    }

    @Test
    public void testAlgoWithFirstYears() throws IOException, ExecutionException, InterruptedException {

        try {
            FirebaseInitialize initialize = new FirebaseInitialize();
            FirebaseInitialize.initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Ranking ranking = new Ranking();
        UserInformation userInfo = new UserInformation();
        System.out.println(ranking.rankOnSearch("H6Tkobj4jLpoU6IE1E6S"));


    }
    @Test
    public void testGetRanking() throws IOException, ExecutionException, InterruptedException {

        try {
            FirebaseInitialize initialize = new FirebaseInitialize();
            FirebaseInitialize.initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        UserInformation userInfo = new UserInformation();
        User user = userInfo.getUserFromId("H6Tkobj4jLpoU6IE1E6S","FirstYears");
        System.out.println(user.getTagRankings("H6Tkobj4jLpoU6IE1E6S"));

    }

    @Test
    public void testMatching() throws IOException, ExecutionException, InterruptedException {
        try {
            FirebaseInitialize initialize = new FirebaseInitialize();
            FirebaseInitialize.initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Matching matching = new Matching();
        //System.out.println(matching.getMatchingMeiksByTag("H6Tkobj4jLpoU6IE1E6S"));
        //System.out.println(matching.getMatchingMeiksConcentration("H6Tkobj4jLpoU6IE1E6S"));

    }
}
