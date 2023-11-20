package edu.brown.cs.student.main.server;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Interface to connect the APIOutput to the CacheData . This interface is implemented by both
 * classes so that the guava cache library can utilize APIOutput’s output method in its overridden
 * load method. The two other methods defined in the interface is mainly for testing purposes
 * (getStates, getCounties)
 */
public interface Icache {

  String output(String state) throws IOException, ExecutionException;

  String getStates() throws IOException;

  String getCounties(String state) throws IOException;
}
