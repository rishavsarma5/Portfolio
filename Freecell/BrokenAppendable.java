import java.io.IOException;

/**
 * This class represents a BrokenAppendable.
 * It is used to test appendables that throw IOExceptions.
 */
public class BrokenAppendable implements Appendable {

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("The appendable failed!");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("The appendable failed!");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("The appendable failed!");
  }
}

