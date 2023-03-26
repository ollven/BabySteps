import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class MainTest {

    @Test
    public void testResult() {
        int a = 3;
        int b = 5;
        int c = 7;
        int actual = Main.result(a, b, c);
        int expected = 11;
        Assertions.assertEquals(expected, actual);
    }
}