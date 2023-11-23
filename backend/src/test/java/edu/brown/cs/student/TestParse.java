package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.brown.cs.student.main.csvSearch.FailCreator;
import edu.brown.cs.student.main.csvSearch.OtherCreator;
import edu.brown.cs.student.main.csvSearch.Parse;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

public class TestParse {

  public Parse<HashMap<Integer, String>> parse;
  public HashMap<Integer, String> answer1 =
      new HashMap<>() {
        {
          put(0, "RI");
          put(1, "White");
          put(2, "\" $1,058.47 \"");
          put(3, "395773.6521");
          put(4, " $1.00 ");
          put(5, "75%");
        }
      };
  public OtherCreator creator = new OtherCreator();
  public FailCreator failCreator = new FailCreator();

  @Test
  public void testParse() throws FileNotFoundException {
    this.parse =
        new Parse<>(
            this.creator, new FileReader("data/census/dol_ri_earnings_disparity.csv"), false);
    ArrayList<HashMap<Integer, String>> rows = this.parse.parseMethod().rows;
    // Test Parse with another Creator Class.
    Assert.assertEquals(rows.get(1), answer1);
    // Test that the length for all rows is the same with a different Creator method.
    Assert.assertEquals(rows.get(3).size(), answer1.size());
  }

  @Test
  public void testExceptions() throws FileNotFoundException {
    // Test file name that doesn't exist
    assertThrows(
        FileNotFoundException.class,
        () -> {
          this.parse = new Parse<>(this.creator, new FileReader("a"), true);
          this.parse.parseMethod();
        });
    // Test failedCreator
    this.parse =
        new Parse<>(
            this.failCreator, new FileReader("data/census/dol_ri_earnings_disparity.csv"), true);
    this.parse.parseMethod();
  }
}
