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
 * A mock class for the InteractiveVisualController that is used for testing.
 */
public class InteractiveVisualControllerMock implements IInteractiveController {
  private IModel model;
  private IView view;
  private int currTick;
  private int tempo = 1;
  private Timer timer;
  private boolean isLooping;
  private boolean isPaused;
  private String log;

  /**
   * Creates an InteractiveVisualControllerMock instance used for testing.
   * @param model the given Model
   * @param view the given View
   * @throws IllegalArgumentException if the inputs are null or the view is not an
   *         InteractiveVisualView
   */
  public InteractiveVisualControllerMock(IModel model, IView view) {
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
    log = "";
  }

  @Override
  public void start() {
    log += "The timer was created!";
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
    log += "The timer was set to the given one!";
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
    log += "The tempo was set to the given tempo " + tempo + "!";
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
    log += "The timer was started!";
  }

  @Override
  public void resume() {
    log += "The animation was un-paused and will continue to run.";
    isPaused = false;
  }

  @Override
  public void restart() {
    log += "The timer was reset to have a current tick of 1!";
    currTick = 0;
  }

  @Override
  public void pause() {
    log += "The animation was paused and will not continue to run until resumed!";
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
    log += "The loop value has now been set to: " + isLooping + "!";
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
    return log;
  }
}
