package controller;

import model.IModel;
import view.IView;

/**
 * This class is a factory class for creating an IController. It takes in a IModel, IView, and a
 * string to determine which IController implementation to create.
 */
public class CreateController {

  /**
   * Creates an IController instance with a given IModel, IView, and string. Returns either a
   * TextController (with IModel, IView), VisualAnimationController (with IModel, IView) or
   * SVGController (with IModel, IView) depending on string.
   * @param model the given model
   * @param view the given view
   * @param s the String to determine the type to return
   * @return an IController instance
   * @throws IllegalArgumentException if inputs are null
   */
  public IController create(IModel model, IView view, String s) {
    if (s == null || model == null || view == null) {
      throw new IllegalArgumentException("Invalid inputs!");
    }
    switch (s) {
      case "text":
        return new TextController(model, view);
      case "visual":
        return new VisualAnimationController(model, view);
      case "svg":
        return new SVGController(model, view);
      case "interactive":
        return new InteractiveVisualController(model, view);
      default:
        throw new IllegalArgumentException("Invalid string input!");
    }
  }
}
