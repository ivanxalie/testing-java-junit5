package guru.springframework.sfgpetclinic;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class CustomArgsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of("AL", 7, 7),
                Arguments.of("OH", 9, 9),
                Arguments.of("DC", 5, 5),
                Arguments.of("TX", 3, 3),
                Arguments.of("CA", 0, 0)
        );
    }
}
