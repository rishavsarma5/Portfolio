import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.SimpleFreecellModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * JUnit test cases for the simple freecell model.
 */
public class FreecellModelTest {
  ArrayList<String> suits;
  ArrayList<String> ranks;
  List<Card> testDeck;
  List<Card> riggedDeck;
  List<Card> riggedDeck2;
  FreecellModel<Card> model;
  FreecellModel<Card> modelRigged;
  FreecellModel<Card> notStartedModel;
  FreecellModel<Card> notStartedModel2;
  FreecellModel<Card> diffNumPilesModel;
  FreecellModel<Card> diffOpenPilesModel;
  FreecellModel<Card> constructModel;

  /**
   * Initializes the variables in the test class.
   */
  @Before
  public void initData() {
    this.constructModel = FreecellModelCreator.create(FreecellModelCreator.GameType.SINGLEMOVE);
    this.notStartedModel = FreecellModelCreator.create(FreecellModelCreator.GameType.SINGLEMOVE);
    this.notStartedModel2 = FreecellModelCreator.create(FreecellModelCreator.GameType.SINGLEMOVE);
    this.diffNumPilesModel = FreecellModelCreator.create(FreecellModelCreator.GameType.SINGLEMOVE);
    this.diffOpenPilesModel = FreecellModelCreator.create(FreecellModelCreator.GameType.SINGLEMOVE);
    this.model = FreecellModelCreator.create(FreecellModelCreator.GameType.SINGLEMOVE);
    this.testDeck = model.getDeck();
    model.startGame(testDeck, 4, 1, false);
    diffNumPilesModel.startGame(testDeck, 6, 4, false);
    suits = new ArrayList<String>(Arrays.asList("♣", "♠", "♦", "♥"));
    ranks = new ArrayList<String>(Arrays.asList("A", "2", "3", "4", "5", "6", "7", "8",
            "9", "10", "J", "Q", "K"));
    this.modelRigged = FreecellModelCreator.create(FreecellModelCreator.GameType.SINGLEMOVE);
    riggedDeck = new ArrayList<Card>(Arrays.asList(
            new Card("K", "♥"), new Card("K", "♠"), new Card("K", "♦"),
            new Card("K", "♣"), new Card("Q", "♥"), new Card("Q", "♠"),
            new Card("Q", "♦"), new Card("Q", "♣"), new Card("J", "♥"),
            new Card("J", "♠"), new Card("J", "♦"), new Card("J", "♣"),
            new Card("10", "♥"), new Card("10", "♠"),
            new Card("10", "♦"), new Card("10", "♣"),
            new Card("9", "♥"), new Card("9", "♠"), new Card("9", "♦"),
            new Card("9", "♣"), new Card("8", "♥"), new Card("8", "♠"),
            new Card("8", "♦"), new Card("8", "♣"), new Card("7", "♥"),
            new Card("7", "♠"), new Card("7", "♦"), new Card("7", "♣"),
            new Card("6", "♥"), new Card("6", "♠"), new Card("6", "♦"),
            new Card("6", "♣"), new Card("5", "♥"), new Card("5", "♠"),
            new Card("5", "♦"), new Card("5", "♣"), new Card("4", "♥"),
            new Card("4", "♠"), new Card("4", "♦"), new Card("4", "♣"),
            new Card("3", "♥"), new Card("3", "♠"), new Card("3", "♦"),
            new Card("3", "♣"), new Card("2", "♥"), new Card("2", "♠"),
            new Card("2", "♦"), new Card("2", "♣"), new Card("A", "♥"),
            new Card("A", "♠"), new Card("A", "♦"),
            new Card("A", "♣")));
    riggedDeck2 = new ArrayList<Card>(Arrays.asList(
            new Card("K", "♥"), new Card("K", "♠"), new Card("K", "♦"),
            new Card("K", "♣"), new Card("Q", "♥"), new Card("Q", "♠"),
            new Card("Q", "♦"), new Card("Q", "♣"), new Card("J", "♥"),
            new Card("J", "♠"), new Card("J", "♦"), new Card("J", "♣"),
            new Card("10", "♥"), new Card("10", "♠"),
            new Card("10", "♦"), new Card("10", "♣"),
            new Card("9", "♥"), new Card("9", "♠"), new Card("9", "♦"),
            new Card("9", "♣"), new Card("8", "♥"), new Card("8", "♠"),
            new Card("8", "♦"), new Card("8", "♣"), new Card("7", "♥"),
            new Card("7", "♠"), new Card("7", "♦"), new Card("7", "♣"),
            new Card("6", "♥"), new Card("6", "♠"), new Card("6", "♦"),
            new Card("6", "♣"), new Card("5", "♥"), new Card("5", "♠"),
            new Card("5", "♦"), new Card("5", "♣"), new Card("4", "♥"),
            new Card("4", "♠"), new Card("4", "♦"), new Card("4", "♣"),
            new Card("3", "♥"), new Card("3", "♠"), new Card("3", "♦"),
            new Card("3", "♣"), new Card("2", "♥"), new Card("2", "♠"),
            new Card("2", "♦"), new Card("2", "♣"), new Card("A", "♥"),
            new Card("A", "♠"), new Card("A", "♦"),
            new Card("A", "♣")));
    modelRigged.startGame(riggedDeck, 4, 1, false);
    diffOpenPilesModel.startGame(riggedDeck, 6, 4, false);
  }

  @Test
  public void testConstructor() {
    initData();
    assertEquals(testDeck, new SimpleFreecellModel().getDeck());
  }

  @Test
  public void testGetDeck() {
    initData();
    ArrayList<Card> createDeck = new ArrayList<>(52);
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < ranks.size(); j++) {
        Card temp = new Card(ranks.get(j), suits.get(i));
        createDeck.add(temp);
      }
    }
    assertEquals(createDeck, model.getDeck());
  }

  @Test
  public void testStartNotStarted() {
    initData();
    modelRigged.startGame(riggedDeck, 4, 1, false);
    assertEquals(13, modelRigged.getNumCardsInCascadePile(2));
    modelRigged.move(PileType.CASCADE, 2, 12,
            PileType.FOUNDATION, 2);
    assertEquals(12, modelRigged.getNumCardsInCascadePile(2));
    modelRigged.startGame(riggedDeck, 4, 1, false);
    assertEquals(13, modelRigged.getNumCardsInCascadePile(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartInvalidCascadePiles() {
    initData();
    notStartedModel.startGame(testDeck, 3, 1, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartInvalidOpenPiles() {
    initData();
    notStartedModel.startGame(testDeck, 7, -2, true);
  }

  @Test
  public void testStartWorks() {
    initData();
    boolean isShuffled = false;
    notStartedModel.startGame(riggedDeck, 4, 2, true);
    notStartedModel2.startGame(riggedDeck2, 4, 2, false);
    for (int i = 0; i < 52; i++) {
      if (!notStartedModel.getCascadeCardAt(i % 4, i / 4)
              .equals(notStartedModel2.getCascadeCardAt(i % 4, i / 4))) {
        isShuffled = true;
      }
    }
    assertTrue(isShuffled);
  }

  @Test(expected = IllegalStateException.class)
  public void testMoveNotStarted() {
    initData();
    notStartedModel.move(PileType.CASCADE, 2, 0, PileType.OPEN, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFromFoundation() {
    initData();
    model.move(PileType.FOUNDATION, 2, 0, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveTooBigOpenPile() {
    initData();
    model.move(PileType.CASCADE, 2, 12, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 3, 12, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveOutOfDestBounds() {
    initData();
    model.move(PileType.CASCADE, 2, 12, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 3, 12, PileType.OPEN, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveIncorrectCardIndex() {
    initData();
    model.move(PileType.CASCADE, 2, 17, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveIncorrectCardToFoundation1() {
    initData();
    modelRigged.move(PileType.CASCADE, 2, 12,
            PileType.FOUNDATION, 0);
    modelRigged.move(PileType.CASCADE, 1, 12,
            PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveIncorrectCardToFoundation2() {
    initData();
    modelRigged.move(PileType.CASCADE, 2, 12,
            PileType.FOUNDATION, 0);
    modelRigged.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    modelRigged.move(PileType.CASCADE, 0, 11,
            PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveIncorrectCardToFoundation3() {
    initData();
    modelRigged.move(PileType.CASCADE, 2, 12,
            PileType.OPEN, 0);
    modelRigged.move(PileType.CASCADE, 2, 11,
            PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveIncorrectCardToCascade1() {
    initData();
    modelRigged.move(PileType.CASCADE, 2, 12,
            PileType.OPEN, 0);
    modelRigged.move(PileType.CASCADE, 0, 12,
            PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveIncorrectCardToCascade2() {
    initData();
    modelRigged.move(PileType.CASCADE, 2, 12,
            PileType.FOUNDATION, 0);
    modelRigged.move(PileType.CASCADE, 2, 11,
            PileType.FOUNDATION, 0);
    modelRigged.move(PileType.CASCADE, 1, 12,
            PileType.CASCADE, 2);
  }

  @Test
  public void testMoveOpenToFoundation() {
    initData();
    modelRigged.move(PileType.CASCADE, 2, 12, PileType.OPEN, 0);
    assertEquals(1, modelRigged.getNumCardsInOpenPile(0));
    assertEquals(0, modelRigged.getNumCardsInFoundationPile(0));
    assertEquals(new Card("A", "♦"), modelRigged.getOpenCardAt(0));
    modelRigged.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(0, modelRigged.getNumCardsInOpenPile(0));
    assertEquals(1, modelRigged.getNumCardsInFoundationPile(0));
    assertEquals(new Card("A", "♦"),
            modelRigged.getFoundationCardAt(0, 0));
  }

  @Test
  public void testMoveOpenToCascade() {
    initData();
    modelRigged.move(PileType.CASCADE, 2, 12,
            PileType.FOUNDATION, 2);
    modelRigged.move(PileType.CASCADE, 1, 12, PileType.OPEN, 0);
    assertEquals(1, modelRigged.getNumCardsInOpenPile(0));
    assertEquals(12, modelRigged.getNumCardsInCascadePile(2));
    assertEquals(new Card("A", "♠"), modelRigged.getOpenCardAt(0));
    modelRigged.move(PileType.OPEN, 0, 0, PileType.CASCADE, 2);
    assertEquals(0, modelRigged.getNumCardsInOpenPile(0));
    assertEquals(13, modelRigged.getNumCardsInCascadePile(2));
    assertEquals(new Card("A", "♠"),
            modelRigged.getCascadeCardAt(2, 12));
  }

  @Test
  public void testMoveOpenToOpen() {
    initData();
    diffOpenPilesModel.move(PileType.CASCADE, 2, 8,
            PileType.OPEN, 3);
    assertEquals(1, diffOpenPilesModel.getNumCardsInOpenPile(3));
    assertEquals(0, diffOpenPilesModel.getNumCardsInOpenPile(1));
    assertEquals(new Card("A", "♦"), diffOpenPilesModel.getOpenCardAt(3));
    diffOpenPilesModel.move(PileType.OPEN, 3, 0, PileType.OPEN, 1);
    assertEquals(0, diffOpenPilesModel.getNumCardsInOpenPile(3));
    assertEquals(1, diffOpenPilesModel.getNumCardsInOpenPile(1));
    assertEquals(new Card("A", "♦"), diffOpenPilesModel.getOpenCardAt(1));
  }

  @Test
  public void testMoveCascadeToOpen() {
    initData();
    assertEquals(8, diffOpenPilesModel.getNumCardsInCascadePile(5));
    assertEquals(0, diffOpenPilesModel.getNumCardsInOpenPile(1));
    assertEquals(new Card("2", "♣"),
            diffOpenPilesModel.getCascadeCardAt(5, 7));
    diffOpenPilesModel.move(PileType.CASCADE, 5, 7,
            PileType.OPEN, 1);
    assertEquals(1, diffOpenPilesModel.getNumCardsInOpenPile(1));
    assertEquals(7, diffOpenPilesModel.getNumCardsInCascadePile(5));
    assertEquals(new Card("2", "♣"), diffOpenPilesModel.getOpenCardAt(1));
  }

  @Test
  public void testMoveCascadeToFoundation() {
    initData();
    assertEquals(13, modelRigged.getNumCardsInCascadePile(3));
    assertEquals(0, modelRigged.getNumCardsInFoundationPile(2));
    assertEquals(new Card("A", "♣"), modelRigged.getCascadeCardAt(3, 12));
    modelRigged.move(PileType.CASCADE, 3, 12,
            PileType.FOUNDATION, 2);
    assertEquals(12, modelRigged.getNumCardsInCascadePile(3));
    assertEquals(1, modelRigged.getNumCardsInFoundationPile(2));
    assertEquals(new Card("A", "♣"),
            modelRigged.getFoundationCardAt(2, 0));

    assertEquals(new Card("2", "♣"), modelRigged.getCascadeCardAt(3, 11));
    modelRigged.move(PileType.CASCADE, 3, 11,
            PileType.FOUNDATION, 2);
    assertEquals(11, modelRigged.getNumCardsInCascadePile(3));
    assertEquals(2, modelRigged.getNumCardsInFoundationPile(2));
    assertEquals(new Card("2", "♣"),
            modelRigged.getFoundationCardAt(2, 1));
  }

  @Test
  public void testMoveCascadeToCascade() {
    initData();
    modelRigged.move(PileType.CASCADE, 3, 12,
            PileType.FOUNDATION, 2);
    assertEquals(12, modelRigged.getNumCardsInCascadePile(3));
    assertEquals(13, modelRigged.getNumCardsInCascadePile(2));
    assertEquals(new Card("A", "♦"), modelRigged.getCascadeCardAt(2, 12));
    modelRigged.move(PileType.CASCADE, 2, 12,
            PileType.CASCADE, 3);
    assertEquals(13, modelRigged.getNumCardsInCascadePile(3));
    assertEquals(12, modelRigged.getNumCardsInCascadePile(2));
    assertEquals(new Card("A", "♦"), modelRigged.getCascadeCardAt(3, 12));
  }

  @Test
  public void testIsGameOver() {
    initData();
    assertFalse(modelRigged.isGameOver());
    for (int i = 0; i < modelRigged.getNumCascadePiles(); i++) {
      for (int j = 12; j >= 0; j--) {
        modelRigged.move(PileType.CASCADE, i, j, PileType.FOUNDATION, i);
      }
    }
    assertTrue(modelRigged.isGameOver());
  }

  @Test
  public void testIsGameOverWhenGameIsRunning() {
    initData();
    for (int i = 0; i < modelRigged.getNumCascadePiles(); i++) {
      for (int j = 12; j >= 5; j--) {
        modelRigged.move(PileType.CASCADE, i, j, PileType.FOUNDATION, i);
      }
    }
    assertFalse(modelRigged.isGameOver());
  }

  @Test
  public void testGetNumCardsInFoundationPileWorking() {
    initData();
    assertEquals(0, modelRigged.getNumCardsInFoundationPile(3));
    for (int i = 0; i < modelRigged.getNumCascadePiles(); i++) {
      for (int j = 12; j >= 5; j--) {
        modelRigged.move(PileType.CASCADE, i, j, PileType.FOUNDATION, i);
      }
    }
    assertEquals(8, modelRigged.getNumCardsInFoundationPile(3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInFoundationPile1() {
    initData();
    modelRigged.getNumCardsInFoundationPile(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInFoundationPile2() {
    initData();
    for (int i = 0; i < modelRigged.getNumCascadePiles(); i++) {
      for (int j = 12; j >= 5; j--) {
        modelRigged.move(PileType.CASCADE, i, j, PileType.FOUNDATION, i);
      }
    }
    modelRigged.getNumCardsInFoundationPile(5);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInFoundationPile3() {
    initData();
    notStartedModel.getNumCardsInFoundationPile(0);
  }

  @Test
  public void testGetNumCardsInCascadePileWorking() {
    initData();
    assertEquals(13, modelRigged.getNumCardsInCascadePile(1));
    modelRigged.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 0);
    assertEquals(12, modelRigged.getNumCardsInCascadePile(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInCascadePile1() {
    initData();
    modelRigged.getNumCardsInCascadePile(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInCascadePile2() {
    initData();
    for (int i = 0; i < modelRigged.getNumCascadePiles(); i++) {
      for (int j = 12; j >= 5; j--) {
        modelRigged.move(PileType.CASCADE, i, j, PileType.FOUNDATION, i);
      }
    }
    modelRigged.getNumCardsInCascadePile(5);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInCascadePile3() {
    initData();
    notStartedModel.getNumCardsInCascadePile(0);
  }

  @Test
  public void testGetNumCardsInOpenPileWorking() {
    initData();
    assertEquals(0, modelRigged.getNumCardsInOpenPile(0));
    modelRigged.move(PileType.CASCADE, 3, 4, PileType.OPEN, 0);
    assertEquals(1, modelRigged.getNumCardsInOpenPile(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInOpenPile1() {
    initData();
    modelRigged.getNumCardsInOpenPile(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInOpenPile2() {
    initData();
    for (int i = 0; i < modelRigged.getNumCascadePiles(); i++) {
      for (int j = 12; j >= 5; j--) {
        modelRigged.move(PileType.CASCADE, i, j, PileType.FOUNDATION, i);
      }
    }
    modelRigged.getNumCardsInOpenPile(5);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInOpenPile3() {
    initData();
    notStartedModel.getNumCardsInOpenPile(0);
  }

  @Test
  public void testGetNumOpenPiles() {
    initData();
    assertEquals(4, diffNumPilesModel.getNumOpenPiles());
    assertEquals(-1, notStartedModel.getNumOpenPiles());
  }

  @Test
  public void testGetNumCascadePiles() {
    initData();
    assertEquals(6, diffNumPilesModel.getNumCascadePiles());
    assertEquals(-1, notStartedModel.getNumCascadePiles());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetFoundationCardAtError1() {
    initData();
    notStartedModel.getFoundationCardAt(3, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtError2() {
    initData();
    modelRigged.getFoundationCardAt(-1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtError3() {
    initData();
    modelRigged.getFoundationCardAt(6, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtError4() {
    initData();
    modelRigged.getFoundationCardAt(2, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtError5() {
    initData();
    modelRigged.getFoundationCardAt(2, 16);
  }

  @Test
  public void testGetFoundationCardAtWorks() {
    initData();
    modelRigged.move(PileType.CASCADE, 3, 12,
            PileType.FOUNDATION, 2);
    assertEquals(new Card("A", "♣"),
            modelRigged.getFoundationCardAt(2, 0));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCascadeCardAtError1() {
    initData();
    notStartedModel.getCascadeCardAt(3, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtError2() {
    initData();
    modelRigged.getCascadeCardAt(-1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtError3() {
    initData();
    modelRigged.getCascadeCardAt(6, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtError4() {
    initData();
    assertNull(modelRigged.getCascadeCardAt(2, -1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtError5() {
    initData();
    assertNull(null, modelRigged.getCascadeCardAt(2, 16));
  }

  @Test
  public void testGetCascadeCardAtWorks() {
    initData();
    assertEquals(new Card("10", "♦"),
            modelRigged.getCascadeCardAt(2, 3));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetOpenCardAtError1() {
    initData();
    notStartedModel.getOpenCardAt(3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOpenCardAtError2() {
    initData();
    modelRigged.getOpenCardAt(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOpenCardAtError3() {
    initData();
    modelRigged.getOpenCardAt(6);
  }

  @Test
  public void testGetOpenCardAtWorks() {
    initData();
    modelRigged.move(PileType.CASCADE, 3, 12,
            PileType.OPEN, 0);
    assertEquals(new Card("A", "♣"),
            modelRigged.getOpenCardAt(0));
  }
}
