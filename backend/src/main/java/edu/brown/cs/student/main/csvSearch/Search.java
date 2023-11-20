package edu.brown.cs.student.main.csvSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Search is a class that equipped with the tools to search a string in an array of list of strings.
 * The search can be done through columns or in general depending on the input given to the main
 * search method.
 */
public class Search {

  /**
   * Method that searches items with a given Container object, an input and a column. If the column
   * is empty then it looks through all the columns in the row. If it is given a number as a column
   * it will search for that column index starting with 0. If a word is not found in the whole file
   * a print statement will be given saying that the word wasn't found. If the input is empty then
   * it will return the whole list regardless of what column is inputted.
   *
   * @param container is a list of classes that conform to the CreatorFromRow interface. They are
   *     the data used for the method
   * @param input refers to the key word that will be searched
   * @param column is an optional value that helps narrow down the search
   * @throws NoSuchFieldException when there is an issue retrieving a column with the getColumnIndex
   *     method
   * @throws IllegalArgumentException if any of the inputted elements doesn't correspond to their
   *     respective types
   * @throws IndexOutOfBoundsException when an int column greater than the columns available is
   *     given
   */
  public ArrayList<List<String>> searchItems(
      Container<List<String>> container, String input, String column)
      throws NoSuchFieldException, IllegalAccessException, IndexOutOfBoundsException {
    int intRow;
    ArrayList<List<String>> filteredRows = new ArrayList<>();
    if (column.matches("\\d+")) {
      intRow = Integer.parseInt(column);
    } else {
      intRow = getColumnIndex(container, column);
    }

    for (List<String> row : container.rows) {
      if (!column.isEmpty() && (row.get(intRow)).contains(input.toLowerCase())) {
        System.out.println(row);
        filteredRows.add(row);
      }
      for (String str : row) {
        if (column.isEmpty() && str.contains(input.toLowerCase())) {
          System.out.println(row);
          filteredRows.add(row);
          break;
        }
      }
    }
    if (filteredRows.isEmpty()) {
      throw new RuntimeException("Input not in the rows provided");
    }
    return filteredRows;
  }

  /**
   * Method that searches items with a given Container, and a column. If the column was not found it
   * throws a NoSuchFieldError, this also happens if you ask for a column when the Container's
   * hasHeader field is false. If the Container object specifies * that there was no header and a
   * non int column is inputted then it will
   *
   * @param rows is a container object that holds a list of objects type List of Strings, a
   *     hasHeader boolean, and a header field.
   * @param column is an optional value that helps narrow down the search.
   * @throws NoSuchFieldException if a column is not found or if a column is given when there is no
   *     header
   */
  public int getColumnIndex(Container<List<String>> rows, String column)
      throws NoSuchFieldException {
    int intRow;
    try {
      intRow = rows.header.indexOf(column.toLowerCase());
      if (!column.isEmpty() && intRow == -1) {
        if (!rows.hasHeader) {
          throw new NoSuchElementException();
        }
        throw new IndexOutOfBoundsException();
      }
    } catch (IndexOutOfBoundsException e) {
      throw new NoSuchFieldException("Column not Found");
    } catch (NoSuchElementException e) {
      throw new NoSuchFieldException("Found Column name when given no header");
    }
    return intRow;
  }
}
