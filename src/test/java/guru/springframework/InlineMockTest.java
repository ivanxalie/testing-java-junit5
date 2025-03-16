package guru.springframework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class InlineMockTest {
    @Test
    void testInlineMock() {
        Map<String, Object> mapMock = mock(Map.class);
        assertThat(mapMock).hasSize(0);
    }
}
