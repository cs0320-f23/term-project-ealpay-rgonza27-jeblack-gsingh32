package edu.brown.cs.student.main.User;


import java.util.List;
import java.util.Map;

public record FirstYear(String name, String concentrations, String location,
                        List<String> tags, String email, Map<String,Integer> search)
        implements User {


    public String getName(){
        return this.name;
    }


}
