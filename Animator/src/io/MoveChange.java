package io;

/**
 * Represent a move change in our animator. A move change only includes information
 * about the start position and the end position of the change.
 */
public class MoveChange extends AChange {
  private float startX;
  private float startY;
  private float endX;
  private float endY;

  /**
   * A public constructor for the move change.
   * @param startX start x-coordinate
   * @param startY start y-coordinate
   * @param endX end x-coordinate
   * @param endY end y-coordinate
   */
  public MoveChange(int startTime,int endTime,float startX,float startY,float endX,float endY) {
    super(startTime,endTime);
    this.startX = startX;
    this.startY = startY;
    this.endX = endX;
    this.endY = endY;
  }

  @Override
  public float getStartX() {
    return this.startX;
  }

  @Override
  public float getStartY() {
    return this.startY;
  }

  @Override
  public float getEndX() {
    return this.endX;
  }

  @Override
  public float getEndY() {
    return this.endY;
  }

  @Override
  public int getStartR() {
    throw new UnsupportedOperationException("The operation is not supported.");
  }

  @Override
  public int getStartG() {
    throw new UnsupportedOperationException("The operation is not supported.");
  }

  @Override
  public int getStartB() {
    throw new UnsupportedOperationException("The operation is not supported.");
  }

  @Override
  public int getEndR() {
    throw new UnsupportedOperationException("The operation is not supported.");
  }

  @Override
  public int getEndG() {
    throw new UnsupportedOperationException("The operation is not supported.");
  }

  @Override
  public int getEndB() {
    throw new UnsupportedOperationException("The operation is not supported.");
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
