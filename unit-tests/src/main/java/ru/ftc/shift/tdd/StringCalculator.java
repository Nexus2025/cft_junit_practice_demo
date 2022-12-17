package ru.ftc.shift.tdd;

import java.util.Arrays;

/**
 * @since 13/10/22
 */
public class StringCalculator {
  public int add(String numbers) {
    if (numbers == null || numbers.trim().isEmpty()) {
      return 0;
    }
    try {
      return Arrays.stream(numbers.split(","))
          .mapToInt(Integer::parseInt)
          .filter(i -> i <= 1000)
          .sum();
    } catch (NumberFormatException e) {
      throw new StringCalculatorException();
    }
  }
}
