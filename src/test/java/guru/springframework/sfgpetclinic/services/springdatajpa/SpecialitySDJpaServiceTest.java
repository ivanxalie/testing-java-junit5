package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SpecialitySDJpaServiceTest {

    @Mock
    private SpecialtyRepository specialtyRepository;

    @InjectMocks
    private SpecialitySDJpaService service;

    @AfterEach
    void tearDown() {
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteById() {
        // when
        service.deleteById(1L);
        service.deleteById(1L);

        // then
        then(specialtyRepository).should(times(2)).deleteById(1L);
    }

    @Test
    void deleteByIdAtLeast() {
        // when
        service.deleteById(1L);
        service.deleteById(1L);

        // then
        then(specialtyRepository).should(atLeastOnce()).deleteById(1L);
    }

    @Test
    void deleteByIdAtMost() {
        // when
        service.deleteById(1L);

        // then
        then(specialtyRepository).should(atMost(2)).deleteById(1L);
    }

    @Test
    void deleteNever() {
        // when
        service.deleteById(1L);
        service.deleteById(1L);

        // then
        then(specialtyRepository).should(never()).deleteById(5L);
        then(specialtyRepository).should(atLeastOnce()).deleteById(1L);
    }

    @Test
    void delete() {
        // given
        Speciality speciality = new Speciality();

        // when
        service.delete(speciality);

        // then
        then(specialtyRepository).should().delete(speciality);
    }

    @Test
    void deleteByObject() {
        // given
        Speciality speciality = new Speciality();

        // when
        service.delete(speciality);

        // then
        then(specialtyRepository).should().delete(any(Speciality.class));
    }

    @Test
    void findByIdTest() {
        // given
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(1L)).willReturn(Optional.of(speciality));

        // when
        Speciality found = service.findById(1L);

        // then
        assertThat(found).isNotNull().isEqualTo(speciality);
        then(specialtyRepository).should().findById(1L);
    }

    @Test
    void testDoThrow() {
        // given
        willThrow(new RuntimeException("boom")).given(specialtyRepository).delete(any(Speciality.class));

        // when
        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));

        // then
        then(specialtyRepository).should().delete(any(Speciality.class));
    }

    @Test
    void testFindByIdThrows() {
        // given
        given(specialtyRepository.findById(any(Long.class))).willThrow(new RuntimeException("boom"));

        // when
        assertThrows(RuntimeException.class, () -> service.findById(1L));

        // then
        then(specialtyRepository).should().findById(1L);
    }

    @Test
    void testSaveLambda() {
        // given
        String match = "MATCH_ME";
        Speciality speciality = new Speciality();
        speciality.setDescription(match);

        Speciality saved = new Speciality();
        saved.setId(1L);

        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(match))))
                .willReturn(saved);

        // when
        Speciality returned = service.save(speciality);

        // then
        assertThat(returned.getId()).isEqualTo(1L);
        then(specialtyRepository).should().save(any(Speciality.class));
    }

    @Test
    void testSaveLambdaNoMatch() {
        // given
        String match = "MATCH_ME";
        Speciality speciality = new Speciality();
        speciality.setDescription("Not a chance!");

        Speciality saved = new Speciality();
        saved.setId(1L);

        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(match))))
                .willReturn(saved);

        // when
        Speciality returned = service.save(speciality);

        // then
        assertNull(returned);
        then(specialtyRepository).should().save(any(Speciality.class));
    }
}