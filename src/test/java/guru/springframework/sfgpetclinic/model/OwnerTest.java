package guru.springframework.sfgpetclinic.model;

import guru.springframework.sfgpetclinic.CustomArgsProvider;
import guru.springframework.sfgpetclinic.ModelTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OwnerTest implements ModelTests {

    public static Stream<Arguments> provider() {
        return Stream.of(
                Arguments.of("AL", 1, 2),
                Arguments.of("OH", 3, 4),
                Arguments.of("DC", 5, 6),
                Arguments.of("TX", 7, 8),
                Arguments.of("CA", 9, 0)
        );
    }

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

    @DisplayName("Value source test")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @ValueSource(strings = {"This", "is", "example"})
    void testValueSource(String value) {
        System.out.println(value);
    }

    @DisplayName("Enum source test")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @EnumSource(OwnerType.class)
    void enumTest(OwnerType type) {
        System.out.println(type);
    }

    @DisplayName("Csv input source test")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @CsvSource(value = {
            "FL, 1, 1",
            "OH, 2, 2",
            "MI, 3, 4"
    })
    void csvInputTest(String stateName, int val1, int val2) {
        System.out.printf("State name: %s, val1: %s, val2: %s%n", stateName, val1, val2);
    }

    @DisplayName("Csv input source file test")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @CsvFileSource(resources = "/input.csv", numLinesToSkip = 1)
    void csvFileInputTest(String stateName, int val1, int val2) {
        System.out.printf("State name: %s, val1: %s, val2: %s%n", stateName, val1, val2);
    }

    @DisplayName("Method Provider test")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @MethodSource("provider")
    void methodProviderTest(String stateName, int val1, int val2) {
        System.out.printf("State name: %s, val1: %s, val2: %s%n", stateName, val1, val2);
    }

    @DisplayName("Custom Arguments Provider test")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @ArgumentsSource(CustomArgsProvider.class)
    void methodCustomProviderTest(String stateName, int val1, int val2) {
        System.out.printf("State name: %s, val1: %s, val2: %s%n", stateName, val1, val2);
    }
}