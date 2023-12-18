package edu.brown.cs.student.main.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class ImageCacheService {

  private final Cache<String, String> imageCache;

  public ImageCacheService() {
    // Initialize the cache with a maximum size of 50 images and a 10-minute expiration time
    imageCache = CacheBuilder.newBuilder()
        .maximumSize(50)
        .expireAfterWrite(10, TimeUnit.MINUTES)
        .build();
  }

  public String getImage(String meikId) {
    // Try to get the image from the cache
    String base64Image = imageCache.getIfPresent(meikId);

    if (base64Image != null) {
      // Image found in the cache
      System.out.println("image cache hit: " + meikId);
      return base64Image;
    } else {
      // Image not found in the cache, load it and put it in the cache
      String newImage = loadImageFromFirebase(meikId);
      if (newImage != null) {
        System.out.println("image cached: " + meikId);
        imageCache.put(meikId, newImage);
      }
      return newImage;
    }
  }

  private String loadImageFromFirebase(String meikId) {
    System.out.println("image cache miss: " + meikId);
    // Get meik image
    FirebaseStorageService storageService = new FirebaseStorageService();
    try{
      byte[] image = storageService.getImageBytes("images/" + meikId + ".jpg");
      return Base64.getEncoder().encodeToString(image);
    }catch (Exception e){
      System.out.println(e.getMessage());
      return null;
    }
}
}

