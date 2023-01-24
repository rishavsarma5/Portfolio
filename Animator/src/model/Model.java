package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * An implementation for the animation model.
 */
public class Model implements IModel {
  private int width;
  private int height;
  private int maxTick;
  private List<IShape> shapes;
  private Map<String, List<IMotion>> motionsOfShape;

  /**
   * A concrete constructor of the animation.
   *
   * @param shapes         the shapes involved in the animation
   * @param width          the width of the canvas of the animation window
   * @param height         the height of the canvas of the animation window
   * @param motionsOfShape all the motions involved in the animation
   *                       this is in the form of a hashmap
   *                       you can get the list of motions of a shape by passing in the key
   */
  public Model(List<IShape> shapes, Map<String, List<IMotion>> motionsOfShape, int width,
               int height, int maxTick) {
    if (width < 0 || height < 0 || maxTick <= 0) {
      throw new IllegalArgumentException("The width and height is less than 0");
    }
    this.width = width;
    this.height = height;
    this.maxTick = maxTick;

    if (shapes == null || motionsOfShape == null) {
      throw new IllegalArgumentException("Inputs are invalid!");
    }
    if (checkForNonOverLap(shapes, motionsOfShape)) {
      throw new IllegalArgumentException("The input has overlaps/intervals");
    }
    this.shapes = shapes;
    this.motionsOfShape = motionsOfShape;
  }

  /**
   * A short constructor for the animation.
   */
  public Model() {
    this.shapes = new ArrayList<>();
    this.motionsOfShape = new HashMap<>();
    this.width = width;
    this.height = height;
    this.maxTick = maxTick;
  }

  @Override
  public List<IShape> getAllShapes() {
    return this.shapes;
  }

  @Override
  public List<IMotion> getAllMotions(String s) {
    return this.motionsOfShape.get(s);
  }

  @Override
  public List<IKeyFrame> getAllKeyFrames(String s) {
    List<IKeyFrame> result = new ArrayList<>();
    List<IMotion> motions = this.motionsOfShape.get(s);
    for (int i = 0; i < motions.size(); i++) {
      result.addAll(motions.get(i).getKeyFrames());
    }
    return result;
  }

  @Override
  public IKeyFrame getKeyFrameAt(String s, int tick) {
    ArrayList<String> names = new ArrayList<>();
    for (IShape is : shapes) {
      names.add(is.getID());
    }
    if (s == null || !names.contains(s)) {
      throw new IllegalArgumentException("The shape is not in the given list");
    } else {
      List<IMotion> motions = this.motionsOfShape.get(s);
      if (motions.size() == 0) {
        throw new IllegalArgumentException("The shape does not have any motions");
      }

      List<IKeyFrame> keyFrames = this.getAllKeyFrames(s);
      for (int i = 0; i < keyFrames.size(); i++) {
        if (keyFrames.get(i).getTick() == tick) {
          return keyFrames.get(i);
        }
      }
      throw new IllegalArgumentException("The tick does not exist in the motions of the shape s");
    }
  }

  @Override
  public void addShape(IShape s) {
    if (s == null) {
      throw new IllegalArgumentException("The shape is null");
    }
    if (!shapes.contains(s)) {
      List<IMotion> motions = new ArrayList<>();
      motionsOfShape.put(s.getID(), motions);
      shapes.add(s);
    } else {
      throw new IllegalArgumentException("The adding is invalid");
    }
  }

  @Override
  public int getBoundWidth() {
    return this.width;
  }

  @Override
  public int getBoundHeight() {
    return this.height;
  }

  @Override
  public void setMaxTick(int t) {
    this.maxTick = t;
  }

  @Override
  public int getMaxTick() {
    return maxTick;
  }

  @Override
  public void addMotion(IMotion motion, IShape shape) {
    if (!shapes.contains(shape)) {
      shapes.add(shape);
      motionsOfShape.put(shape.getID(),new ArrayList<>());
    }

    else {
      this.motionsOfShape.get(shape.getID()).add(motion);
    }
  }

  @Override
  public void removeShape(IShape s) {
    if (shapes.contains(s)) {
      motionsOfShape.remove(s.getID());
      shapes.remove(s);
    } else {
      throw new IllegalArgumentException("The move is invalid");
    }
  }

  @Override
  public List<IKeyFrame> getKeyFramesForEachShapeAtGivenTick(int currTick) {
    List<IKeyFrame> keyFrameList = new ArrayList<>();
    for (int i = 0; i < shapes.size(); i++) {
      for (IMotion m : motionsOfShape.get(shapes.get(i).getID())) {
        if (hasKeyFrameAt(m, currTick)) {
          keyFrameList.add(m.getKeyFrameAt(currTick));
        }
      }
    }
    return keyFrameList;
  }

  private boolean hasKeyFrameAt(IMotion motion, int currTick) {
    for (IKeyFrame kf : motion.getKeyFrames()) {
      if (kf.getTick() == currTick) {
        return true;
      }
    }
    return false;
  }


  @Override
  public void setBounds(int x, int y) {

    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("The input is negative");
    } else {
      this.width = x;
      this.height = y;
    }
  }

  @Override
  public String toString() {
    if (this.shapes.size() == 0) {
      return "";
    }

    String resultString = "";
    for (int i = 0; i < shapes.size(); i++) {
      List<IMotion> motions =
              this.motionsOfShape.get(shapes.get(i).getID());

      resultString += "shape "
              + shapes.get(i).getID() + " "
              + shapes.get(i).getShapeType().toString() + "\n";
      for (int j = 0; j < motions.size(); j++) {
        resultString = resultString + motions.get(j).toString() + "\n";
      }
      resultString = resultString + "\n\n";
    }
    resultString = resultString.substring(0, resultString.length() - 3);

    return resultString;
  }

  /**
   * Check if there are any overalps between motions.
   *
   * @return a boolean representing if there are intervals between motions
   */
  private boolean checkForNonOverLap(List<IShape> shapesAll, Map<String
          , List<IMotion>> motionsOfS) {
    if (shapesAll.size() == 0) {
      return false;
    }
    for (IShape s : shapesAll) {
      List<IMotion> motions = motionsOfS.get(s.getID());
      if (motions.size() == 0) {
        return false;
      } else {
        int endTick = motions.get(0).getStartTick();
        for (int i = 0; i < motions.size(); i++) {
          if (endTick != motions.get(i).getStartTick()) {
            return true;
          } else {
            endTick = motions.get(i).getEndTick();
          }
        }
      }
    }
    return false;
  }
}



