package model;

/**
 * Representing a 2D position with both x and y coordinate
 * represented as float.
 */
public final class Position {
  private final float x;
  private final float y;

  /**
   * A public constructor representing a position.
   * @param x x-coordinate
   * @param y y-coordinate
   */
  public Position(float x, float y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Get the x-coordinate of a position.
   * @return the x-coordinate of a position
   */
  public float getX() {
    return x;
  }

  /**
   * Get the y-coordinate of a position.
   * @return the y-coordinate of a position
   */
  public float getY() {
    return y;
  }

  @Override
  public String toString() {
    return Float.toString(this.getX()) + " " + Float.toString(this.getY());
  }
}
