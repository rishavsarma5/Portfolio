package model;

import java.awt.Color;
import java.util.Objects;

/**
 * Representation of the Oval-shape.
 */
public class Oval extends AShape {

  /**
   * A public constructor for Oval which is a Oval-like shape.
   * @param id the name of the oval
   * @param position the position of the oval
   * @param color the color of the oval
   * @param height the height of the oval
   * @param width the width of the oval
   */
  public Oval(String id, Position position, Color color, float height, float width) {
    super(id, ShapeType.Oval, position, color, height, width);
  }

  /**
   * A faster and shorter constructor for Oval.
   * @param id the name of the Oval
   */
  public Oval(String id) {
    super(id, ShapeType.Oval);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Oval) {
      return (this.getID() == ((Oval) o).getID()
              && this.getPosition() == ((Oval) o).getPosition()
              && this.getColor() == ((Oval) o).getColor()
              && this.getWidth() == ((Oval) o).getWidth()
              && this.getHeight() == ((Oval) o).getHeight());
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
