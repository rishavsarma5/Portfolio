package model;

import java.util.List;

/**
 * The interface of IMotions that represent the motions in a game.
 */
public interface IMotion {
  /**
   * Get the start tick of the motion.
   * @return the start tick of the motion
   */
  public int getStartTick();

  /**
   * Get the end tick of the motion.
   * @return the end tick of the animation
   */
  public int getEndTick();

  /**
   * Get all the keyFrames of a motion.
   * @return all the keyFrames in the motion
   */
  public List<IKeyFrame> getKeyFrames();

  /**
   * Get the IKeyFrame at a given time.
   * @param tick the given time
   * @return the KeyFrame at a given tick
   */
  public IKeyFrame getKeyFrameAt(int tick);

  /**
   * Add a frame to the given motion, a frame can only be added to the front or back of
   * a motion.
   * @param f1 the keyFrame to be added
   */
  public void addFrame(IKeyFrame f1);

  public String createString(int tempo);

  String createSVGString(int tempo, boolean loop);

  /**
   * Creates the mock string output used for testing for a IMotion.
   * @param tempo the speed of the Animation
   * @return the string version of the IMotion
   */
  String createTextViewMockString(int tempo);
}
