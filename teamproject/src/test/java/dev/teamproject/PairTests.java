package dev.teamproject;

import dev.teamproject.common.Pair;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class PairTests {

  @Test
  public void testPairConstructorAndGetters() {
    // Arrange
    String key = "testKey";
    Integer value = 42;
    // Act
    Pair<String, Integer> pair = new Pair<>(key, value);
    // Assert
    assertEquals(key, pair.getKey(), "Key should be 'testKey'");
    assertEquals(value, pair.getValue(), "Value should be 42");
  }

  @Test
  public void testEqualityWithSameKeyAndValue() {
    // Arrange
    String key = "testKey";
    Integer value = 42;
    Pair<String, Integer> pair1 = new Pair<>(key, value);
    Pair<String, Integer> pair2 = new Pair<>(key, value);
    // Act & Assert
    assertEquals(pair1, pair2, "Pairs with the same key and value should be equal");
  }

  @Test
  public void testEqualityWithDifferentKey() {
    // Arrange
    String key1 = "key1";
    String key2 = "key2";
    Integer value = 42;

    Pair<String, Integer> pair1 = new Pair<>(key1, value);
    Pair<String, Integer> pair2 = new Pair<>(key2, value);
    // Act & Assert
    assertNotEquals(pair1, pair2, "Pairs with different keys should not be equal");
  }

  @Test
  public void testEqualityWithDifferentValue() {
    // Arrange
    String key = "testKey";
    Integer value1 = 42;
    Integer value2 = 100;
    Pair<String, Integer> pair1 = new Pair<>(key, value1);
    Pair<String, Integer> pair2 = new Pair<>(key, value2);
    // Act & Assert
    assertNotEquals(pair1, pair2, "Pairs with different values should not be equal");
  }

  @Test
  public void testEqualityWithNullKey() {
    // Arrange
    Integer value = 42;
    Pair<String, Integer> pair1 = new Pair<>(null, value);
    Pair<String, Integer> pair2 = new Pair<>(null, value);
    // Act & Assert
    assertEquals(pair1, pair2, "Pairs with null keys and the same value should be equal");
  }

  @Test
  public void testEqualityWithNullValue() {
    // Arrange
    String key = "testKey";
    Pair<String, Integer> pair1 = new Pair<>(key, null);
    Pair<String, Integer> pair2 = new Pair<>(key, null);
    // Act & Assert
    assertEquals(pair1, pair2, "Pairs with null values and the same key should be equal");
  }

  @Test
  public void testHashCodeWithEqualPairs() {
    // Arrange
    String key = "testKey";
    Integer value = 42;
    Pair<String, Integer> pair1 = new Pair<>(key, value);
    Pair<String, Integer> pair2 = new Pair<>(key, value);
    // Act & Assert
    assertEquals(pair1.hashCode(), pair2.hashCode(), "Equal pairs should have the same hash code");
  }

  @Test
  public void testHashCodeWithDifferentPairs() {
    // Arrange
    String key1 = "key1";
    String key2 = "key2";
    Integer value = 42;
    Pair<String, Integer> pair1 = new Pair<>(key1, value);
    Pair<String, Integer> pair2 = new Pair<>(key2, value);
    // Act & Assert
    assertNotEquals(pair1.hashCode(), pair2.hashCode(), "Different pairs should have different hash codes");
  }

  @Test
  public void testPairWithNullKeyAndValue() {
    // Arrange
    Pair<String, String> pair = new Pair<>(null, null);
    // Act & Assert
    assertNull(pair.getKey(), "Key should be null");
    assertNull(pair.getValue(), "Value should be null");
  }
}
