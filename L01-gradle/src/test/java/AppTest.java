import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    @Test
    void when_given_then_expected() {
        PrintStream defaultOut = System.out;
        ByteArrayOutputStream customOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(customOut));

        App.main((String) null);

        System.setOut(defaultOut);

        assertThat(customOut.toString()).isEqualTo("[9, 8, 7, 6, 5, 4, 3, 2, 1]" + System.lineSeparator());
    }
}
