import org.junit.Test;

import java.awt.Color;

import model.AShape;
import model.Oval;
import model.Position;
import model.Rectangle;
import model.ShapeType;

import static org.junit.Assert.assertEquals;

/**
 * Test class for Oval.
 */
public class OvalTest {
  private Position position_2 = new Position(3, 4);
  private Position position_3 = new Position(440.6715f, 370.1868f);
  private Color color_1 = new Color(200, 200, 200);
  private Color color_2 = new Color(130, 0, 200);
  private Position null_position = null;

  private AShape ovalValid =
          new Oval("oval_1", position_2, color_1, 15, 20);
  private AShape ovalValid_2 =
          new Oval("oval_2", position_3, color_2, 15.55f, 20.55f);


  @Test(expected = IllegalArgumentException.class)
  public void testCreatingRectangleWithNullID() {
    Rectangle rectangleNullID =
            new Rectangle(null, position_2, color_1, 15, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreatingRectangleWithNullPosition() {
    Rectangle rectangleNullPosition =
            new Rectangle("Invalid_rect", null_position, color_1, 15, 20);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreatingRectangleWithNullColor() {
    Rectangle rectangleNullColor =
            new Rectangle("Invalid_rect", position_2, null, 15, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreatingRectangleWithNegativeHeight() {
    Rectangle rectangleInvalid =
            new Rectangle("Invalid_rect", position_2, color_1, -15, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreatingRectangleWithZeroHeight() {
    Rectangle rectangleInvalid =
            new Rectangle("Invalid_rect", position_2, color_1, 0, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreatingRectangleWithNegativeWidth() {
    Rectangle rectangleInvalid =
            new Rectangle("Invalid_rect", position_2, color_1, 15, -20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreatingRectangleWithZeroWidth() {
    Rectangle rectangleInvalid =
            new Rectangle("Invalid_rect", position_2, color_1, 15, 0);
  }

  @Test
  public void testGetHeight() {
    assertEquals(15, ovalValid.getHeight(), 0.01);
    assertEquals(15.55, ovalValid_2.getHeight(), 0.01);
  }

  @Test
  public void testGetWidth() {
    assertEquals(20, ovalValid.getWidth(), 0.01);
    assertEquals(20.55, ovalValid_2.getWidth(), 0.01);
  }

  @Test
  public void testSetHeightValidInput() {
    assertEquals(15, ovalValid.getHeight(), 0.01);
    ovalValid.setHeight(17.55f);
    assertEquals(17.55, ovalValid.getHeight(), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetHeightInvalidInput() {
    assertEquals(15, ovalValid.getHeight(), 0.01);
    ovalValid.setHeight(-17.55f);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetHeightInvalidInputZero() {
    assertEquals(15, ovalValid.getHeight(), 0.01);
    ovalValid.setHeight(0);
  }

  @Test
  public void testSetWidthValidInput() {
    assertEquals(20, ovalValid.getWidth(), 0.01);
    ovalValid.setWidth(17.55f);
    assertEquals(17.55, ovalValid.getWidth(), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetWidthInvalidInput() {
    assertEquals(20, ovalValid.getWidth(), 0.01);
    ovalValid.setWidth(-17.55f);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetWidthInvalidInputZero() {
    assertEquals(20, ovalValid.getWidth(), 0.01);
    ovalValid.setWidth(0);
  }

  @Test
  public void testSetSizeBothInputAreInvalid() {
    assertEquals(20, ovalValid.getWidth(), 0.01);
    assertEquals(15, ovalValid.getHeight(), 0.01);
    ovalValid.setSize(23.33f, 34.51f);
    assertEquals(20, ovalValid.getWidth(), 34.51);
    assertEquals(15, ovalValid.getHeight(), 23.33);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetSizeHeightInvalid() {
    assertEquals(20, ovalValid.getWidth(), 0.01);
    assertEquals(15, ovalValid.getHeight(), 0.01);
    ovalValid.setSize(-23.33f, 34.51f);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetSizeWidthInvalid() {
    assertEquals(20, ovalValid.getWidth(), 0.01);
    assertEquals(15, ovalValid.getHeight(), 0.01);
    ovalValid.setSize(23.33f, -34.51f);
  }

  @Test
  public void testSetPositionValidInput() {
    assertEquals(position_2, ovalValid.getPosition());
    ovalValid.setPosition(position_3);
    assertEquals(position_3, ovalValid.getPosition());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetPositionNullPosition() {
    assertEquals(position_2, ovalValid.getPosition());
    ovalValid.setPosition(null);
  }

  @Test
  public void testSetPositionXYValidInput() {
    assertEquals(position_2, ovalValid.getPosition());
    ovalValid.setPosition(440.6715f, 370.1868f);
    assertEquals(440.6715, ovalValid.getPosition().getX(), 0.01);
    assertEquals(370.1868, ovalValid.getPosition().getY(), 0.01);
  }

  @Test
  public void testSetPositionXYValidInputSetToNegative() {
    assertEquals(position_2, ovalValid.getPosition());
    ovalValid.setPosition(-440.6715f, -370.1868f);
    assertEquals(-440.6715, ovalValid.getPosition().getX(), 0.01);
    assertEquals(-370.1868, ovalValid.getPosition().getY(), 0.01);
  }

  @Test
  public void testGetColor() {
    assertEquals(color_1, ovalValid.getColor());
    ovalValid.setColor(color_2);
    assertEquals(color_2, ovalValid.getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetColorSetToNull() {
    assertEquals(color_1, ovalValid.getColor());
    ovalValid.setColor(null);
  }

  @Test
  public void testSetColorRGBValid() {
    assertEquals(color_1, ovalValid.getColor());
    ovalValid.setColorRGB(130, 0, 200);
    assertEquals(color_2, ovalValid.getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetColorRGBInvalidR() {
    assertEquals(color_1, ovalValid.getColor());
    ovalValid.setColorRGB(-130, 0, 200);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetColorRGBInvalidG() {
    assertEquals(color_1, ovalValid.getColor());
    ovalValid.setColorRGB(130, -20, 200);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetColorRGBInvalidB() {
    assertEquals(color_1, ovalValid.getColor());
    ovalValid.setColorRGB(130, 20, -200);
  }

  @Test
  public void testGetShapeType() {
    assertEquals(ShapeType.Oval, ovalValid.getShapeType());
  }

  @Test
  public void testEquals() {
    assertEquals(true, ovalValid.equals(ovalValid));
    AShape ovalValid_3
            = new Oval("oval_2", position_3, color_2, 15.55f, 20.55f);
    assertEquals(true, ovalValid_2.equals(ovalValid_3));
    assertEquals(false, ovalValid_2.equals(ovalValid));
    String randomObject = "";
    assertEquals(false, ovalValid_3.equals(randomObject));
  }

  @Test
  public void testHashCode() {
    AShape ovalValid_3
            = new Oval("oval_2", position_3, color_2, 15.55f, 20.55f);
    assertEquals(ovalValid_2.hashCode(), ovalValid_3.hashCode());
    assertEquals(false, ovalValid_3.toString() == ovalValid.toString());
  }

  @Test
  public void testToString() {
    assertEquals("3.0 4.0 20.0 15.0 200 200 200", ovalValid.toString());
    assertEquals("440.6715 370.1868 20.55 15.55 130 0 200", ovalValid_2.toString());
  }
}
