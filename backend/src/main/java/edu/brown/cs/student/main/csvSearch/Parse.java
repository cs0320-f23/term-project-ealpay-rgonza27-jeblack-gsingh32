package edu.brown.cs.student.main.csvSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Parse is a class equipped to parse a csv file given a CreatorFromRow object, reader, and boolean.
 * It contains 1 main method that does the parsing, and it handles its own exceptions.
 */
public class Parse<T> implements Iterable<T> {

  private final CreatorFromRow<T> creator;
  private final Reader reader;

  private final Boolean header;

  private ArrayList<T> finalList;

  /**
   * Constructor that runs parseCSV with the provided information.
   *
   * @param row Is a class that implements the interface CreatorFromRow. This parameter will provide
   *     the method parseCSV with a way to convert the list of strings from the parse to row objects
   * @param reader Is an object the implements the reader interface. It will be used to read the CSV
   *     file.
   * @param header is a boolean that tells us if there is a header on the CSV File or not
   */
  public Parse(CreatorFromRow<T> row, Reader reader, Boolean header) {
    this.creator = row;
    this.reader = reader;
    this.header = header;
  }

  /**
   * Method that parses the file given, and it converts them into rows with the create method. It
   * uses a regex to split each line from the CSV to strings whenever it encounters a comma.
   * However, if the comma is in between quotation marks, it won't split the string. In the outcome
   * of an I/O error or a FactoryFailure error it will print it. If it is indicated that there is a
   * header it will convert this one into a list of strings, and it will be stored in the Container
   * object alongside the rows and a boolean hasHeader.
   *
   * @return This method returns a Container type Object that withholds information about the parsed
   *     rows, the header (if it has one), and a boolean that tells us if the file had a header.
   */
  public Container<T> parseMethod() {
    ArrayList<T> finalList = new ArrayList<>();
    String[] header = new String[] {};
    Container<T> rowContainer = new Container<>();
    try {
      String line;
      BufferedReader br = new BufferedReader(this.reader);
      if (this.header) {
        header = br.readLine().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
      }
      while ((line = br.readLine()) != null) {
        String[] rowStrings = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        finalList.add(this.creator.create(List.of(rowStrings)));
      }
    } catch (IOException | FactoryFailureException e) {
      System.out.print("ERROR:" + e);
    }
    rowContainer.setContainer(finalList, this.header, List.of(header));
    this.finalList = finalList;
    return rowContainer;
  }

  @NotNull
  @Override
  public Iterator<T> iterator() {
    return this.finalList.iterator();
  }
}
