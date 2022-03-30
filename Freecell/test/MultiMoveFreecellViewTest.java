import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.PileType;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * JUnit test cases for the multi-move freecell model view.
 */
public class MultiMoveFreecellViewTest {
  FreecellModel<Card> model;
  FreecellModel<Card> modelRigged;
  FreecellModel<Card> multiOpenPilesModel;
  FreecellTextView tv;
  FreecellTextView tv2;
  FreecellTextView tvRigged;
  List<Card> deck;
  List<Card> riggedDeck;
  List<Card> riggedDeck2;

  /**
   * Initializes the variables in the test class.
   */
  @Before
  public void initData() {
    deck = new ArrayList<Card>(Arrays.asList(
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
            new Card("Q", "♣"), new Card("Q", "♦"), new Card("J", "♥"),
            new Card("J", "♠"), new Card("J", "♦"), new Card("J", "♣"),
            new Card("10", "♥"), new Card("10", "♠"),
            new Card("10", "♣"), new Card("10", "♦"),
            new Card("9", "♥"), new Card("9", "♠"), new Card("9", "♦"),
            new Card("9", "♣"), new Card("8", "♥"), new Card("8", "♠"),
            new Card("8", "♣"), new Card("8", "♦"), new Card("7", "♥"),
            new Card("7", "♠"), new Card("7", "♦"), new Card("7", "♣"),
            new Card("6", "♥"), new Card("6", "♠"), new Card("6", "♣"),
            new Card("6", "♦"), new Card("5", "♥"), new Card("5", "♠"),
            new Card("5", "♦"), new Card("5", "♣"), new Card("4", "♥"),
            new Card("4", "♠"), new Card("4", "♣"), new Card("4", "♦"),
            new Card("3", "♥"), new Card("3", "♠"), new Card("3", "♦"),
            new Card("3", "♣"), new Card("2", "♥"), new Card("2", "♠"),
            new Card("2", "♣"), new Card("2", "♦"), new Card("A", "♥"),
            new Card("A", "♠"), new Card("A", "♦"),
            new Card("A", "♣")));
    model = FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE);
    modelRigged = FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE);
    multiOpenPilesModel = FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE);
    multiOpenPilesModel.startGame(deck, 4, 2, false);
    modelRigged.startGame(riggedDeck2, 4, 2, false);
    tv = new FreecellTextView(model);
    tv2 = new FreecellTextView(multiOpenPilesModel);
    tvRigged = new FreecellTextView(modelRigged);
  }

  @Test
  public void testConstructor() {
    initData();
    assertEquals("", new FreecellTextView(model).toString());
    model.startGame(deck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣",
            new FreecellTextView(model).toString());
  }

  @Test
  public void testToStringStartGameException() {
    initData();
    try {
      model.startGame(deck, -1, 1, false);
    } catch (IllegalArgumentException e) {
      assertEquals("", tv.toString());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorError() {
    new FreecellTextView(null);
  }

  @Test
  public void testToStringInitial() {
    initData();
    assertEquals("", tv.toString());
    model.startGame(deck, 4, 1, false);
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣", tv.toString());
  }

  @Test
  public void testToStringMidGame() {
    initData();
    model.startGame(deck, 4, 1, false);
    model.move(PileType.CASCADE, 3, 12, PileType.FOUNDATION, 2);
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3: A♣\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣", tv.toString());
    model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3: A♣\n" +
            "F4:\n" +
            "O1: A♥\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣", tv.toString());
    model.move(PileType.CASCADE, 1, 12, PileType.CASCADE, 0);
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3: A♣\n" +
            "F4:\n" +
            "O1: A♥\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♠\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣", tv.toString());
    model.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
    assertEquals("F1: A♥\n" +
            "F2:\n" +
            "F3: A♣\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♠\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣", tv.toString());
    model.move(PileType.CASCADE, 2, 12, PileType.OPEN, 0);
    model.move(PileType.OPEN, 0, 0, PileType.CASCADE, 1);
    assertEquals("F1: A♥\n" +
            "F2:\n" +
            "F3: A♣\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♠\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♦\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣", tv.toString());
    multiOpenPilesModel.move(PileType.CASCADE, 2, 12,
            PileType.OPEN, 0);
    multiOpenPilesModel.move(PileType.OPEN, 0, 0,
            PileType.OPEN, 1);
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2: A♦\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣", tv2.toString());
  }

  @Test
  public void testToStringMidGameMultiMoves2EmptyPiles0EmptyCascade() {
    initData();
    for (int j = 12; j >= 10; j--) {
      modelRigged.move(PileType.CASCADE, 0, j, PileType.FOUNDATION, 0);
    }
    modelRigged.move(PileType.CASCADE, 3, 10, PileType.CASCADE, 0);
    assertEquals("F1: A♥, 2♥, 3♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♣, 2♦, A♣\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
            "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦", tvRigged.toString());
  }

  @Test
  public void testToStringMidGameMultiMovesToNonEmptyPile2EmptyPiles1EmptyCascade() {
    initData();
    for (int j = 12; j >= 0; j--) {
      modelRigged.move(PileType.CASCADE, 0, j, PileType.FOUNDATION, 0);
    }
    for (int i = 12; i >= 9; i--) {
      modelRigged.move(PileType.CASCADE, 1, i, PileType.FOUNDATION, 1);
    }
    modelRigged.move(PileType.CASCADE, 3, 9, PileType.CASCADE, 1);
    assertEquals("F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♦, 3♣, 2♦, A♣\n" +
            "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
            "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣", tvRigged.toString());
  }

  @Test
  public void testToStringMidGameMultiMovesToEmptyPile2EmptyPiles1EmptyCascade() {
    initData();
    for (int j = 12; j >= 0; j--) {
      modelRigged.move(PileType.CASCADE, 0, j, PileType.FOUNDATION, 0);
    }
    modelRigged.move(PileType.CASCADE, 3, 11, PileType.CASCADE, 0);
    assertEquals("F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "C1: 2♦, A♣\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
            "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣", tvRigged.toString());
  }

  @Test
  public void testToStringMidGameMultiMovesToNonEmptyPileFilledOpenPile() {
    initData();
    for (int j = 12; j >= 0; j--) {
      modelRigged.move(PileType.CASCADE, 0, j, PileType.FOUNDATION, 0);
    }
    modelRigged.move(PileType.CASCADE, 2, 12, PileType.OPEN, 0);
    for (int i = 12; i >= 9; i--) {
      modelRigged.move(PileType.CASCADE, 1, i, PileType.FOUNDATION, 1);
    }
    modelRigged.move(PileType.CASCADE, 3, 9, PileType.CASCADE, 1);
    assertEquals("F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: A♦\n" +
            "O2:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♦, 3♣, 2♦, A♣\n" +
            "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣\n" +
            "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣", tvRigged.toString());
  }

  @Test
  public void testToStringMidGameMultiMovesToEmptyPileFilledOpenPile() {
    initData();
    for (int j = 12; j >= 0; j--) {
      modelRigged.move(PileType.CASCADE, 0, j, PileType.FOUNDATION, 0);
    }
    modelRigged.move(PileType.CASCADE, 2, 12, PileType.OPEN, 0);
    for (int i = 12; i >= 9; i--) {
      modelRigged.move(PileType.CASCADE, 1, i, PileType.FOUNDATION, 1);
    }
    modelRigged.move(PileType.CASCADE, 3, 11, PileType.CASCADE, 0);
    assertEquals("F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: A♦\n" +
            "O2:\n" +
            "C1: 2♦, A♣\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠\n" +
            "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣\n" +
            "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣", tvRigged.toString());
  }

  @Test
  public void testToStringEndGame() {
    initData();
    model.startGame(deck, 4, 1, false);
    for (int i = 0; i < model.getNumCascadePiles(); i++) {
      for (int j = 12; j >= 0; j--) {
        model.move(PileType.CASCADE, i, j, PileType.FOUNDATION, i);
      }
    }
    assertEquals("F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4:", tv.toString());
  }

  @Test
  public void testIOExceptionAppendableRenderBoard() {
    initData();
    Appendable output = new BrokenAppendable();
    FreecellView fv = new FreecellTextView(model, output);
    try {
      fv.renderBoard();
      fail("Expected IOException did not happen!");
    } catch (IOException e) {
      assertEquals("The appendable failed!", e.getMessage());
    }
  }

  @Test
  public void testIOExceptionAppendableRenderMessage() {
    initData();
    Appendable output = new BrokenAppendable();
    FreecellView fv = new FreecellTextView(model, output);
    try {
      fv.renderMessage("tootooroo");
      fail("Expected IOException did not happen!");
    } catch (IOException e) {
      assertEquals("The appendable failed!", e.getMessage());
    }
  }
}


