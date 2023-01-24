import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.AShape;
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

import static org.junit.Assert.assertEquals;

/**
 * Test class for model.
 */
public class ModelTest {
  private Position position_2 = new Position(3, 4);
  private Color color_1 = new Color(200, 200, 200);
  private AShape rectangleValid =
          new Rectangle("rect_1", position_2, color_1, 15, 20);

  private Position position_3 = new Position(440.6715f, 370.1868f);
  private Color color_2 = new Color(130, 0, 200);

  private Color color_3 = new Color(130, 0, 200);
  private Color color_4 = new Color(130, 100, 200);
  private Color color_5 = new Color(130, 150, 200);
  private Color color_6 = new Color(130, 155, 200);
  private Color color_7 = new Color(130, 200, 200);
  private Color color_8 = new Color(130, 255, 200);

  private AShape ovalValid_2 =
          new Oval("oval_2", position_3, color_2, 15.55f, 20.55f);

  private Position rectangle_motion1_frame2 = new Position(3, 5);
  private Position rectangle_motion1_frame3 = new Position(3, 6);
  private Position rectangle_motion1_frame4 = new Position(3, 7);
  private Position rectangle_motion1_frame5 = new Position(3, 8);
  private Position rectangle_motion1_frame6 = new Position(3, 9);

  private Position rectangle_motion2_frame2 = new Position(3, 9);
  private Position rectangle_motion2_frame3 = new Position(4, 9);
  private Position rectangle_motion2_frame4 = new Position(5, 9);
  private Position rectangle_motion2_frame5 = new Position(6, 9);
  private Position rectangle_motion2_frame6 = new Position(7, 9);

  private Position oval_motion2_frame2 = new Position(441.6715f, 370.1868f);
  private Position oval_motion2_frame3 = new Position(442.6715f, 370.1868f);
  private Position oval_motion2_frame4 = new Position(443.6715f, 370.1868f);
  private Position oval_motion2_frame5 = new Position(444.6715f, 370.1868f);
  private Position oval_motion2_frame6 = new Position(445.6715f, 370.1868f);

  private AShape ovalValid_motion_1_frame2 =
          new Oval("oval_2", position_3, color_3, 15.55f, 20.55f);
  private AShape ovalValid_motion_1_frame3 =
          new Oval("oval_2", position_3, color_4, 15.55f, 20.55f);
  private AShape ovalValid_motion_1_frame4 =
          new Oval("oval_2", position_3, color_5, 15.55f, 20.55f);
  private AShape ovalValid_motion_1_frame5 =
          new Oval("oval_2", position_3, color_6, 15.55f, 20.55f);
  private AShape ovalValid_motion_1_frame6 =
          new Oval("oval_2", position_3, color_7, 15.55f, 20.55f);

  private AShape ovalValid_motion_2_frame2 =
          new Oval("oval_2", oval_motion2_frame2, color_7, 15.55f, 20.55f);
  private AShape ovalValid_motion_2_frame3 =
          new Oval("oval_2", oval_motion2_frame2, color_7, 15.55f, 20.55f);
  private AShape ovalValid_motion_2_frame4 =
          new Oval("oval_2", oval_motion2_frame3, color_7, 15.55f, 20.55f);
  private AShape ovalValid_motion_2_frame5 =
          new Oval("oval_2", oval_motion2_frame4, color_7, 15.55f, 20.55f);
  private AShape ovalValid_motion_2_frame6 =
          new Oval("oval_2", oval_motion2_frame5, color_7, 15.55f, 20.55f);

  private KeyFrame ovalValid_motion2_keyFrame_2 =
          new KeyFrame(5, ovalValid_motion_2_frame2);
  private KeyFrame ovalValid_motion2_keyFrame_3 =
          new KeyFrame(6, ovalValid_motion_2_frame3);
  private KeyFrame ovalValid_motion2_keyFrame_4 =
          new KeyFrame(7, ovalValid_motion_2_frame4);
  private KeyFrame ovalValid_motion2_keyFrame_5 =
          new KeyFrame(8, ovalValid_motion_2_frame5);
  private KeyFrame ovalValid_motion2_keyFrame_6 =
          new KeyFrame(9, ovalValid_motion_2_frame6);

  private KeyFrame ovalValid_motion1_keyFrame_2 =
          new KeyFrame(1, ovalValid_motion_1_frame2);
  private KeyFrame ovalValid_motion1_keyFrame_3 =
          new KeyFrame(2, ovalValid_motion_1_frame3);
  private KeyFrame ovalValid_motion1_keyFrame_4 =
          new KeyFrame(3, ovalValid_motion_1_frame4);
  private KeyFrame ovalValid_motion1_keyFrame_5 =
          new KeyFrame(4, ovalValid_motion_1_frame5);
  private KeyFrame ovalValid_motion1_keyFrame_6 =
          new KeyFrame(5, ovalValid_motion_1_frame6);

  private AShape rectangleValid_motion1_frame2 =
          new Rectangle("rect_1", rectangle_motion1_frame2, color_1, 15, 20);
  private AShape rectangleValid_motion1_frame3 =
          new Rectangle("rect_1", rectangle_motion1_frame3, color_1, 15, 20);
  private AShape rectangleValid_motion1_frame4 =
          new Rectangle("rect_1", rectangle_motion1_frame4, color_1, 15, 20);
  private AShape rectangleValid_motion1_frame5 =
          new Rectangle("rect_1", rectangle_motion1_frame5, color_1, 15, 20);
  private AShape rectangleValid_motion1_frame6 =
          new Rectangle("rect_1", rectangle_motion1_frame6, color_1, 15, 20);

  private AShape rectangleValid_motion2_frame2 =
          new Rectangle("rect_1", rectangle_motion2_frame2, color_1, 15, 20);
  private AShape rectangleValid_motion2_frame3 =
          new Rectangle("rect_1", rectangle_motion2_frame3, color_1, 15, 20);
  private AShape rectangleValid_motion2_frame4 =
          new Rectangle("rect_1", rectangle_motion2_frame4, color_1, 15, 20);
  private AShape rectangleValid_motion2_frame5 =
          new Rectangle("rect_1", rectangle_motion2_frame5, color_1, 15, 20);
  private AShape rectangleValid_motion2_frame6 =
          new Rectangle("rect_1", rectangle_motion2_frame6, color_1, 15, 20);

  private KeyFrame rectangleValid_motion1_keyFrame_2 =
          new KeyFrame(2, rectangleValid_motion1_frame2);
  private KeyFrame rectangleValid_motion1_keyFrame_3 =
          new KeyFrame(3, rectangleValid_motion1_frame3);
  private KeyFrame rectangleValid_motion1_keyFrame_4 =
          new KeyFrame(4, rectangleValid_motion1_frame4);
  private KeyFrame rectangleValid_motion1_keyFrame_5 =
          new KeyFrame(5, rectangleValid_motion1_frame5);
  private KeyFrame rectangleValid_motion1_keyFrame_6 =
          new KeyFrame(6, rectangleValid_motion1_frame6);

  private KeyFrame rectangleValid_motion2_keyFrame_2 =
          new KeyFrame(6, rectangleValid_motion2_frame2);
  private KeyFrame rectangleValid_motion2_keyFrame_3 =
          new KeyFrame(7, rectangleValid_motion2_frame3);
  private KeyFrame rectangleValid_motion2_keyFrame_4 =
          new KeyFrame(8, rectangleValid_motion1_frame4);
  private KeyFrame rectangleValid_motion2_keyFrame_5 =
          new KeyFrame(9, rectangleValid_motion1_frame5);
  private KeyFrame rectangleValid_motion2_keyFrame_6 =
          new KeyFrame(10, rectangleValid_motion1_frame6);

  List<IKeyFrame> keyFrames_rectangle_1 = new ArrayList<>();
  List<IKeyFrame> keyFrames_rectangle_2 = new ArrayList<>();
  List<IKeyFrame> keyFrames_oval_1 = new ArrayList<>();
  List<IKeyFrame> keyFrames_oval_2 = new ArrayList<>();
  List<IMotion> motions_rectangle = new ArrayList<>();
  List<IMotion> motions_oval = new ArrayList<>();
  List<IShape> shapes = new ArrayList<>();

  Map<String, List<IMotion>> motions = new HashMap<>();
  IModel rectAndOval;

  IMotion m1;
  IMotion m2;
  IMotion m3;
  IMotion m4;
  int width = 25;
  int height = 25;


  @Before
  public void init() {
    keyFrames_rectangle_1.add(rectangleValid_motion1_keyFrame_2);
    keyFrames_rectangle_1.add(rectangleValid_motion1_keyFrame_3);
    keyFrames_rectangle_1.add(rectangleValid_motion1_keyFrame_4);
    keyFrames_rectangle_1.add(rectangleValid_motion1_keyFrame_5);
    keyFrames_rectangle_1.add(rectangleValid_motion1_keyFrame_6);

    keyFrames_rectangle_2.add(rectangleValid_motion2_keyFrame_2);
    keyFrames_rectangle_2.add(rectangleValid_motion2_keyFrame_3);
    keyFrames_rectangle_2.add(rectangleValid_motion2_keyFrame_4);
    keyFrames_rectangle_2.add(rectangleValid_motion2_keyFrame_5);
    keyFrames_rectangle_2.add(rectangleValid_motion2_keyFrame_6);

    m1 = new Motion("rect_1", 2, 6, keyFrames_rectangle_1);
    m2 = new Motion("rect_1", 6, 10, keyFrames_rectangle_2);


    motions_rectangle.add(m1);
    motions_rectangle.add(m2);

    keyFrames_oval_1.add(ovalValid_motion1_keyFrame_2);
    keyFrames_oval_1.add(ovalValid_motion1_keyFrame_3);
    keyFrames_oval_1.add(ovalValid_motion1_keyFrame_4);
    keyFrames_oval_1.add(ovalValid_motion1_keyFrame_5);
    keyFrames_oval_1.add(ovalValid_motion1_keyFrame_6);

    keyFrames_oval_2.add(ovalValid_motion2_keyFrame_2);
    keyFrames_oval_2.add(ovalValid_motion2_keyFrame_3);
    keyFrames_oval_2.add(ovalValid_motion2_keyFrame_4);
    keyFrames_oval_2.add(ovalValid_motion2_keyFrame_5);
    keyFrames_oval_2.add(ovalValid_motion2_keyFrame_6);

    m3 = new Motion("oval_2", 1, 5, keyFrames_oval_1);
    m4 = new Motion("oval_2", 5, 9, keyFrames_oval_2);

    motions_oval.add(m3);
    motions_oval.add(m4);
    shapes.add(rectangleValid);
    shapes.add(ovalValid_2);
    motions.put("rect_1", motions_rectangle);
    motions.put("oval_2", motions_oval);
    rectAndOval = new Model(shapes, motions, width, height, 100);

  }

  @Test
  public void testGetAllShape() {
    assertEquals(2, rectAndOval.getAllShapes().size());
    assertEquals(rectangleValid, rectAndOval.getAllShapes().get(0));
    assertEquals(ovalValid_2, rectAndOval.getAllShapes().get(1));

  }

  @Test
  public void testGetAllMotions() {
    assertEquals(2, rectAndOval.getAllMotions("rect_1").size());
    assertEquals(m1, rectAndOval.getAllMotions("rect_1").get(0));
    assertEquals(m2, rectAndOval.getAllMotions("rect_1").get(1));

    assertEquals(2, rectAndOval.getAllMotions("oval_2").size());
    assertEquals(m3, rectAndOval.getAllMotions("oval_2").get(0));
    assertEquals(m4, rectAndOval.getAllMotions("oval_2").get(1));
  }

  @Test
  public void testAllKeyFrames() {
    assertEquals(10, rectAndOval.getAllKeyFrames("rect_1").size());
    assertEquals(10, rectAndOval.getAllKeyFrames("oval_2").size());
  }

  @Test
  public void testGetKeyFrameAt() {
    assertEquals(rectangleValid_motion1_keyFrame_2, rectAndOval.getKeyFrameAt("rect_1", 2));
    assertEquals(2, m1.getStartTick());
    assertEquals(6, m1.getEndTick());
    assertEquals(rectangleValid_motion1_keyFrame_3, rectAndOval.getKeyFrameAt("rect_1", 3));
    assertEquals(rectangleValid_motion1_keyFrame_4, rectAndOval.getKeyFrameAt("rect_1", 4));
    assertEquals(rectangleValid_motion1_keyFrame_5, rectAndOval.getKeyFrameAt("rect_1", 5));
    assertEquals(rectangleValid_motion1_keyFrame_6, rectAndOval.getKeyFrameAt("rect_1", 6));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetKeyFrameAtTheShapeIsInvalid() {
    AShape rectangleValid_rect3 =
            new Rectangle("rect_3", position_2, color_1, 15, 20);
    rectAndOval.getKeyFrameAt("rect_3", 3);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetKeyFrameAtTheShapeMotionsListEmpty() {
    AShape rectangleValid_rect3 =
            new Rectangle("rect_3", position_2, color_1, 15, 20);
    rectAndOval.addShape(rectangleValid_rect3);
    rectAndOval.getKeyFrameAt("rect_3", 3);
  }


  @Test
  public void testGetKeyFrameAtValid() {
    assertEquals(ovalValid_motion1_keyFrame_2, rectAndOval.getKeyFrameAt("oval_2", 1));
    assertEquals(ovalValid_motion1_keyFrame_3, rectAndOval.getKeyFrameAt("oval_2", 2));
    assertEquals(ovalValid_motion1_keyFrame_4, rectAndOval.getKeyFrameAt("oval_2", 3));
    assertEquals(ovalValid_motion1_keyFrame_5, rectAndOval.getKeyFrameAt("oval_2", 4));
    assertEquals(ovalValid_motion1_keyFrame_6, rectAndOval.getKeyFrameAt("oval_2", 5));
  }

  @Test
  public void testAddShape() {
    assertEquals(2, rectAndOval.getAllShapes().size());
    AShape rectangleValid2 =
            new Rectangle("rect_2", position_2, color_1, 15, 20);
    rectAndOval.addShape(rectangleValid2);
    assertEquals(3, rectAndOval.getAllShapes().size());
    assertEquals(rectangleValid2, rectAndOval.getAllShapes().get(2));
  }

  @Test
  public void removeShape() {
    assertEquals(2, rectAndOval.getAllShapes().size());
    assertEquals(rectangleValid, rectAndOval.getAllShapes().get(0));
    rectAndOval.removeShape(rectangleValid);
    assertEquals(1, rectAndOval.getAllShapes().size());
    assertEquals(ovalValid_2, rectAndOval.getAllShapes().get(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeAddingNullShape() {
    rectAndOval.addShape(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeAddingTheSameShape() {
    rectAndOval.addShape(rectangleValid);
  }

  @Test
  public void testSetBound() {
    assertEquals(25, rectAndOval.getBoundHeight());
    assertEquals(25, rectAndOval.getBoundWidth());
    rectAndOval.setBounds(15, 20);
    assertEquals(15, rectAndOval.getBoundWidth());
    assertEquals(20, rectAndOval.getBoundHeight());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetBoundNegative() {
    assertEquals(25, rectAndOval.getBoundHeight());
    assertEquals(25, rectAndOval.getBoundWidth());
    rectAndOval.setBounds(15, -20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructringModelWithOverlap() {
    List<IKeyFrame> overlappedKeyFrames = new ArrayList<>();
    List<IKeyFrame> overlappedKeyFrames2 = new ArrayList<>();

    overlappedKeyFrames.add(rectangleValid_motion1_keyFrame_5);
    overlappedKeyFrames.add(rectangleValid_motion1_keyFrame_6);

    overlappedKeyFrames2.add(rectangleValid_motion1_keyFrame_3);
    overlappedKeyFrames2.add(rectangleValid_motion1_keyFrame_4);
    overlappedKeyFrames2.add(rectangleValid_motion1_keyFrame_5);
    overlappedKeyFrames2.add(rectangleValid_motion1_keyFrame_6);

    Motion overlappedMotion = new Motion(rectangleValid.getID(), 5, 6,
            overlappedKeyFrames);
    Motion overlappedMotion2 = new Motion(rectangleValid.getID(), 3, 6,
            overlappedKeyFrames2);
    motions_rectangle.add(overlappedMotion);
    motions_rectangle.add(overlappedMotion2);
    List<IMotion> newMotions = new ArrayList<>();
    List<IShape> shapes2 = new ArrayList<>();
    shapes2.add(rectangleValid);
    newMotions.add(overlappedMotion);
    newMotions.add(overlappedMotion2);
    Map<String, List<IMotion>> motions2 = new HashMap<>();
    motions2.put("rect_1", newMotions);
    Model newModel = new Model(shapes2, motions2, 25, 25, 100);
    assertEquals(0, newModel.getAllMotions("rect_1"));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructringModelWithGaps() {
    List<IKeyFrame> overlappedKeyFrames = new ArrayList<>();
    List<IKeyFrame> overlappedKeyFrames2 = new ArrayList<>();

    overlappedKeyFrames.add(rectangleValid_motion1_keyFrame_5);
    overlappedKeyFrames.add(rectangleValid_motion1_keyFrame_6);

    IKeyFrame rectangleValid_motion1_keyFrame_3 =
            new KeyFrame(10, rectangleValid_motion1_frame3);
    IKeyFrame rectangleValid_motion1_keyFrame_4 =
            new KeyFrame(11, rectangleValid_motion1_frame3);

    overlappedKeyFrames2.add(rectangleValid_motion1_keyFrame_3);
    overlappedKeyFrames2.add(rectangleValid_motion1_keyFrame_4);


    IMotion overlappedMotion = new Motion(rectangleValid.getID(), 5, 6,
            overlappedKeyFrames);
    IMotion overlappedMotion2 = new Motion(rectangleValid.getID(), 10, 11,
            overlappedKeyFrames2);
    motions_rectangle.add(overlappedMotion);
    motions_rectangle.add(overlappedMotion2);
    List<IMotion> newMotions = new ArrayList<>();
    List<IShape> shapes2 = new ArrayList<>();
    shapes2.add(rectangleValid);
    newMotions.add(overlappedMotion);
    newMotions.add(overlappedMotion2);
    Map<String, List<IMotion>> motions2 = new HashMap<>();
    motions2.put("rect_1", newMotions);
    Model newModel = new Model(shapes2, motions2, 25, 25, 100);
    assertEquals(0, newModel.getAllMotions("rect_1"));

  }
}






