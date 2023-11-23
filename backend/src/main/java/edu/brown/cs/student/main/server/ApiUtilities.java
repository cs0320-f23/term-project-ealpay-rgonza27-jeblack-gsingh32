package edu.brown.cs.student.main.server;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs.student.main.csvSearch.Container;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okio.Buffer;

/**
 * Class for serialization and deserialization.
 *
 * @param <T> for general json method
 */
public class ApiUtilities<T> {

  /**
   * Our APIUtilities class handles the main logic for serialization and deserialization for our
   * overarching program. Constructor doesn’t have functionality itself. (i.e is empty)
   */
  public ApiUtilities() {}

  /**
   * This is our main method for deserialization. fromJson takes a json and forms it into an
   * arrayList<>. This is used in the BroadBand handler to deserialize the output we get from
   * APIOutput.
   *
   * @param json JSON String
   * @return -> an arraylist with the content provided via the JSON
   * @throws IOException throws an error if the info can't be deserialized
   */
  public static ArrayList<List<String>> fromJson(String json) throws IOException {
    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<List<List<String>>> adapter =
        moshi.adapter(Types.newParameterizedType(List.class, List.class, String.class));

    try {
      // Deserialize the JSON string into a List<List<String>>
      List<List<String>> result = adapter.fromJson(json);
      if (result == null) {
        throw new IOException("Result is Empty");
      }
      // Create an ArrayList from the result
      return new ArrayList<>(result);
    } catch (IOException e) {
      System.err.println("Error parsing JSON: " + e.getMessage());
      throw e;
    }
  }

  /**
   * This is a secondary method to handle the logic behind deserialization. Functions similarly to
   * fromJson but returns a hash map instead of arrayList (response map) This is used to create the
   * hash map between the States and their respective state codes.
   *
   * @param json JSON String
   * @param hasHeader boolean to see if there is header
   * @return a hashmap with the content provided via the JSON
   * @throws IOException throws an error if the info can't be deserialized
   */
  public static HashMap<String, String> fromJsonMap(String json, Boolean hasHeader)
      throws IOException {
    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<List<List<String>>> adapter =
        moshi.adapter(Types.newParameterizedType(List.class, List.class, String.class));

    try {
      List<List<String>> result = adapter.fromJson(json);
      HashMap<String, String> responseMap = new HashMap<>();
      if (result == null) {
        throw new IOException("Result is Empty");
      }
      if (hasHeader) {
        result.remove(0);
      }
      for (List<String> element : result) {
        responseMap.put(element.get(0), element.get(1));
      }
      return responseMap;
    } catch (IOException e) {
      System.err.println("Error parsing JSON: " + e.getMessage());
      throw e;
    }
  }

  /**
   * This is our third and final method for deserialization, it has very similar logic to
   * fromJSONMap but is used to create a hash map between counties and their respective county codes
   * instead.
   *
   * @param json JSON String
   * @param hasHeader boolean telling if there was a header
   * @return a hashmap with the content provided via the JSON
   * @throws IOException throws an error if the info can't be deserialized
   */
  public static HashMap<String, String> fromJsonMapCounties(String json, Boolean hasHeader)
      throws IOException {
    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<List<List<String>>> adapter =
        moshi.adapter(Types.newParameterizedType(List.class, List.class, String.class));
    try {
      List<List<String>> result = adapter.fromJson(json);
      if (result == null) {
        throw new IOException("Result is Empty");
      }
      HashMap<String, String> responseMap = new HashMap<>();
      if (hasHeader) {
        result.remove(0);
      }
      for (List<String> element : result) {
        responseMap.put(element.get(0).split(",")[0], element.get(2));
        responseMap.put(element.get(0), element.get(2));
      }
      return responseMap;
    } catch (IOException e) {
      System.err.println("Error parsing JSON: " + e.getMessage());
      throw e;
    }
  }

  /**
   * This is our main method for serialization. It is used to serialize a container objects data and
   * display it on our server page. Utilized for viewCSV and searchCSV endpoints
   *
   * @param container container object from parse to be serialized
   * @return A JSON String containing the data provided
   * @throws IOException throws an error if the info can't be deserialized
   */
  public static String toJson(Container<List<String>> container) throws IOException {
    Moshi moshi = new Moshi.Builder().build();
    Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);
    Map<String, Object> responseMap = new HashMap<>();
    JsonAdapter<Boolean> hasHeaderData = moshi.adapter(Boolean.class);
    responseMap.put("return Type", "success");
    responseMap.put("hasHeader", hasHeaderData.toJson(container.hasHeader));

    // Process the header to remove double quotes
    List<String> processedHeader = new ArrayList<>();
    for (String headerItem : container.header) {
      processedHeader.add(headerItem.replace("\"", ""));
    }

    responseMap.put("header", processedHeader);
    responseMap.put("Time", LocalDateTime.now().toString());

    // Process the rows to remove double quotes
    List<List<String>> processedRows = new ArrayList<>();
    for (List<String> row : container.rows) {
      List<String> processedRow = new ArrayList<>();
      for (String rowItem : row) {
        processedRow.add(rowItem.replace("\"", ""));
      }
      processedRows.add(processedRow);
    }

    responseMap.put("data", processedRows);

    return prettyJson(adapter.toJson(responseMap));
  }

  /**
   * This is a secondary method for serialization that uses method overload to do serialization on a
   * List of List of Strings as opposed to a container object. Utilized for broadBand handling.
   *
   * @param rows List of List of Strings to be serialized
   * @return A JSON String containing the data provided
   * @throws IOException throws an error if the info can't be serialized
   */
  public static String toJson(ArrayList<List<String>> rows) throws IOException {
    Moshi moshi = new Moshi.Builder().build();
    Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("return Type", "success");
    responseMap.put("Time", LocalDateTime.now().toString());
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);
    if (rows.size() == 2) {
      List<String> header = rows.remove(0);
      List<String> info = rows.remove(0);
      for (String column : header) {
        responseMap.put(column, info.get(header.indexOf(column)));
      }
    } else {
      responseMap.put("data", rows);
    }
    return prettyJson(adapter.toJson(responseMap));
  }

  /**
   * This is a method to handle the actual display of our JSON’s within our server. We have made it
   * so that the JSON provided to the user is readable and looks nice.
   *
   * @param json JSON String
   * @return formatted JSON String
   * @throws IOException throws an error if the info can't be serialized
   */
  public static String prettyJson(String json) throws IOException {
    Buffer source = new Buffer().writeUtf8(json);
    JsonReader reader = JsonReader.of(source);
    Object value = reader.readJsonValue();
    JsonAdapter<Object> adapter = new Moshi.Builder().build().adapter(Object.class).indent("    ");
    return adapter.toJson(value);
  }

  /**
   * This is a method to handle certain responses that only return failure or success responses
   * without any actual data. This is mainly utilized in our defensive programing where we catch the
   * errors that may be thrown and use toJSONStatus to display that to out users in a sensible way.
   *
   * @param reason reason for failure
   * @param failed boolean to see if failed
   * @return JSON String with reason for failure or success
   * @throws IOException throws an error if the info can't be serialized
   */
  public static String toJsonStatus(String reason, Boolean failed) throws IOException {
    Moshi moshi = new Moshi.Builder().build();
    Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);
    Map<String, Object> responseMap = new HashMap<>();
    if (failed) {
      responseMap.put("return Type", "Failure");
      responseMap.put("reason", reason);
    } else {
      responseMap.put("return Type", "Success");
    }
    return prettyJson(adapter.toJson(responseMap));
  }

  /**
   * This is a general JSON serializer that could theoretically be used to change any given type to
   * JSON String. (This is for other developers' use)
   *
   * @param object generic object to serialize
   * @return JSON String for given object(data)
   * @throws IllegalAccessException thrown if the serialization goes wrong
   */
  public String toJsonGeneral(T object) throws IllegalAccessException {
    Field[] fields = object.getClass().getDeclaredFields();

    Moshi moshi = new Moshi.Builder().build();
    Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);

    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("return Type", "success");
    for (Field field : fields) {
      responseMap.put(field.getName(), field.get(object));
    }
    return adapter.toJson(responseMap);
  }

  public static <T> T fromJsonGeneral(JsonReader source, Class<T> targetType) throws IOException {
    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<T> adapter = moshi.adapter(targetType);
    source.setLenient(true);

    return adapter.fromJson(source);
  }
}
