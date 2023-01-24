package io;

import java.util.List;

/**
 * This represents the bridge we built between the tween builder and the model class.
 * The reason we created this interface and subclasses is that the model from the previous
 * assignments are not seperated by the type. However, we don't actually want to touch the
 * model class we have already tested. Therefore, we would like to store the info in the tween
 * builder before finishing and then pass all of them to the model.
 */
public interface IChange {

  /**
   * Get the start tick of the change.
   *
   * @return the int representing the start tick of the change
   */
  int getStartTick();

  /**
   * Get the end tick of the change.
   *
   * @return the int representing the end tick of the change
   */
  int getEndTick();

  /**
   * Get the starting x-coordinate of the change.
   *
   * @return a float representing the x-coordinate of the change
   */
  float getStartX();

  /**
   * Get the starting y-coordinate of the change.
   *
   * @return a float representing the starting y-coordinate of the change
   */
  float getStartY();

  /**
   * Get the ending x-coordinate of the change.
   *
   * @return a float representing the ending x-coordinate of the change
   */
  float getEndX();

  /**
   * Get the ending x-coordinate of the change.
   *
   * @return a float representing the ending x-coordinate of the change
   */
  float getEndY();

  /**
   * Get the starting red value of the change.
   *
   * @return the starting red value of the change
   */
  int getStartR();

  /**
   * Get the starting green value of the change.
   *
   * @return the starting green value of the change
   */
  int getStartG();

  /**
   * Get the starting blue value of the change.
   *
   * @return the starting blue value of the change.
   */
  int getStartB();

  /**
   * Get the ending blue value of the change.
   *
   * @return the ending blue value of the change
   */
  int getEndR();

  /**
   * Get the starting green value of the change.
   *
   * @return the ending green value of the change
   */
  int getEndG();

  /**
   * Get the ending blue value of the change.
   *
   * @return the ending blue value of the change
   */
  int getEndB();

  /**
   * Get the starting width of the change.
   *
   * @return the starting width of the change.
   */
  float getStartWidth();

  /**
   * Get the starting height of the change.
   *
   * @return the starting height of the change.
   */
  float getStartHeight();

  /**
   * Get the ending width of the change.
   *
   * @return the ending width of the change
   */
  float getEndWidth();

  /**
   * Get the ending height of the change.
   *
   * @return the height of the change
   */
  float getEndHeight();

  /**
   * Return the index of motion the given tick lies in.
   *
   * @param changes the changes given
   * @param tick    the tick we want to search for
   * @return an int representing the given tick is at what IChange
   */
  int getTheNumberInTheList(List<IChange> changes, int tick);

  /**
   * Given a change, output the middle value at the given tick.
   *
   * @param change the change given
   * @param tick   the tick
   * @param type   the type of value we want to calculate
   * @return a float representing that value at the given tick
   */
  float perFormInterpolation(IChange change, int tick, String type);

  /**
   * Given the start/end time and start/end value and current tick, able to figure out that value
   * at the given tick.
   *
   * @param startTime  The startTime
   * @param endTime    The endTime
   * @param startValue The startValue
   * @param endValue   The endValue
   * @param current    The current tick
   * @return the intermediate value
   */
  float calculateInterpolation(int startTime, int endTime, float startValue, float endValue,
                               int current);
}
