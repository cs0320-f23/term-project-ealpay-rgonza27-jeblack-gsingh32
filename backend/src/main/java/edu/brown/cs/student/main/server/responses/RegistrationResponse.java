package edu.brown.cs.student.main.server.responses;

import com.squareup.moshi.Moshi;

/***
 * This is used to represent the
 * @param result If the registration was successful
 * @param emailAddress The email associated with this user
 * @param response Boolean representing if the registration was successful.
 * @param message Descriptive message on status of registration.
 */
public record RegistrationResponse(
    String result, String emailAddress, Boolean response, String message) {

  /***
   * Serialize into json format.
   * @return
   */
  public String serialize() {
    Moshi moshi = new Moshi.Builder().build();
    return moshi.adapter(RegistrationResponse.class).toJson(this);
  }
}
