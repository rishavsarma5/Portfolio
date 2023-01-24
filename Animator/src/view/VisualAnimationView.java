package view;

import java.awt.Dimension;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import controller.IController;
import controller.IInteractiveController;
import model.IKeyFrame;
import model.IModel;

/**
 * A public class that represents the realization of visual animations.
 */
public class VisualAnimationView extends JFrame implements IView {
  private AnimatorPanel animPanel;

  /**
   * A public constructor for the visual animation view class.
   * @param cx the preferred size of the canvas.
   * @param cy the preferred size of the canvs.
   */
  public VisualAnimationView(int cx, int cy) {
    super();
    this.setTitle("Visual Animation");
    this.setSize(800, 800);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    animPanel = new AnimatorPanel();
    animPanel.setPreferredSize(new Dimension(cx, cy));
    JScrollPane jScrollPane = new JScrollPane(animPanel);

    this.add(jScrollPane);

    setVisible(true);
    this.pack();
  }

  @Override
  public void drawGUI(List<IKeyFrame> keyFrames) throws IOException {
    animPanel.setKeyFrames(keyFrames);
    animPanel.repaint();
  }

  @Override
  public void drawSVG() throws IOException {
    throw new UnsupportedOperationException("Method not used!");
  }

  @Override
  public String drawText(int tempo) {
    throw new UnsupportedOperationException("Method not used!");
  }

  @Override
  public void export() {
    throw new UnsupportedOperationException("Method not used!");
  }

  @Override
  public void setTempo(int tempo) {
    throw new UnsupportedOperationException("Method not used!");
  }

  @Override
  public void setFileName(String file) {
    throw new UnsupportedOperationException("Method not used!");
  }

  @Override
  public void setModel(IModel model) {
    throw new UnsupportedOperationException("Method not used!");
  }

  @Override
  public String getFinalString() {
    throw new UnsupportedOperationException("Method not used!");
  }

  @Override
  public void setController(IInteractiveController ctrl) {
    throw new UnsupportedOperationException("Method not used!");
  }

  @Override
  public IController getController() {
    throw new UnsupportedOperationException("Method not used!");
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
  public void appendToFile(String s) throws IOException {
    throw new UnsupportedOperationException("Method not used!");
  }

  @Override
  public IModel getModel() {
    throw new UnsupportedOperationException("Method not used!");
  }
}
