package edu.brown.cs.student.main.User;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public record FirstYear(String name, List<String> concentrations, String location,
                        List<String> tags, String email, Map<String,Double> search)
        implements User {





    @Override
    public void addUserTags(String tag) throws ExecutionException, InterruptedException {

    }

    @Override
    public void removeUserTags(String tag) throws ExecutionException, InterruptedException {

    }


    @Override
    public void updateUserText(String text) throws ExecutionException, InterruptedException {

    }

    @Override
    public void updateUserLocation(String location) throws ExecutionException, InterruptedException {

    }

    @Override
    public void updateUserConcentration(String concentration) throws ExecutionException, InterruptedException {

    }

    @Override
    public void updateUserName(String name) throws ExecutionException, InterruptedException {

    }

    @Override
    public void updateUserEmail(String email) throws ExecutionException, InterruptedException {

    }

    @Override
    public void updateUserYear(String year) throws ExecutionException, InterruptedException {

    }

    @Override
    public void updateUserTags(List<String> tags) throws ExecutionException, InterruptedException {

    }

    @Override
    public void initializeTagsBuckets() throws Exception {
        Map<String,Double> tagRating = new HashMap<>();
        for (String tag: this.tags){
            if(!tagRating.containsKey(tag)){
                tagRating.put(tag,5.0);
            }
        }
    }

    @Override
    public void updateTagBucket() {

    }


    @Override
    public void initializeConcentrationBuckets() throws Exception {
        Map<String,Double> concentrationRating = new HashMap<>();
        for (String tag: this.concentrations){
            if(!concentrationRating.containsKey(tag)){
                concentrationRating.put(tag,5.0);
            }
        }
    }
w
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Map<String,Double> getTagRankings() {
        Map<String,Double> ratings = new HashMap<>();

        List<String> officialTags = new ArrayList<>();
        officialTags.add("Music");
        officialTags.add("Sports");
        officialTags.add("International");
        officialTags.add("On Campus Job");
        officialTags.add("UFLI");
        officialTags.add("Pre-Med");
        officialTags.add("Pre-Law");
        officialTags.add("LGBTIQA2S+");
        officialTags.add("Dance");
        officialTags.add("Arts");
        officialTags.add("Sprint/Utra");
        officialTags.add("Languages");

        for(String tag: this.tags){
            if(!ratings.containsKey(tag)){
                ratings.put(tag,5.0);
            }
        }

        for (String search : this.search.keySet() ){
            if (officialTags.contains(search)){
                    double val = this.search.get(search)*0.75;
                    ratings.put(search,val);
            }
        }

        return ratings;

    }

}
