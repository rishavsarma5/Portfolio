
import org.junit.Before;
import org.junit.Test;

import io.AnimatorBuilder;
import model.IModel;
import view.TextView;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test cases for the TextView class.
 */
public class TextViewTest {
  private AnimatorBuilder animatorBuilder1 = new AnimatorBuilder();
  private AnimatorBuilder animatorBuilder2 = new AnimatorBuilder();
  private AnimatorBuilder animatorBuilder4 = new AnimatorBuilder();
  private TextView textView1 = new TextView();

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
  public void testCreateOvalAndMove() {
    IModel model1 = animatorBuilder1.build();
    textView1.setModel(model1);
    String resultString = textView1.drawText(5);
    textView1.appendToFile(resultString);
    assertEquals("canvas 15 20\n" +
            "shape oval_1 ellipse\n" +
            "motion oval_1 0 2.0 3.0 5.0 6.0 51 128 77     0 2.0 3.0 5.0 6.0 51 128 77\n" +
            "motion oval_1 0 2.0 3.0 5.0 6.0 51 128 77     1 4.0 5.0 5.0 6.0 51 128 77\n" +
            "motion oval_1 1 4.0 5.0 5.0 6.0 51 128 77     2 4.0 5.0 5.0 6.0 51 128 77",
            textView1.getFinalString());

  }

  @Test
  public void testCreateRectangleAndMove() {
    IModel model1 = animatorBuilder2.build();
    textView1.setModel(model1);
    String resultString = textView1.drawText(5);
    textView1.appendToFile(resultString);
    assertEquals("canvas 125 200\n" +
                    "shape rect_1 rectangle\n" +
                    "motion rect_1 0 2.0 3.0 5.0 6.0 51 128 77     0 2.0 3.0 5.0 6.0 51 128 77\n" +
                    "motion rect_1 0 2.0 3.0 5.0 6.0 51 128 77     1 4.0 5.0 5.0 6.0 51 128 77\n" +
                    "motion rect_1 1 4.0 5.0 5.0 6.0 51 128 77     2 4.0 5.0 5.0 6.0 51 128 77",
            textView1.getFinalString());

  }

  @Test
  public void testAddColorChangeForOval() {
    animatorBuilder1.addColorChange("oval_1", (float) 0.2, (float) 0.5, (float) 0.3,
            (float) 0.4, (float) 0.4, (float) 0.4, 1, 4);
    IModel model1 = animatorBuilder1.build();
    textView1.setModel(model1);
    String resultString = textView1.drawText(5);
    textView1.appendToFile(resultString);
    assertEquals("canvas 15 20\n" +
                    "shape oval_1 ellipse\n" +
                    "motion oval_1 0 2.0 3.0 5.0 6.0 51 128 77     0 2.0 3.0 5.0 6.0 68 119 85\n" +
                    "motion oval_1 0 2.0 3.0 5.0 6.0 68 119 85    " +
                    " 0 3.0 4.0 5.0 6.0 102 102 102\n" +
                    "motion oval_1 0 3.0 4.0 5.0 6.0 102 102 102     " +
                    "1 4.0 5.0 5.0 6.0 102 102 102\n" +
                    "motion oval_1 1 4.0 5.0 5.0 6.0 102 102 102     2 4.0 5.0 5.0 6.0 102 102 102",
            textView1.getFinalString());
  }

  @Test
  public void testAddScaleChange() {
    animatorBuilder1.addScaleToChange("oval_1", 5, 6, 4, 3,
            2, 5);
    IModel model1 = animatorBuilder1.build();
    textView1.setModel(model1);
    String resultString = textView1.drawText(5);
    textView1.appendToFile(resultString);
    assertEquals("canvas 15 20\n" +
                    "shape oval_1 ellipse\n" +
                    "motion oval_1 0 2.0 3.0 5.0 6.0 51 128 77     0 2.0 3.0 5.0 6.0 51 128 77\n" +
                    "motion oval_1 0 2.0 3.0 5.0 6.0 51 128 77     1 3.5 4.5 4.0 3.0 51 128 77\n" +
                    "motion oval_1 1 3.5 4.5 4.0 3.0 51 128 77     1 4.0 5.0 4.0 3.0 51 128 77\n" +
                    "motion oval_1 1 4.0 5.0 4.0 3.0 51 128 77     2 4.0 5.0 4.0 3.0 51 128 77",
            textView1.getFinalString());
  }

  @Test
  public void testAllThreeChanges() {
    AnimatorBuilder animatorBuilder3 = new AnimatorBuilder();
    animatorBuilder3.addOval("oval_2", 2, 3,
            5, 6, (float) 0.2, (float) 0.5, (float) 0.3, 1, 10);
    animatorBuilder3.addMove("oval_2", 2
            , 3, 5, 6, 2, 5);
    animatorBuilder3.addColorChange("oval_2", (float) 0.2, (float) 0.5, (float) 0.3,
            (float) 0.4, (float) 0.4, (float) 0.4, 1, 4);

    animatorBuilder3.addRectangle("rect_2", 2, 3,
            5, 6, (float) 0.2, (float) 0.5, (float) 0.3, 3, 15);
    animatorBuilder3.addMove("rect_2", 2
            , 3, 6, 6, 4, 9);
    animatorBuilder3.addScaleToChange("rect_2", 5, 6, 4, 3,
            5, 5);

    animatorBuilder3.addRectangle("rect_3", 2, 3,
            5, 6, (float) 0.2, (float) 0.5, (float) 0.3, 2, 16);
    animatorBuilder3.addMove("rect_3", 2
            , 3, 6, 6, 3, 7);
    animatorBuilder3.addMove("rect_3", 6
            , 6, 8, 9, 8, 12);
    IModel newModel = animatorBuilder3.build();
    textView1.setModel(newModel);
    String resultString = textView1.drawText(5);
    textView1.appendToFile(resultString);
    assertEquals("canvas 0 0\n" +
                    "shape oval_2 ellipse\n" +
                    "motion oval_2 0 2.0 3.0 5.0 6.0 51 128 77     0 2.0 3.0 5.0 6.0 68 119 85\n" +
                    "motion oval_2 0 2.0 3.0 5.0 6.0 68 119 85     " +
                    "0 4.0 5.0 5.0 6.0 102 102 102\n" +
                    "motion oval_2 0 4.0 5.0 5.0 6.0 102 102 102    " +
                    " 1 5.0 6.0 5.0 6.0 102 102 102\n" +
                    "motion oval_2 1 5.0 6.0 5.0 6.0 102 102 102     " +
                    "2 5.0 6.0 5.0 6.0 102 102 102\n" +
                    "\n" +
                    "\n" +
                    "shape rect_2 rectangle\n" +
                    "motion rect_2 0 2.0 3.0 5.0 6.0 51 128 77     0 2.0 3.0 5.0 6.0 51 128 77\n" +
                    "motion rect_2 0 2.0 3.0 5.0 6.0 51 128 77     " +
                    "1 2.8000002 3.6000001 5.0 6.0 51 128 77\n" +
                    "motion rect_2 1 2.8000002 3.6000001 5.0 6.0 51 128 77     " +
                    "1 6.0 6.0 4.0 3.0 51 128 77\n" +
                    "motion rect_2 1 6.0 6.0 4.0 3.0 51 128 77     3 6.0 6.0 4.0 3.0 51 128 77\n" +
                    "\n" +
                    "\n" +
                    "shape rect_3 rectangle\n" +
                    "motion rect_3 0 2.0 3.0 5.0 6.0 51 128 77     0 2.0 3.0 5.0 6.0 51 128 77\n" +
                    "motion rect_3 0 2.0 3.0 5.0 6.0 51 128 77     1 6.0 6.0 5.0 6.0 51 128 77\n" +
                    "motion rect_3 1 6.0 6.0 5.0 6.0 51 128 77     1 6.0 6.0 5.0 6.0 51 128 77\n" +
                    "motion rect_3 1 6.0 6.0 5.0 6.0 51 128 77     2 8.0 9.0 5.0 6.0 51 128 77\n" +
                    "motion rect_3 2 8.0 9.0 5.0 6.0 51 128 77     3 8.0 9.0 5.0 6.0 51 128 77",
            textView1.getFinalString());
  }

  @Test
  public void testEmptyShape() {
    IModel newModel = animatorBuilder4.build();
    textView1.setModel(newModel);
    String resultString = textView1.drawText(5);
    textView1.appendToFile(resultString);
    assertEquals("canvas 0", textView1.getFinalString());
  }
}
