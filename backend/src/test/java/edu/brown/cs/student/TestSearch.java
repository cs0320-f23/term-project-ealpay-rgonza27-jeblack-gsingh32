package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.brown.cs.student.main.csvSearch.Container;
import edu.brown.cs.student.main.csvSearch.Creator;
import edu.brown.cs.student.main.csvSearch.Parse;
import edu.brown.cs.student.main.csvSearch.Search;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

public class TestSearch {

  public Parse<List<String>> parse;
  public Parse<List<String>> parse2;

  public String answer1 = "[ri, white, \" $1,058.47 \", 395773.6521,  $1.00 , 75%]";
  public String answer2 =
      "[state, data type, average weekly earnings, number of workers, earnings disparity, employed percent]";

  public String answerL1 = "[1172, solomon, 80.469, 5.15235, 17.48726]";
  public String answerL2 = "[starid, propername, x, y, z]";

  @Test
  public void SearchTests()
      throws FileNotFoundException, NoSuchFieldException, IllegalAccessException {
    Search search = new Search();
    Creator row = new Creator();
    this.parse =
        new Parse<>(row, new FileReader("data/census/dol_ri_earnings_disparity.csv"), true);
    Container<List<String>> rows = this.parse.parseMethod();
    this.parse2 =
        new Parse<>(row, new FileReader("data/census/dol_ri_earnings_disparity.csv"), false);
    Container<List<String>> rows2 = this.parse2.parseMethod();

    // Test with a column value with and without header
    Assert.assertEquals(
        search.searchItems(rows, "white", "Data Type").get(0).toString(), this.answer1);

    // Test with no column value with and without header
    Assert.assertEquals(search.searchItems(rows, "white", "").get(0).toString(), this.answer1);

    Assert.assertEquals(search.searchItems(rows2, "state", "").get(0).toString(), this.answer2);

    // Test with a column value with and without header and with Caps on the input
    Assert.assertEquals(
        search.searchItems(rows, "WhIte", "Data Type").get(0).toString(), this.answer1);

    // Test with no column value with and without header and with Caps on the input
    Assert.assertEquals(search.searchItems(rows, "whiTe", "").get(0).toString(), this.answer1);

    Assert.assertEquals(search.searchItems(rows2, "StaTe", "").get(0).toString(), this.answer2);

    // Test with a column value with Caps on the column
    Assert.assertEquals(
        search.searchItems(rows, "white", "daTa Type").get(0).toString(), this.answer1);

    // Tests with a number column value with and without header
    Assert.assertEquals(search.searchItems(rows, "white", "1").get(0).toString(), this.answer1);

    Assert.assertEquals(search.searchItems(rows2, "white", "1").get(0).toString(), this.answer1);

    for (List<String> a : this.parse) {
      System.out.println(a.get(1));
    }
  }

  @Test
  public void SearchTestsLarge()
      throws FileNotFoundException, NoSuchFieldException, IllegalAccessException {
    Search search = new Search();
    Creator row = new Creator();
    this.parse = new Parse<>(row, new FileReader("data/stars/stardata.csv"), true);
    Container<List<String>> rows = this.parse.parseMethod();
    this.parse2 = new Parse<>(row, new FileReader("data/stars/stardata.csv"), false);
    Container<List<String>> rows2 = this.parse2.parseMethod();

    // Test with a column value with and without header
    Assert.assertEquals(
        search.searchItems(rows, "solomon", "ProperName").get(0).toString(), this.answerL1);

    // Test with no column value with and without header
    Assert.assertEquals(search.searchItems(rows, "solomon", "").get(0).toString(), this.answerL1);

    Assert.assertEquals(search.searchItems(rows2, "StarID", "").get(0).toString(), this.answerL2);

    // Test with a column value with and without header and with Caps on the input
    Assert.assertEquals(
        search.searchItems(rows, "solOMon", "ProperName").get(0).toString(), this.answerL1);

    // Test with no column value with and without header and with Caps on the input
    Assert.assertEquals(search.searchItems(rows, "soLOmon", "").get(0).toString(), this.answerL1);

    Assert.assertEquals(search.searchItems(rows2, "StaRid", "").get(0).toString(), this.answerL2);

    // Test with a column value with Caps on the column
    Assert.assertEquals(
        search.searchItems(rows, "solomon", "PRoPerName").get(0).toString(), this.answerL1);

    // Tests with a number column value with and without header
    Assert.assertEquals(search.searchItems(rows, "solomon", "1").get(0).toString(), this.answerL1);

    Assert.assertEquals(search.searchItems(rows2, "solomon", "1").get(0).toString(), this.answerL1);
  }

  @Test
  public void testSearchErrors() throws FileNotFoundException {
    Search search = new Search();
    Creator row = new Creator();
    this.parse =
        new Parse<>(row, new FileReader("data/census/dol_ri_earnings_disparity.csv"), true);
    this.parse2 =
        new Parse<>(row, new FileReader("data/census/dol_ri_earnings_disparity.csv"), false);
    Container<List<String>> row1 = this.parse.parseMethod();
    Container<List<String>> row2 = this.parse2.parseMethod();

    // Entering a search for row 0 when the header is eliminated
    assertThrows(
        RuntimeException.class,
        () -> {
          search.searchItems(row1, "state", "state").get(0).toString();
        });
    // Entering a Column that doesn't exist with no header
    assertThrows(
        NoSuchFieldException.class,
        () -> {
          search.searchItems(row1, "state", "jhf").get(0).toString();
        });
    // Entering a Column that doesn't exist with a header
    assertThrows(
        NoSuchFieldException.class,
        () -> {
          search.searchItems(row2, "state", "jhf").get(0).toString();
        });
    // Entering a Column that doesn't exist with a header with negative entry
    assertThrows(
        NoSuchFieldException.class,
        () -> {
          search.searchItems(row2, "state", "-1").get(0).toString();
        });
    // Entering a Column that doesn't exist with a header with entry bigger than list length
    assertThrows(
        IndexOutOfBoundsException.class,
        () -> {
          search.searchItems(row2, "state", "33").get(0).toString();
        });
  }
}
