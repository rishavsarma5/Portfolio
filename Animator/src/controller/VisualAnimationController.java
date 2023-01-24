package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import model.IKeyFrame;
import model.IModel;
import view.IView;
import view.VisualAnimationView;

/**
 * This class represents a VisualAnimationController which handles rendering the Animation to the
 * screen using the given View and the Model created from the Main class. The start method calls
 * VisualAnimationView which uses JFrame to render the information from the Model to the screen on
 * a JPanel with scroll bars. The Controller implements a Swing Timer to play the animation for the
 * specific amount of time and has a tick rate (tempo) that can be set to play the animation at
 * different speeds.
 */
public class VisualAnimationController implements IController {
  private IModel model;
  private IView view;
  private int currTick;
  private int tempo = 1;
  private Timer timer;

  /**
   * Creates a VisualAnimationController instance with the given IModel and IView.
   * @param model the given Model
   * @param view the given View
   * @throws IllegalArgumentException if inputs are null or view is not a VisualAnimationView
   */
  public VisualAnimationController(IModel model, IView view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Inputs are invalid!");
    }

    if (!(view instanceof VisualAnimationView)) {
      throw new IllegalArgumentException("Invalid view given!");
    }
    this.model = model;
    this.view = view;
  }

  @Override
  public void start() {
    timer = new Timer(1000 / tempo, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        if (currTick > model.getMaxTick()) {
          timer.stop();
        }
        List<IKeyFrame> framesForShapesPerTick = new ArrayList<>();

        framesForShapesPerTick = model.getKeyFramesForEachShapeAtGivenTick(currTick);

        try {
          view.drawGUI(framesForShapesPerTick);
        }
        catch (IOException e) {
          e.printStackTrace();
        }
        currTick++;
      }
    });
    timer.start();
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
    return "visual";
  }
}
