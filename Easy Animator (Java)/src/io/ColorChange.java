package io;

/**
 * Represent a color change in our animator. A move change only includes information
 * about the start color and the end color of the change.
 */
public class ColorChange extends AChange {
  private int startR;
  private int startG;
  private int startB;
  private int endR;
  private int endG;
  private int endB;

  /**
   * A public constructor for the ColorChange.
   *
   * @param startTime the start time of the Shape
   * @param endTime   the end time of the Shape
   * @param startR    the start red component of the Shape
   * @param startG    the start green component of the Shape
   * @param startB    the start blue component of the Shape
   * @param endR      the end blue component of the Shape
   * @param endG      the end blue component of the Shape
   * @param endB      the end blue component of the Shape
   */
  public ColorChange(int startTime, int endTime, int startR, int startG
          , int startB, int endR, int endG, int endB) {
    super(startTime, endTime);
    this.startR = startR;
    this.startG = startG;
    this.startB = startB;
    this.endR = endR;
    this.endG = endG;
    this.endB = endB;

  }

  @Override
  public float getStartX() {
    throw new UnsupportedOperationException("The operation is not supported.");
  }

  @Override
  public float getStartY() {
    throw new UnsupportedOperationException("The operation is not supported.");
  }

  @Override
  public float getEndX() {
    throw new UnsupportedOperationException("The operation is not supported.");
  }

  @Override
  public float getEndY() {
    throw new UnsupportedOperationException("The operation is not supported.");
  }

  @Override
  public int getStartR() {
    return this.startR;
  }

  @Override
  public int getStartG() {
    return this.startG;
  }

  @Override
  public int getStartB() {
    return this.startB;
  }

  @Override
  public int getEndR() {
    return this.endR;
  }

  @Override
  public int getEndG() {
    return this.endG;
  }

  @Override
  public int getEndB() {
    return this.endB;
  }

  @Override
  public float getStartWidth() {
    throw new UnsupportedOperationException("The operation is not supported.");
  }

  @Override
  public float getStartHeight() {
    throw new UnsupportedOperationException("The operation is not supported.");
  }

  @Override
  public float getEndWidth() {
    throw new UnsupportedOperationException("The operation is not supported.");
  }

  @Override
  public float getEndHeight() {
    throw new UnsupportedOperationException("The operation is not supported.");
  }
}
