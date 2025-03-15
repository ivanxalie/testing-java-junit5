package guru.springframework.sfgpetclinic.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("model")
class PersonTest {

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