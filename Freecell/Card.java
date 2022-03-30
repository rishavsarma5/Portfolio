package cs3500.freecell.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a Card. A card has a rank and suit.
 */
public class Card {
  List<String> validSuits = new ArrayList<String>(Arrays.asList("♣", "♠", "♦", "♥"));
  List<String> validRanks = new ArrayList<String>(Arrays.asList("A", "2", "3", "4", "5",
          "6", "7", "8", "9", "10", "J", "Q", "K"));

  private final String rank;
  private final String suit;

  /**
   * The types of colors that the suits of Cards can be. BLACK for clubs and spades, RED for hearts
   * and diamonds.
   */
  public enum Color {
    RED, BLACK;
  }

  /**
   * Constructs a Card object that has the provided rank and suit.
   * @param rank the rank of the card as a string (between "A" to "K")
   * @param suit the suit of the card as a string (one of "♣", "♠", "♦", "♥")
   */
  public Card(String rank, String suit) {
    if (!(validSuits.contains(suit)) || !(validRanks.contains(rank))) {
      throw new IllegalArgumentException("Illegal card suit or rank!");
    }
    this.rank = rank;
    this.suit = suit;
  }

  /**
   * Returns correct color for the suit (black for clubs and spades, red for hearts and diamonds).
   * @return the Color of the suit of the card
   */
  public Color suitColor() {
    if (this.suit.equals("♣") || this.suit.equals("♠")) {
      return Color.BLACK;
    }
    else {
      return Color.RED;
    }
  }

  /**
   * Converts the string version of the rank of the card to the integer equivalent.
   * "A" is 1, "J" is 11, "Q" is 12, "K" is 13
   * @return the integer version of the rank (returns -1 if invalid)
   */
  public int rankNum() {
    int num;

    switch (this.rank) {
      case "A":
        num = 1;
        break;
      case "J":
        num = 11;
        break;
      case "Q":
        num = 12;
        break;
      case "K":
        num = 13;
        break;
      default:
        if (Integer.parseInt(rank) < 0 || (Integer.parseInt(rank) > 10)) {
          num = -1;
        }
        else {
          num = Integer.parseInt(this.rank);
        }
        break;
    }
    return num;
  }

  /**
   * Overrides the toString() method to return the rank and suit of the card concatenated.
   * @return the concatenated string of the rank and suit of the card
   */
  @Override
  public String toString() {
    return this.rank + this.suit;
  }

  /**
   * Overrides the equals() method to compare two cards.
   * @param o the card being compared
   * @return a boolean
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Card)) {
      return false;
    }

    Card that = (Card) o;
    return that.rank.equals(this.rank) && that.suit.equals(this.suit);
  }

  /**
   * Overrides the hashCode() method to ensure that two cards with the same values are
   * equal.
   * @return the hashcode as an integer
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.rank, this.suit);
  }

  /**
   * Determines if the suit of this Card and the given Card are equal.
   * @param before the given Card
   * @return true if the suits are equal and false if not
   */
  public boolean validSuit(Card before) {
    return this.suit.equals(before.suit);
  }

  /**
   * Determines if the suit of this Card is a valid suit.
   * @return true if the suit is valid and false if not
   */
  public boolean isValidSuit() {
    return this.validSuits.contains(this.suit);
  }

  /**
   * Determines if the rank of this Card is a valid rank.
   * @return true if the rank is valid and false if not
   */
  public boolean isValidRank() {
    return this.validRanks.contains(this.rank);
  }

  /**
   * Determines if the rank of this Card is equal to "A".
   * @return true if this Card's rank is equal to "A"
   */
  public boolean equalsA() {
    return this.rank.equals("A");
  }
}

