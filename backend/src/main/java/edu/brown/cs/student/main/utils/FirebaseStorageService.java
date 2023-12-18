package edu.brown.cs.student.main.utils;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.FileInputStream;

public class FirebaseStorageService {

  private final Storage storage;
  private final String bucketName;

  public FirebaseStorageService() {
    try{
      this.storage = StorageOptions.newBuilder()
          .setCredentials(GoogleCredentials.fromStream(new FileInputStream("src/main/resources/meikdatabase-firebase-adminsdk-5r9bn-be1c95c791.json")))
          .build()
          .getService();
      this.bucketName = "meikdatabase.appspot.com";
    }catch(Exception e){
      e.printStackTrace();
      System.out.println("Error in FirebaseStorageService");
      throw new RuntimeException("Error in FirebaseStorageService");
    };

  }

  public byte[] getImageBytes( String imagePath) {
    try{
      BlobId blobId = BlobId.of(this.bucketName, imagePath);
      Blob blob = storage.get(blobId);
      // Check if the blob exists
      if (blob != null) {
        return blob.getContent();
      } else {
        System.out.println("No image exists for id: " + imagePath);
        return null;
      }

    }catch (Exception e) {
      e.printStackTrace();
      System.out.println("Error in getImageBytes");
      return null;
    }

  }
}

