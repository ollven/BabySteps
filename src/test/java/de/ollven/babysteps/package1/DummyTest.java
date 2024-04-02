package de.ollven.babysteps.package1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DummyTest {

    @Test
    public void testThatAlwaysFails() throws InterruptedException {
        Thread.sleep(200000);
        Assertions.assertTrue(true);    }

    @Test
    public void testThatAlwaysPasses() throws InterruptedException {
        Thread.sleep(200000);
        Assertions.assertTrue(true);
    }

    @Test
    public void testThatIsSlowButAlwaysFails() throws InterruptedException {
        Thread.sleep(20000);
        Assertions.assertTrue(true);
    }

    @Test
    public void testThatIsSlowButAlwaysPasses() throws InterruptedException {
        Thread.sleep(20000);
        Assertions.assertTrue(true);
    }
}
