package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import model.IKeyFrame;
import model.IShape;

/**
 * The panel class that is used to draw all
 * components on board, including shapes.
 */
public class AnimatorPanel extends JPanel {
  List<IKeyFrame> keyFrames;

  public AnimatorPanel() {
    keyFrames = new ArrayList<>();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    super.repaint();
    Graphics2D g2 = (Graphics2D) g;

    for (int i = 0; i < keyFrames.size(); i++) {
      IShape s = keyFrames.get(i).getShape();
      RectangularShape shape = null;

      if (s.getShapeType().toString().equals("ellipse")) {
        shape = new Ellipse2D.Float(s.getPosition().getX(), s.getPosition().getY(), s.getWidth(),
                s.getHeight());
      }

      if (s.getShapeType().toString().equals("rectangle")) {
        shape = new Rectangle2D.Float(s.getPosition().getX(), s.getPosition().getY(),
                s.getWidth(), s.getHeight());
      }
      g.setColor(new Color(
              (s.getColor().getRed()),
              (s.getColor().getGreen()),
              (s.getColor().getBlue())));
      g2.fill(shape);
      g2.draw(shape);
    }
  }

  public void setKeyFrames(List<IKeyFrame> kFs) {
    this.keyFrames = kFs;
  }

}
