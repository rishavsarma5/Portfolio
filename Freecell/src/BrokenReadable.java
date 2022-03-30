import java.io.IOException;
import java.nio.CharBuffer;

/**
 * This class represents a BrokenReadable.
 * It is used to test readables that throw IOExceptions.
 */
public class BrokenReadable implements Readable {

  @Override
  public int read(CharBuffer cb) throws IOException {
    throw new IOException("The readable failed!");
  }
}
