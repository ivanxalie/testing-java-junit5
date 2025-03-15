package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.ControllerTests;
import guru.springframework.sfgpetclinic.fauxspring.ModelMapImpl;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.map.VetMapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class VetControllerTest implements ControllerTests {
    private VetController controller;
    private VetService vetService;

    @BeforeEach
    void setUp() {
        vetService = new VetMapService(null);
        controller = new VetController(vetService);

        vetService.save(new Vet(1L, "Andrew", "Wilson", Set.of()));
        vetService.save(new Vet(2L, "Ann", "Ginger", Set.of()));
        vetService.save(new Vet(3L, "John", "Omit", Set.of()));
    }

    @SuppressWarnings("unchecked")
    @Test
    void listVetsViewName() {
        ModelMapImpl model = new ModelMapImpl();
        assertThat(controller.listVets(model)).isEqualTo("vets/index");
        Map<String, Object> storage = model.storage();
        assertThat(storage.size()).isEqualTo(1);
        assertThat(storage).containsKeys("vets");
        Set<Vet> vets = (Set<Vet>) storage.get("vets");
        assertThat(vets).isNotNull().hasSize(3);
    }
}