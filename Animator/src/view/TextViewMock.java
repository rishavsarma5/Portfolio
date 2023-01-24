package view;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import controller.IController;
import controller.IInteractiveController;
import model.IKeyFrame;
import model.IModel;
import model.IMotion;

/**
 * The mock class for text view, used for testing.
 */
public class TextViewMock implements IView {
  private String log;
  private BufferedWriter writer;
  private String fileName;
  private IModel model;
  private String output;

  /**
   * A default constructor for the text view mock class.
   */
  public TextViewMock() {
    log = "";
    fileName = "";
    output = "out";
  }

  @Override
  public String toString() {
    throw new UnsupportedOperationException("Method not used!");
  }

  @Override
  public void drawGUI(List<IKeyFrame> keyFrames) throws IOException {
    throw new UnsupportedOperationException("Not used!");
  }

  @Override
  public void drawSVG() throws IOException {
    throw new UnsupportedOperationException("Not used!");
  }

  @Override
  public String drawText(int tempo) {
    log = "";
    log += "Created canvas at " + model.getBoundWidth() + " " + model.getBoundHeight() + ".\n";
    for (int i = 0; i < model.getAllShapes().size(); i++) {
      List<IMotion> motions = this.model.getAllMotions(model.getAllShapes().get(i).getID());
      log += "Created shape "
              + model.getAllShapes().get(i).getID() + " "
              + model.getAllShapes().get(i).getShapeType().toString() + "\n";
      for (int j = 0; j < motions.size(); j++) {
        log += motions.get(j).createTextViewMockString(tempo) + "\n";
      }
      log = log + "\n\n";
    }
    log = log.substring(0, log.length() - 3);
    return log;
  }

  @Override
  public void appendToFile(String s) {
    if (output.equals("out")) {
      System.out.append(s);
    }
    else {
      try {
        writer = new BufferedWriter(new FileWriter(fileName));
        writer.append(s);
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void export() {
    if (!output.equals("out")) {
      try {
        writer.close();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void setTempo(int tempo) {
    throw new UnsupportedOperationException("Method not used!");
  }

  @Override
  public void setFileName(String file) {
    this.fileName = file;
  }

  @Override
  public void setLoopInfo(int maxTick) {
    throw new UnsupportedOperationException("Method not used!");
  }

  @Override
  public boolean getLoopState() {
    throw new UnsupportedOperationException("Method not used!");
  }

  @Override
  public int getMaxTick() {
    throw new UnsupportedOperationException("Method not used!");
  }

  @Override
  public IModel getModel() {
    return model;
  }

  @Override
  public void setModel(IModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Invalid input!");
    }
    this.model = model;
  }

  @Override
  public String getFinalString() {
    return log;
  }

  @Override
  public void setController(IInteractiveController ctrl) {
    throw new UnsupportedOperationException("Method not used!");
  }

  @Override
  public IController getController() {
    throw new UnsupportedOperationException("Method not used!");
  }
}
