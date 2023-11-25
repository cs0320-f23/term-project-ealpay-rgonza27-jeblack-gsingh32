package edu.brown.cs.student.main.server;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

/** Class to handle caching implements ICache to do so. */
public class CacheData implements Icache {

  private final LoadingCache<String, String> cache;
  private final ApiOutput apiReturn;

  /**
   * Our cache constructor that utilizes Guava's cache library. Takes in three parameters: the data
   * to cache in the form of an AIOutput, the maximum number of items the cache can hold and the
   * amount of time an entry will remain in the cache before being deleted. The class overrides the
   * output method form the APIOutput class to use in the cache loader as the class implements the
   * same interface as APIOutput.
   *
   * @param apiOutput To get The output received from the API
   * @param maxSize maximum size the cache can have
   * @param expTime the time at which a new addition to the cache gets dumped
   */
  public CacheData(ApiOutput apiOutput, int maxSize, int expTime) {
    this.apiReturn = apiOutput;
    this.cache =
        CacheBuilder.newBuilder()
            .maximumSize(maxSize)
            .expireAfterWrite(expTime, TimeUnit.MINUTES)
            .recordStats()
            .build(
                new CacheLoader<>() {
                  @NotNull
                  @Override
                  public String load(@NotNull String key) throws Exception {
                    System.out.println("loaded");
                    return apiReturn.output(key);
                  }
                });
  }

  /**
   * Overriding the output method from Icache interface to utilize it in the load method above.
   *
   * @param state State to search
   * @return String -> the corresponding string value in the cache for key state.
   * @throws ExecutionException handles any potential exception
   */
  @Override
  public String output(String state) throws ExecutionException {
    String result = cache.get(state);
    System.out.println(cache.stats());
    System.out.println(cache.asMap());
    return result;
  }

  /** Used for testing. */
  @Override
  public String getStates() throws IOException {
    return null;
  }

  /** Used for testing. */
  @Override
  public String getCounties(String state) throws IOException {
    return null;
  }
}
