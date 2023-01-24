import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.AnimationFileReader;
import io.AnimatorBuilder;
import io.TweenModelBuilder;
import model.IModel;
import model.IMotion;
import model.IShape;
import view.IView;
import view.SVGView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for SVG view.
 */
public class SVGViewTest {
  private TweenModelBuilder<IModel> builder;
  private IView svgView;
  private IModel model;


  @Before
  public void initData() {
    AnimationFileReader reader = new AnimationFileReader();
    builder = new AnimatorBuilder();
    List<IShape> shapesList = new ArrayList<>();
    List<IMotion> motionList = new ArrayList<>();
    svgView = new SVGView();
  }

  @Test
  public void testSVGViewJustShapes() {
    initData();
    model = builder.setBounds(1000, 1000)
            .addRectangle("R1", 25, 25, 100,
                    100, 0.5f, 0.5f, 0.5f, 1, 6)
            .addOval("E1", 300, 250, 25, 25,
                    0f, 1f, 1f, 3, 12).build();
    svgView.setModel(model);
    svgView.setFileName("out.svg");
    assertEquals("", svgView.getFinalString());
    try {
      svgView.drawSVG();
      svgView.export();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals("<svg width=\"1200\" height=\"1200\" version=\"1.1\" " +
            "xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<rect id=\"R1\" x=\"25.0\" y=\"25.0\" width=\"100.0\" height=\"100.0\" " +
            "fill=\"rgb(128,128,128)\" visibility=\"visible\" >\n" +
            "<animate attributeType=\"xml\" begin=\"1000.0ms\" dur=\"5000.0ms\" " +
            "attributeName=\"x\" from=\"25.0\" to=\"25.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1000.0ms\" dur=\"5000.0ms\" " +
            "attributeName=\"y\" from=\"25.0\" to=\"25.0\" fill=\"freeze\" />" +
            "<animate attributeName=\"fill\" attributeType=\"xml\" " +
            "begin=\"1000.0ms\" dur=\"5000.0ms\" from=\"rgb(128,128,128)\" " +
            "to=\"rgb(128,128,128)\" fill=\"freeze\" />\n" +
            "</rect>\n" +
            "\n" +
            "<ellipse id=\"E1\" cx=\"300.0\" cy=\"250.0\" rx=\"25.0\" " +
            "ry=\"25.0\" fill=\"rgb(0,255,255)\" visibility=\"visible\" >\n" +
            "<animate attributeType=\"xml\" begin=\"3000.0ms\" dur=\"9000.0ms\" " +
            "attributeName=\"cx\" from=\"300.0\" to=\"300.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"3000.0ms\" dur=\"9000.0ms\" " +
            "attributeName=\"cy\" from=\"250.0\" to=\"250.0\" fill=\"freeze\" />" +
            "<animate attributeName=\"fill\" attributeType=\"xml\" " +
            "begin=\"3000.0ms\" dur=\"9000.0ms\" from=\"rgb(0,255,255)\" " +
            "to=\"rgb(0,255,255)\" fill=\"freeze\" />\n" +
            "</ellipse>\n" +
            "\n" +
            "</svg>", svgView.getFinalString());
  }

  @Test
  public void testSVGView() {
    initData();
    model = builder.setBounds(1000, 1000)
            .addRectangle("R1", 25, 25, 100,
                    100, 0.5f, 0.5f, 0.5f, 1, 6)
            .addOval("E1", 300, 250, 25, 25,
                    0f, 1f, 1f, 3, 12)
            .addMove("R1", 25, 25, 16, 16,
                    2, 4)
            .addScaleToChange("E1", 25, 25, 40, 40, 4,
                    11).build();
    svgView.setModel(model);
    svgView.setFileName("out.svg");
    assertEquals("", svgView.getFinalString());
    try {
      svgView.drawSVG();
      svgView.export();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals("<svg width=\"1200\" height=\"1200\" version=\"1.1\" " +
            "xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<rect id=\"R1\" x=\"25.0\" y=\"25.0\" width=\"100.0\" height=\"100.0\" " +
            "fill=\"rgb(128,128,128)\" visibility=\"visible\" >\n" +
            "<animate attributeType=\"xml\" begin=\"1000.0ms\" dur=\"1000.0ms\" " +
            "attributeName=\"x\" from=\"25.0\" to=\"25.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1000.0ms\" dur=\"1000.0ms\" " +
            "attributeName=\"y\" from=\"25.0\" to=\"25.0\" fill=\"freeze\" /><animate " +
            "attributeName=\"fill\" attributeType=\"xml\" begin=\"1000.0ms\" " +
            "dur=\"1000.0ms\" from=\"rgb(128,128,128)\" to=\"rgb(128,128,128)\" " +
            "fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2000.0ms\" dur=\"2000.0ms\" " +
            "attributeName=\"x\" from=\"25.0\" to=\"16.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2000.0ms\" dur=\"2000.0ms\" " +
            "attributeName=\"y\" from=\"25.0\" to=\"16.0\" fill=\"freeze\" /><animate " +
            "attributeName=\"fill\" attributeType=\"xml\" begin=\"2000.0ms\" " +
            "dur=\"2000.0ms\" from=\"rgb(128,128,128)\" to=\"rgb(128,128,128)\" " +
            "fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"4000.0ms\" dur=\"2000.0ms\" " +
            "attributeName=\"x\" from=\"16.0\" to=\"16.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"4000.0ms\" dur=\"2000.0ms\" " +
            "attributeName=\"y\" from=\"16.0\" to=\"16.0\" fill=\"freeze\" />" +
            "<animate attributeName=\"fill\" attributeType=\"xml\" begin=\"4000.0ms\" " +
            "dur=\"2000.0ms\" from=\"rgb(128,128,128)\" to=\"rgb(128,128,128)\" " +
            "fill=\"freeze\" />\n" +
            "</rect>\n" +
            "\n" +
            "<ellipse id=\"E1\" cx=\"300.0\" cy=\"250.0\" rx=\"25.0\" " +
            "ry=\"25.0\" fill=\"rgb(0,255,255)\" visibility=\"visible\" >\n" +
            "<animate attributeType=\"xml\" begin=\"3000.0ms\" dur=\"1000.0ms\" " +
            "attributeName=\"cx\" from=\"300.0\" to=\"300.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"3000.0ms\" dur=\"1000.0ms\" " +
            "attributeName=\"cy\" from=\"250.0\" to=\"250.0\" fill=\"freeze\" />" +
            "<animate attributeName=\"fill\" attributeType=\"xml\" begin=\"3000.0ms\" " +
            "dur=\"1000.0ms\" from=\"rgb(0,255,255)\" " +
            "to=\"rgb(0,255,255)\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"4000.0ms\" dur=\"7000.0ms\" " +
            "attributeName=\"cx\" from=\"300.0\" to=\"300.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"4000.0ms\" dur=\"7000.0ms\" " +
            "attributeName=\"cy\" from=\"250.0\" to=\"250.0\" fill=\"freeze\" />" +
            "<animate attributeType=\"xml\" begin=\"4000.0ms\" dur=\"7000.0ms\" " +
            "attributeName=\"rx\" from=\"25.0\" to=\"40.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"4000.0ms\" dur=\"7000.0ms\" " +
            "attributeName=\"ry\" from=\"25.0\" to=\"40.0\" fill=\"freeze\" />" +
            "<animate attributeName=\"fill\" attributeType=\"xml\" begin=\"4000.0ms\" " +
            "dur=\"7000.0ms\" from=\"rgb(0,255,255)\" to=\"rgb(0,255,255)\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"11000.0ms\" dur=\"1000.0ms\" " +
            "attributeName=\"cx\" from=\"300.0\" to=\"300.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"11000.0ms\" dur=\"1000.0ms\" " +
            "attributeName=\"cy\" from=\"250.0\" to=\"250.0\" fill=\"freeze\" />" +
            "<animate attributeName=\"fill\" attributeType=\"xml\" " +
            "begin=\"11000.0ms\" dur=\"1000.0ms\" from=\"rgb(0,255,255)\" " +
            "to=\"rgb(0,255,255)\" fill=\"freeze\" />\n" +
            "</ellipse>\n" +
            "\n" +
            "</svg>", svgView.getFinalString());
    model.removeShape(model.getAllShapes().get(0));
    svgView.setModel(model);
    try {
      svgView.drawSVG();
      svgView.export();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals("<svg width=\"1200\" height=\"1200\"" +
            " version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<ellipse id=\"E1\" cx=\"300.0\" cy=\"250.0\" rx=\"25.0\" " +
            "ry=\"25.0\" fill=\"rgb(0,255,255)\" visibility=\"visible\" >\n" +
            "<animate attributeType=\"xml\" begin=\"3000.0ms\" " +
            "dur=\"1000.0ms\" attributeName=\"cx\" from=\"300.0\" " +
            "to=\"300.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"3000.0ms\" " +
            "dur=\"1000.0ms\" attributeName=\"cy\" from=\"250.0\" " +
            "to=\"250.0\" fill=\"freeze\" /><animate attributeName=\"fill\"" +
            " attributeType=\"xml\" begin=\"3000.0ms\" dur=\"1000.0ms\" from=\"" +
            "rgb(0,255,255)\" to=\"rgb(0,255,255)\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"4000.0ms\" " +
            "dur=\"7000.0ms\" attributeName=\"cx\" from=\"300.0\" to=\"300.0\"" +
            " fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"4000.0ms\" " +
            "dur=\"7000.0ms\" attributeName=\"cy\" from=\"250.0\" to=\"250.0\"" +
            " fill=\"freeze\" /><animate attributeType=\"xml\" begin=\"4000.0ms\"" +
            " dur=\"7000.0ms\" attributeName=\"rx\" from=\"25.0\" to=\"40.0\" " +
            "fill=\"freeze\"" +
            " />\n" +
            "<animate attributeType=\"xml\" begin=\"4000.0ms\" dur=\"7000.0ms\" " +
            "attributeName=\"ry\" from=\"25.0\" to=\"40.0\" fill=\"freeze\" " +
            "/><animate attributeName=\"fill\" attributeType=\"xml\" begin=\"" +
            "4000.0ms\" dur=\"7000.0ms\" from=\"rgb(0,255,255)\" " +
            "to=\"rgb(0,255,255)\"" +
            " fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"11000.0ms\" dur=\"1000.0ms\"" +
            " attributeName=\"cx\" from=\"300.0\" to=\"300.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"11000.0ms\" dur=\"1000.0ms\" " +
            "attributeName=\"cy\" from=\"250.0\" to=\"250.0\" fill=\"freeze\" " +
            "/><animate attributeName=\"fill\" attributeType=\"xml\" " +
            "begin=\"11000.0ms\" dur=\"1000.0ms\" from=\"rgb(0,255,255)\"" +
            " to=\"rgb(0,255,255)\" fill=\"freeze\" />\n" +
            "</ellipse>\n" +
            "\n" +
            "</svg>", svgView.getFinalString());
  }

  @Test
  public void testSetAndGetModelWorks() {
    initData();
    assertNull(svgView.getModel());
    model = builder.setBounds(1000, 1000)
            .addRectangle("R1", 25, 25, 100,
                    100, 0.5f, 0.5f, 0.5f, 1, 6)
            .addOval("E1", 300, 250, 25, 25,
                    0f, 1f, 1f, 3, 12)
            .addMove("R1", 25, 25, 16, 16,
                    2, 4)
            .addScaleToChange("E1", 25, 25, 40, 40, 4,
                    11).build();
    svgView.setModel(model);
    assertEquals(model, svgView.getModel());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetModelInvalidInput() {
    initData();
    assertNull(svgView.getModel());
    svgView.setModel(model);
  }

  @Test
  public void testSetLooping() {
    initData();
    assertFalse(svgView.getLoopState());
    assertEquals(0, svgView.getMaxTick());
    svgView.setLoopInfo(100);
    assertTrue(svgView.getLoopState());
    assertEquals(100, svgView.getMaxTick());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetLoopingInvalidInput() {
    initData();
    assertFalse(svgView.getLoopState());
    assertEquals(0, svgView.getMaxTick());
    svgView.setLoopInfo(-1);
  }

  @Test
  public void testPrintsEmptyAnimation() {
    initData();
    model = builder.setBounds(1000, 1000)
            .build();
    svgView.setModel(model);
    svgView.setFileName("out.svg");
    assertEquals("", svgView.getFinalString());
    try {
      svgView.drawSVG();
      svgView.export();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals("<svg width=\"1200\" height=\"1200\" version=\"1.1\" " +
            "xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "</svg>", svgView.getFinalString());
  }

  @Test
  public void testSVGViewAllThreeTypesOfMoves() {
    initData();
    model = builder.setBounds(1000, 1000)
            .addRectangle("R1", 25, 25, 100,
                    100, 0.5f, 0.5f, 0.5f, 1, 6)
            .addOval("E1", 300, 250, 25, 25,
                    0f, 1f, 1f, 3, 12)
            .addMove("R1", 25, 25, 16, 16,
                    2, 4)
            .addScaleToChange("E1", 25, 25, 40, 40, 4,
                    11)
            .addColorChange("R1", 0.5f, 0.5f, 0.5f, 1, 1, 1,
                    1, 5).build();
    svgView.setModel(model);
    svgView.setFileName("out.svg");
    assertEquals("", svgView.getFinalString());
    try {
      svgView.drawSVG();
      svgView.export();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals("<svg width=\"1200\" height=\"1200\" version=\"1.1\"" +
            " xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<rect id=\"R1\" x=\"25.0\" y=\"25.0\" width=\"100.0\" height=\"100.0\"" +
            " fill=\"rgb(128,128,128)\" visibility=\"visible\" >\n" +
            "<animate attributeType=\"xml\" begin=\"1000.0ms\" dur=\"1000.0ms\" " +
            "attributeName=\"x\" from=\"25.0\" to=\"25.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1000.0ms\" dur=\"1000.0ms\"" +
            " attributeName=\"y\" from=\"25.0\" to=\"25.0\" fill=\"freeze\" " +
            "/><animate attributeName=\"fill\" attributeType=\"xml\" begin=\"1000.0ms\"" +
            " dur=\"1000.0ms\"" +
            " from=\"rgb(128,128,128)\" to=\"rgb(159,159,159)\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2000.0ms\" dur=\"2000.0ms\"" +
            " attributeName=\"x\" from=\"25.0\" to=\"16.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2000.0ms\" dur=\"2000.0ms\"" +
            " attributeName=\"y\" from=\"25.0\" to=\"16.0\" fill=\"freeze\" />" +
            "<animate attributeName=\"fill\" " +
            "attributeType=\"xml\" begin=\"2000.0ms\" dur=\"2000.0ms\" from=\"rgb(159,159,159)\"" +
            " to=\"rgb(223,223,223)\"" +
            " fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"4000.0ms\" dur=\"1000.0ms\"" +
            " attributeName=\"x\" from=\"16.0\" to=\"16.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"4000.0ms\" dur=\"1000.0ms\" " +
            "attributeName=\"y\" from=\"16.0\" to=\"16.0\" fill=\"freeze\"" +
            " /><animate attributeName=\"fill\" " +
            "attributeType=\"xml\" begin=\"4000.0ms\" dur=\"1000.0ms\" from=\"" +
            "rgb(223,223,223)\" to=\"rgb(255,255,255)\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"5000.0ms\" dur=\"1000.0ms\"" +
            " attributeName=\"x\" from=\"16.0\" to=\"16.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"5000.0ms\" dur=\"1000.0ms\"" +
            " attributeName=\"y\" from=\"16.0\" to=\"16.0\" fill=\"freeze\" " +
            "/><animate attributeName=\"fill\" attributeType=\"xml\" begin=\"5000.0ms\" " +
            "dur=\"1000.0ms\" from=\"rgb(255,255,255)\" to=\"rgb(255,255,255)\" fill=\"" +
            "freeze\" />\n" +
            "</rect>\n" +
            "\n" +
            "<ellipse id=\"E1\" cx=\"300.0\" cy=\"250.0\" rx=\"25.0\" ry=\"25.0\" " +
            "fill=\"rgb(0,255,255)\" visibility=\"visible\" >\n" +
            "<animate attributeType=\"xml\" begin=\"3000.0ms\" dur=\"1000.0ms\"" +
            " attributeName=\"cx\" from=\"300.0\" to=\"300.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"3000.0ms\" dur=\"1000.0ms\" " +
            "attributeName=\"cy\" from=\"250.0\" to=\"250.0\" fill=\"freeze\" " +
            "/><animate attributeName=\"fill\" attributeType=\"xml\" begin=\"3000.0ms\"" +
            " dur=\"1000.0ms\" from=\"rgb(0,255,255)\" to=\"rgb(0,255,255)\" " +
            "fill=\"freeze\"" +
            " />\n" +
            "<animate attributeType=\"xml\" begin=\"4000.0ms\" dur=\"7000.0ms\"" +
            " attributeName=\"cx\" from=\"300.0\" to=\"300.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"4000.0ms\" dur=\"7000.0ms\"" +
            " attributeName=\"cy\" from=\"250.0\" to=\"250.0\" fill=\"freeze\" " +
            "/><animate attributeType=\"xml\" begin=\"4000.0ms\" dur=\"7000.0ms\"" +
            " attributeName=\"rx\" from=\"25.0\" to=\"40.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"4000.0ms\" dur=\"7000.0ms\"" +
            " attributeName=\"ry\" from=\"25.0\" to=\"40.0\" fill=\"freeze\" " +
            "/><animate attributeName=\"fill\" attributeType=\"xml\" begin=\"4000.0ms\"" +
            " dur=\"7000.0ms\" from=\"rgb(0,255,255)\" to=\"rgb(0,255,255)\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"11000.0ms\" dur=\"1000.0ms\"" +
            " attributeName=\"cx\" from=\"300.0\" to=\"300.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"11000.0ms\" dur=\"1000.0ms\"" +
            " attributeName=\"cy\" from=\"250.0\" to=\"250.0\" fill=\"freeze\" " +
            "/><animate attributeName=\"fill\" attributeType=\"xml\" begin=\"11000.0ms\"" +
            " dur=\"1000.0ms\" from=\"rgb(0,255,255)\" to=\"rgb(0,255,255)\" " +
            "fill=\"freeze\" />\n" +
            "</ellipse>\n" +
            "\n" +
            "</svg>", svgView.getFinalString());
  }
}
