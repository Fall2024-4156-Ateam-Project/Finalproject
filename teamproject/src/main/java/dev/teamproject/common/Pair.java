package dev.teamproject.common;

/**
 * Helper class to generate key-value pairs.
 */
public record Pair<K, V>(K key, V value) {

  // @Override
  public K getKey() {
    return key;
  }

  // @Override
  public V getValue() {
    return value;
  }
  // intentionally empty

}
