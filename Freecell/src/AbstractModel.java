package cs3500.freecell.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * This class represents an abstract model.
 * It handles abstracting the code for the FreecellModel interface.
 */
public abstract class AbstractModel implements FreecellModel<Card> {
  protected final List<List<Card>> cascadePiles;
  protected final List<List<Card>> openPiles;
  protected final List<List<Card>> foundationPiles;
  protected boolean started;

  /**
   * Constructs a AbstractModel with no parameters.
   */
  protected AbstractModel() {
    this.cascadePiles = new ArrayList<>();
    this.openPiles = new ArrayList<>();
    this.foundationPiles = new ArrayList<>(4);
    this.started = false;
  }

  /**
   * Return a valid and complete deck of cards for a game of Freecell. There is
   * no restriction imposed on the ordering of these cards in the deck. An
   * invalid deck is defined as a deck that has one or more of these flaws: <ul>
   * <li>It does not have 52 cards</li> <li>It has duplicate cards</li> <li>It
   * has at least one invalid card (invalid suit or invalid number) </li> </ul>
   *
   * @return the deck of cards as a list
   */
  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<Card>(52);
    List<String> suits = new ArrayList<String>(Arrays.asList("♣", "♠", "♦", "♥"));

    for (int i = 0; i < 4; i++) {
      for (int j = 1; j < 14; j++) {
        if (j == 1 || j == 11 || j == 12 || j == 13) {
          Card temp = new Card(letterCards(j), suits.get(i));
          deck.add(temp);
        } else {
          Card temp = new Card(String.valueOf(j), suits.get(i));
          deck.add(temp);
        }
      }
    }
    return deck;
  }

  /**
   * Determines if there are any cards in the deck that have an invalid rank or suit.
   *
   * @param deck the deck of cards as a list
   * @return true if any card in the deck is invalid and false if not
   */
  protected boolean anyInvalidCards(List<Card> deck) {
    for (Card temp : deck) {
      if (!(temp.isValidSuit()) || !(temp.isValidRank())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Determines if there are any cards in the deck that are duplicates.
   *
   * @param deck the deck of cards as a list
   * @return true if any cards in the deck are duplicates and false if not
   */
  protected boolean checkForDuplicates(List<Card> deck) {
    List<Card> workList = new ArrayList<>();

    for (Card c : deck) {
      if (workList.contains(c)) {
        return true;
      } else {
        workList.add(c);
      }
    }
    return false;
  }

  /**
   * Returns the string version of the rank of a card.
   * 1 is "A", 11 is "J", 12 is "Q", 13 is "K".
   *
   * @param n the rank of the card as an int
   * @return the string version of the rank
   */
  protected String letterCards(int n) {
    String letterCard;

    switch (n) {
      case 1:
        letterCard = "A";
        break;
      case 11:
        letterCard = "J";
        break;
      case 12:
        letterCard = "Q";
        break;
      case 13:
        letterCard = "K";
        break;
      default:
        letterCard = "";
        break;
    }
    return letterCard;
  }

  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle) {
    if (shuffle) {
      Collections.shuffle(deck);
    }

    if (numCascadePiles < 4 || numOpenPiles < 1 || deck.size() != 52 || checkForDuplicates(deck)
            || anyInvalidCards(deck)) {
      throw new IllegalArgumentException("Invalid arguments!");
    }

    if (!started) {
      started = true;
    } else {
      foundationPiles.clear();
      cascadePiles.clear();
      openPiles.clear();
    }

    for (int f = 0; f < numCascadePiles; f++) {
      cascadePiles.add(new ArrayList<>());
    }

    for (int g = 0; g < numOpenPiles; g++) {
      openPiles.add(new ArrayList<>());
    }

    for (int h = 0; h < 4; h++) {
      foundationPiles.add(new ArrayList<>());
    }

    for (int i = 0; i < deck.size(); i++) {
      int lengthPile = (i % numCascadePiles);
      Card temp = deck.get(i);
      cascadePiles.get(lengthPile).add(temp);
    }
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex,
                   PileType destination, int destPileNumber) {
    // has the game started
    if (!started) {
      throw new IllegalStateException("The game has not started yet!");
    }

    // can't move from foundation pile
    if (source == PileType.FOUNDATION) {
      throw new IllegalArgumentException("Cannot move cards from foundation pile.");
    }

    // can't move to pile that is out of bounds
    if (destPileNumber < 0 || (destination == PileType.FOUNDATION
            && destPileNumber >= foundationPiles.size())
            || (destination == PileType.OPEN && destPileNumber >= openPiles.size())
            || (destination == PileType.CASCADE && destPileNumber >= cascadePiles.size())) {
      throw new IllegalArgumentException("Cannot move to pile that is out of bounds!");
    }

    if (source.equals(PileType.OPEN)) {
      Card temp = getOpenCardAt(pileNumber);

      if (temp == null) {
        throw new IllegalArgumentException("Illegal card index!");
      }

      moveFromOpen(temp, pileNumber, cardIndex, destination, destPileNumber);
    }
    else {
      Card temp2 = getCascadeCardAt(pileNumber, cardIndex);

      if (temp2 == null) {
        throw new IllegalArgumentException("Illegal card index!");
      }

      // if move is cascade => cascade
      if (destination == PileType.CASCADE) {
        moveFromCascadeToCascade(temp2, pileNumber, cardIndex, destPileNumber);
      }
      // if move is cascade => foundation or open
      else {
        moveFromCascadeToNotCascade(temp2, pileNumber, cardIndex, destination, destPileNumber);
      }
    }
  }

  /**
   * Deals with Card moves from Cascade pile to Cascade pile depending on the type of model. Only
   * moves one Card for the SimpleFreecellModel and can move multiple cards for the
   * MultiMoveFreecellModel.
   * @param c the starting Card to be moved from the source Cascade pile (can be part of a build)
   * @param pileNum the number of the source Cascade pile the card or build is being moved from
   * @param index the index of the Card to be moved or the Card at the head of the build to be moved
   * @param destPileNum the pile number of the destination Cascade pile the Card or Cards are being
   *                    moved to
   */
  protected abstract void moveFromCascadeToCascade(Card c, int pileNum,
                                                   int index, int destPileNum);

  /**
   * Deals with Card moves from an Open pile.
   * @param c the given Card to be moved from the source pile
   * @param pileNum the number of the source Cascade pile the card is being moved from
   * @param index the index of the card to be moved in the source Cascade pile
   * @param dest the destination pile (either Foundation or Open)
   * @param destPileNum the pile number the Card is being moved to (either Foundation or Open)
   */
  protected void moveFromOpen(Card c, int pileNum, int index, PileType dest, int destPileNum) {
    // if move is open => foundation
    if (dest == PileType.FOUNDATION) {
      if (addToFoundation(c, destPileNum)) {
        foundationPiles.get(destPileNum).add(c);
        openPiles.get(pileNum).remove(index);
      } else {
        throw new
                IllegalArgumentException("Can't add non-ace card as first in foundation pile!");
      }
    }
    // if move is open => open
    else if (dest == PileType.OPEN) {
      if (emptyOpenPiles(destPileNum)) {
        openPiles.get(destPileNum).add(c);
        openPiles.get(pileNum).remove(index);
      }
    }
    // if move is open => cascade
    else {
      Card before = getCascadeCardAt(destPileNum,
              getNumCardsInCascadePile(destPileNum) - 1);
      if (validCardBefore(c, before)) {
        cascadePiles.get(destPileNum).add(c);
        openPiles.get(pileNum).remove(index);
      }
    }
  }

  /**
   * Deals with Card moves from a Cascade pile to either a Foundation pile or Open pile.
   * @param c the given Card to be moved from the source pile
   * @param pileNum the number of the source Cascade pile the card is being moved from
   * @param index the index of the card to be moved in the source Cascade pile
   * @param dest the destination pile (either Foundation or Open)
   * @param destPileNum the pile number the Card is being moved to (either Foundation or Open)
   */
  protected void moveFromCascadeToNotCascade(Card c, int pileNum, int index,
                                             PileType dest, int destPileNum) {
    // if move is cascade => open
    if (dest == PileType.OPEN) {
      if (emptyOpenPiles(destPileNum)) {
        openPiles.get(destPileNum).add(c);
        cascadePiles.get(pileNum).remove(index);
      }
    }
    // if move is cascade => foundation
    else {
      if (addToFoundation(c, destPileNum)) {
        foundationPiles.get(destPileNum).add(c);
        cascadePiles.get(pileNum).remove(index);
      }
      else {
        throw new
                IllegalArgumentException("Can't add non-ace card as first in foundation pile!");
      }
    }
  }


  /**
   * Determines if a card can be added to a Foundation pile.
   * It checks if the first card of the pile is an ace, if the card to be added has a rank that is
   * one more than the rank of the previous card in the pile, and if the card has a different color
   * suit than the card before in the pile.
   *
   * @param c       the card to be added to the pile
   * @param pileNum the index of the Foundation pile
   * @return true if the card complies with all conditions and false if not
   * @throws IllegalArgumentException if either of the second two conditions are not met
   */
  protected boolean addToFoundation(Card c, int pileNum) {
    if (getNumCardsInFoundationPile(pileNum) > 0) {
      Card before = this.getFoundationCardAt(pileNum,
              this.getNumCardsInFoundationPile(pileNum) - 1);
      if (c.validSuit(before) && (c.rankNum() - before.rankNum() == 1)) {
        return true;
      } else {
        throw new IllegalArgumentException("Cannot add card to foundation pile of different suit.");
      }
    } else {
      return c.equalsA();
    }
  }

  /**
   * Determines if a card can be added to a Cascade pile.
   * It checks if the card to be added has a rank that is
   * one less than the rank of the previous card in the pile, and if the card has a different color
   * suit than the card before in the pile.
   *
   * @param c      the card to be added to the pile
   * @param before the last card in the current pile to be compared
   * @return true if the card complies with all conditions and false if not
   * @throws IllegalArgumentException if both of the two conditions are not met
   */
  protected boolean validCardBefore(Card c, Card before) {
    if (c.suitColor() != before.suitColor() && (before.rankNum() - c.rankNum() == 1)) {
      return true;
    } else {
      throw new IllegalArgumentException("Cannot move card of same rank to another of same rank.");
    }
  }

  /**
   * Determines if the Open pile at the given index already has a card in the list.
   *
   * @param pileSize the index of the Open pile
   * @return true if the Open pile has no cards in its list and false if not
   * @throws IllegalArgumentException if there are more than 0 cards in the list
   */
  protected boolean emptyOpenPiles(int pileSize) {
    if (getNumCardsInOpenPile(pileSize) == 0) {
      return true;
    } else {
      throw new IllegalArgumentException("Cannot add multiple cards to open pile.");
    }
  }

  @Override
  public boolean isGameOver() {
    for (int i = 0; i < 4; i++) {
      if (this.getNumCardsInFoundationPile(i) != 13) {
        return false;
      }
    }
    return true;
  }

  /**
   * Abstracts getting the number of cards in either a Foundation or Cascade pile.
   * @param list either the list of Foundation piles or Cascade piles
   * @param index the index of the pile, starting at 0
   * @return the number of cards in the given open pile
   * @throws IllegalArgumentException if the provided index is invalid
   * @throws IllegalStateException    if the game has not started
   */
  protected int getNumCardsInAnyPile(List<List<Card>> list, int index) {
    if (!started) {
      throw new IllegalStateException("The game has not started!");
    } else if (index < 0 || index >= list.size()) {
      throw new IllegalArgumentException("Illegal index value!");
    } else {
      return list.get(index).size();
    }
  }

  @Override
  public int getNumCardsInFoundationPile(int index) {
    return getNumCardsInAnyPile(this.foundationPiles, index);
  }

  @Override
  public int getNumCascadePiles() {
    return getNumPiles(this.cascadePiles);
  }

  @Override
  public int getNumCardsInCascadePile(int index) {
    return getNumCardsInAnyPile(this.cascadePiles, index);
  }

  @Override
  public int getNumCardsInOpenPile(int index) {
    return getNumCardsInAnyPile(this.openPiles, index);
  }

  @Override
  public int getNumOpenPiles() {
    return getNumPiles(this.openPiles);
  }

  /**
   * Abstracts getting the number of Open or Cascade piles in the game.
   * @param list either the list of Open piles or Cascade piles
   * @return the number of piles of either type as an int
   */
  protected int getNumPiles(List<List<Card>> list) {
    if (!started) {
      return -1;
    }
    return list.size();
  }

  /**
   * Abstracts getting a Card from either a Foundation or Cascade pile by its pile index
   * and the Card's index in the list.
   * @param list either the list of Foundation piles or Cascade piles
   * @param pileIndex the index of the cascade pile, starting at 0
   * @param cardIndex the index of the card in the above cascade pile, starting at 0
   * @return the card at the provided indices
   * @throws IllegalArgumentException if the pileIndex or cardIndex is invalid
   * @throws IllegalStateException    if the game has not started
   */
  protected Card getCardInAnyPile(List<List<Card>> list, int pileIndex, int cardIndex) {
    if (!started) {
      throw new IllegalStateException("The game has not started!");
    } else if (pileIndex < 0 || pileIndex >= list.size()) {
      throw new IllegalArgumentException("Illegal pile index value!");
    } else if (getNumCardsInAnyPile(list, pileIndex) == 0) {
      throw new IllegalArgumentException("The pile has no cards!");
    } else if (cardIndex < 0 || cardIndex >= list.get(pileIndex).size()) {
      throw new IllegalArgumentException("Illegal card index value!");
    } else {
      return list.get(pileIndex).get(cardIndex);
    }
  }

  @Override
  public Card getFoundationCardAt(int pileIndex, int cardIndex) {
    return getCardInAnyPile(this.foundationPiles, pileIndex, cardIndex);
  }

  @Override
  public Card getCascadeCardAt(int pileIndex, int cardIndex) {
    return getCardInAnyPile(this.cascadePiles, pileIndex, cardIndex);
  }

  @Override
  public Card getOpenCardAt(int pileIndex) {
    if (!started) {
      throw new IllegalStateException("The game has not started!");
    } else if (pileIndex < 0 || pileIndex >= this.openPiles.size()) {
      throw new IllegalArgumentException("Illegal pile index value!");
    } else if (getNumCardsInOpenPile(pileIndex) == 0) {
      return null;
    } else {
      return this.openPiles.get(pileIndex).get(0);
    }
  }
}
