import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.JTextField;
import javax.swing.JCheckBox;

import controller.IController;
import controller.IInteractiveController;
import controller.InteractiveVisualController;
import controller.InteractiveVisualControllerMock;
import io.AnimatorBuilder;
import io.TweenModelBuilder;
import model.IModel;
import view.IView;
import view.InteractiveVisualView;
import view.TextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test class for interactive visual controller and view.
 */
public class InteractiveVisualTest {
  IInteractiveController mockCtrl;
  IView view;
  IModel model;
  TweenModelBuilder<IModel> builder;
  int tempo;

  @Before
  public void initData() {
    tempo = 1;
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
    view = new InteractiveVisualView(tempo);
    mockCtrl = new InteractiveVisualControllerMock(model, view);
  }

  @Test
  public void testSetTempoWorks() {
    initData();
    IInteractiveController ctrl = new InteractiveVisualController(model, view);
    assertEquals(1, ctrl.getTempo());
    ctrl.setTempo(50);
    assertEquals(50, ctrl.getTempo());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetTempoInvalidTempo() {
    initData();
    IInteractiveController ctrl = new InteractiveVisualController(model, view);
    assertEquals(1, ctrl.getTempo());
    ctrl.setTempo(-5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBothBadInputs() {
    IController badCtrl = new InteractiveVisualController(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBadView() {
    initData();
    IController badCtrl = new InteractiveVisualController(model, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBadModel() {
    initData();
    IController badCtrl = new InteractiveVisualController(null, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidView() {
    initData();
    IController badCtrl = new InteractiveVisualController(model, new TextView());
  }

  @Test
  public void testStartWithMock() {
    InteractiveVisualView interView = new InteractiveVisualView(1);
    interView.setController(mockCtrl);
    ActionEvent start = new ActionEvent(interView, 10, "Start animation");
    assertEquals("", mockCtrl.getLog());
    interView.actionPerformed(start);
    assertEquals("The timer was started!", mockCtrl.getLog());
  }

  @Test
  public void testPauseAndResumeWithMock() {
    InteractiveVisualView interView = new InteractiveVisualView(1);
    interView.setController(mockCtrl);
    ActionEvent pause = new ActionEvent(interView, 10, "Pause animation");
    ActionEvent resume = new ActionEvent(interView, 10, "Resume animation");
    assertEquals("", mockCtrl.getLog());
    assertEquals(false, mockCtrl.getPausedState());
    interView.actionPerformed(pause);
    assertEquals("The animation was paused and will not continue to run until resumed!",
            mockCtrl.getLog());
    assertEquals(true, mockCtrl.getPausedState());
    interView.actionPerformed(resume);
    assertEquals("The animation was paused and will not continue to run until resumed!" +
                    "The animation was un-paused and will continue to run.",
            mockCtrl.getLog());
    assertEquals(false, mockCtrl.getPausedState());
  }

  @Test
  public void testRestartWithMock() {
    InteractiveVisualView interView = new InteractiveVisualView(1);
    interView.setController(mockCtrl);
    ActionEvent restart = new ActionEvent(interView, 10, "Restart animation");
    assertEquals("", mockCtrl.getLog());
    interView.actionPerformed(restart);
    assertEquals("The timer was reset to have a current tick of 1!",
            mockCtrl.getLog());
  }

  @Test
  public void testLoopingWithMock() {
    InteractiveVisualView interView = new InteractiveVisualView(1);
    interView.setController(mockCtrl);
    JCheckBox looper = new JCheckBox("Loop animation");
    looper.setSelected(false);
    looper.setActionCommand("Loop animation");
    ItemEvent loop = new ItemEvent(looper, 10, interView, ItemEvent.DESELECTED);
    assertEquals("", mockCtrl.getLog());
    assertEquals(false, mockCtrl.getLoopingState());
    interView.itemStateChanged(loop);
    assertEquals("The loop value has now been set to: true!",
            mockCtrl.getLog());
    assertEquals(true, mockCtrl.getLoopingState());

    interView.itemStateChanged(loop);
    assertEquals("The loop value has now been set to: true!" +
                    "The loop value has now been set to: false!",
            mockCtrl.getLog());
    assertEquals(false, mockCtrl.getLoopingState());
  }

  @Test
  public void testChangeSpeedWithMock() {
    InteractiveVisualView interView = new InteractiveVisualView(1);
    interView.setController(mockCtrl);
    ActionEvent setTempo = new ActionEvent(interView, 10, "Set tempo");
    JTextField tField = new JTextField(5);
    tField.setActionCommand("Set tempo");
    assertEquals("", mockCtrl.getLog());
    assertEquals(1, mockCtrl.getTempo());
    tField.addActionListener(interView);
    tField.setText("56");
    interView.add(tField);
    try {
      interView.actionPerformed(setTempo);
      mockCtrl.setTempo(Integer.parseInt(tField.getText()));
      assertEquals("The tempo was set to the given tempo 56!",
              mockCtrl.getLog());
      assertEquals(56, mockCtrl.getTempo());
    }
    catch (NumberFormatException nfe) {
      assertEquals(1, mockCtrl.getTempo());
    }
  }

  @Test(expected = NumberFormatException.class)
  public void testChangeSpeedWithMockInvalid() {
    InteractiveVisualView interView = new InteractiveVisualView(1);
    interView.setController(mockCtrl);
    ActionEvent setTempo = new ActionEvent(interView, 10, "Set tempo");
    JTextField tField = new JTextField(5);
    tField.setActionCommand("Set tempo");
    assertEquals("", mockCtrl.getLog());
    assertEquals(1, mockCtrl.getTempo());
    tField.addActionListener(interView);
    tField.setText("ty");
    interView.add(tField);
    mockCtrl.setTempo(Integer.parseInt(tField.getText()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeSpeedWithMockInvalidNeg() {
    InteractiveVisualView interView = new InteractiveVisualView(1);
    interView.setController(mockCtrl);
    ActionEvent setTempo = new ActionEvent(interView, 10, "Set tempo");
    JTextField tField = new JTextField(5);
    tField.setActionCommand("Set tempo");
    assertEquals("", mockCtrl.getLog());
    assertEquals(1, mockCtrl.getTempo());
    tField.addActionListener(interView);
    tField.setText("-1");
    interView.add(tField);
    mockCtrl.setTempo(Integer.parseInt(tField.getText()));
  }

  @Test
  public void testSetController() {
    initData();
    IView interView = new InteractiveVisualView(1);
    assertNull(interView.getController());
    interView.setController(mockCtrl);
    assertEquals(mockCtrl, interView.getController());
  }
}
