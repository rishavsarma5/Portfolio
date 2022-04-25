package model;

import java.util.List;

/**
 * A representation for model of an animation.
 */
public interface IModel {

  /**
   * Get all the shapes that show up in the current animation.
   * @return list of shapes show up in the current animation
   */
  List<IShape> getAllShapes();

  /**
   * Get all the motions of a shape.
   * @param s a motion s
   * @return the list of all shapes
   */
  public List<IMotion> getAllMotions(String s);

  /**
   * Get the KeyFrames of all the shapes of the animation.
   * @param s the shape specified
   * @return list of KeyFrames of a shape
   */
  public List<IKeyFrame> getAllKeyFrames(String s);

  /**
   * Get the keyframe of a given shape at a given tick.
   * @param s the shape the user specified
   * @param tick the time tick
   * @return a keyFrame for a given shape at a given tick
   */
  public IKeyFrame getKeyFrameAt(String s, int tick);

  /**
   * Add a new shape to the given model.
   * @param s the new shape to be added
   */
  public void addShape(IShape s);

  /**
   * Remove a existing shape to the given model.
   * @param s the existing shape to be removed
   */
  public void removeShape(IShape s);

  /**
   * Get all the keyframes at a given tick.
   * @param currTick the current tick
   * @return all keyframes at a given tick
   */

  public List<IKeyFrame> getKeyFramesForEachShapeAtGivenTick(int currTick);


  /**
   * Set the bounds for the model.
   * @param x width
   * @param y height
   */

  public void setBounds(int x, int y);

  /**
   * Get the width bound of the model.
   */
  public int getBoundWidth();

  /**
   * Get the height bound of the model.
   */
  public int getBoundHeight();

  public void setMaxTick(int t);

  public int getMaxTick();

  /**
   * Add a given motion to the model, if the shape is not added, we add that shape.
   * @param motion the motion
   * @param shape the shape
   */
  public void addMotion(IMotion motion, IShape shape);
}
