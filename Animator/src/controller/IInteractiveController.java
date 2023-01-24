package controller;

/**
 * This interface represents an IInteractiveController which extends the IController interface to
 * provide methods that are called for each interactive feature that users might interact with
 * dynamically. Otherwise, it contains and serves the same purpose as the IController interface for
 * outputting the IModel info into a different output format.
 */
public interface IInteractiveController extends IController {

  /**
   * Starts the timer of an animation to begin running it. It is called after the Start button is
   * pressed in the InteractiveVisualView.
   */
  void startAnimation();

  /**
   * Sets the paused feature to false in order to continue running an animation. It is called after
   * the Resume button is pressed in the InteractiveVisualView.
   */
  void resume();

  /**
   * Sets the current tick rate (tempo) back to 1 to reset the timer in order play the animation
   * again. It is called after the Restart button is pressed in the InteractiveVisualView.
   */
  void restart();

  /**
   * Sets the paused feature to false in order to pause an animation (pause the timer). It is called
   * after the Pause button is pressed in the InteractiveVisualView.
   */
  void pause();

  /**
   * Sets the looping feature to true in order to play the animation again (reset the timer) when it
   * finishes. It is called after the Loop checkbox is checked in the InteractiveVisualView.
   */
  void setLooping();

  /**
   * Returns the looping state as a boolean.
   * @return true if the animation will loop and false if it will not
   */
  boolean getLoopingState();

  /**
   * Returns the paused state as a boolean.
   * @return true if the animation is paused and false if it is not
   */
  boolean getPausedState();

  /**
   * Returns the String log used for the mock (for testing).
   * @return String value of log
   */
  String getLog();
}
