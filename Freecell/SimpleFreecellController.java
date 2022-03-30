package cs3500.freecell.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.view.FreecellTextView;

/**
 * This class represents a SimpleFreecellController. It handles I/O operations and playing the game,
 * and controlling the view and state of the game.
 */
public class SimpleFreecellController implements FreecellController<Card> {
  private final FreecellModel<Card> model;
  private final Readable rd;
  private final Appendable ap;

  /**
   * Constructs a SimpleFreecellController object that has the provided model, readable,
   * and appendable.
   *
   * @param model the given FreecellModel
   * @param rd    the provided readable to intake user input
   * @param ap    the provided appendable to output messages and the view
   */
  public SimpleFreecellController(FreecellModel<Card> model, Readable rd, Appendable ap) {
    if (model == null || rd == null || ap == null) {
      throw new IllegalArgumentException("Inputs are null!");
    }
    this.model = model;
    this.rd = rd;
    this.ap = ap;
  }

  @Override
  public void playGame(List<Card> deck, int numCascades, int numOpens, boolean shuffle) {
    FreecellTextView view = new FreecellTextView(model, ap);

    if (deck == null) {
      throw new IllegalArgumentException("Deck is invalid!");
    }

    try {
      model.startGame(deck, numCascades, numOpens, shuffle);
    } catch (IllegalArgumentException e) {
      tryRenderMessage("Could not start game.", view);
      return;
    }

    tryRenderBoard(view);
    validateMove(view);
  }

  /**
   * Waits for the input from the user for the duration of the game until either the user
   * prematurely quits or the game ends. Validates the input from the user and tries to
   * successfully complete moves in the model.
   *
   * @param view the FreecellTextView of the game
   * @throws IllegalArgumentException if any of the inputs are invalid indexes
   * @throws IllegalStateException    if renderMessage() can not print the message to the console
   */
  private void validateMove(FreecellTextView view) {
    Scanner scan = new Scanner(rd);
    PileType sourcePile = null;
    PileType destPile = null;
    int sourcePileIndex = -1;
    int cardIndex = -1;
    int destPileIndex = -1;
    int numValids = 0;
    String input;

    if (!scan.hasNext()) {
      throw new IllegalStateException("The given readable is empty!");
    }

    while (!model.isGameOver() && scan.hasNext()) {
      if (!scan.hasNext()) {
        throw new IllegalStateException("Cannot read from readable!");
      }

      input = scan.next();

      if (hasQuit(input)) {
        tryRenderMessage("Game quit prematurely.", view);
        return;
      }

      if (numValids == 0) {
        if (sourcePile != null) {
          sourcePileIndex = getValidPileInputOnly(input,
                  view, "source");
        } else {
          sourcePile = getValidPile(input,
                  view, "source");
          if (sourcePile != null) {
            sourcePileIndex = getValidPileInput(input,
                    view, "source");
          }
        }

        if (sourcePile != null && sourcePileIndex != -1) {
          numValids++;
        }
      } else if (numValids == 1) {
        try {
          cardIndex = validateIndex(input);
        } catch (IllegalArgumentException e) {
          tryRenderMessage("Invalid card index input: " + input
                  + ". Please enter a valid card index.\n", view);
        }
        if (cardIndex != -1) {
          numValids++;
        }
      } else if (numValids == 2) {
        if (destPile != null) {
          destPileIndex = getValidPileInputOnly(input,
                  view, "destination");
        } else {
          destPile = getValidPile(input,
                  view, "destination");
          if (destPile != null) {
            destPileIndex = getValidPileInput(input,
                    view, "destination");
          }
        }

        if (destPile != null && destPileIndex != -1) {
          numValids++;
        }
      }

      if (numValids == 3) {
        try {
          model.move(sourcePile, sourcePileIndex, cardIndex, destPile, destPileIndex);
        } catch (IllegalArgumentException e) {
          tryRenderMessage("Invalid move was made! Try again!", view);
          tryRenderMessage("\n", view);
        }

        tryRenderBoard(view);

        if (model.isGameOver()) {
          tryRenderMessage("Game over.", view);
        }

        sourcePile = null;
        destPile = null;
        sourcePileIndex = -1;
        cardIndex = -1;
        destPileIndex = -1;
        numValids = 0;
      }
    }
  }

  /**
   * Tries to render the given message to the destination from the view and catches the exception if
   * thrown.
   *
   * @param message the message to be outputted as a String
   * @param view    the given FreecellTextView
   * @throws IllegalStateException if the message cannot be outputted to the destination
   */
  protected void tryRenderMessage(String message, FreecellTextView view) {
    try {
      view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("Could not render message.");
    }
  }

  /**
   * Tries to render the given board that represents the current game state of the FreecellModel to
   * the destination from the view and catches the exception if thrown. Also appends a new line at
   * the end if successful.
   *
   * @param view the given FreecellTextView
   * @throws IllegalStateException if the board state cannot be outputted to the destination
   */
  protected void tryRenderBoard(FreecellTextView view) {
    try {
      view.renderBoard();
      ap.append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Could not render board.");
    }
  }

  /**
   * Determines if the input is a valid index, namely an integer.
   *
   * @param input the string input from the user
   * @return the converted int type of the input
   * @throws IllegalArgumentException if the input is invalid
   */
  private int validateIndex(String input) {
    try {
      return Integer.parseInt(input) - 1;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid index input!");
    }
  }

  /**
   * Determines if the user wants to quit the game by inputting "q" or "Q".
   *
   * @param input the string input from user input
   * @return true if the input is "q" or "Q" and false if not
   */
  private boolean hasQuit(String input) {
    return input.equalsIgnoreCase("Q");
  }

  /**
   * Determines if input is a valid pile type identifier.
   *
   * @param input the string input is one char
   * @return the enum type for valid inputs and null for invalid inputs
   */
  private PileType validatePileType(String input) {
    switch (input) {
      case "F":
        return PileType.FOUNDATION;
      case "O":
        return PileType.OPEN;
      case "C":
        return PileType.CASCADE;
      default:
        return null;
    }
  }

  /**
   * Returns valid source pile or destination pile values from user input and throws error messages
   * if the inputs are invalid. Will wait for new user input that is valid for the source pile or
   * destination pile.
   *
   * @param input the string input from the user
   * @param view  the given FreecellTextView
   * @param type  the source of destination type identifier as a String
   * @return the valid source or destination pile as a PileType or null
   */
  protected PileType getValidPile(String input,
                                  FreecellTextView view, String type) {
    PileType pile = validatePileType(input.substring(0, 1));

    if (pile == null) {
      tryRenderMessage("Invalid pile type input: " + input.substring(0, 1)
              + ". Please enter a valid " + type + " pile and index.\n", view);
      return null;
    } else {
      return pile;
    }
  }

  /**
   * Returns valid source pile index or destination pile index values from user input if the source
   * or destination pile input is valid and throws error messages if the inputs are invalid.
   * Will wait for new user input that is valid for the source pile index or destination pile index
   * only.
   *
   * @param input the string input from the user
   * @param view  the given FreecellTextView
   * @param type  the source of destination type identifier as a String
   * @return the valid source or destination pile index as an int or -1
   */
  protected int getValidPileInputOnly(String input,
                                      FreecellTextView view, String type) {
    try {
      return validateIndex(input);
    } catch (IllegalArgumentException e) {
      tryRenderMessage("Invalid pile index input: " + input
              + ". Please enter a valid " + type + " pile index number.\n", view);
      return -1;
    }
  }

  /**
   * Returns valid source pile index or destination pile index values from user input if the source
   * or destination pile input is valid and throws error messages if the inputs are invalid.
   * Will wait for new user input that is valid for the source pile index or destination pile index
   * only.
   *
   * @param input the string input from the user
   * @param view  the given FreecellTextView
   * @param type  the source of destination type identifier as a String
   * @return the valid source or destination pile index as an int or -1
   */
  protected int getValidPileInput(String input,
                                  FreecellTextView view, String type) {
    try {
      return validateIndex(input.substring(1));
    } catch (IllegalArgumentException e) {
      tryRenderMessage("Invalid pile index input: " + input.substring(1)
              + ". Please enter a valid " + type + " pile index number.\n", view);
      return -1;
    }
  }
}

