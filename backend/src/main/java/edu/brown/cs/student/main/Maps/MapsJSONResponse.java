package edu.brown.cs.student.main.Maps;

import java.util.List;

public class MapsJSONResponse {
  public String type;
  public Geometry geometry;
  public Properties properties;

  public static class Geometry {
    public String type;
    public List<Double> coordinates;
  }

  public static class Properties {
    public String name;
  }
}
