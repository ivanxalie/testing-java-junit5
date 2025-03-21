package guru.springframework.sfgpetclinic.model;

import guru.springframework.sfgpetclinic.ModelTests;
import org.junit.jupiter.api.Test;

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
}