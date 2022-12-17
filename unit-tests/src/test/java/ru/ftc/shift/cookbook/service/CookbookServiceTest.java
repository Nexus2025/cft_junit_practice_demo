package ru.ftc.shift.cookbook.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.ftc.shift.cookbook.dao.RecipeDao;
import ru.ftc.shift.cookbook.exception.CookbookException;
import ru.ftc.shift.cookbook.model.DishType;
import ru.ftc.shift.cookbook.model.Ingredient;
import ru.ftc.shift.cookbook.model.Recipe;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static ru.ftc.shift.cookbook.exception.CookbookStatus.GENERAL_ERROR;
import static ru.ftc.shift.cookbook.model.DishType.*;

/**
 * @since 13/10/22
 */
public class CookbookServiceTest {
  @Mock
  private RecipeDao recipeDao;
  @InjectMocks
  private CookbookService sut;

  private AutoCloseable closeable;

  @BeforeEach
  void setUp() {
    closeable = openMocks(this);
  }

  @Test
  @DisplayName("Успешно добавляется рецепт")
  void addRecipe() {
    String description = "Смешать и запечь";
    List<Ingredient> ingredients = List.of(new Ingredient("мука", 100, "грамм"), new Ingredient("вода", 1, "стакан"));
    long expectedId = 123L;
    when(recipeDao.insert(any(), any(), anyString())).thenReturn(expectedId);

    long actualId = sut.addRecipe(ingredients, BAKE, description);

    assertEquals(expectedId, actualId);
    verify(recipeDao).insert(ingredients, BAKE, description);
    verifyNoMoreInteractions(recipeDao);
  }

  @Test
  @DisplayName("Возвращает список рецептов, если это не напитки")
  void fetchByDishType_not_potables() {
    Recipe bake = new Recipe(null, BAKE, "выпекать");
    Recipe soup = new Recipe(List.of(new Ingredient("картошка", 2, "шт")), SOUPS, "варить");
    Recipe firstCourse = new Recipe(null, FIRST_COURSE, "жарить");
    Recipe drink = new Recipe(null, POTABLES, "взболтать");
    when(recipeDao.fetchAll()).thenReturn(List.of(bake, soup, firstCourse, drink));

    List<Recipe> list = sut.fetchByDishType(SOUPS);

    assertThat(list).usingRecursiveComparison().isEqualTo(List.of(soup));
    verify(recipeDao).fetchAll();
    verifyNoMoreInteractions(recipeDao);
  }

  @Test
  @DisplayName("Возвращает напиток-заглушку")
  void fetchByDishType_potables() {
    Recipe drink = new Recipe(null, POTABLES, "Напиток-заглушка");

    List<Recipe> list = sut.fetchByDishType(POTABLES);

    assertThat(list).usingRecursiveComparison().isEqualTo(List.of(drink));
    verifyNoInteractions(recipeDao);
  }

  @Test
  @DisplayName("Проверка на супы работает, если передали суп")
  void isSoup_soup() {
    Recipe soup = new Recipe(List.of(new Ingredient("картошка", 2, "шт")), SOUPS, "варить");

    assertTrue(sut.isSoup(soup));

    verifyNoInteractions(recipeDao);
  }

  @ParameterizedTest(name = "Проверка на супы работает, если передали не супы; тип блюда: {0}")
  @EnumSource(value = DishType.class, names = "SOUPS", mode = EXCLUDE)
  void isSoup_not_soup(DishType type) {
    Recipe recipe = new Recipe(null, type, "варить");

    assertFalse(sut.isSoup(recipe));

    verifyNoInteractions(recipeDao);
  }

  @ParameterizedTest(name = "Проверка на супы работает: тип блюда - {0}, это суп - {1}")
  @CsvSource({
      "FIRST_COURSE, false",
      "POTABLES, false",
      "BAKE, false",
      "SOUPS, true",

  })
  void isSoup(DishType type, boolean expected) {
    Recipe recipe = new Recipe(null, type, "варить");

    assertEquals(expected, sut.isSoup(recipe));

    verifyNoInteractions(recipeDao);
  }

  @ParameterizedTest(name = "Проверка на супы работает: тип блюда - {0}, это суп - {1}")
  @MethodSource("entityProvider")
  void isSoup_withMethod(DishType type, boolean expected) {
    Recipe recipe = new Recipe(null, type, "варить");

    assertEquals(expected, sut.isSoup(recipe));

    verifyNoInteractions(recipeDao);
  }

  @Test
  @DisplayName("Возвращает весь список рецептов")
  void getCookbook() {
    Recipe bake = new Recipe(null, BAKE, "выпекать");
    Recipe soup = new Recipe(List.of(new Ingredient("картошка", 2, "шт")), SOUPS, "варить");
    Recipe firstCourse = new Recipe(null, FIRST_COURSE, "жарить");
    Recipe drink = new Recipe(null, POTABLES, "взболтать");
    when(recipeDao.fetchAll()).thenReturn(List.of(bake, soup, firstCourse, drink));

    List<Recipe> list = sut.getCookbook();

    assertThat(list)
        .usingRecursiveComparison()
        .ignoringCollectionOrder()
        .isEqualTo(List.of(soup, drink, firstCourse, bake));
    verify(recipeDao).fetchAll();
    verifyNoMoreInteractions(recipeDao);
  }

  @Test
  @DisplayName("Возвращает ошибку, если не смогли достать список рецептов")
  void getCookbook_fail() {
    when(recipeDao.fetchAll()).thenThrow(new RuntimeException());

    CookbookException ex = assertThrows(CookbookException.class, () -> sut.getCookbook());

    assertEquals(GENERAL_ERROR, ex.getStatus());
    verify(recipeDao).fetchAll();
    verifyNoMoreInteractions(recipeDao);
  }

  static Stream<Arguments> entityProvider() {
    return Arrays.stream(DishType.values())
        .map(dishType -> arguments(dishType, SOUPS.equals(dishType)));
  }

  @AfterEach
  void tearDown() throws Exception {
    closeable.close();
  }
}
