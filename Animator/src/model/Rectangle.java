package model;

import java.awt.Color;
import java.util.Objects;

/**
 * Represents a rectangle in our program.
 */
public class Rectangle extends AShape {
  /**
   * The constructor for the rectangle shape.
   * @param id the name of the rectangle shape
   * @param position the position of the rectangle
   * @param color the color of the rectangle
   * @param height the height of the rectangle
   * @param width the width of the rectangle
   */
  public Rectangle(String id, Position position, Color color, float height, float width) {
    super(id, ShapeType.Rectangle, position, color, height, width);
  }

  /**
   * A second, shorter constructor for the rectangle shape.
   * @param id the name of the rectangle shape
   */
  public Rectangle(String id) {
    super(id, ShapeType.Rectangle);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Rectangle) {
      return (this.getID() == ((Rectangle) o).getID()
              && this.getPosition() == ((Rectangle) o).getPosition()
              && this.getColor() == ((Rectangle) o).getColor()
              && this.getWidth() == ((Rectangle) o).getWidth()
              && this.getHeight() == ((Rectangle) o).getHeight());
    }

    else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getID(),this.getColor(),this.getPosition()
    , this.getWidth(),this.getHeight());
  }

  @Override
  public String toString() {
    return this.getPosition().toString()
            + " " + Float.toString(this.getWidth())
            + " " + Float.toString(this.getHeight())
            + " " + Integer.toString(this.getColor().getRed())
            + " " + Integer.toString(this.getColor().getGreen())
            + " " + Integer.toString(this.getColor().getBlue());
  }
}
