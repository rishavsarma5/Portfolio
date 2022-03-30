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
 * JUnit test cases for the simple freecell model view.
 */
public class FreecellViewTest {
  FreecellModel<Card> model;
  FreecellModel<Card> multiOpenPilesModel;
  FreecellTextView tv;
  FreecellTextView tv2;
  List<Card> deck;
  Appendable ap;
  List<Card> riggedDeck;

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
    model = FreecellModelCreator.create(FreecellModelCreator.GameType.SINGLEMOVE);
    multiOpenPilesModel = FreecellModelCreator.create(FreecellModelCreator.GameType.SINGLEMOVE);
    multiOpenPilesModel.startGame(deck, 4, 2, false);
    tv = new FreecellTextView(model);
    tv2 = new FreecellTextView(multiOpenPilesModel);
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

  @Test
  public void testRenderMessage() {
    initData();
    FreecellView fv = new FreecellTextView(model, ap);
    try {
      fv.renderMessage("tootooroo");
    } catch (IOException e) {
      assertEquals("tootooroo", e.getMessage());
    }
  }

  @Test
  public void testRenderBoardEmpty() {
    initData();
    FreecellView fv = new FreecellTextView(model, ap);
    try {
      fv.renderBoard();
    } catch (IOException e) {
      assertEquals("", e.getMessage());
    }
  }

  @Test
  public void testRenderBoardWithModel() {
    initData();
    FreecellView fv = new FreecellTextView(model, ap);
    model.startGame(riggedDeck, 4, 2, false);
    try {
      fv.renderBoard();
      assertEquals("F1:\n" +
              "F2:\n" +
              "F3:\n" +
              "F4:\n" +
              "O1:\n" +
              "O2:\n" +
              "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
              "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
              "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
              "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣", fv.toString());
    } catch (IOException e) {
      // should not reach here
    }
  }
}
