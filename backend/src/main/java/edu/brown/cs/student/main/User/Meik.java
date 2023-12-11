package edu.brown.cs.student.main.User;

import java.util.List;

public record Meik(String name, String email, String location, String year, String text,
                   List<String> tags) implements User{


    }

