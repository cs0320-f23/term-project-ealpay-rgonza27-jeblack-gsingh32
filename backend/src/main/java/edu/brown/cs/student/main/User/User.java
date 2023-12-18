package edu.brown.cs.student.main.User;

import com.google.cloud.firestore.DocumentReference;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface User {


    void updateUserText(String text,String uid,String collection) throws ExecutionException, InterruptedException;
    void updateUserLocation(String location,String uid,String collection) throws ExecutionException, InterruptedException;
    void updateUserConcentration(String concentration,String uid,String collection) throws ExecutionException, InterruptedException;
    void updateUserName(String name,String uid,String collection) throws ExecutionException, InterruptedException;
    void updateUserEmail(String email,String uid,String collection) throws ExecutionException, InterruptedException;
    void updateUserYear(String year,String uid,String collection) throws ExecutionException, InterruptedException;
    void updateUserTags(List<String> tags,String uid,String collection) throws ExecutionException, InterruptedException;
    public DocumentReference doc(String uid,String collection);



    }
