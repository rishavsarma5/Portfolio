package io;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import model.IKeyFrame;
import model.IModel;
import model.IMotion;
import model.IShape;
import model.KeyFrame;
import model.Model;
import model.Motion;
import model.Oval;
import model.Position;
import model.Rectangle;
import model.ShapeType;

/**
 * This class describes constructing any animation, shape-by-shape and motion-by-motion. Adapts
 * from the model interface that the AnimationFileReader expects to Model.
 */
public class AnimatorBuilder implements TweenModelBuilder<IModel> {
  int wBound;
  int hBound;
  List<IShape> shapes;
  HashMap<String, Integer> startTicks;
  HashMap<String, Integer> endTicks;
  HashMap<String, List<IChange>> moves;
  HashMap<String, List<IChange>> colorChanges;
  HashMap<String, List<IChange>> scaleChanges;

  /**
   * A public constructor for the animatorBuilder class.
   */
  public AnimatorBuilder() {
    this.wBound = 0;
    this.hBound = 0;
    this.shapes = new ArrayList<>();
    this.startTicks = new HashMap<>();
    this.endTicks = new HashMap<>();
    this.moves = new HashMap<>();
    this.colorChanges = new HashMap<>();
    this.scaleChanges = new HashMap<>();
  }

  /**
   * Another public constructor for the Animator Builder.
   *
   * @param wb the width bound
   * @param hb the height bound
   * @param s  list of shapes
   * @param st list of starting times
   * @param et list of ending times
   * @param mv list of move changes
   * @param cc list of color changes
   * @param sc list of scale changes
   */
  public AnimatorBuilder(int wb, int hb, List<IShape> s,
                         HashMap<String, Integer> st, HashMap<String, Integer> et
          , HashMap<String, List<IChange>> mv, HashMap<String, List<IChange>> cc
          , HashMap<String, List<IChange>> sc) {
    this.wBound = wb;
    this.hBound = hb;
    this.shapes = s;
    this.startTicks = st;
    this.endTicks = et;
    this.moves = mv;
    this.colorChanges = cc;
    this.scaleChanges = sc;
  }

  @Override
  public TweenModelBuilder<IModel> setBounds(int width, int height) {
    this.wBound = width;
    this.hBound = height;
    return this;
  }

  @Override
  public TweenModelBuilder<IModel> addOval(String name, float cx, float cy, float xRadius,
                                           float yRadius, float red, float green, float blue,
                                           int startOfLife, int endOfLife) {
    List<String> names = new ArrayList<>();
    for (IShape s : shapes) {
      names.add(s.getID());
    }

    if (names.contains(name)) {
      throw new IllegalArgumentException("The shape is already contained in the shapes");
    }

    Position newPosition = new Position(cx, cy);
    Color newColor = new Color(red, green, blue);
    IShape newOval = new Oval(name, newPosition, newColor, yRadius, xRadius);
    shapes.add(newOval);
    moves.put(name, new ArrayList<>());
    colorChanges.put(name, new ArrayList<>());
    scaleChanges.put(name, new ArrayList<>());
    startTicks.put(name, startOfLife);
    endTicks.put(name, endOfLife);
    return this;
  }

  @Override
  public TweenModelBuilder<IModel> addRectangle(String name, float lx, float ly, float width,
                                                float height, float red, float green, float blue,
                                                int startOfLife, int endOfLife) {
    List<String> names = new ArrayList<>();
    for (IShape s : shapes) {
      names.add(s.getID());
    }

    if (names.contains(name)) {
      throw new IllegalArgumentException("The shape is already contained in the shapes");
    }

    Position newPosition = new Position(lx, ly);
    int int_red = Math.round(red * 255);
    int int_green = Math.round(green * 255);
    int int_blue = Math.round(blue * 255);
    Color newColor = new Color(int_red, int_green, int_blue);
    IShape newRectangle = new Rectangle(name, newPosition, newColor, height, width);
    shapes.add(newRectangle);
    moves.put(name, new ArrayList<>());
    colorChanges.put(name, new ArrayList<>());
    scaleChanges.put(name, new ArrayList<>());
    startTicks.put(name, startOfLife);
    endTicks.put(name, endOfLife);
    return this;
  }

  @Override
  public TweenModelBuilder<IModel> addMove(String name, float moveFromX, float moveFromY,
                                           float moveToX, float moveToY, int startTime,
                                           int endTime) {
    List<String> names = new ArrayList<>();
    for (IShape s : shapes) {
      names.add(s.getID());
    }

    if (!names.contains(name)) {
      throw new IllegalArgumentException("the shape does not exist in the list");
    }

    List<IChange> newMoveChange = this.moves.get(name);
    newMoveChange.sort(Comparator.comparingInt(IChange::getStartTick));

    if (newMoveChange.size() == 0) {
      this.moves.get(name).add(new MoveChange(startTime, endTime, moveFromX, moveFromY, moveToX,
              moveToY));
    } else {
      for (int i = 0; i < newMoveChange.size(); i++) {
        if ((newMoveChange.get(i).getStartTick() < startTime
                && newMoveChange.get(0).getEndTick() > endTime)
                || (startTime < newMoveChange.get(i).getStartTick() &&
                endTime > newMoveChange.get(0).getEndTick())) {
          throw new IllegalArgumentException("The adding will create overlaps");
        }
      }

      this.moves.get(name).add(new MoveChange(startTime, endTime, moveFromX, moveFromY, moveToX,
              moveToY));

    }
    return this;
  }

  @Override
  public TweenModelBuilder<IModel> addColorChange(String name, float oldR, float oldG,
                                                  float oldB, float newR, float newG, float newB,
                                                  int startTime, int endTime) {

    List<String> names = new ArrayList<>();
    for (IShape s : shapes) {
      names.add(s.getID());
    }

    if (!names.contains(name)) {
      throw new IllegalArgumentException("the shape does not exist in the list");
    }

    List<IChange> newColorChanges = this.colorChanges.get(name);

    int int_oldR = Math.round(255 * oldR);
    int int_oldG = Math.round(255 * oldG);
    int int_oldB = Math.round(255 * oldB);
    int int_newR = Math.round(255 * newR);
    int int_newG = Math.round(255 * newG);
    int int_newB = Math.round(255 * newB);
    if (newColorChanges.size() == 0) {
      this.colorChanges.get(name).add(new ColorChange(startTime, endTime, int_oldR, int_oldG,
              int_oldB, int_newR, int_newG, int_newB));
    } else {
      for (int i = 0; i < newColorChanges.size(); i++) {
        if ((newColorChanges.get(i).getStartTick() < startTime
                && newColorChanges.get(0).getEndTick() > endTime)
                || (startTime < newColorChanges.get(i).getStartTick() &&
                endTime > newColorChanges.get(0).getEndTick())) {
          throw new IllegalArgumentException("The adding will create overlaps");
        }
      }
      this.colorChanges.get(name)
              .add(new ColorChange(startTime, endTime, int_oldR, int_oldG,
                      int_oldB, int_newR, int_newG, int_newB));
    }
    return this;
  }

  @Override
  public TweenModelBuilder<IModel> addScaleToChange(String name, float fromSx, float fromSy,
                                                    float toSx, float toSy, int startTime,
                                                    int endTime) {
    List<String> names = new ArrayList<>();
    for (IShape s : shapes) {
      names.add(s.getID());
    }

    if (!names.contains(name)) {
      throw new IllegalArgumentException("the shape does not exist in the list");
    }

    List<IChange> newScaleChanges = this.scaleChanges.get(name);
    if (newScaleChanges.size() == 0) {
      this.scaleChanges.get(name).add(new ScaleChange(startTime, endTime, fromSx, fromSy, toSx,
              toSy));
    } else {
      for (int i = 0; i < newScaleChanges.size(); i++) {
        if ((newScaleChanges.get(i).getStartTick() < startTime
                && newScaleChanges.get(0).getEndTick() > endTime)
                || (startTime < newScaleChanges.get(i).getStartTick() &&
                endTime > newScaleChanges.get(0).getEndTick())) {
          throw new IllegalArgumentException("The adding will create overlaps");
        }
      }
      this.scaleChanges.get(name).add(new ScaleChange(startTime, endTime, fromSx, fromSy, toSx,
              toSy));
    }
    return this;
  }

  /**
   * Returns the largest tick of the end tick list.
   *
   * @return an int representing the last tick of life
   */
  private int findLargestTick() {
    int largestTick = 0;
    for (int i : endTicks.values()) {
      if (i > largestTick) {
        largestTick = i;
      }
    }
    return largestTick;
  }

  @Override
  public IModel build() {
    IModel newModel = new Model();
    newModel.setBounds(wBound, hBound);
    newModel.setMaxTick(findLargestTick());

    for (IShape s : shapes) {
      newModel.addShape(s);
      List<IChange> moveChanges = moves.get(s.getID());
      moveChanges.sort(Comparator.comparingInt(IChange::getStartTick));
      List<IChange> cChanges = colorChanges.get(s.getID());
      cChanges.sort(Comparator.comparingInt(IChange::getStartTick));
      List<IChange> sChanges = scaleChanges.get(s.getID());
      sChanges.sort(Comparator.comparingInt(IChange::getStartTick));

      int st = startTicks.get(s.getID());
      int et = endTicks.get(s.getID());

      if (checkFor("Position", moveChanges, s) || checkFor("Color", cChanges, s)
              || checkFor("Scale", sChanges, s)) {
        throw new IllegalArgumentException("There are jumps between changes");
      }

      checkForTicksInList(s, moveChanges, st, et, "move");
      checkForTicksInList(s, cChanges, st, et, "color");
      checkForTicksInList(s, sChanges, st, et, "scale");

      HashSet<Integer> startTimesAndEndTimes = new HashSet<>();
      for (int i = 0; i < moveChanges.size(); i++) {
        startTimesAndEndTimes.add(moveChanges.get(i).getStartTick());
        startTimesAndEndTimes.add(moveChanges.get(i).getEndTick());
      }

      for (int i = 0; i < cChanges.size(); i++) {
        startTimesAndEndTimes.add(cChanges.get(i).getStartTick());
        startTimesAndEndTimes.add(cChanges.get(i).getEndTick());
      }

      for (int i = 0; i < sChanges.size(); i++) {
        startTimesAndEndTimes.add(sChanges.get(i).getStartTick());
        startTimesAndEndTimes.add(sChanges.get(i).getEndTick());
      }

      List<Integer> startEnd = new ArrayList<Integer>(startTimesAndEndTimes);
      Collections.sort(startEnd);
      moveChanges.sort(Comparator.comparingInt(IChange::getStartTick));
      cChanges.sort(Comparator.comparingInt(IChange::getStartTick));
      sChanges.sort(Comparator.comparingInt(IChange::getStartTick));

      for (int i = 0; i < startEnd.size() - 1; i++) {
        int start = startEnd.get(i);
        int end = startEnd.get(i + 1);
        int indexMoveStart = moveChanges.get(0).getTheNumberInTheList(moveChanges, start);
        int indexMoveEnd = moveChanges.get(0).getTheNumberInTheList(moveChanges, end);

        int indexColorStart = cChanges.get(0).getTheNumberInTheList(cChanges, start);
        int indexColorEnd = cChanges.get(0).getTheNumberInTheList(cChanges, end);

        int indexScaleStart = sChanges.get(0).getTheNumberInTheList(sChanges, start);
        int indexScaleEnd = sChanges.get(0).getTheNumberInTheList(sChanges, end);
        float startX =
                moveChanges.get(0).perFormInterpolation(moveChanges.get(indexMoveStart), start,
                        "X");
        float startY =
                moveChanges.get(0).perFormInterpolation(moveChanges.get(indexMoveStart), start,
                        "Y");
        float endX =
                moveChanges.get(0).perFormInterpolation(moveChanges.get(indexMoveEnd), end,
                        "X");
        float endY =
                moveChanges.get(0).perFormInterpolation(moveChanges.get(indexMoveEnd), end,
                        "Y");

        float startR =
                cChanges.get(0).perFormInterpolation(cChanges.get(indexColorStart), start,
                        "R");
        float startG =
                cChanges.get(0).perFormInterpolation(cChanges.get(indexColorStart), start,
                        "G");
        float startB =
                cChanges.get(0).perFormInterpolation(cChanges.get(indexColorStart), start,
                        "B");
        float endR =
                cChanges.get(0).perFormInterpolation(cChanges.get(indexColorEnd), end, "R");
        float endG =
                cChanges.get(0).perFormInterpolation(cChanges.get(indexColorEnd), end, "G");
        float endB =
                cChanges.get(0).perFormInterpolation(cChanges.get(indexColorEnd), end, "B");

        float startW =
                sChanges.get(0).perFormInterpolation(sChanges.get(indexScaleStart), start,
                        "W");
        float startH =
                sChanges.get(0).perFormInterpolation(sChanges.get(indexScaleStart), start,
                        "H");

        float endW =
                sChanges.get(0).perFormInterpolation(sChanges.get(indexScaleEnd), end, "W");
        float endH =
                sChanges.get(0).perFormInterpolation(sChanges.get(indexScaleEnd), end, "H");


        List<IKeyFrame> kf = generateInterpolationKeyFrames(s.getID(), s.getShapeType(),
                startX, startY, endX, endY, startR, endR,
                startG, endG, startB, endB, startW,
                endW, startH, endH, start, end);
        IMotion newMotion = new Motion(s.getID(), start, end, kf);

        newModel.addMotion(newMotion, s);
      }
    }
    return newModel;
  }

  /**
   * Check if there are any gaps or overlaps in the given list of changes.
   *
   * @param changes   the list of changes we want to check for
   * @param startTick the startTick of the changes
   * @param endTick   the endTick of the changes
   * @return
   */
  private static void checkForTicksInList(IShape s, List<IChange> changes,
                                          int startTick, int endTick, String type) {
    if (type.equals("move")) {

      if (changes.size() == 0) {
        IChange newMove = new MoveChange(startTick, endTick, s.getPosition().getX()
                , s.getPosition().getY(), s.getPosition().getX(), s.getPosition().getY());
        changes.add(newMove);
      } else {
        List<IChange> temp_list = new ArrayList<>();

        int lastEnd = startTick;
        float endX = s.getPosition().getX();
        float endY = s.getPosition().getY();

        for (int i = 0; i < changes.size(); i++) {
          if (changes.get(i).getStartTick() != lastEnd) {
            IChange newMove =
                    new MoveChange(lastEnd, changes.get(i).getStartTick(),
                            endX, endY, changes.get(i).getStartX(), changes.get(i).getStartY());
            temp_list.add(newMove);
            endX = changes.get(i).getEndX();
            endY = changes.get(i).getEndY();
            lastEnd = changes.get(i).getEndTick();
          }
        }
        if (lastEnd != endTick) {
          IChange newMove =
                  new MoveChange(lastEnd, endTick,
                          endX, endY, endX, endY);
          temp_list.add(newMove);

        }
        changes.addAll(temp_list);
      }
    }


    if (type.equals("color")) {

      if (changes.size() == 0) {
        IChange newMove = new ColorChange(startTick, endTick, s.getColor().getRed()
                , s.getColor().getGreen(), s.getColor().getBlue(), s.getColor().getRed()
                , s.getColor().getGreen(), s.getColor().getBlue());
        changes.add(newMove);
      } else {
        List<IChange> temp_list = new ArrayList<>();

        int lastEnd = startTick;
        int endR = s.getColor().getRed();
        int endG = s.getColor().getGreen();
        int endB = s.getColor().getBlue();

        for (int i = 0; i < changes.size(); i++) {
          if (changes.get(i).getStartTick() != lastEnd) {
            IChange newColor =
                    new ColorChange(lastEnd, changes.get(i).getStartTick(),
                            endR, endG, endB, changes.get(i).getStartR(),
                            changes.get(i).getStartG(),
                            changes.get(i).getStartB());
            temp_list.add(newColor);
            endR = changes.get(i).getEndR();
            endG = changes.get(i).getEndG();
            endB = changes.get(i).getEndB();
            lastEnd = changes.get(i).getEndTick();
          } else {
            endR = changes.get(i).getEndR();
            endG = changes.get(i).getEndG();
            endB = changes.get(i).getEndB();
            lastEnd = changes.get(i).getEndTick();
          }
        }
        if (lastEnd != endTick) {
          IChange newColor =
                  new ColorChange(lastEnd, endTick,
                          endR, endG, endB, endR, endG, endB);
          temp_list.add(newColor);

        }
        changes.addAll(temp_list);
      }
    }

    if (type.equals("scale")) {

      if (changes.size() == 0) {
        IChange newMove = new ScaleChange(startTick, endTick, s.getWidth(),
                s.getHeight(), s.getWidth(), s.getHeight());
        changes.add(newMove);
      } else {
        List<IChange> temp_list = new ArrayList<>();

        int lastEnd = startTick;
        float endW = s.getWidth();
        float endH = s.getHeight();

        for (int i = 0; i < changes.size(); i++) {
          if (changes.get(i).getStartTick() != lastEnd) {
            IChange newChange =
                    new ScaleChange(lastEnd, changes.get(i).getStartTick(),
                            endW, endH, changes.get(i).getStartWidth(),
                            changes.get(i).getStartHeight());
            temp_list.add(newChange);
            endW = changes.get(i).getEndWidth();
            endH = changes.get(i).getEndHeight();
            lastEnd = changes.get(i).getEndTick();
          } else {
            endW = changes.get(i).getEndWidth();
            endH = changes.get(i).getEndHeight();
            lastEnd = changes.get(i).getEndTick();
          }
        }
        if (lastEnd != endTick) {
          IChange newChange =
                  new ScaleChange(lastEnd, endTick,
                          endW, endH, endW, endH);
          temp_list.add(newChange);

        }
        changes.addAll(temp_list);
      }
    }
  }

  /**
   * Checks for if there are any jumps between changes in the Positions, Colors or scales.
   *
   * @param type    the type of changes we want to check for
   * @param changes the list of changes we want to test on
   * @return a boolean representing if there are any jumps
   */
  private static boolean checkFor(String type, List<IChange> changes, IShape s) {
    if (changes.size() == 0) {
      return false;
    }

    if (type.equals("Position")) {

      float lastEndX = s.getPosition().getX();
      float lastEndY = s.getPosition().getY();

      for (int i = 0; i < changes.size(); i++) {
        if (changes.get(i).getStartX() != lastEndX || changes.get(i).getStartY() != lastEndY) {
          return true;
        } else {
          lastEndX = changes.get(i).getEndX();
          lastEndY = changes.get(i).getEndY();
        }
      }
      return false;
    } else if (type.equals("Color")) {
      int lastEndR = s.getColor().getRed();
      int lastEndG = s.getColor().getGreen();
      int lastEndB = s.getColor().getBlue();

      for (int i = 0; i < changes.size(); i++) {
        int sr = changes.get(i).getStartR();
        int sg = changes.get(i).getStartG();
        int sb = changes.get(i).getStartB();
        if (sr != lastEndR
                || sg != lastEndG
                || sb != lastEndB) {
          return true;
        } else {
          lastEndR = sr;
          lastEndG = sg;
          lastEndB = sb;
        }
      }
      return false;
    } else {
      float lastEndWidth = s.getWidth();
      float lastEndHeight = s.getHeight();

      for (int i = 0; i < changes.size(); i++) {
        if (changes.get(i).getStartWidth() != lastEndWidth ||
                changes.get(i).getStartHeight() != lastEndHeight) {
          return true;
        } else {
          lastEndWidth = changes.get(i).getEndWidth();
          lastEndHeight = changes.get(i).getEndHeight();
        }
      }
      return false;

    }
  }

  /**
   * Generates the List of KeyFrames with every Shape updated by the interpolations per tick.
   *
   * @param name   the name of the Shape
   * @param s      the shape type of the Shape
   * @param startX the start position of the Shape (x)
   * @param startY the start position of the Shape (y)
   * @param endX   the end position of the Shape (y)
   * @param endY   the ebd position of the Shape (y)
   * @param startR the start red component of the Shape's color
   * @param endR   the end red component of the Shape's color
   * @param startG the start green component of the Shape's color
   * @param endG   the end green component of the Shape's color
   * @param startB the start blue component of the Shape's color
   * @param endB   the end blue component of the Shape's color
   * @param startW the start width of the Shape
   * @param endW   the end width of the Shape
   * @param startH the start height of the Shape
   * @param endH   the end height of the Shape
   * @param start  the start tick of the Shape
   * @param end    the end tick of the Shape
   * @return the List of KeyFrames for a given Motion of the Shape
   */
  private List<IKeyFrame> generateInterpolationKeyFrames(String name, ShapeType s, float startX,
                                                         float startY, float endX, float endY,
                                                         float startR, float endR, float startG,
                                                         float endG, float startB, float endB,
                                                         float startW, float endW, float startH,
                                                         float endH, int start, int end) {

    List<IKeyFrame> kef = new ArrayList<>();
    IChange temp = moves.get(shapes.get(0).getID()).get(0);
    for (int i = start; i <= end; i++) {
      float currentX = temp.calculateInterpolation(start, end, startX, endX, i);
      float currentY = temp.calculateInterpolation(start, end, startY, endY, i);
      float currentR = temp.calculateInterpolation(start, end, startR, endR, i);
      float currentG = temp.calculateInterpolation(start, end, startG, endG, i);
      float currentB = temp.calculateInterpolation(start, end, startB, endB, i);
      float currentW = temp.calculateInterpolation(start, end, startW, endW, i);
      float currentH = temp.calculateInterpolation(start, end, startH, endH, i);
      IShape newShape;
      int true_r = (int) currentR;
      int true_g = (int) currentG;
      int true_b = (int) currentB;
      if (s == ShapeType.Oval) {
        newShape = new Oval(name, new Position(currentX, currentY)
                , new Color(true_r, true_g, true_b)
                , currentH, currentW);
      } else {
        newShape = new Rectangle(name, new Position(currentX, currentY)
                , new Color(true_r, true_g, true_b)
                , currentH, currentW);
      }
      KeyFrame newKeyFrame = new KeyFrame(i, newShape);
      kef.add(newKeyFrame);
    }
    return kef;
  }
}
