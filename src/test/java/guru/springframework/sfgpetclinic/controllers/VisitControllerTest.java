package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.VisitService;
import guru.springframework.sfgpetclinic.services.map.PetMapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atMost;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {
    @Mock
    private VisitService visitService;

    @Spy
    private PetMapService petService;

    @InjectMocks
    private VisitController controller;

    @Test
    void loadPetWithVisit() {
        // given
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(1L);
        petService.save(pet);
        petService.save(new Pet(3L));
        given(petService.findById(anyLong())).willCallRealMethod();

        // when
        Visit visit = controller.loadPetWithVisit(1L, model);

        // then
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).satisfies(savedPet -> {
            assertThat(savedPet).isNotNull();
            assertThat(savedPet.getId()).isEqualTo(1L);
        });
        then(petService).should(atMost(1)).findById(anyLong());
    }
}