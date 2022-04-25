package animationcreator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class represents a main class that constructs two animations which are written into files
 * that the Main class of the EastAnimator program can then play. Each animation contains 5 shapes.
 * One animation is generated programmatically while another is hard-coded.
 */
public class GeneratorMain {

  /**
   * The main class used to create the two animation files.
   * @param args list of string arguments not used in this method
   */
  public static void main(String[] args) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter("prgen.txt",
              true));
      IAnimationCreator t1 = new TowerOfHanoiGenerator();
      t1.move(5, 1, 3);
      String s = t1.toString();
      writer.write(s);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter("man.txt",
              true));
      IAnimationCreator t1 = new ManualGenerator();
      String s = t1.toString();
      writer.write(s);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
