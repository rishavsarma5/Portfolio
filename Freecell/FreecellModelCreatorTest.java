import org.junit.Before;
import org.junit.Test;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.SimpleFreecellModel;
import cs3500.freecell.model.multimove.MultiMoveFreecellModel;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test cases for the Freecell model creator.
 */
public class FreecellModelCreatorTest {
  FreecellModel<Card> simpleModel;
  FreecellModel<Card> multiModel;
  FreecellModelCreator.GameType gameTypeSingle;
  FreecellModelCreator.GameType gameTypeMulti;

  /**
   * Initializes the variables in the test class.
   */
  @Before
  public void initData() {
    simpleModel = new SimpleFreecellModel();
    multiModel = new MultiMoveFreecellModel();
    gameTypeSingle = FreecellModelCreator.GameType.SINGLEMOVE;
    gameTypeMulti = FreecellModelCreator.GameType.MULTIMOVE;

  }

  @Test (expected = IllegalArgumentException.class)
  public void testCreatorConstructorInvalidEnum() {
    initData();
    FreecellModel<Card> nullModel = FreecellModelCreator.create(null);
  }

  @Test
  public void testCreatorSimple() {
    initData();
    assertEquals(SimpleFreecellModel.class,
            FreecellModelCreator.create(gameTypeSingle).getClass());
  }

  @Test
  public void testCreatorMulti() {
    initData();
    assertEquals(MultiMoveFreecellModel.class,
            FreecellModelCreator.create(gameTypeMulti).getClass());
  }
}
