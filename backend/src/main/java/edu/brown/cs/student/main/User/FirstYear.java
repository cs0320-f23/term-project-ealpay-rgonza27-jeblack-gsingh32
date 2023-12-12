package edu.brown.cs.student.main.User;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public record FirstYear(String name, String concentrations, String location,
                        List<String> tags, String email, Map<String,String> search)
        implements User {



    @Override
    public boolean isMeik() {
        return false;
    }

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
}
