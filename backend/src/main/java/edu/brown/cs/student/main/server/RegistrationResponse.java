package edu.brown.cs.student.main.server;

import com.squareup.moshi.Moshi;

public record RegistrationResponse(
    String result, String emailAddress, Boolean response, String message) {
  public String serialize() {
    Moshi moshi = new Moshi.Builder().build();
    return moshi.adapter(RegistrationResponse.class).toJson(this);
  }
}
