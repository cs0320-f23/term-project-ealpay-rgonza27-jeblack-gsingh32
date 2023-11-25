package edu.brown.cs.student.main.csvSearch;

import java.util.List;
import java.util.stream.Collectors;

/** Abstract class that implements the CreatorFromRow interface and overrides the create method. */
public class Creator implements CreatorFromRow<List<String>> {

  /**
   * Override of create method from CreatorFromRow Interface. This instance of the method turns a
   * list of strings into a lowercase list of strings.
   *
   * @param row Is a list of strings given by a row from the CSV file.
   * @return This method returns a List of strings with all elements in lowercase.
   */
  @Override
  public List<String> create(List<String> row) {
    return row.stream().map(String::toLowerCase).collect(Collectors.toList());
  }
}
