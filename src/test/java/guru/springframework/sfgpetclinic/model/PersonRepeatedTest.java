package guru.springframework.sfgpetclinic.model;

import guru.springframework.sfgpetclinic.ModelRepeatedTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;

public class PersonRepeatedTest implements ModelRepeatedTests {
    @DisplayName("My Repeated Test")
    @RepeatedTest(value = 10, name = "{displayName}:{currentRepetition}/{totalRepetitions}")
    void myRepeatedTest() {
    }

    @RepeatedTest(5)
    void myRepeatedTestWithDI(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        System.out.printf("%s : %s%n", testInfo.getDisplayName(), repetitionInfo.getCurrentRepetition());
    }

    @RepeatedTest(value = 2, name = "{currentRepetition}/{totalRepetitions}")
    @DisplayName("New Repeated Test")
    void newRepeatedTest() {

    }
}
