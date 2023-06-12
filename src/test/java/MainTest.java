import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;


class MainTest {

    @Test
    public void testResult() {
        int a = 3;
        int b = 5;
        int c = 7;
        int actual = Main.result(a, b, c);
        int expected = 4;
        Assertions.assertEquals(expected, actual);
    }
}