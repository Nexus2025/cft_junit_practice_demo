package ru.ftc.shift.cookbook.service;

import ru.ftc.shift.cookbook.dao.RecipeDao;
import ru.ftc.shift.cookbook.exception.CookbookException;
import ru.ftc.shift.cookbook.model.DishType;
import ru.ftc.shift.cookbook.model.Ingredient;
import ru.ftc.shift.cookbook.model.Recipe;

import java.util.List;
import java.util.stream.Collectors;

import static ru.ftc.shift.cookbook.exception.CookbookStatus.GENERAL_ERROR;
import static ru.ftc.shift.cookbook.model.DishType.POTABLES;
import static ru.ftc.shift.cookbook.model.DishType.SOUPS;

/**
 * @since 13/10/22
 */
public class CookbookService {
  private final RecipeDao recipeDao;

  public CookbookService(RecipeDao recipeDao) {
    this.recipeDao = recipeDao;
  }

  public long addRecipe(List<Ingredient> ingredients, DishType dishType, String description) {
    return recipeDao.insert(ingredients, dishType, description);
  }

  public List<Recipe> fetchByDishType(DishType type) {
    if (POTABLES.equals(type)) {
      return List.of(new Recipe(null, POTABLES, "Напиток-заглушка"));
    }
    return recipeDao.fetchAll().stream()
        .filter(r -> r.getType().equals(type))
        .collect(Collectors.toList());
  }

  public boolean isSoup(Recipe recipe) {
    return SOUPS.equals(recipe.getType());
  }

  public List<Recipe> getCookbook() throws CookbookException {
    try {
      return recipeDao.fetchAll();
    } catch (Exception e) {
      throw new CookbookException(GENERAL_ERROR);
    }
  }

}
