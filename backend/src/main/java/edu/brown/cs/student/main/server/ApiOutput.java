package edu.brown.cs.student.main.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

/**
 * Our APIOutput is the actual class that makes the calls to the US census API. Utilizes 4 methods *
 * to accomplish this.
 */
public class ApiOutput implements Icache {

  /**
   * The constructor itself is empty and doesn’t have functionality outside of allowing users to
   * call the methods below.
   */
  public ApiOutput() {}

  /**
   * This is the method that handles the main logic of the calls to the API, it utilizes all other
   * functions defined in the class as helpers to make calls to the US census API given necessary
   * location information.
   *
   * @param locationInfo -> this is a string that contains both the state and the county that the
   *     user is looking for in the API, input should separate the state and the county with a
   *     comma.
   * @return -> The exact data retrieved from the API
   * @throws IOException throws an error if the info can't be fetched correctly
   */
  @Override
  public String output(String locationInfo) throws IOException {
    String[] strList = locationInfo.split(",");
    String state = strList[0];
    String county = strList[1];
    if (state.matches("\\d+") && county.matches("\\d+")) {
      URL url =
          new URL(
              "https://api.census.gov/data/2021/acs/acs1/subject/variables?get=NAME,S2802_C03_022E&for=county:"
                  + county
                  + "&in=state:"
                  + state);
      return setUpCall(url);
    } else {
      String stateNum = ApiUtilities.fromJsonMap(getStates(), true).get(state);
      if (stateNum == null) {
        throw new NullPointerException(
            "error_bad_request. state entered not Found: Please enter a valid state");
      }

      String countyNum = ApiUtilities.fromJsonMapCounties(getCounties(stateNum), true).get(county);
      if (countyNum == null) {
        throw new NullPointerException(
            "error_bad_request. county entered not Found: Please enter a valid county for "
                + state);
      }
      URL url =
          new URL(
              "https://api.census.gov/data/2021/acs/acs1/subject/variables?get=NAME,S2802_C03_022E&for=county:"
                  + countyNum
                  + "&in=state:"
                  + stateNum);
      return setUpCall(url);
    }
  }

  /**
   * getStates method uses a different API from the one in output to get information about states
   * and their relative state codes.
   *
   * @return Data from the URL provided
   * @throws IOException throws an error if the info can't be fetched correctly
   */
  @Override
  public String getStates() throws IOException {
    URL url = new URL("https://api.census.gov/data/2010/dec/sf1?get=NAME&for=state:*");
    return setUpCall(url);
  }

  /**
   * getCounties method uses a different API from the one in output. Given a state, it gets
   * information about the states counties and their respective county codes.
   *
   * @param state State we want to search
   * @return Data from the URL provided
   * @throws IOException throws an error if the info can't be fetched correctly
   */
  @Override
  public String getCounties(String state) throws IOException {
    if (state.isEmpty()) {
      throw new IllegalArgumentException("error_bad_request. Please Enter a State and County");
    }
    URL url =
        new URL("https://api.census.gov/data/2010/dec/sf1?get=NAME&for=county:*&in=state:" + state);
    return setUpCall(url);
  }

  /**
   * This method handles the actual act of putting a request to an API. Given an API url as a
   * parameter, the method makes a request to that API. Used in all other methods in the class.
   *
   * @param url -> for the specific API you're looking to get
   * @return String -> raw data from url provided
   * @throws IOException throws an error if the info can't be fetched correctly
   */
  private String setUpCall(URL url) throws IOException {
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Accept", "application/json");
    if (conn.getResponseCode() == 204) {
      throw new NullPointerException(
          "error_datasource. Failed : HTTP Error input : Contents == "
              + conn.getResponseMessage()
              + ","
              + conn.getResponseCode());
    }
    if (conn.getResponseCode() != 200) {
      throw new RuntimeException(
          "error_datasource. Failed : HTTP Error code : " + conn.getResponseMessage());
    }
    InputStreamReader in = new InputStreamReader(conn.getInputStream());
    BufferedReader br = new BufferedReader(in);
    return br.lines().collect(Collectors.joining());
  }
}
