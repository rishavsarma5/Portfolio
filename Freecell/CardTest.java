import org.junit.Before;
import org.junit.Test;

import cs3500.freecell.model.Card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * JUnit test cases for the Card class.
 */
public class CardTest {
  Card card1;
  Card card2;
  Card card3;
  Card card4;
  Card ace;
  Card queen;
  Card king;
  Card sevenHearts;

  /**
   * Initializes the variables in the test class.
   */
  @Before
  public void initData() {
    card1 = new Card("J", "♠");
    card2 = card1;
    card3 = new Card("J", "♠");
    card4 = new Card("J", "♦");
    ace = new Card("A", "♥");
    queen = new Card("Q", "♣");
    king = new Card("K", "♦");
    sevenHearts = new Card("7", "♥");
  }

  @Test
  public void testConstructor() {
    initData();
    assertEquals("J♠", new Card("J", "♠").toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadRankConstructor() {
    Card temp = new Card("-5", "♠");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadSuitConstructor() {
    Card temp = new Card("J", "h");
  }

  @Test
  public void testSuitColor() {
    initData();
    assertEquals(Card.Color.BLACK, card1.suitColor());
    assertEquals(Card.Color.RED, card4.suitColor());
  }

  @Test
  public void testRankNum() {
    initData();
    assertEquals(1, ace.rankNum());
    assertEquals(11, card1.rankNum());
    assertEquals(12, queen.rankNum());
    assertEquals(13, king.rankNum());
    assertEquals(7, sevenHearts.rankNum());
  }

  @Test
  public void testEquals() {
    initData();
    assertTrue(card1.equals(card3));
    assertFalse(card1.equals(card4));
    assertTrue(card1.equals(card2));
  }

  @Test
  public void testHash() {
    initData();
    assertNotEquals(card1.hashCode(), card4.hashCode());
    assertEquals(card1.hashCode(), card3.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("7♣", new Card("7", "♣").toString());
  }

  @Test
  public void testValidSuit() {
    initData();
    assertTrue(card1.validSuit(card3));
    assertFalse(card1.validSuit(card4));
  }

  @Test
  public void testIsValidSuit() {
    initData();
    assertTrue(sevenHearts.isValidSuit());
  }

  @Test
  public void testIsValidRank() {
    initData();
    assertTrue(sevenHearts.isValidRank());
  }
}
