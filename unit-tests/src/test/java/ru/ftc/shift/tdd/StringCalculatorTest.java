package ru.ftc.shift.tdd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @since 13/10/22
 */
public class StringCalculatorTest {
  StringCalculator sut = new StringCalculator();
  @Test
  void returnZeroWhenEmptyString() {
    assertEquals(0, sut.add(""));
  }

  @Test
  void returnZeroWhenNull() {
    assertEquals(0, sut.add(null));
  }

  @Test
  void returnZeroWhenSpaces() {
    assertEquals(0, sut.add("    "));
  }

  @Test
  void oneNumber() {
    assertEquals(1, sut.add("1"));
    assertEquals(123, sut.add("123"));
  }

  @Test
  void anyNumbers() {
    assertEquals(1 + 2 + 3 + 4 + 5, sut.add("1,2,3,4,5"));
    assertEquals(123 + 27, sut.add("123,27,0"));
  }

  @Test
  void throwsExceptionWhenNonInteger() {
    assertThrows(StringCalculatorException.class, () -> sut.add("asd"));
  }

  @Test
  void notIgnore1000() {
    assertEquals(1 + 2 + 3 + 4 + 5 + 1000, sut.add("1,2,3,4,5,1000"));
  }

  @Test
  void ignoreMoreThan1000() {
    assertEquals(1 + 2 + 3 + 4 + 5, sut.add("1,2,3,4,5,1001"));
    assertEquals(123 + 27, sut.add("123,27,0,2541"));
  }
}
