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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        Set<Visit> visits = Set.of(visit);
        when(visitRepository.findAll()).thenReturn(visits);

        Set<Visit> foundVisits = service.findAll();

        assertThat(foundVisits).isNotNull().hasSize(1).hasSameElementsAs(visits);
        verify(visitRepository).findAll();
    }

    @DisplayName("Test Find by Id")
    @Test
    void findById() {
        when(visitRepository.findById(1L)).thenReturn(Optional.of(visit));

        Visit foundVisit = service.findById(1L);

        assertThat(foundVisit).isNotNull().isEqualTo(visit);
        verify(visitRepository).findById(1L);
    }

    @DisplayName("Test save")
    @Test
    void save() {
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        Visit saved = service.save(visit);

        assertThat(saved).isNotNull().isEqualTo(visit);
        verify(visitRepository).save(any(Visit.class));
    }

    @DisplayName("Test delete")
    @Test
    void delete() {
        service.delete(visit);
        verify(visitRepository).delete(any(Visit.class));
    }

    @DisplayName("Test delete by Id")
    @Test
    void deleteById() {
        service.deleteById(1L);
        verify(visitRepository).deleteById(1L);
    }
}