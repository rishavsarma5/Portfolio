package model;

/**
 * A representation of the types of shapes that can show up in the game.
 */
public enum ShapeType {
  Oval, Rectangle;

  /**
   * Return the string representation of the shape type.
   *
   * @return a string representing the shape type
   */
  public String toString() {
    if (this == ShapeType.Oval) {
      return "ellipse";
    } else {
      return "rectangle";
    }
  }
}
