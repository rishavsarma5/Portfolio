package cs3500.freecell.model;

import cs3500.freecell.model.multimove.MultiMoveFreecellModel;

/**
 * This class represents a freecell model creator.
 * It handles creating a implementation of FreecellModel based on the enum type given.
 */
public class FreecellModelCreator {

  /**
   * Types for the two models of FreecellModel. SINGLEMOVE refers to SimpleFreecellModel and
   * MULTIMOVE refers to MultiMoveFreecellModel.
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE;
  }

  /**
   * This factory method creates either a SimpleFreecellModel or a MultiMoveFreecellModel depending
   * on the enum type given.
   * @param type the enum type of FreecellModel
   * @return either a SimpleFreecellModel object or a MultiMoveFreecellModel
   * @throws IllegalArgumentException if the given enum type parameter is null
   */
  public static FreecellModel<Card> create(GameType type) {
    if (type == null) {
      throw new IllegalArgumentException("Argument is invalid!");
    }

    if (type.equals(GameType.SINGLEMOVE)) {
      return new SimpleFreecellModel();
    }
    else {
      return new MultiMoveFreecellModel();
    }
  }


}
