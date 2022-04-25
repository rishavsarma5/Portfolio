import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


import model.IKeyFrame;
import model.IMotion;
import model.IShape;
import model.KeyFrame;
import model.Motion;
import model.Oval;
import model.Position;
import model.Rectangle;

import static org.junit.Assert.assertEquals;

/**
 * Test class for motions.
 */
public class MotionTest {
  private Position position_2 = new Position(3, 4);
  private Position position_2_1 = new Position(3, 5);
  private Position position_2_2 = new Position(3, 6);

  private Position position_3 = new Position(440.6715f, 370.1868f);
  private Color color_1 = new Color(200, 200, 200);
  private Color color_2 = new Color(130, 0, 200);


  private IShape rectangleValid_1_1 = new Rectangle("rect_1", position_2, color_1, 15,
          20);
  private IShape rectangleValid_1_2 = new Rectangle("rect_1", position_2_1, color_1, 15,
          20);
  private IShape rectangleValid_1_3 = new Rectangle("rect_1", position_2_2, color_1, 15,
          20);

  private IShape ovalValid_2 = new Oval("oval_1", position_3, color_2, 15.55f,
          20.55f);
  private IShape ovalValid_2_2 = new Oval("oval_1", position_3, color_2, 16.55f,
          21.55f);
  private IShape ovalValid_2_3 = new Oval("oval_1", position_3, color_2, 17.55f,
          22.55f);

  private IKeyFrame keyFrame_1_1 = new KeyFrame(5, rectangleValid_1_1);
  private IKeyFrame keyFrame_1_2 = new KeyFrame(6, rectangleValid_1_2);
  private IKeyFrame keyFrame_1_3 = new KeyFrame(7, rectangleValid_1_3);

  private List<IKeyFrame> keyFrames_1 = new ArrayList<IKeyFrame>();
  private List<IKeyFrame> keyFrames_2 = new ArrayList<IKeyFrame>();


  private IKeyFrame keyFrame_2_1 = new KeyFrame(1, ovalValid_2);
  private IKeyFrame keyFrame_2_2 = new KeyFrame(2, ovalValid_2_2);
  private IKeyFrame keyFrame_2_3 = new KeyFrame(3, ovalValid_2_3);

  private IMotion m1;
  private IMotion m2;

  @Test
  public void testGetStartTick() {
    keyFrames_1.add(keyFrame_1_1);
    keyFrames_1.add(keyFrame_1_2);
    keyFrames_1.add(keyFrame_1_3);

    m1 = new Motion("rect_1", 5, 7, keyFrames_1);

    keyFrames_2.add(keyFrame_2_1);
    keyFrames_2.add(keyFrame_2_2);
    keyFrames_2.add(keyFrame_2_3);

    m2 = new Motion("oval_1", 1, 3, keyFrames_2);
    assertEquals(5, m1.getStartTick());
    assertEquals(1, m2.getStartTick());
  }

  @Test
  public void testGetEndTick() {
    keyFrames_1.add(keyFrame_1_1);
    keyFrames_1.add(keyFrame_1_2);
    keyFrames_1.add(keyFrame_1_3);

    m1 = new Motion("rect_1", 5, 7, keyFrames_1);

    keyFrames_2.add(keyFrame_2_1);
    keyFrames_2.add(keyFrame_2_2);
    keyFrames_2.add(keyFrame_2_3);

    m2 = new Motion("oval_1", 1, 3, keyFrames_2);
    assertEquals(7, m1.getEndTick());
    assertEquals(3, m2.getEndTick());
  }

  @Test
  public void testGetKeyFrames() {
    keyFrames_1.add(keyFrame_1_1);
    keyFrames_1.add(keyFrame_1_2);
    keyFrames_1.add(keyFrame_1_3);

    m1 = new Motion("rect_1", 5, 7, keyFrames_1);

    keyFrames_2.add(keyFrame_2_1);
    keyFrames_2.add(keyFrame_2_2);
    keyFrames_2.add(keyFrame_2_3);

    m2 = new Motion("oval_1", 1, 3, keyFrames_2);
    assertEquals(keyFrames_1, m1.getKeyFrames());
    assertEquals(keyFrames_2, m2.getKeyFrames());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetKeyFrameAtTickLessThanStart() {
    keyFrames_1.add(keyFrame_1_1);
    keyFrames_1.add(keyFrame_1_2);
    keyFrames_1.add(keyFrame_1_3);

    m1 = new Motion("rect_1", 5, 7, keyFrames_1);

    keyFrames_2.add(keyFrame_2_1);
    keyFrames_2.add(keyFrame_2_2);
    keyFrames_2.add(keyFrame_2_3);

    m2 = new Motion("oval_1", 1, 3, keyFrames_2);
    m1.getKeyFrameAt(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetKeyFrameAtTickLargerThanEnd() {
    keyFrames_1.add(keyFrame_1_1);
    keyFrames_1.add(keyFrame_1_2);
    keyFrames_1.add(keyFrame_1_3);

    m1 = new Motion("rect_1", 5, 7, keyFrames_1);

    keyFrames_2.add(keyFrame_2_1);
    keyFrames_2.add(keyFrame_2_2);
    keyFrames_2.add(keyFrame_2_3);

    m2 = new Motion("oval_1", 1, 3, keyFrames_2);
    m1.getKeyFrameAt(9);
  }

  @Test
  public void testGetFrameAtValidInput() {
    keyFrames_1.add(keyFrame_1_1);
    keyFrames_1.add(keyFrame_1_2);
    keyFrames_1.add(keyFrame_1_3);

    m1 = new Motion("rect_1", 5, 7, keyFrames_1);

    keyFrames_2.add(keyFrame_2_1);
    keyFrames_2.add(keyFrame_2_2);
    keyFrames_2.add(keyFrame_2_3);

    m2 = new Motion("oval_1", 1, 3, keyFrames_2);
    assertEquals(keyFrame_1_1, m1.getKeyFrameAt(5));
    assertEquals(keyFrame_1_2, m1.getKeyFrameAt(6));
    assertEquals(keyFrame_1_3, m1.getKeyFrameAt(7));
    assertEquals(keyFrame_2_1, m2.getKeyFrameAt(1));
    assertEquals(keyFrame_2_2, m2.getKeyFrameAt(2));
    assertEquals(keyFrame_2_3, m2.getKeyFrameAt(3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructWithInvalidKeyFrames() {
    keyFrames_1.add(keyFrame_1_1);
    keyFrames_1.add(keyFrame_1_3);

    m1 = new Motion("rect_1", 5, 7, keyFrames_1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructNotTheSameName() {
    keyFrames_1.add(keyFrame_1_1);
    keyFrames_1.add(keyFrame_1_2);
    keyFrames_1.add(keyFrame_2_3);
    m1 = new Motion("rect_1", 5, 7, keyFrames_1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructInvalidStartTick() {
    keyFrames_1.add(keyFrame_1_1);
    keyFrames_1.add(keyFrame_1_2);
    keyFrames_1.add(keyFrame_1_3);
    m1 = new Motion("rect_1", 6, 7, keyFrames_1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructInvalidEndTick() {
    keyFrames_1.add(keyFrame_1_1);
    keyFrames_1.add(keyFrame_1_2);
    keyFrames_1.add(keyFrame_1_3);
    m1 = new Motion("rect_1", 5, 6, keyFrames_1);
  }

  @Test
  public void testAddFrame() {
    keyFrames_1.add(keyFrame_1_1);
    keyFrames_1.add(keyFrame_1_2);
    keyFrames_1.add(keyFrame_1_3);
    m1 = new Motion("rect_1", 5, 7, keyFrames_1);
    KeyFrame keyFrame_1_0 = new KeyFrame(4, rectangleValid_1_1);
    m1.addFrame(keyFrame_1_0);
    assertEquals(keyFrame_1_0, m1.getKeyFrameAt(4));

    keyFrames_2.add(keyFrame_2_1);
    keyFrames_2.add(keyFrame_2_2);
    keyFrames_2.add(keyFrame_2_3);

    m2 = new Motion("oval_1", 1, 3, keyFrames_2);
    KeyFrame keyFrame_2_4 = new KeyFrame(4, ovalValid_2_3);
    m2.addFrame(keyFrame_2_4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddToTheMiddle() {
    keyFrames_1.add(keyFrame_1_1);
    keyFrames_1.add(keyFrame_1_2);
    keyFrames_1.add(keyFrame_1_3);
    m1 = new Motion("rect_1", 5, 7, keyFrames_1);
    KeyFrame keyFrame_1_0 = new KeyFrame(6, rectangleValid_1_1);
    m1.addFrame(keyFrame_1_0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddToFarApartFromTheTimeInterval() {
    keyFrames_1.add(keyFrame_1_1);
    keyFrames_1.add(keyFrame_1_2);
    keyFrames_1.add(keyFrame_1_3);
    m1 = new Motion("rect_1", 5, 7, keyFrames_1);
    KeyFrame keyFrame_1_0 = new KeyFrame(15, rectangleValid_1_1);
    m1.addFrame(keyFrame_1_0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddToFarApartFromTheTimeIntervalLess() {
    keyFrames_1.add(keyFrame_1_1);
    keyFrames_1.add(keyFrame_1_2);
    keyFrames_1.add(keyFrame_1_3);
    m1 = new Motion("rect_1", 5, 7, keyFrames_1);
    KeyFrame keyFrame_1_0 = new KeyFrame(2, rectangleValid_1_1);
    m1.addFrame(keyFrame_1_0);
  }
}