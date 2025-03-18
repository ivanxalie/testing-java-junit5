package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static guru.springframework.sfgpetclinic.controllers.OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    @Mock
    private OwnerService service;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private OwnerController controller;

    @Test
    void processCreationFormHasErrors() {
        // given
        given(bindingResult.hasErrors()).willReturn(true);

        // when
        String result = controller.processCreationForm(new Owner(1L, "", ""), bindingResult);

        // then
        assertThat(result).isNotNull().isEqualTo(VIEWS_OWNER_CREATE_OR_UPDATE_FORM);
        then(bindingResult).should().hasErrors();
    }

    @Test
    void processCreationFormHasNoErrors() {
        // given
        given(bindingResult.hasErrors()).willReturn(false);
        Owner owner = new Owner(5L, "", "");
        given(service.save(any(Owner.class))).willReturn(owner);

        // when
        String result = controller.processCreationForm(owner, bindingResult);

        // then
        assertThat(result).isNotNull().isEqualTo("redirect:/owners/5");
        then(bindingResult).should().hasErrors();
        then(service).should().save(owner);
    }
}