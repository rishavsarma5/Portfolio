
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import io.AnimatorBuilder;
import io.TweenModelBuilder;
import model.IModel;
import model.IShape;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test cases for the AnimatorBuilder class.
 */
public class AnimatorBuilderTest {
  private AnimatorBuilder animatorBuilder1 = new AnimatorBuilder();
  private AnimatorBuilder animatorBuilder2 = new AnimatorBuilder();

  @Before
  public void init() {
    animatorBuilder1.setBounds(15, 20);
    animatorBuilder1.addOval("oval_1", 2, 3,
            5, 6, (float) 0.2, (float) 0.5, (float) 0.3, 1, 10);
    animatorBuilder1.addMove("oval_1", 2, 3,
            4, 5, 2, 6);

    animatorBuilder2.setBounds(125, 200);
    animatorBuilder2.addRectangle("rect_1", 2, 3,
            5, 6, (float) 0.2, (float) 0.5, (float) 0.3, 1, 10);
    animatorBuilder2.addMove("rect_1", 2, 3,
            4, 5, 2, 7);
  }

  @Test
  public void testSetBounds() {
    IModel newModel = animatorBuilder1.build();
    assertEquals(20, newModel.getBoundHeight());
    assertEquals(15, newModel.getBoundWidth());
  }

  @Test
  public void testAddOval() {
    IModel newModel = animatorBuilder1.build();
    List<IShape> allShapes = newModel.getAllShapes();
    assertEquals(1, allShapes.size());
    assertEquals("oval_1", allShapes.get(0).getID());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddOvalAddTheSameOvalAgain() {
    animatorBuilder1.addOval("oval_1", 2, 3,
            5, 6, (float) 0.2, (float) 0.5, (float) 0.3, 1, 10);

  }

  @Test
  public void testAddRectangle() {
    IModel newModel = animatorBuilder2.build();
    List<IShape> allShapes = newModel.getAllShapes();
    assertEquals(1, allShapes.size());
    assertEquals("rect_1", allShapes.get(0).getID());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testAddRectangleAddTheSameRectangleAgain() {
    animatorBuilder2.addRectangle("rect_1", 2, 3,
            5, 6, (float) 0.2, (float) 0.5, (float) 0.3, 1, 10);
  }

  @Test
  public void testAddMove() {
    animatorBuilder1.addMove("oval_1", 4, 5, 10, 10
            , 6, 8);
    IModel newModel = animatorBuilder1.build();
    assertEquals(1, newModel.getAllShapes().size());
    assertEquals("oval_1", newModel.getAllShapes().get(0).getID());
    assertEquals(4, newModel.getAllMotions("oval_1").size());
    assertEquals(1, newModel.getAllMotions("oval_1").get(0).getStartTick());
    assertEquals(2, newModel.getAllMotions("oval_1").get(0).getEndTick());
    assertEquals(2, newModel.getAllMotions("oval_1").get(1).getStartTick());
    assertEquals(6, newModel.getAllMotions("oval_1").get(1).getEndTick());
    assertEquals(6, newModel.getAllMotions("oval_1").get(2).getStartTick());
    assertEquals(8, newModel.getAllMotions("oval_1").get(2).getEndTick());
    assertEquals(8, newModel.getAllMotions("oval_1").get(3).getStartTick());
    assertEquals(10, newModel.getAllMotions("oval_1").get(3).getEndTick());
    assertEquals(3, newModel.getKeyFrameAt("oval_1", 4).getPosition().getX(),
            0.01);
    assertEquals(4, newModel.getKeyFrameAt("oval_1", 4).getPosition().getY(),
            0.01);
    assertEquals(7, newModel.getKeyFrameAt("oval_1", 7).getPosition().getX(),
            0.01);
    assertEquals(7.5, newModel.getKeyFrameAt("oval_1", 7).getPosition().getY(),
            0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMoveToANonExistShape() {
    animatorBuilder1.addMove("oval_3", 4, 5, 10
            , 10, 6, 8);

  }

  @Test
  public void testAddMoveComplicatedTestWithThreeShapes() {
    AnimatorBuilder animatorBuilder3 = new AnimatorBuilder();
    animatorBuilder3.addOval("oval_2", 2, 3,
            5, 6, (float) 0.2, (float) 0.5, (float) 0.3, 1, 10);
    animatorBuilder3.addMove("oval_2", 2
            , 3, 5, 6, 2, 5);

    animatorBuilder3.addRectangle("rect_2", 2, 3,
            5, 6, (float) 0.2, (float) 0.5, (float) 0.3, 3, 15);
    animatorBuilder3.addMove("rect_2", 2
            , 3, 6, 6, 4, 9);

    animatorBuilder3.addRectangle("rect_3", 2, 3,
            5, 6, (float) 0.2, (float) 0.5, (float) 0.3, 2, 16);
    animatorBuilder3.addMove("rect_3", 2
            , 3, 6, 6, 3, 7);
    animatorBuilder3.addMove("rect_3", 6
            , 6, 8, 9, 8, 12);
    IModel newModel = animatorBuilder3.build();
    assertEquals(3, newModel.getAllShapes().size());
    assertEquals(3, newModel.getAllMotions("rect_2").size());
    assertEquals(3, newModel.getAllMotions("oval_2").size());
    assertEquals(5, newModel.getAllMotions("rect_3").size());
    assertEquals(3, newModel.getKeyFrameAt("oval_2", 3).getPosition().getX()
            , 0.01);
    assertEquals(4, newModel.getKeyFrameAt("oval_2", 3).getPosition().getY()
            , 0.01);

    assertEquals(2.8, newModel.getKeyFrameAt("rect_2", 5).getPosition().getX()
            , 0.01);
    assertEquals(3.6, newModel.getKeyFrameAt("rect_2", 5).getPosition().getY()
            , 0.01);

    assertEquals(4.0, newModel.getKeyFrameAt("rect_3", 5).getPosition().getX()
            , 0.01);
    assertEquals(4.5, newModel.getKeyFrameAt("rect_3", 5).getPosition().getY()
            , 0.01);
  }

  @Test
  public void testColorChange() {
    animatorBuilder1.addColorChange("oval_1", (float) 0.2, (float) 0.5, (float) 0.3,
            (float) 0.4, (float) 0.4, (float) 0.4, 1, 4);
    IModel newModel = animatorBuilder1.build();
    assertEquals(4, newModel.getAllMotions("oval_1").size());
    assertEquals(1, newModel.getAllMotions("oval_1").get(0).getStartTick());
    assertEquals(2, newModel.getAllMotions("oval_1").get(1).getStartTick());
    assertEquals(4, newModel.getAllMotions("oval_1").get(2).getStartTick());
    assertEquals(6, newModel.getAllMotions("oval_1").get(3).getStartTick());
    assertEquals(85,
            newModel.getKeyFrameAt("oval_1", 3).getShape().getColor().getRed());
    assertEquals(110,
            newModel.getKeyFrameAt("oval_1", 3).getShape().getColor().getGreen());
    assertEquals(93,
            newModel.getKeyFrameAt("oval_1", 3).getShape().getColor().getBlue());
    assertEquals(102,
            newModel.getKeyFrameAt("oval_1", 4).getShape().getColor().getRed());
    assertEquals(102,
            newModel.getKeyFrameAt("oval_1", 4).getShape().getColor().getGreen());
    assertEquals(102,
            newModel.getKeyFrameAt("oval_1", 4).getShape().getColor().getBlue());
    assertEquals(102,
            newModel.getKeyFrameAt("oval_1", 5).getShape().getColor().getRed());
    assertEquals(102,
            newModel.getKeyFrameAt("oval_1", 5).getShape().getColor().getGreen());
    assertEquals(102,
            newModel.getKeyFrameAt("oval_1", 5).getShape().getColor().getBlue());
    assertEquals(68,
            newModel.getKeyFrameAt("oval_1", 2).getShape().getColor().getRed());
    assertEquals(119,
            newModel.getKeyFrameAt("oval_1", 2).getShape().getColor().getGreen());
    assertEquals(85,
            newModel.getKeyFrameAt("oval_1", 2).getShape().getColor().getBlue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void addColorChangeNoSuchShape() {
    animatorBuilder1.addColorChange("oval_4", (float) 0.2, (float) 0.5, (float) 0.3,
            (float) 0.4, (float) 0.4, (float) 0.4, 1, 4);
  }


  @Test
  public void testColorChange2() {
    animatorBuilder1.addColorChange("oval_1", (float) 0.2, (float) 0.5, (float) 0.3,
            (float) 0.4, (float) 0.4, (float) 0.4, 3, 4);
    IModel newModel = animatorBuilder1.build();
    assertEquals(5, newModel.getAllMotions("oval_1").size());
    assertEquals(1, newModel.getAllMotions("oval_1").get(0).getStartTick());
    assertEquals(2, newModel.getAllMotions("oval_1").get(1).getStartTick());
    assertEquals(3, newModel.getAllMotions("oval_1").get(2).getStartTick());
    assertEquals(4, newModel.getAllMotions("oval_1").get(3).getStartTick());
    assertEquals(6, newModel.getAllMotions("oval_1").get(4).getStartTick());
    assertEquals(102,
            newModel.getKeyFrameAt("oval_1", 4).getShape().getColor().getRed());
    assertEquals(102,
            newModel.getKeyFrameAt("oval_1", 4).getShape().getColor().getGreen());
    assertEquals(102,
            newModel.getKeyFrameAt("oval_1", 4).getShape().getColor().getBlue());
    assertEquals(51,
            newModel.getKeyFrameAt("oval_1", 3).getShape().getColor().getRed());
    assertEquals(128,
            newModel.getKeyFrameAt("oval_1", 3).getShape().getColor().getGreen());
    assertEquals(77,
            newModel.getKeyFrameAt("oval_1", 3).getShape().getColor().getBlue());

  }

  @Test
  public void testScaleChange() {
    animatorBuilder1.addScaleToChange("oval_1", 5, 6, 4, 3, 2,
            5);
    IModel newModel = animatorBuilder1.build();
    assertEquals(4, newModel.getAllMotions("oval_1").size());
    assertEquals(5,
            newModel.getKeyFrameAt("oval_1", 2).getShape().getWidth(), 0.01);
    assertEquals(6,
            newModel.getKeyFrameAt("oval_1", 2).getShape().getHeight(), 0.01);
    assertEquals(4.6667,
            newModel.getKeyFrameAt("oval_1", 3).getShape().getWidth(), 0.01);
    assertEquals(5,
            newModel.getKeyFrameAt("oval_1", 3).getShape().getHeight(), 0.01);
    assertEquals(4.3333,
            newModel.getKeyFrameAt("oval_1", 4).getShape().getWidth(), 0.01);
    assertEquals(4,
            newModel.getKeyFrameAt("oval_1", 4).getShape().getHeight(), 0.01);
    assertEquals(4,
            newModel.getKeyFrameAt("oval_1", 5).getShape().getWidth(), 0.01);
    assertEquals(3,
            newModel.getKeyFrameAt("oval_1", 5).getShape().getHeight(), 0.01);
    assertEquals(4,
            newModel.getKeyFrameAt("oval_1", 6).getShape().getWidth(), 0.01);
    assertEquals(3,
            newModel.getKeyFrameAt("oval_1", 6).getShape().getHeight(), 0.01);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testScaleChangeNoSuchShape() {
    animatorBuilder1.addScaleToChange("oval_3", 5, 6, 4, 3, 2,
            5);
  }

  @Test
  public void testAddNoMotions() {
    TweenModelBuilder<IModel> builder = new AnimatorBuilder();
    builder.setBounds(15, 20);
    builder.addOval("oval_1", 2, 3,
            5, 6, (float) 0.2, (float) 0.5, (float) 0.3, 1, 10);
    IModel model1 = builder.build();
    assertEquals(1, model1.getAllShapes().size());
    assertEquals(1, model1.getAllMotions(model1.getAllShapes().get(0).getID()).size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDuplicateMoves() {
    animatorBuilder1.setBounds(15, 20);
    animatorBuilder1.addOval("oval_1", 2, 3,
            5, 6, (float) 0.2, (float) 0.5, (float) 0.3, 1, 10);
    animatorBuilder1.addMove("oval_1", 2, 3,
            4, 5, 2, 6);
    IModel model = animatorBuilder1.build();
  }
}
