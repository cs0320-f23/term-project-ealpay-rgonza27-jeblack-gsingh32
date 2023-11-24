package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.*;

import com.squareup.moshi.JsonReader;
import com.squareup.moshi.Moshi;
import edu.brown.cs.student.main.Maps.MapsJSONResponse;
import edu.brown.cs.student.main.csvSearch.Container;
import edu.brown.cs.student.main.server.ApiUtilities;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import okio.Buffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestApiUtilities {

  private Moshi moshi;

  @BeforeEach
  void setUp() {
    // Create a Moshi instance before each test
    moshi = new Moshi.Builder().build();
  }

  @Test
  void testFromJSON() throws IOException {
    // Define a sample JSON string
    String json = "[[\"Name1\", \"Value1\"], [\"Name2\", \"Value2\"]]";

    // Call the fromJSON method
    ArrayList<List<String>> result = ApiUtilities.fromJson(json);

    // Perform assertions on the result
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Name1", result.get(0).get(0));
    assertEquals("Value1", result.get(0).get(1));
    assertEquals("Name2", result.get(1).get(0));
    assertEquals("Value2", result.get(1).get(1));
  }

  @Test
  void testFromJSONMap() throws IOException {
    // Define a sample JSON string
    String json = "[[\"headerKey\",\"headerVal\"],[\"Key1\", \"Value1\"], [\"Key2\", \"Value2\"]]";

    // Call the fromJSONMap method
    HashMap<String, String> result = ApiUtilities.fromJsonMap(json, true);
    System.out.println(result);
    // Perform assertions on the result
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Value1", result.get("Key1"));
    assertEquals("Value2", result.get("Key2"));

    // Call the fromJSONMap method
    HashMap<String, String> result2 = ApiUtilities.fromJsonMap(json, false);
    System.out.println(result2);
    // Perform assertions on the result
    assertNotNull(result2);
    assertEquals(3, result2.size());
    assertEquals("Value1", result2.get("Key1"));
    assertEquals("Value2", result2.get("Key2"));

    assertThrows(
        NullPointerException.class,
        () -> {
          HashMap<String, String> result3 = ApiUtilities.fromJsonMapCounties(null, null);
        });

    assertThrows(
        IOException.class,
        () -> {
          HashMap<String, String> result3 = ApiUtilities.fromJsonMapCounties("null", null);
        });
  }

  @Test
  void testFromJSONMapCounties() throws IOException {
    // Define a sample JSON string
    String json =
        "[[\"State,County\", \"Value11\", \"Value12\"],[\"San Francisco County, California\", \"Value1\", \"Value2\"]]";

    // Call the fromJSONMapCounties method
    HashMap<String, String> result = ApiUtilities.fromJsonMapCounties(json, true);
    System.out.println(result);
    // Perform assertions on the result
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Value2", result.get("San Francisco County"));

    assertThrows(
        NullPointerException.class,
        () -> {
          HashMap<String, String> result2 = ApiUtilities.fromJsonMap(null, null);
        });

    assertThrows(
        IOException.class,
        () -> {
          HashMap<String, String> result2 = ApiUtilities.fromJsonMap("null", null);
        });
  }

  @Test
  void testToJsonContainer() throws IOException {
    // Create a sample Container
    Container<List<String>> container = new Container<>();
    container.hasHeader = true;
    container.header = List.of("Header1", "Header2");
    container.rows = new ArrayList<>(List.of(List.of("Value1", "Value2")));

    // Call the toJson method
    String result = ApiUtilities.toJson(container);

    // Perform assertions on the result
    assertNotNull(result);
    // Add more specific assertions based on the expected JSON structure
  }

  @Test
  void testToJsonList() throws IOException {
    // Create a sample list of rows
    ArrayList<List<String>> rows = new ArrayList<>();
    rows.add(List.of("Header1", "Value1"));
    rows.add(List.of("Header2", "Value2"));

    // Call the toJson method
    String result = ApiUtilities.toJson(rows);
    // Perform assertions on the result
    assertNotNull(result);
    // Add more specific assertions based on the expected JSON structure
  }

  @Test
  void testPrettyJson() throws IOException {
    // Define a sample JSON string
    String json = "{\"key\": \"value\"}";

    // Call the prettyJson method
    String result = ApiUtilities.prettyJson(json);

    // Perform assertions on the result
    assertNotNull(result);
    // Add more specific assertions based on the expected JSON structure
  }

  @Test
  void testToJsonStatus() throws IOException {
    // Call the toJsonStatus method with success
    String successResult = ApiUtilities.toJsonStatus("Success", false);

    // Perform assertions on the success result
    assertNotNull(successResult);
    // Add more specific assertions based on the expected JSON structure for success

    // Call the toJsonStatus method with failure
    String failureResult = ApiUtilities.toJsonStatus("Failure", true);

    // Perform assertions on the failure result
    assertNotNull(failureResult);
    // Add more specific assertions based on the expected JSON structure for failure
  }

  @Test
  public void testLoadGeoJsonData() throws IOException {
    String json =
        "{\n"
            + "  \"type\": \"Feature\",\n"
            + "  \"geometry\": {\n"
            + "    \"type\": \"Point\",\n"
            + "    \"coordinates\": [125.6, 10.1]\n"
            + "  },\n"
            + "  \"properties\": {\n"
            + "    \"name\": \"Dinagat Islands\"\n"
            + "  }\n"
            + "}";
    JsonReader reader = JsonReader.of(new Buffer().writeUtf8(json));
    MapsJSONResponse geoFeature = ApiUtilities.fromJsonGeneral(reader, MapsJSONResponse.class);

    assertEquals("Feature", geoFeature.type);
    assertEquals("Point", geoFeature.geometry.type);
    assertEquals("[125.6, 10.1]", geoFeature.geometry.coordinates.toString());
    assertEquals("Dinagat Islands", geoFeature.properties.name);
  }

  @Test
  public void testLoadJsonDataNotNeededInfo() throws IOException {
    String json =
        "{\n"
            + "  \"type\": \"Feature\",\n"
            + "  \"geometry\": {\n"
            + "    \"type\": \"Point\",\n"
            + "    \"coordinates\": [125.6, 10.1]\n"
            + "  },\n"
            + "  \"ShouldNotShowUP\": {\n"
            + "    \"class\": \"dot\",\n"
            + "    \"location\": [random, values]\n"
            + "  },\n"
            + "  \"properties\": {\n"
            + "    \"name\": \"Dinagat Islands\"\n"
            + "  }\n"
            + "}";
    JsonReader reader = JsonReader.of(new Buffer().writeUtf8(json));
    MapsJSONResponse geoFeature = ApiUtilities.fromJsonGeneral(reader, MapsJSONResponse.class);

    assertEquals("Feature", geoFeature.type);
    assertEquals("Point", geoFeature.geometry.type);
    assertEquals("[125.6, 10.1]", geoFeature.geometry.coordinates.toString());
    assertEquals("Dinagat Islands", geoFeature.properties.name);
  }

  @Test
  public void testLoadJsonDataMissingInfo() throws IOException {
    String json =
        "{\n"
            + "  \"type\": \"Feature\",\n"
            + "  \"geometry\": {\n"
            + "    \"type\": \"Point\",\n"
            + "    \"coordinates\": [125.6, 10.1]\n"
            + "  },\n"
            + "  \"ShouldNotShowUP\": {\n"
            + "    \"class\": \"dot\",\n"
            + "    \"location\": [random, values]\n"
            + "  }\n"
            + "}";
    JsonReader reader = JsonReader.of(new Buffer().writeUtf8(json));
    MapsJSONResponse geoFeature = ApiUtilities.fromJsonGeneral(reader, MapsJSONResponse.class);

    assertEquals("Feature", geoFeature.type);
    assertEquals("Point", geoFeature.geometry.type);
    assertEquals("[125.6, 10.1]", geoFeature.geometry.coordinates.toString());
    Assertions.assertThrows(
        NullPointerException.class,
        () -> {
          String a = geoFeature.properties.name;
        });
  }
}
