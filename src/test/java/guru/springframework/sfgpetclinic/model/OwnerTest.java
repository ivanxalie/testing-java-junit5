package guru.springframework.sfgpetclinic.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OwnerTest {

    @Test
    void dependantAssertions() {
        Owner owner = new Owner(1L, "Joe", "Buck");
        owner.setCity("New York");
        owner.setTelephone("1234567890");

        assertAll("Properties Test",
                () -> assertAll("Person Properties",
                        () -> assertEquals("Joe", owner.getFirstName(), "First Name did not match!"),
                        () -> assertEquals("Buck", owner.getLastName(), "Last Name did not match!")
                ),
                () -> assertAll("Owner Properties",
                        () -> assertEquals("New York", owner.getCity(), "City did not match!"),
                        () -> assertEquals("1234567890", owner.getTelephone(), "Telephone did not match!")
                )
        );
    }
}