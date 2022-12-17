package ru.ftc.shift.cookbook.model;

import java.util.List;

/**
 * @since 13/10/22
 */
public class Recipe {
  private final List<Ingredient> ingredients;
  private final DishType type;
  private final String description;

  public Recipe(List<Ingredient> ingredients, DishType type, String description) {
    this.ingredients = ingredients;
    this.type = type;
    this.description = description;
  }

  public List<Ingredient> getIngredients() {
    return ingredients;
  }

  public DishType getType() {
    return type;
  }

  public String getDescription() {
    return description;
  }
}
