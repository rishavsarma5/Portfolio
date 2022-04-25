

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.ColorChange;
import io.IChange;
import io.MoveChange;
import io.ScaleChange;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test cases for the AChange class.
 */
public class AChangeTest {
  private IChange moveChange_1 = new MoveChange(24, 27, 35, 33, 35,
          34);
  private IChange colorChange_1 = new ColorChange(2, 5, 255, 255,
          255
          , 255, 122, 122);
  private IChange scaleChange_1 = new ScaleChange(3, 15, 4, 5,
          3, 5);
  private IChange moveChange_2 = new MoveChange(4, 7, 10, 20, 15,
          35);
  private IChange moveChange_3 = new MoveChange(7, 15, 20, 35, 35,
          35);
  private IChange moveChange_4 = new MoveChange(15, 17, 35, 35, 45,
          55);
  private List<IChange> changes1 = new ArrayList<IChange>();
  private List<IChange> changes2 = new ArrayList<IChange>();


  @Before
  public void init() {
    changes1.add(moveChange_2);
    changes1.add(moveChange_3);
    changes1.add(moveChange_4);
  }

  @Test
  public void testStartTick() {
    assertEquals(24, moveChange_1.getStartTick());
    assertEquals(3, scaleChange_1.getStartTick());
    assertEquals(2, colorChange_1.getStartTick());
  }

  @Test
  public void testEndTick() {
    assertEquals(27, moveChange_1.getEndTick());
    assertEquals(5, colorChange_1.getEndTick());
    assertEquals(15, scaleChange_1.getEndTick());
  }

  @Test
  public void testGetStartX() {
    assertEquals(35, moveChange_1.getStartX(), 0.01);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetStartXUnsupported() {
    colorChange_1.getStartX();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetStartXUnsupportedScale() {
    scaleChange_1.getStartX();
  }

  @Test
  public void testGetEndX() {
    assertEquals(35, moveChange_1.getStartX(), 0.01);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetEndUnsupported() {
    colorChange_1.getStartX();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetEndUnsupportedScale() {
    scaleChange_1.getEndX();
  }

  @Test
  public void testGetStartY() {
    assertEquals(33, moveChange_1.getStartY(), 0.01);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetStartYEndUnsupported() {
    colorChange_1.getStartY();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetStartYEndUnsupportedScale() {
    scaleChange_1.getStartY();
  }

  @Test
  public void testGetStartY3() {
    assertEquals(33, moveChange_1.getStartY(), 0.01);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetStartYEndUnsupported2() {
    colorChange_1.getStartY();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetStartYEndUnsupportedScale3() {
    scaleChange_1.getStartY();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetStartRMove() {
    moveChange_1.getStartR();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetStartGMove() {
    moveChange_1.getStartG();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetStartBMove() {
    moveChange_1.getStartB();
  }

  @Test
  public void testGetStartRGBValidColorChange() {
    assertEquals(255, colorChange_1.getStartR(), 0.01);
    assertEquals(255, colorChange_1.getStartG(), 0.01);
    assertEquals(255, colorChange_1.getStartB(), 0.01);
  }

  @Test
  public void testGetEndRGBValidColorChange() {
    assertEquals(255, colorChange_1.getEndR(), 0.01);
    assertEquals(122, colorChange_1.getEndG(), 0.01);
    assertEquals(122, colorChange_1.getEndB(), 0.01);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetStartRScale() {
    scaleChange_1.getStartR();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetStartGScale() {
    scaleChange_1.getStartG();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetStartBScale() {
    scaleChange_1.getStartB();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetEndRMove() {
    moveChange_1.getEndR();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetEndGMove() {
    moveChange_1.getEndG();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetEndBMove() {
    moveChange_1.getEndB();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetEndRScale() {
    scaleChange_1.getEndR();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetEndGScale() {
    scaleChange_1.getEndG();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetEndBScale() {
    scaleChange_1.getEndB();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetStartHeightMove() {
    moveChange_1.getStartHeight();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetStartHeightColorChange() {
    colorChange_1.getStartHeight();
  }

  @Test
  public void testGetStartHeightScale() {
    assertEquals(5, scaleChange_1.getStartHeight(),
            0.01);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetStartWidthMove() {
    moveChange_1.getStartWidth();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetStartWidthColorChange() {
    colorChange_1.getStartWidth();
  }


  @Test
  public void testGetEndHeightScale() {
    assertEquals(5, scaleChange_1.getEndHeight(),
            0.01);
  }


  @Test(expected = UnsupportedOperationException.class)
  public void testGetEndWidthMove() {
    moveChange_1.getEndWidth();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetEndWithColorChange() {
    colorChange_1.getEndWidth();
  }


  @Test
  public void testGetEndWidthScale() {
    assertEquals(3, scaleChange_1.getEndWidth(),
            0.01);
  }

  @Test
  public void testGetEndHeightScaleScale() {
    assertEquals(5, scaleChange_1.getEndHeight(),
            0.01);
  }

  @Test
  public void testGetTheNumberIntheList() {
    assertEquals(0, moveChange_1.getTheNumberInTheList(changes1, 6));
    assertEquals(1, moveChange_1.getTheNumberInTheList(changes1, 8));
    assertEquals(2, moveChange_1.getTheNumberInTheList(changes1, 16));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetTheNumberInTheList() {
    assertEquals(0, moveChange_1.getTheNumberInTheList(changes1, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetTheNumberInTheList2InvalidNull() {
    assertEquals(0, moveChange_1.getTheNumberInTheList(changes2, 55));
  }

  @Test
  public void testCalculateInterpolation() {
    assertEquals(30, moveChange_1.calculateInterpolation(
            5, 10, 25, 50, 6), 0.01);
    assertEquals(35, moveChange_1.calculateInterpolation(
            5, 10, 25, 50, 7), 0.01);
    assertEquals(40, moveChange_1.calculateInterpolation(
            5, 10, 25, 50, 8), 0.01);
    assertEquals(28, moveChange_1.calculateInterpolation(
            5, 10, 25, 30, 8), 0.01);
    assertEquals(49.17, moveChange_1.calculateInterpolation(
            33, 67, 22, 99, 45), 0.01);

  }

  @Test
  public void testPerformInterpolationXY() {
    assertEquals(13.33, moveChange_1.perFormInterpolation(moveChange_2, 6, "X"),
            0.01);
    assertEquals(30, moveChange_1.perFormInterpolation(moveChange_2, 6, "Y"),
            0.01);
  }

  @Test
  public void testPerformInterpolationRGB() {
    assertEquals(255, moveChange_1.perFormInterpolation(colorChange_1, 3, "R"),
            0.01);
    assertEquals(166.33, moveChange_1.perFormInterpolation(colorChange_1, 4, "G"),
            0.01);
    assertEquals(122, moveChange_1.perFormInterpolation(colorChange_1, 5, "B"),
            0.01);
  }

  @Test
  public void testPerformInterpolationWH() {
    assertEquals(3.75, moveChange_1.perFormInterpolation(scaleChange_1, 6, "W"),
            0.01);
    assertEquals(5.0, moveChange_1.perFormInterpolation(scaleChange_1, 7, "H"),
            0.01);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testPerformInterpolationUnsupported() {
    moveChange_1.perFormInterpolation(colorChange_1, 6, "W");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPerformInterpolationIllegalOperationName() {
    moveChange_1.perFormInterpolation(colorChange_1, 6, "K");
  }
}
