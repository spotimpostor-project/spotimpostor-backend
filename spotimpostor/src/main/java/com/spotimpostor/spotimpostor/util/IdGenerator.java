package com.spotimpostor.spotimpostor.util;

import java.util.UUID;

public class IdGenerator {
  private IdGenerator() {
    // Prevent instantiation
  }

  public static UUID generateId() {
    return UUID.randomUUID();
  }
}
