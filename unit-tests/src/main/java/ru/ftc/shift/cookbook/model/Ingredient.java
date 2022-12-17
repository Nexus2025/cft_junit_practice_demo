package ru.ftc.shift.cookbook.model;

/**
 * @since 13/10/22
 */
public class Ingredient {
  private final String name;
  private final double size;
  private final String unit;

  public Ingredient(String name, double size, String unit) {
    this.name = name;
    this.size = size;
    this.unit = unit;
  }
}
