package controller;

import java.io.IOException;

import model.IModel;
import view.IView;
import view.SVGView;

/**
 * This class represents a SVGController which handles creating an SVG output file using the given
 * view and the Model created from the Main class. The start method calls SVGView which formats the
 * information from the Model before adding it to a SVG file to be played on a
 * browser.
 */
public class SVGController implements IController {
  private IModel model;
  private IView view;
  private int tempo = 1;

  /**
   * Creates an instance of SVGController with the given IModel and IView.
   * @param model the given Model
   * @param view the given View
   * @throws IllegalArgumentException if inputs are null or view is not a SVGView
   */
  public SVGController(IModel model, IView view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Inputs are invalid!");
    }

    if (!(view instanceof SVGView)) {
      throw new IllegalArgumentException("Invalid view given!");
    }
    this.model = model;
    this.view = view;
  }

  @Override
  public void start() {
    view.setTempo(tempo);
    view.setModel(model);
    try {
      view.drawSVG();
      view.export();
    } catch (IOException e) {
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
    return "svg";
  }
}
