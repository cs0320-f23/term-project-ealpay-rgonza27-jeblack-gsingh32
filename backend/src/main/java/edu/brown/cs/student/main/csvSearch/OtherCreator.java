package edu.brown.cs.student.main.csvSearch;

import java.util.HashMap;
import java.util.List;

/** Abstract class that implements the CreatorFromRow interface and overrides the create method. */
public class OtherCreator implements CreatorFromRow<HashMap<Integer, String>> {

  /**
   * Override of create method from CreatorFromRow Interface. In this case it parses all elements to
   * a hashMap.
   *
   * @param row Is a list of strings given by a row from the CSV file.
   * @return This method returns a HashMap(Integer,String) with the keys being the column names and
   *     the values being the corresponding string.
   */
  @Override
  public HashMap<Integer, String> create(List<String> row) {
    HashMap<Integer, String> finalHashMap = new HashMap<>();
    for (int i = 0; i < row.size(); i++) {
      finalHashMap.put(i, row.get(i));
    }

    return finalHashMap;
  }
}
