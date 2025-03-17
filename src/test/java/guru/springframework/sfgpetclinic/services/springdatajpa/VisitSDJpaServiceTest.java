package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    private VisitSDJpaService service;

    private Visit visit;

    @BeforeEach
    void setUp() {
        visit = new Visit();
    }

    @DisplayName("Test Find All")
    @Test
    void findAll() {
        // given
        Set<Visit> visits = Set.of(visit);
        given(visitRepository.findAll()).willReturn(visits);

        // when
        Set<Visit> foundVisits = service.findAll();

        // then
        assertThat(foundVisits).isNotNull().hasSize(1).hasSameElementsAs(visits);
        then(visitRepository).should().findAll();
    }

    @DisplayName("Test Find by Id")
    @Test
    void findById() {
        // given
        given(visitRepository.findById(1L)).willReturn(Optional.of(visit));

        // when
        Visit foundVisit = service.findById(1L);

        // then
        assertThat(foundVisit).isNotNull().isEqualTo(visit);
        then(visitRepository).should().findById(1L);
    }

    @DisplayName("Test save")
    @Test
    void save() {
        // given
        given(visitRepository.save(any(Visit.class))).willReturn(visit);

        // when
        Visit saved = service.save(visit);

        // then
        assertThat(saved).isNotNull().isEqualTo(visit);
        then(visitRepository).should().save(any(Visit.class));
    }

    @DisplayName("Test delete")
    @Test
    void delete() {
        // when
        service.delete(visit);

        // then
        then(visitRepository).should().delete(any(Visit.class));
    }

    @DisplayName("Test delete by Id")
    @Test
    void deleteById() {
        // when
        service.deleteById(1L);

        // then
        then(visitRepository).should().deleteById(1L);
    }
}