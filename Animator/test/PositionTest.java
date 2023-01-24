import org.junit.Test;


import model.Position;

import static org.junit.Assert.assertEquals;

/**
 * Test class for Position.
 */
public class PositionTest {
  private Position position_1 = new Position(0,0);
  private Position position_2 = new Position(3,4);
  private Position position_3 = new Position(440.6715f,370.1868f);

  @Test
  public void testGetX() {
    assertEquals(0,position_1.getX(),0.01);
    assertEquals(3,position_2.getX(),0.01);
    assertEquals(440.6715,position_3.getX(),0.01);
  }

  @Test
  public void testGetY() {
    assertEquals(0,position_1.getY(),0.01);
    assertEquals(4,position_2.getY(),0.01);
    assertEquals(370.1868,position_3.getY(),0.01);
  }

  @Test
  public void testToString() {
    assertEquals("0.0 0.0",position_1.toString());
    assertEquals("3.0 4.0",position_2.toString());
    assertEquals("440.6715 370.1868",position_3.toString());
  }

}