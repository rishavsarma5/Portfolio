import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test cases for the multi-move Freecell controller.
 */
public class MultiMoveFreecellControllerTest {
  FreecellModel<Card> model;
  FreecellModel<Card> modelMixed;
  FreecellController<Card> ctrl;
  List<Card> riggedDeck;
  List<Card> riggedDeck2;
  FreecellView view;
  Appendable ap;
  Readable rd;

  /**
   * Initializes the variables in the test class.
   */
  @Before
  public void initData() {
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
    modelMixed = FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE);
    modelMixed.startGame(riggedDeck2, 4, 2, false);
    model.startGame(riggedDeck, 4, 2, false);
    ap = new StringBuilder();
    view = new FreecellTextView(model, ap);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructorNullReadable() {
    SimpleFreecellController controller = new SimpleFreecellController(model, null, ap);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructorNullAppendable() {
    rd = new StringReader("hi");
    SimpleFreecellController controller = new SimpleFreecellController(model, rd, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructorNullModel() {
    rd = new StringReader("hi");
    SimpleFreecellController controller = new SimpleFreecellController(null, rd, ap);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameInvalidDeck() {
    List<Card> deck = new ArrayList<>();
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(deck, 4, 2, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameIllegalCascadePilesMore() {
    initData();
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 14, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameIllegalCascadePilesNeg() {
    initData();
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, -2, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameIllegalOpenPilesMore() {
    initData();
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 7, false);
  }

  @Test
  public void testPlayGameIllegalArgumentsRenderMessage() {
    initData();
    rd = new StringReader("hi");
    ctrl = new SimpleFreecellController(model, rd, ap);
    try {
      ctrl.playGame(riggedDeck, 4, 7, false);
    } catch (IllegalArgumentException e) {
      assertEquals("Could not start game.", ap.toString());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameIllegalOpenPilesNeg() {
    initData();
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, -2, false);
  }

  @Test
  public void testPlayGame() {
    initData();
    rd = new StringReader("C4 13 F1 C4 12 O1 O1 1 F1 C4 11 F1 C3 13 O2 O2 1 O1 C2 13 C3");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 2, false);
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♣\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣\n" +
            "F1: A♣\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: 2♣\n" +
            "O2:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣\n" +
            "F1: A♣, 2♣\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣\n" +
            "F1: A♣, 2♣, 3♣\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣\n" +
            "F1: A♣, 2♣, 3♣\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2: A♦\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣\n" +
            "F1: A♣, 2♣, 3♣\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: A♦\n" +
            "O2:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣\n" +
            "F1: A♣, 2♣, 3♣\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: A♦\n" +
            "O2:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♠\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣\n", ap.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayGameCannotReadFromReadable() {
    initData();
    rd = new BrokenReadable();
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 2, false);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayGameCannotAddToAppendable() {
    initData();
    Appendable output = new BrokenAppendable();
    rd = new StringReader("C4 ");
    ctrl = new SimpleFreecellController(model, rd, output);
    ctrl.playGame(riggedDeck, 4, 2, false);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayGameCannotAddToAppendableAfterMove() {
    initData();
    Appendable output = new BrokenAppendable();
    rd = new StringReader("C4 13 F1");
    ctrl = new SimpleFreecellController(model, rd, output);
    ctrl.playGame(riggedDeck, 4, 2, false);
  }

  @Test
  public void testEndGame() {
    initData();
    String wholeGame = "";
    for (int j = 1; j < 5; j++) {
      for (int i = 13; i > 0; i--) {
        wholeGame += "C" + j + " " + i + " F" + j + " ";
      }
    }
    rd = new StringReader(wholeGame);
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦, 10♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣, 4♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣, 4♣, 5♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣, 10♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4:\n" +
            "Game over.", ap.toString());
  }

  @Test
  public void testEndGameWithInvalidInputs() {
    initData();
    String wholeGame = "";
    for (int j = 1; j < 5; j++) {
      for (int i = 13; i > 0; i--) {
        if (j == 1 && (i == 5 || i == 7)) {
          wholeGame += "d " + " &6 " + "C" + j + " " + i + " F" + j + " ";
        }
        else {
          wholeGame += "C" + j + " " + i + " F" + j + " ";
        }
      }
    }
    rd = new StringReader(wholeGame);
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "Invalid pile type input: d. Please enter a valid source pile and index.\n" +
            "Invalid pile type input: &. Please enter a valid source pile and index.\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "Invalid pile type input: d. Please enter a valid source pile and index.\n" +
            "Invalid pile type input: &. Please enter a valid source pile and index.\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠, 10♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠, J♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠, Q♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2: K♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦, 10♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦, J♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦, Q♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3: K♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4:\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣, 4♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣, 4♣, 5♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣, 10♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣, J♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣, Q♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4: K♣\n" +
            "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n" +
            "O1:\n" +
            "C1:\n" +
            "C2:\n" +
            "C3:\n" +
            "C4:\n" +
            "Game over.", ap.toString());
  }

  @Test
  public void testEndGameWithMultipleCardMoves() {
    initData();
    String wholeGame = "";
    for (int j = 1; j < 3; j++) {
      for (int i = 13; i > 0; i--) {
        wholeGame += "C" + j + " " + i + " F" + j + " ";
      }
    }

    wholeGame += "C3 10 C1 C4 13 O1 C4 11 C2 O1 1 F3 C1 4 F4 C1 3 F3 C2 2 F4 C1 2 F4 C2 1 F3 " +
            "C3 5 C1 C1 1 F3 C4 10 F4 ";

    for (int j = 9; j >= 1; j--) {
      if (j % 2 == 0) {
        wholeGame += "C3 " + j + " F3 ";
        wholeGame += "C4 " + j + " F4 ";
      }
      else {
        wholeGame += "C3 " + j + " F4 ";
        wholeGame += "C4 " + j + " F3 ";
      }
    }

    rd = new StringReader(wholeGame);
    ctrl = new SimpleFreecellController(modelMixed, rd, ap);
    ctrl.playGame(riggedDeck2, 4, 2, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2:\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: 4♣, 3♦, 2♣, A♦\n" +
                    "C2:\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1: A♣\n" +
                    "O2:\n" +
                    "C1: 4♣, 3♦, 2♣, A♦\n" +
                    "C2:\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1: A♣\n" +
                    "O2:\n" +
                    "C1: 4♣, 3♦, 2♣, A♦\n" +
                    "C2: 3♣, 2♦\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: 4♣, 3♦, 2♣, A♦\n" +
                    "C2: 3♣, 2♦\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣\n" +
                    "F4: A♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: 4♣, 3♦, 2♣\n" +
                    "C2: 3♣, 2♦\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣\n" +
                    "F4: A♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: 4♣, 3♦\n" +
                    "C2: 3♣, 2♦\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣\n" +
                    "F4: A♦, 2♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: 4♣, 3♦\n" +
                    "C2: 3♣\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣\n" +
                    "F4: A♦, 2♦, 3♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: 4♣\n" +
                    "C2: 3♣\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣\n" +
                    "F4: A♦, 2♦, 3♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: 4♣\n" +
                    "C2:\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦\n" +
                    "Invalid move was made! Try again!\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣\n" +
                    "F4: A♦, 2♦, 3♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: 4♣\n" +
                    "C2:\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣, 4♣\n" +
                    "F4: A♦, 2♦, 3♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2:\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣, 4♣\n" +
                    "F4: A♦, 2♦, 3♦, 4♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2:\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣, 4♣\n" +
                    "F4: A♦, 2♦, 3♦, 4♦, 5♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2:\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣, 4♣, 5♣\n" +
                    "F4: A♦, 2♦, 3♦, 4♦, 5♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2:\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣\n" +
                    "F4: A♦, 2♦, 3♦, 4♦, 5♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2:\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣\n" +
                    "F4: A♦, 2♦, 3♦, 4♦, 5♦, 6♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2:\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣\n" +
                    "F4: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2:\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣\n" +
                    "F4: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2:\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣\n" +
                    "F4: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2:\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣\n" +
                    "F4: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2:\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣\n" +
                    "F4: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2:\n" +
                    "C3: K♦, Q♣, J♦, 10♣\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣\n" +
                    "F4: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2:\n" +
                    "C3: K♦, Q♣, J♦, 10♣\n" +
                    "C4: K♣, Q♦, J♣, 10♦\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣\n" +
                    "F4: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2:\n" +
                    "C3: K♦, Q♣, J♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣\n" +
                    "F4: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2:\n" +
                    "C3: K♦, Q♣, J♦\n" +
                    "C4: K♣, Q♦, J♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣\n" +
                    "F4: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2:\n" +
                    "C3: K♦, Q♣\n" +
                    "C4: K♣, Q♦, J♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣\n" +
                    "F4: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2:\n" +
                    "C3: K♦, Q♣\n" +
                    "C4: K♣, Q♦\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣\n" +
                    "F4: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2:\n" +
                    "C3: K♦\n" +
                    "C4: K♣, Q♦\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣\n" +
                    "F4: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2:\n" +
                    "C3: K♦\n" +
                    "C4: K♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣\n" +
                    "F4: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2:\n" +
                    "C3:\n" +
                    "C4: K♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                    "F3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n" +
                    "F4: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2:\n" +
                    "C3:\n" +
                    "C4:\n" +
                    "Game over.",
            ap.toString());
  }

  @Test
  public void testValidateMoveQuitAtSourcePile() {
    initData();
    rd = new StringReader("q");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Game quit prematurely.",
            ap.toString());
  }

  @Test
  public void testValidateMoveQuitAtSourcePileIndex() {
    initData();
    rd = new StringReader("FC Q");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid pile index input: C. " +
                    "Please enter a valid source pile index number.\n" +
                    "Game quit prematurely.",
            ap.toString());
  }

  @Test
  public void testValidateMoveQuitAtCardIndex() {
    initData();
    rd = new StringReader("F3 q");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Game quit prematurely.",
            ap.toString());
  }

  @Test
  public void testValidateMoveQuitAtDestPile() {
    initData();
    rd = new StringReader("F3 4 q");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Game quit prematurely.",
            ap.toString());
  }

  @Test
  public void testValidateMoveQuitAtDestPileIndex() {
    initData();
    rd = new StringReader("F3 4 FD Q");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid pile index input: D. " +
                    "Please enter a valid destination pile index number.\n" +
                    "Game quit prematurely.",
            ap.toString());
  }

  @Test
  public void testValidateMoveNoQuitInvalidSourcePile() {
    initData();
    rd = new StringReader("Fq");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid pile index input: q. Please enter a valid source pile index number.\n",
            ap.toString());
  }

  @Test
  public void testValidateMoveNoQuitInvalidWithExtraWords() {
    initData();
    rd = new StringReader("FF qk");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid pile index input: F. " +
                    "Please enter a valid source pile index number.\n" +
                    "Invalid pile index input: qk. " +
                    "Please enter a valid source pile index number.\n",
            ap.toString());
  }

  @Test
  public void testValidateMoveNoQuitMultiQ() {
    initData();
    rd = new StringReader("F3 qqq 4");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid card index input: qqq. Please enter a valid card index.\n",
            ap.toString());
  }

  @Test
  public void testValidateMoveInvalidSourceChar() {
    initData();
    rd = new StringReader("E");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid pile type input: E. Please enter a valid source pile and index.\n",
            ap.toString());
  }

  @Test
  public void testValidateMoveInvalidSourceLowerCase() {
    initData();
    rd = new StringReader("o");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid pile type input: o. Please enter a valid source pile and index.\n",
            ap.toString());
  }

  @Test
  public void testValidateMoveInvalidSourceNum() {
    initData();
    rd = new StringReader("4");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid pile type input: 4. Please enter a valid source pile and index.\n",
            ap.toString());
  }

  @Test
  public void testValidateMoveInvalidSourceDiffInput() {
    initData();
    rd = new StringReader("#g Q");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid pile type input: #. Please enter a valid source pile and index.\n" +
                    "Game quit prematurely.",
            ap.toString());
  }

  @Test
  public void testValidateMoveInvalidSourceIndexChar() {
    initData();
    rd = new StringReader("FC");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid pile index input: C. " +
                    "Please enter a valid source pile index number.\n",
            ap.toString());
  }

  @Test
  public void testValidateMoveInvalidSourceIndexWhenSourceValid() {
    initData();
    rd = new StringReader("FC E F2");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "Invalid pile index input: C. Please enter a valid source pile index number.\n"
            + "Invalid pile index input: E. " +
            "Please enter a valid source pile index number.\n"
            + "Invalid pile index input: F2. " +
            "Please enter a valid source pile index number.\n", ap.toString());
  }

  @Test
  public void testValidateMoveInvalidSourceIndexDiffInput() {
    initData();
    rd = new StringReader("F^ 7 F2");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid pile index input: ^. " +
                    "Please enter a valid source pile index number.\n" +
                    "Invalid card index input: F2. Please enter a valid card index.\n",
            ap.toString());
  }

  @Test
  public void testValidateMoveInvalidSourceIndexSpace() {
    initData();
    rd = new StringReader("F");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid pile index input: . Please enter a valid source pile index number.\n",
            ap.toString());
  }

  @Test
  public void testValidateMoveInvalidPileIndexChar() {
    initData();
    rd = new StringReader("F3 C");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid card index input: C. Please enter a valid card index.\n",
            ap.toString());
  }

  @Test
  public void testValidateMoveInvalidPileIndexDiffInput() {
    initData();
    rd = new StringReader("F3 ^&S q");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid card index input: ^&S. Please enter a valid card index.\n" +
                    "Game quit prematurely.",
            ap.toString());
  }

  @Test
  public void testValidateMoveInvalidDestNum() {
    initData();
    rd = new StringReader("C1 7 2");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid pile type input: 2. " +
                    "Please enter a valid destination pile and index.\n",
            ap.toString());
  }

  @Test
  public void testValidateMoveInvalidDestDiffChar() {
    initData();
    rd = new StringReader("C1 7 J0");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid pile type input: J. " +
                    "Please enter a valid destination pile and index.\n",
            ap.toString());
  }

  @Test
  public void testValidateMoveInvalidDestLowercase() {
    initData();
    rd = new StringReader("C1 7 c");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid pile type input: c. " +
                    "Please enter a valid destination pile and index.\n",
            ap.toString());
  }

  @Test
  public void testValidateMoveInvalidDestDiffInput() {
    initData();
    rd = new StringReader("C1 7 *");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid pile type input: *. " +
                    "Please enter a valid destination pile and index.\n",
            ap.toString());
  }

  @Test
  public void testValidateMoveInvalidDestIndexSpace() {
    initData();
    rd = new StringReader("C1 7 F ");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid pile index input: . " +
                    "Please enter a valid destination pile index number.\n",
            ap.toString());
  }

  @Test
  public void testValidateMoveInvalidDestIndexChar() {
    initData();
    rd = new StringReader("C1 7 FR");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "Invalid pile index input: R. " +
            "Please enter a valid destination pile index number.\n", ap.toString());
  }

  @Test
  public void testValidateMoveInvalidDestIndexWhenDestValid() {
    initData();
    rd = new StringReader("C1 7 FR R");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid pile index input: R. " +
                    "Please enter a valid destination pile index number.\n" +
                    "Invalid pile index input: R. " +
                    "Please enter a valid destination pile index number.\n",
            ap.toString());
  }

  @Test
  public void testValidateMoveInvalidDestIndexDiffInput() {
    initData();
    rd = new StringReader("C1 7 F&h");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "Invalid pile index input: &h. " +
            "Please enter a valid destination pile index number.\n", ap.toString());
  }

  @Test
  public void testValidateMoveValidExtraWordsSource() {
    initData();
    rd = new StringReader("34# C");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid pile type input: 3. Please enter a valid source pile and index.\n" +
                    "Invalid pile index input: . Please enter a valid source pile index number.\n",
            ap.toString());
  }

  @Test
  public void testValidateMoveValidExtraWordsSourceAndSourceIndex() {
    initData();
    rd = new StringReader("haha C%^% 4");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
            "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
            "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
            "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
            "Invalid pile type input: h. Please enter a valid source pile and index.\n" +
            "Invalid pile index input: %^%. " +
            "Please enter a valid source pile index number.\n", ap.toString());
  }

  @Test
  public void testValidateMoveInvalidExtraWordsCardIndex() {
    initData();
    rd = new StringReader("haha C%^% 4 nD 3");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid pile type input: h. Please enter a valid source pile and index.\n" +
                    "Invalid pile index input: %^%. " +
                    "Please enter a valid source pile index number.\n" +
                    "Invalid card index input: nD. Please enter a valid card index.\n",
            ap.toString());
  }

  @Test
  public void testValidateMoveInvalidExtraWordsDest() {
    initData();
    rd = new StringReader("haha C%^% 4 4%^ 3 5%% Oj");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid pile type input: h. Please enter a valid source pile and index.\n" +
                    "Invalid pile index input: %^%. " +
                    "Please enter a valid source pile index number.\n" +
                    "Invalid card index input: 4%^. Please enter a valid card index.\n" +
                    "Invalid pile type input: 5. " +
                    "Please enter a valid destination pile and index.\n" +
                    "Invalid pile index input: j. " +
                    "Please enter a valid destination pile index number.\n",
            ap.toString());
  }

  @Test
  public void testValidateMoveInvalidExtraWordsDestIndex() {
    initData();
    rd = new StringReader("haha C%^% 4 4%^ 3 5%% Oj %ty 3");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid pile type input: h. Please enter a valid source pile and index.\n" +
                    "Invalid pile index input: %^%. " +
                    "Please enter a valid source pile index number.\n" +
                    "Invalid card index input: 4%^. Please enter a valid card index.\n" +
                    "Invalid pile type input: 5. " +
                    "Please enter a valid destination pile and index.\n" +
                    "Invalid pile index input: j. " +
                    "Please enter a valid destination pile index number.\n" +
                    "Invalid pile index input: %ty. " +
                    "Please enter a valid destination pile index number.\n" +
                    "Invalid move was made! Try again!\n" +
                    "F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n",
            ap.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testReadableEmpty() {
    ap = new StringBuilder();
    rd = new StringReader("");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
  }

  @Test
  public void testPlayGameValidMoveInvalidFoundationMove() {
    initData();
    rd = new StringReader("F2 4 O2");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid move was made! Try again!\n" +
                    "F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n",
            ap.toString());
  }

  @Test
  public void testPlayGameValidMoveInvalidOpenMove() {
    initData();
    rd = new StringReader("O2 4 O2");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid move was made! Try again!\n" +
                    "F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n",
            ap.toString());
  }

  @Test
  public void testPlayGameValidMoveInvalidCascadeMove() {
    initData();
    rd = new StringReader("C2 -1 F2");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid move was made! Try again!\n" +
                    "F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n",
            ap.toString());
  }

  @Test
  public void testPlayGameValidMoveInvalidCascadeMove2() {
    initData();
    rd = new StringReader("C5 2 F2");
    ctrl = new SimpleFreecellController(model, rd, ap);
    ctrl.playGame(riggedDeck, 4, 1, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                    "Invalid move was made! Try again!\n" +
                    "F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                    "C4: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n",
            ap.toString());
  }

  @Test
  public void testMoveMultipleCardsMoveToEmptyCascade() {
    initData();
    String readIn = "";
    for (int j = 1; j < 2; j++) {
      for (int i = 13; i > 0; i--) {
        readIn += "C" + j + " " + i + " F" + j + " ";
      }
    }
    readIn += "C4 11 C1";
    rd = new StringReader(readIn);
    ctrl = new SimpleFreecellController(modelMixed, rd, ap);
    ctrl.playGame(riggedDeck2, 4, 2, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: 3♣, 2♦, A♣\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦\n",
            ap.toString());
  }

  @Test
  public void testMoveMultipleCardsMoveToNonEmptyCascade() {
    initData();
    String readIn = "";
    for (int j = 1; j < 2; j++) {
      for (int i = 13; i > 0; i--) {
        readIn += "C" + j + " " + i + " F" + j + " ";
      }
    }

    for (int j = 2; j < 3; j++) {
      for (int i = 13; i > 7; i--) {
        readIn += "C" + j + " " + i + " F" + j + " ";
      }
    }

    readIn += "C4 8 C2";
    rd = new StringReader(readIn);
    ctrl = new SimpleFreecellController(modelMixed, rd, ap);
    ctrl.playGame(riggedDeck2, 4, 2, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣\n",
            ap.toString());
  }

  @Test
  public void testMoveMultipleCardsMoveToNonEmptyWith1FilledOpen() {
    initData();
    String readIn = "";
    for (int j = 1; j < 2; j++) {
      for (int i = 13; i > 0; i--) {
        readIn += "C" + j + " " + i + " F" + j + " ";
      }
    }

    for (int j = 2; j < 3; j++) {
      for (int i = 13; i > 9; i--) {
        readIn += "C" + j + " " + i + " F" + j + " ";
      }
    }

    readIn += "C3 13 O1 C4 10 C2";
    rd = new StringReader(readIn);
    ctrl = new SimpleFreecellController(modelMixed, rd, ap);
    ctrl.playGame(riggedDeck2, 4, 2, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1: A♦\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1: A♦\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♦, 3♣, 2♦, A♣\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣\n",
            ap.toString());
  }

  @Test
  public void testMoveMultipleCardsInvalidMoveToNonEmptyWith1OpenPileFilled() {
    initData();
    String readIn = "";
    for (int j = 1; j < 2; j++) {
      for (int i = 13; i > 0; i--) {
        readIn += "C" + j + " " + i + " F" + j + " ";
      }
    }

    for (int j = 2; j < 3; j++) {
      for (int i = 13; i > 9; i--) {
        readIn += "C" + j + " " + i + " F" + j + " ";
      }
    }

    readIn += "C3 13 O1 C4 7 C2";
    rd = new StringReader(readIn);
    ctrl = new SimpleFreecellController(modelMixed, rd, ap);
    ctrl.playGame(riggedDeck2, 4, 2, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1: A♦\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "Invalid move was made! Try again!\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1: A♦\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n",
            ap.toString());
  }

  @Test
  public void testMoveMultipleCardsInvalidMoveToNonEmptyWithInvalidBuild() {
    initData();
    String readIn = "";
    for (int j = 1; j < 2; j++) {
      for (int i = 13; i > 0; i--) {
        readIn += "C" + j + " " + i + " F" + j + " ";
      }
    }

    for (int j = 2; j < 3; j++) {
      for (int i = 13; i > 9; i--) {
        readIn += "C" + j + " " + i + " F" + j + " ";
      }
    }

    readIn += "C3 13 O1 C3 7 C2";
    rd = new StringReader(readIn);
    ctrl = new SimpleFreecellController(modelMixed, rd, ap);
    ctrl.playGame(riggedDeck2, 4, 2, false);
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥, 9♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥, 10♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥, J♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥, Q♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: K♥\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣, A♦\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1: A♦\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n" +
                    "Invalid move was made! Try again!\n" +
                    "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                    "F2: A♠, 2♠, 3♠, 4♠\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1: A♦\n" +
                    "O2:\n" +
                    "C1:\n" +
                    "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠\n" +
                    "C3: K♦, Q♣, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣, 3♦, 2♣\n" +
                    "C4: K♣, Q♦, J♣, 10♦, 9♣, 8♦, 7♣, 6♦, 5♣, 4♦, 3♣, 2♦, A♣\n",
            ap.toString());
  }
}
