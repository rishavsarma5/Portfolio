import org.junit.Test;

import java.io.IOException;

import controller.CreateController;
import controller.IController;
import controller.TextController;

import io.AnimatorBuilder;
import io.TweenModelBuilder;
import model.IModel;
import model.Model;
import view.IView;
import view.InteractiveVisualView;
import view.SVGView;
import view.TextView;
import view.TextViewMock;
import view.VisualAnimationView;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test cases for the TextController class.
 */
public class TextControllerTest {
  CreateController ctrlFactory = new CreateController();
  private IModel model1 = new Model();
  private IView textView1 = new TextView();
  private IView visualView1 = new VisualAnimationView(5,10);
  private IView svgView1 = new SVGView();
  private IView interactiveView1 = new InteractiveVisualView(1);
  IView mock = new TextViewMock();
  TweenModelBuilder<IModel> builder = new AnimatorBuilder();

  @Test
  public void testForControllerFactory() {
    IController controller1 = ctrlFactory.create(model1,textView1,"text");
    IController controller2 = ctrlFactory.create(model1,visualView1,"visual");
    IController controller3 = ctrlFactory.create(model1,svgView1,"svg");
    IController controller4 = ctrlFactory.create(model1, interactiveView1, "interactive");
    assertEquals("text",controller1.getType());
    assertEquals("visual",controller2.getType());
    assertEquals("svg",controller3.getType());
    assertEquals("interactive",controller4.getType());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testTextControllerInvalidConstructionNull() {
    IController invalid_constructor = new TextController(null,textView1);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testTextControllerInvalidConstruction2Null() {
    IController invalid_constructor = new TextController(model1,null);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testTextControllerInvalidConstructionNotValidViewType() {
    IController invalid_constructor = new TextController(model1,visualView1);
  }

  @Test
  public void testTextControllerSet() {
    IController valid_constructor = new TextController(model1,textView1);
    valid_constructor.setTempo(5);
    assertEquals(5,valid_constructor.getTempo());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTextControllerSetNegative() {
    IController valid_constructor = new TextController(model1,textView1);
    valid_constructor.setTempo(-5);
  }

  @Test
  public void testStartWithMock() {
    IModel model2 = builder.setBounds(1000, 1000)
            .addRectangle("R1", 25, 25, 100,
                    100, 0.5f, 0.5f, 0.5f, 1, 6)
            .addOval("E1", 300, 250, 25, 25,
                    0f, 1f, 1f, 3, 12)
            .addMove("R1", 25, 25, 16, 16,
                    2, 4)
            .addScaleToChange("E1", 25, 25, 40, 40, 4,
                    11).build();
    mock.setModel(model2);
    String resultString = mock.drawText(1);
    try {
      mock.appendToFile(resultString);
      mock.export();
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals("Created canvas at 1000 1000.\n" +
            "Created shape R1 rectangle\n" +
            "Created motion R1 that starts from 1 25.0 25.0 100.0 100.0 128 128 128s     " +
            "to 2 25.0 25.0 100.0 100.0 128 128 128s\n" +
            "Created motion R1 that starts from 2 25.0 25.0 100.0 100.0 128 128 128s     " +
            "to 4 16.0 16.0 100.0 100.0 128 128 128s\n" +
            "Created motion R1 that starts from 4 16.0 16.0 100.0 100.0 128 128 128s     " +
            "to 6 16.0 16.0 100.0 100.0 128 128 128s\n" +
            "\n" +
            "\n" +
            "Created shape E1 ellipse\n" +
            "Created motion E1 that starts from 3 300.0 250.0 25.0 25.0 0 255 255s     " +
            "to 4 300.0 250.0 25.0 25.0 0 255 255s\n" +
            "Created motion E1 that starts from 4 300.0 250.0 25.0 25.0 0 255 255s     " +
            "to 11 300.0 250.0 40.0 40.0 0 255 255s\n" +
            "Created motion E1 that starts from 11 300.0 250.0 40.0 40.0 0 255 255s     " +
            "to 12 300.0 250.0 40.0 40.0 0 255 255s", mock.getFinalString());
  }
}
