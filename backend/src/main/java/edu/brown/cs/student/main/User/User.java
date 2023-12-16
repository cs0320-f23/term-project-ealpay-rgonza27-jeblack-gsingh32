package edu.brown.cs.student.main.User;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface User {

    void addUserTags(String tag) throws ExecutionException, InterruptedException;
    void removeUserTags(String tag) throws ExecutionException, InterruptedException;
    void updateUserText(String text) throws ExecutionException, InterruptedException;
    void updateUserLocation(String location) throws ExecutionException, InterruptedException;
    void updateUserConcentration(String concentration) throws ExecutionException, InterruptedException;
    void updateUserName(String name) throws ExecutionException, InterruptedException;
    void updateUserEmail(String email) throws ExecutionException, InterruptedException;
    void updateUserYear(String year) throws ExecutionException, InterruptedException;
    void updateUserTags(List<String> tags) throws ExecutionException, InterruptedException;
    void initializeTagsBuckets() throws Exception;
    void updateTagBucket();
    void initializeConcentrationBuckets() throws Exception;
    String getName();
    Map<String, Double> getTagRankings();


    }
