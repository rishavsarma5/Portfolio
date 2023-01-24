
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JOptionPane;

import controller.CreateController;
import controller.IController;
import io.AnimationFileReader;
import io.AnimatorBuilder;
import model.IModel;
import view.CreateView;
import view.IView;

/**
 * The Main class of the Animator that takes in command line arguments from the user (-in, -view,
 * -out, -speed) and creates a Model, Controller, and appropriate View. The Main function reads in
 * from the given file and uses the specific type of view to generate the three output formats.
 */
public class Main {

  /**
   * The Main function of the animator.
   * @param args List of String used to create IModel, IController, and IView of the Animator.
   */
  public static void main(String[] args) {
    JPanel panel = new JPanel();
    String file = "";
    String viewType = "";
    String output = "out";
    int tempo = 1;

    for (int i = 0; i < args.length; i += 2) {
      switch (args[i]) {
        case "-in":
          if (!args[i + 1].endsWith(".txt")) {
            JOptionPane.showMessageDialog(panel, "No valid file given after -in!");
          } else {
            file = args[i + 1];
          }
          break;
        case "-view":
          if (!validViewType(args[i + 1])) {
            JOptionPane.showMessageDialog(panel, "Invalid view type!");
          } else {
            viewType = args[i + 1];
          }
          break;
        case "-out":
          output = args[i + 1];
          break;
        case "-speed":
          try {
            tempo = Integer.parseInt(args[i + 1]);
          } catch (NumberFormatException e) {
            throw new NumberFormatException("Input is not a valid int!");
          }
          break;
        default:
          JOptionPane.showMessageDialog(panel, "Invalid input given!!");
      }
    }

    AnimationFileReader reader = new AnimationFileReader();

    if (!file.equals("")) {
      CreateView viewFactory = new CreateView();
      CreateController ctrlFactory = new CreateController();

      try {
        IModel model = reader.readFile(file, new AnimatorBuilder());
        IView view = viewFactory.create(viewType, model, tempo);
        if (viewType.equals("text") || viewType.equals("svg")) {
          view.setFileName(output);
        }
        IController ctrl = ctrlFactory.create(model, view, viewType);
        ctrl.setTempo(tempo);
        ctrl.start();
      } catch (IOException e) {
        JOptionPane.showMessageDialog(panel, "Invalid inputs!");
      }
    }
  }

  /**
   * Returns a boolean if the input of -view is a valid type.
   * @param input the given string
   * @return a boolean
   */
  private static boolean validViewType(String input) {
    if (input == null) {
      throw new IllegalArgumentException("Input is invalid!");
    }

    return input.equals("text") || input.equals("svg") || input.equals("visual")
            || input.equals("interactive");
  }

}

