package model;

import java.awt.Color;
import java.util.Objects;

/**
 * Abstract class for Shape. Represent a shape of an animator.
 */
public class AShape implements IShape {
  private final String id;
  private final ShapeType type;
  private Position position;
  private Color color;
  private float height;
  private float width;

  /**
   * A more concrete constructor for an AShape which the user can assign
   * id, type, position, color, width and height of the shape.
   * @param id The name of the AShape
   * @param type the type of the AShape. Right now, the shape can only be either
   *             rectangle or oval
   * @param position the position of the AShape
   * @param color the color of the AShape
   * @param height the height of the AShape
   * @param width the width of the AShape
   */
  public AShape(String id, ShapeType type, Position position, Color color, float height,
                float width) {
    if (id == null || type == null || position == null || color == null) {
      throw new IllegalArgumentException("The input is invalid");
    }

    if (height <= 0 || width <= 0) {
      throw new IllegalArgumentException("The input is invalid -2");
    }

    this.id = id;
    this.type = type;
    this.position = position;
    this.color = color;
    this.height = height;
    this.width = width;
  }

  /**
   * A short, default constructor for AShape, in which the user can only
   * assign the name of the Shape and the ShapeType.
   * @param id the name of the Shape
   * @param type the type of the shape. Right now, the shape can only be either
   *             rectangle or oval
   */
  public AShape(String id, ShapeType type) {
    if (id == null || type == null) {
      throw new IllegalArgumentException("The input is invalid -2");
    }
    this.id = id;
    this.type = type;
    this.position = new Position(0,0);
    this.color = new Color(0,0,0);
    this.height = 0;
    this.width = 0;
  }

  @Override
  public void setHeight(float h) {
    if (h <= 0) {
      throw new IllegalArgumentException("The input is invalid");
    }

    this.height = h;
  }

  @Override
  public void setWidth(float w) {
    if (w <= 0) {
      throw new IllegalArgumentException("The input is invalid");
    }

    this.width = w;
  }


  @Override
  public void setSize(float height, float width) {
    setHeight(height);
    setWidth(width);
  }

  @Override
  public float getHeight() {
    return this.height;
  }


  @Override
  public float getWidth() {
    return this.width;
  }


  @Override
  public void setPosition(Position newPosition) {
    if (newPosition == null) {
      throw new IllegalArgumentException("The position is invalid");
    }
    else {
      this.position = newPosition;
    }
  }

  @Override
  public void setPosition(float x, float y) {
    this.position = new Position(x, y);
  }

  @Override
  public Position getPosition() {
    return this.position;
  }

  @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public String getID() {
    return this.id;
  }

  @Override
  public void setColorRGB(int r, int g, int b) {
    if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
      throw new IllegalArgumentException("The input is invalid");
    }

    else {
      this.color = new Color(r, g, b);
    }
  }

  @Override
  public void setColor(Color newColor) {
    if (newColor == null) {
      throw new IllegalArgumentException("The color is invalid");
    }
    this.color = newColor;
  }

  @Override
  public ShapeType getShapeType() {
    return type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof AShape)) {
      return false;
    }

    AShape that = (AShape) o;
    return that.id.equals(this.id) && that.position.equals(this.position)
            && that.color == this.color && that.width == this.width && that.height == this.height;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.color, this.position, this.width, this.height);
  }

  @Override
  public String toString() {
    return this.id + this.position.toString()
            + " " + Float.toString(this.width)
            + " " + Float.toString(this.height)
            + " " + Integer.toString(this.getColor().getRed())
            + " " + Integer.toString(this.getColor().getGreen())
            + " " + Integer.toString(this.getColor().getBlue());
  }

  @Override
  public String createSVGString(int tempo, boolean loop) {
    String output = "";
    String color = "rgb(" + this.color.getRed() + "," + this.color.getGreen() + ","
            + this.color.getBlue() + ")";

    if (this.getShapeType() == ShapeType.Oval) {
      output += "<ellipse" + " id=\"" + this.id + "\" cx=\"" + this.position.getX() + "\" " +
              "cy=\"" + this.position.getY() + "\" rx=\"" + this.width + "\" " +
              "ry=\"" + this.height + "\" fill=\"" + color + "\" visibility=\"visible\" >";
    }
    else {
      output += "<rect" + " id=\"" + this.id + "\" x=\"" + this.position.getX() + "\" " +
              "y=\"" + this.position.getY() + "\" width=\"" + this.width + "\" " +
              "height=\"" + this.height + "\" fill=\"" + color + "\" visibility=\"visible\" >";
    }

    return output;
  }
}
