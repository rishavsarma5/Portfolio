package cs3500.freecell.controller;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.SimpleFreecellModel;

/**
 * This class represents a MainController. This controller is used for debugging purposes. It waits
 * for user input in the console and catches error messages, quits the game, and ends the game if
 * the controller dictates.
 */
public class MainController {

  /**
   * This method is used for debugging purposes as it allows the controller and game to be run in
   * the console.
   * @param args an array of arguments for the main method
   */
  public static void main(String[] args) {
    FreecellModel<Card> model = new SimpleFreecellModel();
    Appendable ap = System.out;
    FreecellController<Card> ctrl = new SimpleFreecellController(model,
            new InputStreamReader(System.in), ap);
    List<Card> riggedDeck = new ArrayList<Card>(Arrays.asList(
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
    ctrl.playGame(riggedDeck, 4, 2, false);
    System.out.println(ap);
  }
}
