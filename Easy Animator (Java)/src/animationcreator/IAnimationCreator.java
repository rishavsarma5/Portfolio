package animationcreator;

/**
 * This interface represents the IAnimationCreator that creates two animations that can be run by
 * the EasyAnimator program from the rest of the code. The interface supports generating an
 * animation programmatically and also creates one that is hard-coded in the two string method. The
 * animations created are written into .txt files by the GeneratorMain class.
 */
public interface IAnimationCreator {

  /**
   * Recursively creates move actions that move the diss in the animation.
   * @param numberOfDisks the number of disks
   * @param startRod the starting column
   * @param endRod the ending column
   */
  void move(int numberOfDisks, int startRod, int endRod);

  /**
   * Generates IChange commands and updates the disk's positions each move.
   * @param start the start time of the motion
   * @param end the end time of the motion
   */
  void addMove(int start, int end);

  /**
   * Creates the String that contains the animation motions to be written into a .txt file. It is
   * formatted in a way that the EasyAnimator can parse.
   * @return the String containing the whole animation motions
   */
  String toString();
}
