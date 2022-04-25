package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a motion of a shape in an animation. In real world, a motion
 * is basically a collections of Keyframes in a given time interval. In
 * our representation, we need a shape to represent the shape the motion is
 * focusing on, the start and end tick and the list of keyFrames in between
 * (start and end inclusive)
 */
public class Motion implements IMotion {
  private final String name;
  private int startTick;
  private int endTick;
  private List<IKeyFrame> keyFrames;


  /**
   * A public constructor for a motion in an animation.
   * @param name the shape the motion is about
   * @param start the start tick
   * @param end the end tick
   * @param kf the KeyFrames in between (start and end inclusive)
   * @throws IllegalArgumentException if the keyFrames are not continuous
   */
  public Motion(String name, int start, int end, List<IKeyFrame> kf) {
    this.keyFrames = kf;
    for (IKeyFrame k : keyFrames) {
      if (k.getID() != name) {
        throw new IllegalArgumentException("The input name is not consistent");
      }
    }

    if (name == null || start > end) {
      throw new IllegalArgumentException("The input is invalid!");
    }
    this.name = name;
    this.startTick = start;
    this.endTick = end;


    int previous_tick = kf.get(0).getTick();
    if (previous_tick != start) {
      throw new IllegalArgumentException("The input is invalid-1");
    }

    for (int i = 1; i < kf.size(); i++) {
      if (kf.get(i).getTick() != previous_tick + 1) {
        throw new IllegalArgumentException("The input is invalid-2");
      }
      else {
        previous_tick = kf.get(i).getTick();
      }
    }

    if (previous_tick != end) {
      throw new IllegalArgumentException("The input is invalid-3");
    }
  }

  @Override
  public int getStartTick() {
    return this.startTick;
  }

  @Override
  public int getEndTick() {
    return this.endTick;
  }

  @Override
  public List<IKeyFrame> getKeyFrames() {
    return new ArrayList<>(this.keyFrames);
  }

  @Override
  public IKeyFrame getKeyFrameAt(int tick) {
    if (tick < startTick || tick > endTick) {
      throw new IllegalArgumentException("The tick is invalid");
    }

    IKeyFrame current_key = null;
    List<IKeyFrame> keyFrames = this.keyFrames;
    for (int i = 0; i < keyFrames.size(); i++) {
      if (keyFrames.get(i).getTick() == tick) {
        current_key = keyFrames.get(i);
      }
    }
    return current_key;
  }

  @Override
  public String createString(int tempo) {
    IKeyFrame start = this.getKeyFrameAt(this.startTick);
    IKeyFrame end = this.getKeyFrameAt(this.endTick);
    return "motion" + " " + this.name + " "
            + start.createKFString(tempo) + "     "
            + end.createKFString(tempo);
  }

  @Override
  public String createTextViewMockString(int tempo) {
    IKeyFrame start = this.getKeyFrameAt(this.startTick);
    IKeyFrame end = this.getKeyFrameAt(this.endTick);
    return "Created motion" + " " + this.name + " that starts from "
            + start.createKFString(tempo) + "s     to "
            + end.createKFString(tempo) + "s";
  }

  @Override
  public String createSVGString(int tempo, boolean loop) {
    String output = "";
    String loopPart = "";
    String xMove = "x";
    String yMove = "y";
    String xDim = "width";
    String yDim = "height";
    IShape start = keyFrames.get(0).getShape();
    IShape end = keyFrames.get(keyFrames.size() - 1).getShape();

    if (start.getShapeType().toString().equals("ellipse")) {
      xMove = "cx";
      yMove = "cy";
      xDim = "rx";
      yDim = "ry";
    }

    String intro = "<animate attributeType=\"xml\" ";

    if (loop) {
      loopPart = "base.begin+";
    }
    String begin = "begin=\"" + loopPart + (((float) this.startTick / (float) tempo) * 1000)
            + "ms\" ";
    float duration = (((float) this.endTick - (float) this.startTick) / (float) tempo
            * 1000);

    if (start.getPosition() != end.getPosition()) {
      output += intro + begin + "dur=\"" + duration + "ms\" attributeName=\"" + xMove + "\" from=\""
              + start.getPosition().getX() + "\" to=\"" + end.getPosition().getX()
              + "\" fill=\"freeze\" />\n";

      output += intro + begin + "dur=\"" + duration + "ms\" attributeName=\"" + yMove + "\" from=\""
              + start.getPosition().getY() + "\" to=\"" + end.getPosition().getY()
              + "\" fill=\"freeze\" />";
    }

    if (start.getWidth() != end.getWidth() || start.getHeight() != end.getHeight()) {
      output += intro + begin + "dur=\"" + duration + "ms\" attributeName=\"" + xDim + "\" from=\""
              + start.getWidth() + "\" to=\"" + end.getWidth()
              + "\" fill=\"freeze\" />\n";

      output += intro + begin + "dur=\"" + duration + "ms\" attributeName=\"" + yDim + "\" from=\""
              + start.getHeight() + "\" to=\"" + end.getHeight()
              + "\" fill=\"freeze\" />";
    }


    if (start.getColor() != end.getColor()) {
      output += "<animate attributeName=\"fill\" " + "attributeType=\"xml\" "
              + begin + "dur=\"" + duration + "ms\" "
              + "from=\"rgb("
              + start.getColor().getRed() + ","
              + start.getColor().getGreen() + ","
              + start.getColor().getBlue() + ")\" to=\"rgb("
              + end.getColor().getRed() + ","
              + end.getColor().getGreen() + ","
              + end.getColor().getBlue() + ")\" "
              + "fill=\"freeze\" />";
    }
    return output;
  }

  @Override
  public void addFrame(IKeyFrame f1) {
    if (f1.getID().equals(this.name)) {
      if (f1.getTick() == this.getStartTick() - 1 && f1.getTick() > 0) {
        this.startTick = f1.getTick();
        this.keyFrames.add(0, f1);
      } else if (f1.getTick() == this.getEndTick() + 1 && f1.getTick() > 0) {
        this.endTick = f1.getTick();
        this.keyFrames.add(f1);
      }
      else {
        throw new IllegalArgumentException("The adding tick is invalid");
      }
    }

    else {
      throw new IllegalArgumentException("The adding is invalid");
    }
  }

}
