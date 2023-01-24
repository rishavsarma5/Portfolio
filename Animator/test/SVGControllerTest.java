import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import controller.IController;
import controller.SVGController;
import io.AnimatorBuilder;
import io.TweenModelBuilder;
import model.IModel;
import view.IView;
import view.SVGView;
import view.SVGViewMock;
import view.TextView;

import static org.junit.Assert.assertEquals;

/**
 * Test class for SVG controller.
 */
public class SVGControllerTest {
  IController ctrl;
  IView view;
  IView mock;
  IModel model;
  TweenModelBuilder<IModel> builder;
  int tempo;

  @Before
  public void initData() {
    tempo = 2;
    builder = new AnimatorBuilder();
    model = builder.setBounds(1000, 1000)
            .addRectangle("R1", 25, 25, 100,
                    100, 0.5f, 0.5f, 0.5f, 1, 6)
            .addOval("E1", 300, 250, 25, 25,
                    0f, 1f, 1f, 3, 12)
            .addMove("R1", 25, 25, 16, 16,
                    2, 4)
            .addScaleToChange("E1", 25, 25, 40, 40, 4,
                    11).build();
    view = new SVGView();
    mock = new SVGViewMock();
    ctrl = new SVGController(model, view);
  }

  @Test
  public void testSetTempoWorks() {
    initData();
    assertEquals(1, ctrl.getTempo());
    ctrl.setTempo(50);
    assertEquals(50, ctrl.getTempo());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetTempoInvalidTempo() {
    initData();
    assertEquals(1, ctrl.getTempo());
    ctrl.setTempo(-5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBothBadInputs() {
    IController badCtrl = new SVGController(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBadView() {
    initData();
    IController badCtrl = new SVGController(model, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBadModel() {
    initData();
    IController badCtrl = new SVGController(null, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidView() {
    initData();
    IController badCtrl = new SVGController(model, new TextView());
  }

  @Test
  public void testStart() {
    initData();
    mock.setTempo(tempo);
    mock.setModel(model);
    try {
      mock.drawSVG();
      mock.export();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals("Created correctly formatted SVG file with given output: \n" +
            "<svg width=\"1000\" height=\"1000\" version=\"1.1\" " +
            "xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<rect id=\"R1\" x=\"25.0\" y=\"25.0\" width=\"100.0\" height=\"100.0\" " +
            "fill=\"rgb(128,128,128)\" visibility=\"visible\" >\n" +
            "<animate attributeType=\"xml\" begin=\"500.0ms\" dur=\"500.0ms\" " +
            "attributeName=\"x\" from=\"25.0\" to=\"25.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"500.0ms\" dur=\"500.0ms\" " +
            "attributeName=\"y\" from=\"25.0\" to=\"25.0\" fill=\"freeze\" />" +
            "<animate attributeName=\"fill\" attributeType=\"xml\" " +
            "begin=\"500.0ms\" dur=\"500.0ms\" from=\"rgb(128,128,128)\" " +
            "to=\"rgb(128,128,128)\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1000.0ms\" dur=\"1000.0ms\" " +
            "attributeName=\"x\" from=\"25.0\" to=\"16.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1000.0ms\" dur=\"1000.0ms\" " +
            "attributeName=\"y\" from=\"25.0\" to=\"16.0\" fill=\"freeze\" />" +
            "<animate attributeName=\"fill\" attributeType=\"xml\" " +
            "begin=\"1000.0ms\" dur=\"1000.0ms\" from=\"rgb(128,128,128)\" " +
            "to=\"rgb(128,128,128)\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2000.0ms\" dur=\"1000.0ms\" " +
            "attributeName=\"x\" from=\"16.0\" to=\"16.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2000.0ms\" dur=\"1000.0ms\" " +
            "attributeName=\"y\" from=\"16.0\" to=\"16.0\" fill=\"freeze\" />" +
            "<animate attributeName=\"fill\" attributeType=\"xml\" " +
            "begin=\"2000.0ms\" dur=\"1000.0ms\" from=\"rgb(128,128,128)\" " +
            "to=\"rgb(128,128,128)\" fill=\"freeze\" />\n" +
            "</rect>\n" +
            "\n" +
            "<ellipse id=\"E1\" cx=\"300.0\" cy=\"250.0\" rx=\"25.0\" ry=\"25.0\" " +
            "fill=\"rgb(0,255,255)\" visibility=\"visible\" >\n" +
            "<animate attributeType=\"xml\" begin=\"1500.0ms\" dur=\"500.0ms\" " +
            "attributeName=\"cx\" from=\"300.0\" to=\"300.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1500.0ms\" dur=\"500.0ms\" " +
            "attributeName=\"cy\" from=\"250.0\" to=\"250.0\" fill=\"freeze\" />" +
            "<animate attributeName=\"fill\" attributeType=\"xml\" " +
            "begin=\"1500.0ms\" dur=\"500.0ms\" from=\"rgb(0,255,255)\" " +
            "to=\"rgb(0,255,255)\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2000.0ms\" dur=\"3500.0ms\" " +
            "attributeName=\"cx\" from=\"300.0\" to=\"300.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2000.0ms\" dur=\"3500.0ms\" " +
            "attributeName=\"cy\" from=\"250.0\" to=\"250.0\" fill=\"freeze\" />" +
            "<animate attributeType=\"xml\" begin=\"2000.0ms\" dur=\"3500.0ms\" " +
            "attributeName=\"rx\" from=\"25.0\" to=\"40.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2000.0ms\" dur=\"3500.0ms\" " +
            "attributeName=\"ry\" from=\"25.0\" to=\"40.0\" fill=\"freeze\" />" +
            "<animate attributeName=\"fill\" attributeType=\"xml\" " +
            "begin=\"2000.0ms\" dur=\"3500.0ms\" from=\"rgb(0,255,255)\" " +
            "to=\"rgb(0,255,255)\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"5500.0ms\" dur=\"500.0ms\" " +
            "attributeName=\"cx\" from=\"300.0\" to=\"300.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"5500.0ms\" dur=\"500.0ms\" " +
            "attributeName=\"cy\" from=\"250.0\" to=\"250.0\" fill=\"freeze\" />" +
            "<animate attributeName=\"fill\" attributeType=\"xml\" " +
            "begin=\"5500.0ms\" dur=\"500.0ms\" from=\"rgb(0,255,255)\" " +
            "to=\"rgb(0,255,255)\" fill=\"freeze\" />\n" +
            "</ellipse>\n" +
            "\n" +
            "</svg>", mock.getFinalString());
  }
}
