package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static guru.springframework.sfgpetclinic.controllers.OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OwnerControllerTest {
    @Mock
    private OwnerService service;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private OwnerController controller;

    @Captor
    private ArgumentCaptor<String> captor;

    @BeforeEach
    void setUp() {
        given(service.findAllByLastNameLike(captor.capture()))
                .willAnswer(invocation -> {
                    List<Owner> owners = new ArrayList<>();
                    String name = invocation.getArgument(0);

                    if ("%Buck%".equals(name)) {
                        owners.add(new Owner(5L, "Joe", "Buck"));
                        return owners;
                    } else if ("%CantTouchMe%".equals(name)) {
                        return owners;
                    } else if ("%TouchMe%".equals(name)) {
                        owners.add(new Owner(5L, "Joe", "Buck"));
                        owners.add(new Owner(5L, "Joe", "Buck"));
                        return owners;
                    }

                    throw new RuntimeException("Invalid Argument");
                });
    }

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

    @Test
    void processFindFormWildcardString() {
        // given
        Owner owner = new Owner(5L, "Joe", "Buck");

        // when
        String result = controller.processFindForm(owner, bindingResult, model);

        // then
        assertThat(captor.getValue()).isNotNull().isEqualTo("%Buck%");
        assertThat(result).isNotNull().isEqualTo("redirect:/owners/5");
        then(model).shouldHaveZeroInteractions();
        then(service).should().findAllByLastNameLike(captor.getValue());
    }

    @Test
    void processFindFormWildcardNotFound() {
        // given
        Owner owner = new Owner(1L, "Joe", "CantTouchMe");

        // when
        String result = controller.processFindForm(owner, bindingResult, model);

        // then
        assertThat(captor.getValue()).isNotNull().isEqualTo("%CantTouchMe%");
        assertThat(result).isNotNull().isEqualTo("owners/findOwners");
        then(model).shouldHaveZeroInteractions();
        then(service).should().findAllByLastNameLike(captor.getValue());
    }

    @Test
    void processFindFormWildcardFoundMany() {
        // given
        Owner owner = new Owner(1L, "Joe", "TouchMe");
        InOrder order = inOrder(service, model);

        // when
        String result = controller.processFindForm(owner, bindingResult, model);

        // then
        assertThat(captor.getValue()).isNotNull().isEqualTo("%TouchMe%");
        assertThat(result).isNotNull().isEqualTo("owners/ownersList");
        then(service).should(order).findAllByLastNameLike(captor.getValue());
        then(model).should(order).addAttribute(anyString(), anyList());
        then(model).shouldHaveNoMoreInteractions();
    }
}