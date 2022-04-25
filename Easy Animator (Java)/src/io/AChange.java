package io;

import java.util.List;
import java.util.Objects;

/**
 * This class represents an abstract class for IChange used to avoid duplicate implementation of
 * ColorChange, MoveChange, and ScaleChange.
 */
public abstract class AChange implements IChange {
  private int startTick;
  private int endTick;

  /**
   * Creates an AChange instance with the given start tick and end tick.
   *
   * @param startTick the start tick
   * @param endTick   the end tick
   */
  AChange(int startTick, int endTick) {
    this.startTick = startTick;
    this.endTick = endTick;
  }

  @Override
  public int getStartTick() {
    return this.startTick;
  }

  @Override
  public int getEndTick() {
    return this.endTick;
  }


  public abstract float getStartX();


  public abstract float getStartY();


  public abstract float getEndX();


  public abstract float getEndY();


  public abstract int getStartR();


  public abstract int getStartG();


  public abstract int getStartB();


  public abstract int getEndR();


  public abstract int getEndG();


  public abstract int getEndB();


  public abstract float getStartWidth();


  public abstract float getStartHeight();


  public abstract float getEndWidth();


  public abstract float getEndHeight();

  @Override
  public int getTheNumberInTheList(List<IChange> changes, int tick) {
    if (changes.size() == 0) {
      throw new IllegalArgumentException("There are no elements in the list");
    } else {
      for (int i = 0; i < changes.size(); i++) {
        if (changes.get(i).getStartTick() <= tick && changes.get(i).getEndTick() >= tick) {
          return i;
        }
      }
    }
    throw new IllegalArgumentException("The given tick is not found in the given list of changes");
  }


  @Override
  public float perFormInterpolation(IChange change, int tick, String type) {
    float result;
    int startTime = change.getStartTick();
    int endTime = change.getEndTick();
    if (Objects.equals(type, "X")) {
      float startX = change.getStartX();
      float endX = change.getEndX();
      result = this.calculateInterpolation(startTime, endTime, startX, endX, tick);
    } else if (Objects.equals(type, "Y")) {
      float startY = change.getStartY();
      float endY = change.getEndY();
      result = this.calculateInterpolation(startTime, endTime, startY, endY, tick);
      return result;
    } else if (Objects.equals(type, "R")) {
      float startR = change.getStartR();
      float endR = change.getEndR();
      result = this.calculateInterpolation(startTime, endTime, startR, endR, tick);
    } else if (Objects.equals(type, "G")) {
      float startG = change.getStartG();
      float endG = change.getEndG();
      result = this.calculateInterpolation(startTime, endTime, startG, endG, tick);
    } else if (Objects.equals(type, "B")) {
      float startB = change.getStartB();
      float endB = change.getEndB();
      result = this.calculateInterpolation(startTime, endTime, startB, endB, tick);
    } else if (Objects.equals(type, "W")) {
      float startW = change.getStartWidth();
      float endW = change.getEndWidth();
      result = this.calculateInterpolation(startTime, endTime, startW, endW, tick);
    } else if (Objects.equals(type, "H")) {
      float startH = change.getStartHeight();
      float endH = change.getEndHeight();
      result = this.calculateInterpolation(startTime, endTime, startH, endH, tick);
    } else {
      throw new IllegalArgumentException("the type is not specified properly");
    }
    return result;
  }

  @Override
  public float calculateInterpolation(int startTime, int endTime, float startValue, float endValue,
                                      int current) {
    if (current < startTime) {
      throw new IllegalArgumentException("The current time is invalid");
    }

    if (current > endTime) {
      throw new IllegalArgumentException("The current time is invalid");
    }

    int tA = startTime;
    float aX = startValue;
    int tB = endTime;
    float bX = endValue;
    float t = current;

    return (aX * ((tB - t) / (tB - tA))) + (bX * ((t - tA) / (tB - tA)));
  }
}

