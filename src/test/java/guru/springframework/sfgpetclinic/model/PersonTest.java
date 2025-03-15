package guru.springframework.sfgpetclinic.model;

import guru.springframework.sfgpetclinic.ModelTests;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


class PersonTest implements ModelTests {

    @Test
    void groupedAssertions() {
        // give
        Person person = new Person(1L, "Joe", "Black");

        // then
        assertAll("Test Props Set",
                () -> assertEquals("Joe", person.getFirstName(), "First Name Failed!"),
                () -> assertEquals("Black", person.getLastName(), "Last Name Failed!")
        );
    }

    @DisplayName("My Repeated Test")
    @RepeatedTest(value = 10, name = "{displayName}:{currentRepetition}/{totalRepetitions}")
    void myRepeatedTest() {
    }

    @RepeatedTest(5)
    void myRepeatedTestWithDI(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        System.out.printf("%s : %s%n", testInfo.getDisplayName(), repetitionInfo.getCurrentRepetition());
    }
}