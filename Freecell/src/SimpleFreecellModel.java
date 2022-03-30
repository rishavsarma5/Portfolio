package cs3500.freecell.model;

/**
 * This class represents a simple freecell model.
 * It handles the starting of the game and moving of cards. It also determines if the game is ended.
 */
public class SimpleFreecellModel extends AbstractModel {

  /**
   * Constructs a SimpleFreecellModel with the parameters from AbstractModel.
   */
  public SimpleFreecellModel() {
    super();
  }

  @Override
  protected void moveFromCascadeToCascade(Card c, int pileNum, int index, int destPileNum) {
    Card before = getCascadeCardAt(destPileNum,
            getNumCardsInCascadePile(destPileNum) - 1);
    if (validCardBefore(c, before)) {
      cascadePiles.get(destPileNum).add(c);
      cascadePiles.get(pileNum).remove(index);
    }
  }
}

