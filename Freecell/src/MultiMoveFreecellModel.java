package cs3500.freecell.model.multimove;

import cs3500.freecell.model.AbstractModel;
import cs3500.freecell.model.Card;

/**
 * This class represents a multi-move freecell model.
 * It handles the starting of the game and moving of multiple cards from one cascade pile to
 * another. It also determines if the game is ended.
 */
public class MultiMoveFreecellModel extends AbstractModel {

  /**
   * Constructs a MultiMoveFreecellModel with the parameters from AbstractModel.
   */
  public MultiMoveFreecellModel() {
    super();
  }

  @Override
  protected void moveFromCascadeToCascade(Card c, int pileNum, int index, int destPileNum) {
    if (validBuild(pileNum, index)) {
      if (validAmountToMove(pileNum, index, destPileNum)) {
        if (getNumCardsInCascadePile(destPileNum) == 0) {
          addSubCascadePileCards(pileNum, index, destPileNum);
        }
        else {
          Card before = getCascadeCardAt(destPileNum,
                  getNumCardsInCascadePile(destPileNum) - 1);
          if (validCardBefore(c, before)) {
            addSubCascadePileCards(pileNum, index, destPileNum);
          }
        }
      }
      else {
        throw new IllegalArgumentException("Invalid amount of cards to move!");
      }
    }
    else {
      throw new IllegalArgumentException("Invalid build of cards to move!");
    }
  }

  /**
   * Starts from the given card index of the given source Cascade piles and adds cards to the
   * destination Cascade pile. Deletes the cards in the source Cascade pile afterwards.
   * @param pileNum the number of the Cascade pile the Card or build of cards is from
   * @param index the index in the source Cascade pile where the first Card or build of cards is
   * @param destPileNum the index of the destination Cascade pile the build of Cards is moving to
   */
  protected void addSubCascadePileCards(int pileNum, int index, int destPileNum) {
    int originalLengthOfCascade = getNumCardsInCascadePile(pileNum);

    for (int i = index; i < getNumCardsInCascadePile(pileNum); i++) {
      Card temp = getCascadeCardAt(pileNum, i);

      if (temp == null) {
        throw new IllegalArgumentException("Illegal card index!");
      }
      cascadePiles.get(destPileNum).add(temp);
    }

    for (int i = 0; i < originalLengthOfCascade - index; i++) {
      Card temp = getCascadeCardAt(pileNum,getNumCardsInCascadePile(pileNum) - 1);

      if (temp == null) {
        throw new IllegalArgumentException("Illegal card index!");
      }
      cascadePiles.get(pileNum).remove(getNumCardsInCascadePile(pileNum) - 1);
    }
  }

  /**
   * Determines if the Card or build of cards is valid to be moved. This means that all the cards
   * are 1 less in rank going down the build and have alternating suit colors.
   *
   * @param pileNum the number of the Cascade pile the Card or build of cards is from
   * @param index   the index in the source Cascade pile where the first Card or build of cards is
   * @return true if the build is valid to be moved and false if not
   */
  private boolean validBuild(int pileNum, int index) {
    for (int i = index; i < getNumCardsInCascadePile(pileNum) - 1; i++) {
      Card before = getCascadeCardAt(pileNum, i);
      Card c = getCascadeCardAt(pileNum, i + 1);
      if (!validCardBefore(c, before)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines if the number of cards in the build that are to be moved is a valid amount for the
   * amount of empty Open and Cascade piles that are currently in the game state. The maximum
   * number of cards that can be moved from one cascade pile to another when there are N
   * free open piles and K empty cascade piles is (N + 1) * 2^K cards. If the destination Cascade
   * pile is empty, then 1 is subtracted from the maximum amount of cards allowed to be moved.
   *
   * @param pileNum     the number of the Cascade pile the Card or build of cards is from
   * @param index       the index in the source Cascade pile of the first Card or build of cards
   * @param destPileNum the index of the destination Cascade pile the build of Cards is moving to
   * @return true if the card amount is less than or equal to the formula expressed above and false
   *         if not
   */
  private boolean validAmountToMove(int pileNum, int index, int destPileNum) {
    int numCards = 0;
    int numOpenPiles = 0;
    int numCascadePiles = 0;

    for (int i = index; i < getNumCardsInCascadePile(pileNum); i++) {
      numCards += 1;
    }

    for (int j = 0; j < openPiles.size(); j++) {
      if (getNumCardsInOpenPile(j) == 0) {
        numOpenPiles += 1;
      }
    }

    for (int k = 0; k < cascadePiles.size(); k++) {
      if (getNumCardsInCascadePile(k) == 0) {
        numCascadePiles += 1;
      }
    }

    if (getNumCardsInCascadePile(destPileNum) == 0) {
      numCascadePiles -= 1;
    }

    return numCards <= (numOpenPiles + 1) * Math.pow(2, numCascadePiles);
  }
}
