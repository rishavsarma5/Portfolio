import org.junit.Test;


import model.ShapeType;

import static org.junit.Assert.assertEquals;

/**
 * Test class for ShapeType.
 */
public class ShapeTypeTest {

  @Test
  public void testShapeTypeToString() {
    assertEquals("ellipse", ShapeType.Oval.toString());
    assertEquals("rectangle",ShapeType.Rectangle.toString());
  }

}