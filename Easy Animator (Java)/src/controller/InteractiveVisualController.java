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
import view.InteractiveVisualView;

/**
 * This class represents a InteractiveVisualController which handles rendering the Animation to the
 * screen using the given View and the Model created from the Main class. The start method calls
 * InteractiveVisualView which uses JFrame to render the animation to the screen along with
 * interactive features like start, pause, resume, and restart buttons that can perform the given
 * action on the animation being rendered. There is also a checkbox that can be checked to enable
 * looping of the animation, as well as a text field and change speed button that can change the
 * tick rate (tempo) of the animation dynamically, so it runs at different speeds based on the
 * valid text-input provided.
 */
public class InteractiveVisualController implements IInteractiveController {
  private IModel model;
  private IView view;
  private int currTick;
  private int tempo = 1;
  private Timer timer;
  private boolean isLooping;
  private boolean isPaused;

  /**
   * Creates an InteractiveVisualController instance that takes in a given IModel and IView.
   * @param model the given Model
   * @param view the given View
   * @throws IllegalArgumentException if the inputs are null or the view is not an
   *         InteractiveVisualView
   */
  public InteractiveVisualController(IModel model, IView view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Inputs are invalid!");
    }

    if (!(view instanceof InteractiveVisualView)) {
      throw new IllegalArgumentException("Invalid view given!");
    }
    this.model = model;
    this.view = view;
    isLooping = false;
    isPaused = false;
    view.setController(this);
  }

  @Override
  public void start() {
    createTimer(tempo);
  }

  /**
   * Creates the timer of the animation that has calls the actionPerform to render the shapes each
   * tick based on the given tick rate t.
   * @param t the tick rate as an int
   */
  private void createTimer(int t) {
    timer = new Timer(1000 / t, new ActionListener() {
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

        if (!isPaused && !isLooping) {
          currTick++;
        }

        if (!isPaused && isLooping) {
          if (currTick == model.getMaxTick()) {
            currTick = 0;
          }
          else {
            currTick++;
          }
        }
      }
    });
    setTimer(timer);
  }

  /**
   * Sets the timer to the given timer to be mutated directly.
   * @param t the given Timer t
   * @throws IllegalArgumentException if the input is null
   */
  private void setTimer(Timer t) {
    if (t == null) {
      throw new IllegalArgumentException("Timer input is null!");
    }
    this.timer = t;
  }

  @Override
  public void setTempo(int tempo) {
    if (tempo <= 0) {
      throw new IllegalArgumentException("Tempo can't be <= 0!");
    }
    this.tempo = tempo;

    if (timer != null) {
      timer.setDelay(1000 / tempo);
    }
  }

  @Override
  public int getTempo() {
    return tempo;
  }

  @Override
  public String getType() {
    return "interactive";
  }

  @Override
  public void startAnimation() {
    timer.start();
  }

  @Override
  public void resume() {
    isPaused = false;
  }

  @Override
  public void restart() {
    currTick = 0;
  }

  @Override
  public void pause() {
    isPaused = true;
  }

  @Override
  public void setLooping() {
    if (isLooping) {
      isLooping = false;
    }
    else {
      isLooping = true;
    }
  }

  @Override
  public boolean getLoopingState() {
    return isLooping;
  }

  @Override
  public boolean getPausedState() {
    return isPaused;
  }

  @Override
  public String getLog() {
    throw new UnsupportedOperationException("Method not used!");
  }
}
