package view;

import java.io.IOException;
import java.util.List;

import controller.IController;
import controller.IInteractiveController;
import model.IKeyFrame;
import model.IModel;


/**
 * An interface that represents all types of view in our program.
 */
public interface IView {
  /**
   * Draws the animations for the Key Frames of a shape.
   *
   * @param keyFrames the list of key frames for the shape
   * @throws IOException With a bad list of key frames.
   */
  void drawGUI(List<IKeyFrame> keyFrames) throws IOException;

  /**
   * Shows the shape and its animation in SVG.
   * @throws IOException with bad list of shapes
   */
  void drawSVG() throws IOException;

  /**
   * Creates the string of the TextView.
   * @param tempo the tempo of the animation as an int
   */
  String drawText(int tempo);

  /**
   * Sends or enters the finished frames to the output.
   */
  void export();

  /**
   * Sets the tempo (the ticks per second).
   * @param tempo the tempo the ticks play in the View
   */
  void setTempo(int tempo);

  /**
   * Sets the file name with given input.
   * @param file the name of the output file as a String
   */
  void setFileName(String file);

  /**
   * Enables the looping mechanism for a SVG file and sets the maxTick for constructing the faux
   * rectangle.
   * @param maxTick the max tick of the model
   * @throws IllegalArgumentException if max tick is < 0
   */
  void setLoopInfo(int maxTick);

  /**
   * Returns the boolean representing if the svg will loop or not.
   * @return boolean representing loop state
   */
  boolean getLoopState();

  /**
   * Returns the max tick of the animation as an int.
   * @return an int > 0
   */
  int getMaxTick();

  /**
   * Shows the string of the animation.
   * @param s the string of the animation
   * @throws IOException if it can't write to file
   */
  void appendToFile(String s) throws IOException;

  /**
   * Returns the IModel of the IView.
   * @return the IModel
   */
  IModel getModel();

  /**
   * Sets the model of the IView to the given model.
   * @param model the passed in IModel
   * @throws IllegalArgumentException if input is null
   */
  void setModel(IModel model);

  /**
   * Returns the final output string of the IView if applicable.
   * @return the output of the IView (for SVGView)
   */
  String getFinalString();

  /**
   * Sets the controller of the IView if applicable.
   * @param ctrl an IController instance
   * @throws IllegalArgumentException if the input is null
   */
  void setController(IInteractiveController ctrl);

  /**
   * Returns the controller of the IView if applicable.
   * @return an IController
   */
  IController getController();
}
