package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.ControllerTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;


class IndexControllerTest implements ControllerTests {
    private IndexController indexController;

    @BeforeEach
    void setUp() {
        indexController = new IndexController();
    }

    @Test
    @DisplayName("Test Proper View name is returned for index page")
    void index() {
        assertEquals("index", indexController.index(), "Wrong View Returned!");

        assertThat("index").isEqualTo(indexController.index());
    }

    @Test
    @DisplayName("Test exception")
    void oupsHandler() {
        assertThrows(ValueNotFoundException.class, () -> indexController.oopsHandler(),
                "Should throw ValueNotFoundException");
    }

    @Test
    @Disabled("Demo of timeout")
    void testTimeout() {
        assertTimeout(Duration.ofMillis(100), () -> Thread.sleep(2000));
    }

    @Test
    @Disabled("Demo of timeout")
    void testTimeoutPreempt() {
        assertTimeoutPreemptively(Duration.ofMillis(100),
                () -> Thread.sleep(2000));
    }

    @Test
    void testAssumptionTrue() {
        assumeTrue("GURU".equalsIgnoreCase(System.getProperty("GURU_RUNTIME")));
        assertFalse(true);
    }

    @Test
    void testAssumptionFalse() {
        assumeTrue("GURU".equalsIgnoreCase("GURU"));
//        assertFalse(true);
    }

    @Test
    @EnabledOnOs(OS.MAC)
    void testMeOnMacOS() {

    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testMeOnWindows() {

    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void testMeOnJava8() {

    }

    @Test
    @EnabledOnJre(JRE.JAVA_11)
    void testMeOnJava11() {

    }

    @Test
    @EnabledIfEnvironmentVariable(named = "USERNAME", matches = "ivan")
    void testIfUserMe() {

    }

    @Test
    @EnabledIfEnvironmentVariable(named = "USERNAME", matches = "john")
    void testIFUserJohn() {

    }
}