import org.junit.Test;

import model.IModel;
import model.Model;
import view.CreateView;
import view.InteractiveVisualView;
import view.SVGView;
import view.TextView;
import view.VisualAnimationView;

import static org.junit.Assert.assertTrue;

/**
 * JUnit test cases for the CreateView class.
 */
public class CreateViewTest {
  CreateView viewFactory = new CreateView();
  IModel model;

  @Test
  public void testViewFactory() {
    model = new Model();
    model.setBounds(1000, 1000);
    assertTrue(viewFactory.create("text", model, 1) instanceof TextView);
    assertTrue(viewFactory.create("svg", model, 1) instanceof SVGView);
    assertTrue(viewFactory.create("visual", model, 1) instanceof VisualAnimationView);
    assertTrue(viewFactory.create("interactive", model, 1)
            instanceof InteractiveVisualView);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testViewFactoryInvalidInputs1() {
    viewFactory.create("text", model, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testViewFactoryInvalidInputs2() {
    model = new Model();
    model.setBounds(1000, 1000);
    viewFactory.create("yo", model, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testViewFactoryInvalidInputs3() {
    model = new Model();
    model.setBounds(1000, 1000);
    viewFactory.create(null, model, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testViewFactoryInvalidInputs4() {
    model = new Model();
    model.setBounds(1000, 1000);
    viewFactory.create(null, model, -5);
  }
}
