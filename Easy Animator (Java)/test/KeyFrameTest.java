import org.junit.Test;

import java.awt.Color;


import model.IShape;
import model.KeyFrame;
import model.Oval;
import model.Position;
import model.Rectangle;

import static org.junit.Assert.assertEquals;


/**
 * Test class for KeyFrames.
 */
public class KeyFrameTest {
  private Position position_2 = new Position(3, 4);
  private Position position_3 = new Position(440.6715f,370.1868f);
  private Color color_1 = new Color(200, 200, 200);
  private Color color_2 = new Color(130, 0, 200);


  private IShape rectangleValid =
          new Rectangle("rect_1",position_2,color_1,15,20);
  private IShape ovalValid_2 =
          new Oval("oval_1",position_3,color_2,15.55f,20.55f);

  private KeyFrame keyFrame_1 =
          new KeyFrame(5,rectangleValid);
  private KeyFrame keyFrame_2 =
          new KeyFrame(3,ovalValid_2);

  @Test
  public void testChangeSizeInvalidInput() {
    assertEquals(20,keyFrame_1.getWidth(),0.01);
    assertEquals(15,keyFrame_1.getHeight(),0.01);
    keyFrame_1.changeSize(16,21);
    assertEquals(16,keyFrame_1.getWidth(),0.01);
    assertEquals(21,keyFrame_1.getHeight(),0.01);
  }

  @Test
  public void testChangeSizeInvalidInput2() {
    assertEquals(20.55,keyFrame_2.getWidth(),0.01);
    assertEquals(15.55,keyFrame_2.getHeight(),0.01);
    keyFrame_2.changeSize(16,21);
    assertEquals(16,keyFrame_2.getWidth(),0.01);
    assertEquals(21,keyFrame_2.getHeight(),0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeSizeValidInput() {
    assertEquals(20,keyFrame_1.getWidth(),0.01);
    assertEquals(15,keyFrame_1.getHeight(),0.01);
    keyFrame_1.changeSize(-16,21);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeSizeValidInput_1() {
    assertEquals(20.55,keyFrame_2.getWidth(),0.01);
    assertEquals(15.55,keyFrame_2.getHeight(),0.01);
    keyFrame_2.changeSize(16,-21);
  }

  @Test
  public void testChangeColor() {
    assertEquals(color_1,keyFrame_1.getColor());
    keyFrame_1.changeColor(color_2);
    assertEquals(color_2,keyFrame_1.getColor());
  }

  @Test
  public void testChangeColor_1() {
    assertEquals(color_2,keyFrame_2.getColor());
    keyFrame_2.changeColor(color_1);
    assertEquals(color_1,keyFrame_2.getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeColorNullColor() {
    assertEquals(color_2,keyFrame_2.getColor());
    keyFrame_2.changeColor(null);
  }

  @Test
  public void testChangeColorRGB() {
    assertEquals(color_1,keyFrame_1.getColor());
    keyFrame_1.changeColorRGB(130, 0, 200);
    assertEquals(color_2,keyFrame_1.getColor());

    assertEquals(color_2,keyFrame_2.getColor());
    keyFrame_2.changeColorRGB(200, 200, 200);
    assertEquals(color_1,keyFrame_2.getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeColorRGBInvalid() {
    assertEquals(color_1,keyFrame_1.getColor());
    keyFrame_1.changeColorRGB(-130, 0, 200);
  }

  @Test
  public void testChangePosition() {
    assertEquals(position_2,keyFrame_1.getPosition());
    keyFrame_1.changePositionOne(position_3);
    assertEquals(position_3,keyFrame_1.getPosition());

    assertEquals(position_3,keyFrame_2.getPosition());
    keyFrame_2.changePositionOne(position_2);
    assertEquals(position_2,keyFrame_2.getPosition());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangePositionInvalid() {
    assertEquals(position_2,keyFrame_1.getPosition());
    keyFrame_1.changePositionOne((Position) null);
  }

  @Test
  public void testChangePositionXY() {
    assertEquals(position_2,keyFrame_1.getPosition());
    keyFrame_1.changePosition(440.6715f,370.1868f);
    assertEquals(position_3.getX(),keyFrame_1.getPosition().getX(),0.01);
    assertEquals(position_3.getY(),keyFrame_1.getPosition().getY(),0.01);

    assertEquals(position_3,keyFrame_2.getPosition());
    keyFrame_1.changePosition(3, 4);
    assertEquals(position_2.getX(),keyFrame_1.getPosition().getX(),0.01);
    assertEquals(position_2.getY(),keyFrame_1.getPosition().getY(),0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangePositionXYInvalid() {
    assertEquals(position_2,keyFrame_1.getPosition());
    keyFrame_1.changePosition(-440.6715f,370.1868f);
  }

  @Test
  public void testGetTick() {
    assertEquals(5,keyFrame_1.getTick());
    assertEquals(3,keyFrame_2.getTick());
  }

  @Test
  public void testGetID() {
    assertEquals("rect_1",keyFrame_1.getID());
    assertEquals("oval_1",keyFrame_2.getID());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructInvalidKeyFrame() {
    KeyFrame keyFrame_3 = new KeyFrame(-1,rectangleValid);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructInvalidKeyFrame2() {
    KeyFrame keyFrame_3 = new KeyFrame(1,null);
  }

}