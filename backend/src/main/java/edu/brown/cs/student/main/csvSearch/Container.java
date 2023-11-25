package edu.brown.cs.student.main.csvSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract class made to hold information regarding the parsed list of row objects, the header (if
 * it has one) and a boolean to let other methods know whether the header field has a value or not.
 */
public class Container<T> {

  public ArrayList<T> rows = new ArrayList<>();
  public Boolean hasHeader = false;
  public List<String> header = new ArrayList<>();

  /**
   * Method to populate the three fields of the method.
   *
   * @param rows is an Arraylist of objects type T. This is the result of the parsed CSV file
   * @param hasHeader is a boolean that tells us if the header field is empty or not
   * @param header is a list of strings containing the names of the columns of the CSV file
   */
  public void setContainer(ArrayList<T> rows, Boolean hasHeader, List<String> header) {
    this.rows = rows;
    this.hasHeader = hasHeader;
    this.header = header.stream().map(String::toLowerCase).collect(Collectors.toList());
    ;
  }
}
