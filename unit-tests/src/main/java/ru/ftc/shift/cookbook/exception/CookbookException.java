package ru.ftc.shift.cookbook.exception;

/**
 * @since 13/10/22
 */
public class CookbookException extends RuntimeException {
  private final CookbookStatus status;

  public CookbookException(CookbookStatus status) {
    this.status = status;
  }

  public CookbookStatus getStatus() {
    return status;
  }
}
