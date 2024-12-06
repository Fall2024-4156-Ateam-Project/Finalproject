package dev.teamproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import dev.teamproject.common.Pair;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Pair class, which represents a key-value pair.
 * These tests ensure that the constructor, getters, and the equality and hash code
 * methods of the Pair class work correctly. The tests include various cases such as
 * equality checks with same/different keys or values, handling of null keys/values,
 * and ensuring consistent behavior for the hash code.
 */
public class PairTests {
  @Test
  public void testPairConstructorAndGetters() {
    String key = "testKey";
    Integer value = 42;

    Pair<String, Integer> pair = new Pair<>(key, value);

    assertEquals(key, pair.getKey(), "Key should be 'testKey'");
    assertEquals(value, pair.getValue(), "Value should be 42");
  }

  @Test
  public void testEqualityWithSameKeyAndValue() {

    String key = "testKey";
    Integer value = 42;

    Pair<String, Integer> pair1 = new Pair<>(key, value);
    Pair<String, Integer> pair2 = new Pair<>(key, value);

    assertEquals(pair1, pair2, "Pairs with the same key and value should be equal");
  }

  @Test
  public void testEqualityWithDifferentKey() {

    String key1 = "key1";
    String key2 = "key2";
    Integer value = 42;

    Pair<String, Integer> pair1 = new Pair<>(key1, value);
    Pair<String, Integer> pair2 = new Pair<>(key2, value);

    assertNotEquals(pair1, pair2, "Pairs with different keys should not be equal");
  }

  @Test
  public void testEqualityWithDifferentValue() {

    String key = "testKey";
    Integer value1 = 42;
    Integer value2 = 100;

    Pair<String, Integer> pair1 = new Pair<>(key, value1);
    Pair<String, Integer> pair2 = new Pair<>(key, value2);

    assertNotEquals(pair1, pair2, "Pairs with different values should not be equal");
  }

  @Test
  public void testEqualityWithNullKey() {

    Integer value = 42;
    Pair<String, Integer> pair1 = new Pair<>(null, value);
    Pair<String, Integer> pair2 = new Pair<>(null, value);

    assertEquals(pair1, pair2, "Pairs with null keys and the same value should be equal");
  }

  @Test
  public void testEqualityWithNullValue() {

    String key = "testKey";
    Pair<String, Integer> pair1 = new Pair<>(key, null);
    Pair<String, Integer> pair2 = new Pair<>(key, null);

    assertEquals(pair1, pair2, "Pairs with null values and the same key should be equal");
  }

  @Test
  public void testHashCodeWithEqualPairs() {
    String key = "testKey";
    Integer value = 42;

    Pair<String, Integer> pair1 = new Pair<>(key, value);
    Pair<String, Integer> pair2 = new Pair<>(key, value);

    assertEquals(pair1.hashCode(), pair2.hashCode(), "Equal pairs should have the same hash code");
  }

  @Test
  public void testHashCodeWithDifferentPairs() {

    String key1 = "key1";
    String key2 = "key2";
    Integer value = 42;

    Pair<String, Integer> pair1 = new Pair<>(key1, value);
    Pair<String, Integer> pair2 = new Pair<>(key2, value);

    assertNotEquals(pair1.hashCode(), pair2.hashCode(),
            "Different pairs should have different hash codes");
  }

  @Test
  public void testPairWithNullKeyAndValue() {

    Pair<String, String> pair = new Pair<>(null, null);

    assertNull(pair.getKey(), "Key should be null");
    assertNull(pair.getValue(), "Value should be null");
  }
}
