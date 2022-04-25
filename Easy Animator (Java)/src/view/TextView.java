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
 * This class represents the view in text form.
 */
public class TextView implements IView {
  private String resultString;
  private BufferedWriter writer;
  private String fileName;
  private IModel model;

  public TextView() {
    resultString = "";
    fileName = "";
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
    resultString = "";
    resultString += "canvas " + model.getBoundWidth() + " " + model.getBoundHeight() + "\n";
    for (int i = 0; i < model.getAllShapes().size(); i++) {
      List<IMotion> motions = this.model.getAllMotions(model.getAllShapes().get(i).getID());
      resultString += "shape "
              + model.getAllShapes().get(i).getID() + " "
              + model.getAllShapes().get(i).getShapeType().toString() + "\n";
      for (int j = 0; j < motions.size(); j++) {
        resultString += motions.get(j).createString(tempo) + "\n";
      }
      resultString = resultString + "\n\n";
    }
    resultString = resultString.substring(0, resultString.length() - 3);
    return resultString;
  }

  @Override
  public void appendToFile(String s) {
    if (fileName.equals("out")) {
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
    if (!fileName.equals("out")) {
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
    return resultString;
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
