package cs3500.freecell.view;

import java.io.IOException;

import cs3500.freecell.model.FreecellModelState;

/**
 * This class represents the freecell text view.
 * It outputs a string of the current game state of the piles based on the given FreecellModelState
 * given.
 */
public class FreecellTextView implements FreecellView {
  private final FreecellModelState<?> model;
  private final Appendable ap;

  /**
   * Constructs a FreecellTextView with the inputted FreecellModelState and Appendable
   * as parameters.
   * @throws IllegalArgumentException if the given FreecellModelState is null
   */
  public FreecellTextView(FreecellModelState<?> model, Appendable ap) {
    if (model == null) {
      throw new IllegalArgumentException("Arguments can't be null!");
    }
    else if (ap == null) {
      this.ap = System.out;
    }
    else {
      this.ap = ap;
    }
    this.model = model;
  }

  /**
   * Constructs a FreecellTextView with the inputted FreecellModelState as a parameter.
   * @throws IllegalArgumentException if the given FreecellModelState is null
   */
  public FreecellTextView(FreecellModelState<?> model) {
    this(model, null);
  }

  /**
   * Overrides the toString method to output the current game state (where all the cards are).
   * It checks if the inputted SimpleFreecellModel is valid as well.
   * @return the String of the current state of the board of the FreecellModelState.
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public String toString() {
    try {
      model.getNumCardsInCascadePile(0);
    } catch (IllegalStateException e) {
      return "";
    }

    StringBuilder output = new StringBuilder();
    output.append(addFoundationPiles(model));
    output.append(addOpenPiles(model));
    output.append(addCascadePiles(model));
    return output.toString();
  }

  @Override
  public void renderBoard() throws IOException {
    ap.append(this.toString());
  }

  @Override
  public void renderMessage(String message) throws IOException {
    if (message == null || message.equals("")) {
      throw new IllegalArgumentException("Invalid message!");
    }
    ap.append(message);
  }

  /**
   * Returns the String of the current state of the Foundation piles.
   * It also ensures that the formatting is correct for the string.
   * @param m the given FreecellModelState
   * @return the String of the current Foundation piles
   */
  private String addFoundationPiles(FreecellModelState<?> m) {
    String foundationPiles = "";
    for (int i = 0; i < 4; i++) {
      foundationPiles += "F" + (i + 1) + ":";
      for (int j = 0; j < m.getNumCardsInFoundationPile(i); j++) {
        if (j == (m.getNumCardsInFoundationPile(i) - 1)) {
          foundationPiles += " " + m.getFoundationCardAt(i, j).toString();
        }
        else {
          foundationPiles += " " + m.getFoundationCardAt(i, j).toString() + ",";
        }
      }
      foundationPiles += "\n";
    }
    return foundationPiles;
  }

  /**
   * Returns the String of the current state of the Cascade piles.
   * It also ensures that the formatting is correct for the string.
   * @param m the given FreecellModelState
   * @return the String of the current Cascade piles
   */
  private String addCascadePiles(FreecellModelState<?> m) {
    String cascadePiles = "";
    for (int i = 0; i < m.getNumCascadePiles(); i++) {
      cascadePiles += "C" + (i + 1) + ":";
      for (int j = 0; j < m.getNumCardsInCascadePile(i); j++) {
        if (j == (m.getNumCardsInCascadePile(i) - 1) && (i == (m.getNumCascadePiles() - 1))) {
          cascadePiles += " " + m.getCascadeCardAt(i, j).toString();
        }
        else if (j == (m.getNumCardsInCascadePile(i) - 1)) {
          cascadePiles += " " + m.getCascadeCardAt(i, j).toString() + "\n";
        }
        else {
          cascadePiles += " " + m.getCascadeCardAt(i, j).toString() + ",";
        }
      }
      if (m.getNumCardsInCascadePile(i) == 0) {
        if (i != (m.getNumCascadePiles() - 1)) {
          cascadePiles += "\n";
        }
      }
    }
    return cascadePiles;
  }

  /**
   * Returns the String of the current state of the Open piles.
   * It also ensures that the formatting is correct for the string.
   * @param m the given FreecellModelState
   * @return the String of the current Open piles
   */
  private String addOpenPiles(FreecellModelState<?> m) {
    String openPiles = "";
    for (int i = 0; i < m.getNumOpenPiles(); i++) {
      openPiles += "O" + (i + 1) + ":";
      if (m.getNumCardsInOpenPile(i) == 0) {
        openPiles += "\n";
      }
      else {
        openPiles += " " + m.getOpenCardAt(i).toString() + "\n";
      }
    }
    return openPiles;
  }
}
