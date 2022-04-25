package animationcreator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import io.IChange;
import io.MoveChange;
import model.IShape;
import model.Oval;
import model.Position;

/**
 * This class represents an animation generator that manually creates a formatted string
 * representing an animation.
 */
public class ManualGenerator implements IAnimationCreator {
  private List<IChange> changes;
  List<String> shapesBeingChanged;
  String result;

  /**
   * Constructs a ManualGenerator instance that generates the shapes and IChanges in the animation.
   */
  public ManualGenerator() {
    IShape shape1 = new Oval("disk1", new Position((float) 190, (float) 168),
            new Color((float) 0.4987596, (float) 0.72675294, (float) 0.96805584), (float) 18,
            (float) 20);
    IShape shape2 = new Oval("disk2", new Position((float) 178.75, (float) 186),
            new Color((float) 0.40170985, (float) 0.6379354, (float) 0.19164959), (float) 18,
            (float) 42.5);
    IShape shape3 = new Oval("disk3", new Position((float) 167.5, (float) 204.0),
            new Color((float) 0.61867917, (float) 0.80975264, (float) 0.46404484), (float) 18,
            (float) 65);
    IShape shape4 = new Oval("disk4", new Position((float) 156.25, (float) 222.0),
            new Color((float) 0.98098457, (float) 0.4923268, (float) 0.8237369), (float) 18,
            (float) 87.5);
    IShape shape5 = new Oval("disk5", new Position((float) 145, (float) 240),
            new Color((float) 0.2233703, (float) 0.84182423, (float) 0.7612209), (float) 18,
            (float) 110);
    shapesBeingChanged = new ArrayList<>();
    List<IShape> shapes = new ArrayList<>();
    shapes.add(shape1);
    shapes.add(shape2);
    shapes.add(shape3);
    shapes.add(shape4);
    shapes.add(shape5);
    List<IShape> ovals = shapes;
    int ticks = 1166;
    List<IChange> newChanges =  new ArrayList<>();
    for (int i = 1; i < 51; i ++) {
      for (int j = 0; j < 5; j ++) {
        int a = i - 1;
        IChange newChange = new MoveChange(i,i + 1
                ,ovals.get(j).getPosition().getX() + a,
                ovals.get(j).getPosition().getY() + a
                ,ovals.get(j).getPosition().getX() + a + 1,
                ovals.get(j).getPosition().getY() + a + 1);
        newChanges.add(newChange);
        shapesBeingChanged.add(ovals.get(j).getID());
      }
    }
    this.changes = newChanges;

    this.result = "";
  }

  @Override
  public void move(int numberOfDisks, int startRod, int endRod) {
    throw new UnsupportedOperationException("The operation is invalid");
  }

  @Override
  public void addMove(int start, int end) {
    throw new UnsupportedOperationException("The operation is invalid");
  }

  @Override
  public String toString() {
    this.result = "canvas " + Integer.toString(400)
            + " " + Integer.toString(300);
    result = result + "\noval name disk1 center-x 190.0 center-y " +
            "168.0 x-radius 20.0 y-radius 18 color 0.49875963 0.72675294 0.96805584 " +
            "from 1 to 1166\n" +
            "oval name disk2 center-x 178.75 center-y 186.0 x-radius " +
            "42.5 y-radius 18 color 0.40170985 0.6379354 0.19164959 from 1 to 1166\n" +
            "oval name disk3 center-x 167.5 center-y 204.0 x-radius " +
            "65.0 y-radius 18 color 0.61867917 0.80975264 0.46404484 from 1 to 1166\n" +
            "oval name disk4 center-x 156.25 center-y 222.0 x-radius " +
            "87.5 y-radius 18 color 0.98098457 0.4923268 0.8237369 from 1 to 1166\n" +
            "oval name disk5 center-x 145.0 center-y 240.0 x-radius " +
            "110.0 y-radius 18 color 0.2233703 0.84182423 0.7612209 from 1 to 1166\n";

    for (int i = 0; i < 250; i ++) {
      result = result + "move name " + shapesBeingChanged.get(i) + " moveto " +
              changes.get(i).getStartX() + " " + changes.get(i).getStartY() + " " +
              changes.get(i).getEndX() + " " + changes.get(i).getEndY() + " from " +
              changes.get(i).getStartTick() + " to " + changes.get(i).getEndTick() + "\n";
    }
    this.result = this.result.substring(0, result.length() - 1);
    return result;
  }
}
