package io;

/**
 * A class that represents a change in scale of a shape. Only contains the start width/height and
 * end width/height.
 */
public class ScaleChange extends AChange {
  private float startWidth;
  private float startHeight;
  private float endWidth;
  private float endHeight;

  /**
   * Public constructor for the scale change.
   *
   * @param startTime   the startTime of the change
   * @param endTime     the endTime of the change
   * @param startWidth  the startWidth of the change
   * @param startHeight the startHeight of the change
   * @param endWidth    the endWidth of the change
   * @param endHeight   the endHeight of the change
   */
  public ScaleChange(int startTime, int endTime, float startWidth
          , float startHeight, float endWidth, float endHeight) {
    super(startTime, endTime);
    this.startWidth = startWidth;
    this.startHeight = startHeight;
    this.endWidth = endWidth;
    this.endHeight = endHeight;
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
    return this.startWidth;
  }

  @Override
  public float getStartHeight() {
    return this.startHeight;
  }

  @Override
  public float getEndWidth() {
    return this.endWidth;
  }

  @Override
  public float getEndHeight() {
    return this.endHeight;
  }
}
