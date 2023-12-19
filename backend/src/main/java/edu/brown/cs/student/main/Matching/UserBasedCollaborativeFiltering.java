package edu.brown.cs.student.main.Matching;

import java.util.HashMap;
import java.util.Map;



public class UserBasedCollaborativeFiltering {
        private Map<String, Map<String, Double>> userItemRatings;



        public UserBasedCollaborativeFiltering() {
            userItemRatings = new HashMap<>();
        }

        // Add user-item ratings
        public void addUserRating(String user, String item, double rating) {
            userItemRatings.computeIfAbsent(user, k -> new HashMap<>()).put(item, rating);
        }

        // Calculate cosine similarity between two users
        private double cosineSimilarity(Map<String, Double> user1, Map<String, Double> user2) {
            double dotProduct = 0.0;
            double norm1 = 0.0;
            double norm2 = 0.0;

            for (String item : user1.keySet()) {
                if (user2.containsKey(item)) {
                    dotProduct += user1.get(item) * user2.get(item);
                }
                norm1 += Math.pow(user1.get(item), 2);
            }

            for (String item : user2.keySet()) {
                norm2 += Math.pow(user2.get(item), 2);
            }

            if (norm1 == 0 || norm2 == 0) {
                return 0.0; // Handle division by zero
            }

            return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
        }

        // Get recommendations for a user
        public Map<String, Double> getRecommendations(String targetUser,Map<String,Map<String,Double>> allRatings) {
            Map<String, Double> recommendations = new HashMap<>();
            System.out.println("ratings"+allRatings);
            System.out.println("target user "+targetUser);


            // Iterate over all users
            for (String user : allRatings.keySet()) {
                if (!user.equals(targetUser)) {
                    double similarity = cosineSimilarity(allRatings.get(targetUser), allRatings.get(user));

                    // Only consider items not rated by the target user
                    for (String item : allRatings.get(user).keySet()) {
                        if (!allRatings.get(targetUser).containsKey(item)) {
                            // Weighted sum of ratings
                            recommendations.merge(item, allRatings.get(user).get(item) * similarity, Double::sum);
                        }
                    }
                }
            }

            return recommendations;

        }
}
