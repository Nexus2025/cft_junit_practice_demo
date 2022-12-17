package ru.ftc.shift.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @since 13/10/22
 */
class CalculatorTest {
  private final Calculator calculator = new Calculator();

  @Test
  @DisplayName("Корректно суммирует числа")
  void add_returnsSum_IfPassCorrectNumbers() {
    BigDecimal result = calculator.add("1", "2");
    assertEquals(new BigDecimal(3), result);
  }

  @Test
  @DisplayName("Возвращает ошибку, если переданы не числа")
  void add_throwsCalculatorException_IfPassNoDigitStrings() {
    assertThrows(CalculatorException.class, () -> calculator.add("asd", "acd"));
  }

  @Test
  @DisplayName("Корректно суммирует отрицательные числа")
  void add_returnsNegative_IfPassNegativeNums() {
    BigDecimal result = calculator.add("-10", "-32");
    assertEquals(new BigDecimal(-42), result);
  }
}
