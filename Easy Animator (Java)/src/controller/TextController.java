package controller;

import java.io.IOException;

import model.IModel;
import view.IView;
import view.TextView;

/**
 * This class represents a TextController which handles creating a .txt output file using the given
 * view and the Model created from the Main class. The start method calls TextView which formats the
 * information from the Model before adding it to a .txt output specified in the
 * main arguments. If the output is not specified, the Model information is printed to the console.
 */
public class TextController implements IController {
  private IModel model;
  private IView view;
  private int tempo = 1;

  /**
   * Creates a TextController instance which takes in an IModel and IView.
   * @param model the given Model
   * @param view the given View
   * @throws IllegalArgumentException if inputs are null or view is not a TextView
   */
  public TextController(IModel model, IView view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Inputs are invalid!");
    }

    if (!(view instanceof TextView)) {
      throw new IllegalArgumentException("Invalid view given!");
    }

    this.model = model;
    this.view = view;
  }

  @Override
  public void start() {
    if (model.getAllShapes().size() == 0) {
      try {
        view.appendToFile("");
        view.export();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    view.setModel(model);
    String resultString = view.drawText(tempo);
    try {
      view.appendToFile(resultString);
      view.export();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setTempo(int tempo) {
    if (tempo <= 0) {
      throw new IllegalArgumentException("Tempo can't be <= 0!");
    }
    this.tempo = tempo;
  }

  @Override
  public int getTempo() {
    return tempo;
  }

  @Override
  public String getType() {
    return "text";
  }
}
