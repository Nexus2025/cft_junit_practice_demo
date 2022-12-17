package ru.ftc.shift.calculator;

import java.math.BigDecimal;

/**
 * @since 13/10/22
 */
public class Calculator {
  BigDecimal add(String left, String right) {
    try {
      BigDecimal leftNumber = new BigDecimal(left);
      BigDecimal rightNumber = new BigDecimal(right);
      return leftNumber.add(rightNumber);

    } catch (NumberFormatException err) {
      throw new CalculatorException("Формат данных не поддерживается", err);
    }
  }

}
