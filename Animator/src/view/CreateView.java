package view;

import model.IModel;

/**
 * A factory class that generates the corresponding view based on the input
 * string.
 */
public class CreateView {

  /**
   * A default constructor for the createView class.
   */
  public CreateView() {
    // Used as a factory Class.
  }

  /**
   * A factory method that generates the corresponding view based on the input.
   * @param s the string to represent the type of the view we want
   * @param model the model passed to the view by the model
   * @return a view that is used to generate the visual representation
   */
  public IView create(String s, IModel model, int tempo) {
    if (s == null || model == null || tempo <= 0) {
      throw new IllegalArgumentException("Invalid inputs!");
    }
    switch (s) {
      case "text":
        return new TextView();
      case "visual":
        return new VisualAnimationView(model.getBoundWidth(),model.getBoundHeight());
      case "svg":
        return new SVGView();
      case "interactive":
        return new InteractiveVisualView(tempo);
      default:
        throw new IllegalArgumentException("Invalid string input!");
    }
  }
}
