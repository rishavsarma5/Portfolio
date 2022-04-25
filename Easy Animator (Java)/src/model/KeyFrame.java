package model;

import java.awt.Color;

/**
 * A public constructor for the KeyFrame of the animator, which
 * represents what the user will see at a specific tick.
 */
public class KeyFrame implements IKeyFrame {

  private final int tick;
  private IShape shape;

  /**
   * A public constructor for the KeyFrame.
   * @param tick the time of the frame
   * @param s the shape in the frame
   */
  public KeyFrame(int tick, IShape s) {
    if (tick <= 0) {
      throw new IllegalArgumentException("The tick is invalid");
    }

    if (s == null) {
      throw new IllegalArgumentException("The shape is invalid");
    }
    this.tick = tick;
    this.shape = s;
  }

  @Override
  public void changeSize(float x, float y) {
    this.shape.setSize(y, x);
  }

  @Override
  public void changeColor(Color newColor) {
    this.shape.setColor(newColor);
  }

  @Override
  public void changeColorRGB(int r, int g, int b) {
    this.shape.setColorRGB(r, g, b);
  }

  @Override
  public void changePositionOne(Position newPosition) {
    this.shape.setPosition(newPosition);
  }

  @Override
  public void changePosition(float x, float y) {
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("The input is invalid");
    }
    this.shape.setPosition(x, y);
  }

  @Override
  public String getID() {
    return this.shape.getID();
  }

  @Override
  public Position getPosition() {
    return this.shape.getPosition();
  }

  @Override
  public Color getColor() {
    return this.shape.getColor();
  }

  @Override
  public float getWidth() {
    return this.shape.getWidth();
  }

  @Override
  public float getHeight() {
    return this.shape.getHeight();
  }

  @Override
  public int getTick() {
    return this.tick;
  }

  @Override
  public IShape getShape() {
    return this.shape;
  }

  @Override
  public ShapeType getShapeType() {
    return this.shape.getShapeType();
  }

  @Override
  public String createKFString(int tempo) {
    return ((tick / tempo) + " " + this.shape.toString());
  }
}
