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
import model.IShape;

/**
 * An SVG view class that is used to generate the SVG representation.
 */
public class SVGView implements IView {
  private int tempo;
  private boolean loop;
  private String output;
  private int maxTick;
  private IModel model;
  private String outputFile;

  /**
   * A default constructor for the SVG view.
   */
  public SVGView() {
    this.tempo = 1;
    this.loop = false;
    this.output = "";
    this.maxTick = 0;
    this.outputFile = "out.svg";
  }

  @Override
  public void drawGUI(List<IKeyFrame> keyFrames) throws IOException {
    throw new UnsupportedOperationException("Method not used!");
  }

  @Override
  public void drawSVG() throws IOException {
    output = "";
    output += "<svg width=\"" + 1200 + "\" height=\"" + 1200
            + "\" version=\"1.1\" " + "xmlns=\"http://www.w3.org/2000/svg\">\n";


    for (IShape s : model.getAllShapes()) {
      String forShape = "";
      forShape += s.createSVGString(tempo, loop) + "\n";
      for (IMotion m : model.getAllMotions(s.getID())) {
        forShape += m.createSVGString(tempo, loop) + "\n";
      }


      if (s.getShapeType().toString().equals("ellipse")) {
        forShape += "</ellipse>";
      }
      else if (s.getShapeType().toString().equals("rectangle")) {
        forShape += "</rect>";
      }
      output += forShape + "\n\n";
    }

    output += "</svg>";
  }

  @Override
  public String drawText(int tempo) {
    throw new UnsupportedOperationException("Method not used!");
  }

  @Override
  public void export() {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
      writer.write(output);
      writer.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  @Override
  public void setFileName(String file) {
    this.outputFile = file;
  }

  @Override
  public void setLoopInfo(int maxTick) {
    if (maxTick < 0) {
      throw new IllegalArgumentException("Invalid input!");
    }
    loop = true;
    this.maxTick = maxTick;
  }

  @Override
  public boolean getLoopState() {
    return loop;
  }

  @Override
  public int getMaxTick() {
    return maxTick;
  }

  @Override
  public void appendToFile(String s) throws IOException {
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
    return output;
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
