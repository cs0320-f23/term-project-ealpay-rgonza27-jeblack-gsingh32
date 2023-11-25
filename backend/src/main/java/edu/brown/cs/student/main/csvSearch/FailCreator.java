package edu.brown.cs.student.main.csvSearch;

import java.util.HashMap;
import java.util.List;

/** Abstract class that implements the CreatorFromRow interface and overrides the create method. */
public class FailCreator implements CreatorFromRow<HashMap<Integer, String>> {

  /**
   * Override of create method from CreatorFromRow Interface. In this case it trys to parse all
   * elements to a hashMap but fails. This class is used to test exceptions
   *
   * @param row Is a list of strings given by a row from the CSV file.
   * @return This method returns a HashMap(Integer,String) with the keys being the column names and
   *     the values being the corresponding string.
   * @throws FactoryFailureException when there is a problem creating the rows
   */
  @Override
  public HashMap<Integer, String> create(List<String> row) throws FactoryFailureException {
    HashMap<Integer, String> finalHashMap = new HashMap<>();
    try {
      for (int i = -1; i < (row.size()); i++) {
        finalHashMap.put(i, row.get(i));
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new FactoryFailureException(e.getMessage(), row);
    }
    return finalHashMap;
  }
}
