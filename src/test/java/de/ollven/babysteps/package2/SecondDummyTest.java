package de.ollven.babysteps.package2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SecondDummyTest {

    @Test
    public void secondtestThatAlwaysFails() throws InterruptedException {
        Thread.sleep(1000);
        Assertions.assertTrue(true);
    }

    @Test
    public void secondtestThatAlwaysPasses() throws InterruptedException {
        Thread.sleep(1000);
        Assertions.assertTrue(false);
    }

    @Test
    public void secondtestThatIsSlowButAlwaysFails() throws InterruptedException {
        Thread.sleep(1000);
        Assertions.assertTrue(true);
    }

    @Test
    public void secondtestThatIsSlowButAlwaysPasses() throws InterruptedException {
        Thread.sleep(1000);
        Assertions.assertTrue(true);
    }
}
