package edu.brown.cs.student.main.server.responses;

import com.squareup.moshi.Moshi;
import edu.brown.cs.student.main.User.User;

import java.util.Map;

/**
 * Record used to send server the status of updating our user.
 * @param uid UID of thr user
 * @param result Result of the process
 * @param status Status of process
 */
public record UserUpdateResponse(String uid, Map<String,Object> result,String status) {

    /***
     * Serialize into json format.
     * @return Json format
     */
    public String serialize(){
        Moshi moshi = new Moshi.Builder().build();
        return moshi.adapter(UserUpdateResponse.class).toJson(this);

    }
}
