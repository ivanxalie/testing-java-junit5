package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.SpecialtyService;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.map.SpecialityMapService;
import guru.springframework.sfgpetclinic.services.map.VetMapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class VetControllerTest {
    private VetController controller;
    private VetService vetService;
    private SpecialtyService specialtyService;

    @BeforeEach
    void setUp() {
        specialtyService = new SpecialityMapService();
        vetService = new VetMapService(specialtyService);
        controller = new VetController(vetService);
        Speciality therapist = new Speciality(1L, "Therapist");
        Speciality gastroenterologist = new Speciality(2L, "Gastroenterologist");
        Speciality oncologist = new Speciality(3L, "Oncologist");
        Speciality psychologist = new Speciality(4L, "Psychologist");

        vetService.save(new Vet(1L, "Andrew", "Wilson", Set.of(
                therapist, oncologist
        )));
        vetService.save(new Vet(2L, "Ann", "Ginger", Set.of(
                gastroenterologist
        )));
        vetService.save(new Vet(3L, "John", "Omit", Set.of(
                therapist, psychologist
        )));
    }

    @Test
    void listVetsViewName() {
        assertThat(controller.listVets(new EmptyModel())).isEqualTo("vets/index");
    }


}

class EmptyModel implements Model {

    @SuppressWarnings("unchecked")
    @Override
    public void addAttribute(String key, Object o) {
        assertThat(key).isEqualTo("vets");
        assertThat(o).isInstanceOf(Set.class);
        Set<Vet> vets = (Set<Vet>) o;
        assertThat(vets).isNotNull().hasSize(3);
    }

    @Override
    public void addAttribute(Object o) {

    }
}