package ru.ftc.shift.cookbook.dao;

import ru.ftc.shift.cookbook.model.DishType;
import ru.ftc.shift.cookbook.model.Ingredient;
import ru.ftc.shift.cookbook.model.Recipe;

import java.util.List;

import static java.util.Collections.emptyList;

/**
 * @since 13/10/22
 */
public class RecipeDao {

  public long insert(List<Ingredient> ingredients, DishType dishType, String description) {
    // вставляем в БД, возвращаем id записи
    return 0;
  }

  public List<Recipe> fetchAll() {
    // вытаскиваем из БД все записи
    return emptyList();
  }
}
