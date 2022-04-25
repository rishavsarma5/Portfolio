package model;

import java.awt.Color;

/**
 * Represent the shape interface in our programs.
 */
public interface IShape {

  /**
   * Set the height of a Shape to the height given.
   * @param height the height of the shape
   */
  public void setHeight(float height);

  /**
   * Set the width of a Shape to the height given.
   * @param width the width of the shape
   */
  public void setWidth(float width);

  /**
   * Set both the height and width of the given shape.
   * @param height the new height given
   * @param width the new width given
   */
  public void setSize(float height, float width);

  /**
   * Set the color to the given rgb values.
   * @param r the red
   * @param g the green
   * @param b the blue
   */
  public void setColorRGB(int r, int g, int b);

  /**
   * Set the color toe the given color.
   * @param newColor the new color we want to set to
   */
  public void setColor(Color newColor);

  /**
   * Get the height of the given Shape.
   * @return the height of the given shpae
   */
  public float getHeight();

  /**
   * Get the width of the given Shape.
   * @return the width of the given Shape
   */
  public float getWidth();

  /**
   * Set the position to the new position.
   * @param newPosition the new position given
   */
  public void setPosition(Position newPosition);

  /**
   * Set the position to the new position.
   * @param x the new x-coordinate
   * @param y the new y-coordinate
   */
  public void setPosition(float x, float y);

  /**
   * Get the position of the shape.
   * @return the position of the shape
   */
  public Position getPosition();

  /**
   * Get the color of the shape.
   * @return the color of the given shape
   */
  public Color getColor();

  /**
   * Get the ID of the shape.
   * @return the ID of the shape
   */
  public String getID();

  /**
   * Get the shape type of the shape.
   * @return the shapeType of the shape
   */
  public ShapeType getShapeType();

  /**
   * Overrides the equals() method to compare two shapes.
   * @param o the given object o
   * @return a boolean
   */
  public boolean equals(Object o);

  /**
   * Overrides the hashCode() method to ensure that two shapes with the same values are
   * equal.
   * @return the hashcode as an integer
   */
  public int hashCode();

  /**
   * Overrides the toString() method to ensure output fields of shapes to converted to String and
   * formatted.
   * @return the hashcode as an integer
   */
  public String toString();

  /**
   * Creates a properly formatted SVG string for creating the IShape to be added to the output SVG
   * file.
   * @param tempo the speed of the ticks (ticks per second)
   * @param loop if the SVG output is loop backed
   * @return a properly formatted string for a IShape for SVG output
   */
  public String createSVGString(int tempo, boolean loop);
}
