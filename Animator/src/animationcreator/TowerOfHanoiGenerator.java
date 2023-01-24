package animationcreator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import io.IChange;
import io.MoveChange;
import model.IShape;
import model.Position;
import model.Rectangle;

/**
 * This class represents an animation generator that programmatically creates a 5-shape tower of
 * hanoi animation using the IChange interface and generated shape's motions.
 */
public class TowerOfHanoiGenerator implements IAnimationCreator {
  private List<IShape> rectangles;
  private static int numberOfDisks;
  private List<IChange> changes;
  private static int canvasWidth;
  private static int canvasHeight;
  private static int endTick;
  private String result;
  private int tickStartingMoving;
  private HashMap<Integer, Stack<String>> shapesOnEachRod;
  private HashMap<String, Position> currentPositions;
  private List<String> shapesBeenMoved;

  /**
   * Creates a TowerOfHanoiGenerator instance that generates the shapes and IChanges in the
   * animation.
   */
  public TowerOfHanoiGenerator() {
    numberOfDisks = 5;
    List<IShape> shapes = new ArrayList<>();
    IShape shape1 = new Rectangle("disk1", new Position((float) 190, (float) 168),
            new Color((float) 0.4987596, (float) 0.72675294, (float) 0.96805584), (float) 18,
            (float) 20);
    IShape shape2 = new Rectangle("disk2", new Position((float) 178.75, (float) 186),
            new Color((float) 0.40170985, (float) 0.6379354, (float) 0.19164959), (float) 18,
            (float) 42.5);
    IShape shape3 = new Rectangle("disk3", new Position((float) 167.5, (float) 204.0),
            new Color((float) 0.61867917, (float) 0.80975264, (float) 0.46404484), (float) 18,
            (float) 65);
    IShape shape4 = new Rectangle("disk4", new Position((float) 156.25, (float) 222.0),
            new Color((float) 0.98098457, (float) 0.4923268, (float) 0.8237369), (float) 18,
            (float) 87.5);
    IShape shape5 = new Rectangle("disk5", new Position((float) 145, (float) 240),
            new Color((float) 0.2233703, (float) 0.84182423, (float) 0.7612209), (float) 18,
            (float) 110);
    shapes.add(shape1);
    shapes.add(shape2);
    shapes.add(shape3);
    shapes.add(shape4);
    shapes.add(shape5);
    rectangles = shapes;
    canvasWidth = 410;
    canvasHeight = 208;
    endTick = 1166;
    this.changes = new ArrayList<>();
    result = "";
    tickStartingMoving = 15;
    Stack<String> rod1 = new Stack<>();
    rod1.push("disk5");
    rod1.push("disk4");
    rod1.push("disk3");
    rod1.push("disk2");
    rod1.push("disk1");
    shapesOnEachRod = new HashMap<>();
    shapesOnEachRod.put(1, rod1);
    Stack<String> rod2 = new Stack<>();
    shapesOnEachRod.put(2, rod2);
    Stack<String> rod3 = new Stack<>();
    shapesOnEachRod.put(3, rod3);
    currentPositions = new HashMap<>();
    currentPositions.put("disk1", new Position((float) 190, (float) 168));
    currentPositions.put("disk2", new Position((float) 178.75, (float) 186));
    currentPositions.put("disk3", new Position((float) 167.5, (float) 204.0));
    currentPositions.put("disk4", new Position((float) 156.25, (float) 222.0));
    currentPositions.put("disk5", new Position((float) 145, (float) 240));
    shapesBeenMoved = new ArrayList<>();
  }

  @Override
  public void move(int numberOfDisks, int startRod, int endRod) {
    if (numberOfDisks == 1) {
      addMove(startRod, endRod);
      return;
    } else {
      int auxRod = 6 - (startRod + endRod);
      move(numberOfDisks - 1, startRod, auxRod);
      addMove(startRod, endRod);
      move(numberOfDisks - 1, auxRod, endRod);
    }

  }

  @Override
  public void addMove(int start, int end) {
    if (this.shapesOnEachRod.get(start).size() == 0) {
      return;
    } else {
      String name = this.shapesOnEachRod.get(start).peek();
      Position currentPosition = this.currentPositions.get(name);
      IChange newChange = new MoveChange(this.tickStartingMoving,
              this.tickStartingMoving + 10, (float) currentPosition.getX(),
              (float) currentPosition.getY(), (float) currentPosition.getX(), (float) 100);
      this.changes.add(newChange);
      this.shapesBeenMoved.add(name);
      this.currentPositions.replace(name, new Position(currentPosition.getX(), (float) 100));
      this.tickStartingMoving += 10;


      Position currentPosition1 = this.currentPositions.get(name);
      IChange newChange1 = new MoveChange(this.tickStartingMoving,
              this.tickStartingMoving + 10, (float) currentPosition1.getX(),
              (float) currentPosition1.getY(), (float) currentPosition1.getX() + 150 *
              (end - start), (float) currentPosition1.getY());
      this.changes.add(newChange1);
      this.shapesBeenMoved.add(name);
      this.tickStartingMoving = this.tickStartingMoving + 10;
      this.currentPositions.replace(name, new Position(
              (float) currentPosition1.getX() + 150 * (end - start),
              (float) currentPosition1.getY()));


      Position currentPosition2 = this.currentPositions.get(name);
      float newY = (float) (240 - shapesOnEachRod.get(end).size() * 18);
      IChange newChange2 = new MoveChange(this.tickStartingMoving,
              this.tickStartingMoving + 10, (float) currentPosition2.getX(),
              (float) currentPosition2.getY(), (float) currentPosition2.getX(), (float) newY);
      this.tickStartingMoving = this.tickStartingMoving + 10;
      this.changes.add(newChange2);
      this.shapesBeenMoved.add(name);
      this.currentPositions.replace(name, new Position((float) currentPosition2.getX(),
              (float) newY));
      String nameOfShape = this.shapesOnEachRod.get(start).pop();
      this.shapesOnEachRod.get(end).push(nameOfShape);
    }
  }

  @Override
  public String toString() {
    this.result = "canvas " + Integer.toString(canvasWidth)
            + " " + Integer.toString(canvasHeight) + "\n";

    for (int i = 0; i < rectangles.size(); i++) {
      result = result + "rectangle name " + rectangles.get(i).getID() +
              " min-x " + Float.toString(rectangles.get(i).getPosition().getX()) + " min-y " +
              Float.toString(rectangles.get(i).getPosition().getY()) +
              " width " + Float.toString(rectangles.get(i).getWidth()) + " height " +
              Float.toString(rectangles.get(i).getHeight()) + " color " +
              Float.toString((float) rectangles.get(i).getColor().getRed() / 255) + " " +
              Float.toString((float) rectangles.get(i).getColor().getGreen() / 255) + " " +
              Float.toString((float) rectangles.get(i).getColor().getBlue() / 255)
              + " from 1 to 1166\n";
    }

    for (int i = 0; i < changes.size(); i++) {
      result = result + "move name " + shapesBeenMoved.get(i) + " moveto " +
              changes.get(i).getStartX() + " " + changes.get(i).getStartY() + " " +
              changes.get(i).getEndX() + " " + changes.get(i).getEndY() + " from " +
              changes.get(i).getStartTick() + " to " + changes.get(i).getEndTick() + "\n";
    }
    this.result = this.result.substring(0, result.length() - 1);
    return this.result;
  }
}
