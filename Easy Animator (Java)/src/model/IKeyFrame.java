package model;


import java.awt.Color;


/**
 * The interface that represents a sortment of KeyFrames.
 */
public interface IKeyFrame {

  /**
   * Change the size of the Shape in the given KeyFrame.
   *
   * @param x the new x-coordinate of the given Shape
   * @param y the new y-coordinate of the given Shape
   */
  public void changeSize(float x, float y);

  /**
   * Change the color of the Shape in the given KeyFrame.
   *
   * @param newColor the new Color of the given Shape
   */
  public void changeColor(Color newColor);

  /**
   * Change the position of the Shape in the given KeyFrame.
   *
   * @param newPosition the new position of the given Shape
   */
  public void changePositionOne(Position newPosition);

  /**
   * Get the name of the Shape in the given KeyFrame.
   *
   * @return the name of the Shape in the given KeyFrame
   */
  public String getID();

  /**
   * Get the position of the Shape in the given KeyFrame.
   *
   * @return the position of the Shape in the given KeyFrame
   */
  public Position getPosition();

  /**
   * Get the Color of the Shape in the given KeyFrame.
   *
   * @return the color of the Shape in the given KeyFrame
   */

  public Color getColor();

  /**
   * Get the width of the Shape in the given KeyFrame.
   *
   * @return the width of the Shape in the given KeyFrame
   */
  public float getWidth();

  /**
   * Get the height of the Shape in the given KeyFrame.
   * @return the height of the Shape in the given KeyFrame
   */
  public float getHeight();

  /**
   * Set the RGB values of the Shape to the given RGB values.
   * @param r the red
   * @param g the green
   * @param b the blue
   */
  public void changeColorRGB(int r, int g, int b);

  /**
   * Change the position of the Shape to the given x & y values.
   * @param x the new x-coordinate
   * @param y the new y-coordinate
   */
  public void changePosition(float x, float y);

  /**
   * Get the time of the KeyFrame.
   * @return the time of the KeyFrame
   */
  public int getTick();

  /**
   * Gets the Shape of the Key Frame.
   * @return the shape of the key frame
   */
  public IShape getShape();

  /**
   * Gets the shape type of the shape of the Key Frame.
   * @return the shape type of the shape of the key frame
   */
  public ShapeType getShapeType();

  public String createKFString(int tempo);
}
