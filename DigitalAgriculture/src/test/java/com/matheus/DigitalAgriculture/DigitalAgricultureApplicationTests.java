package com.matheus.DigitalAgriculture;

import com.matheus.DigitalAgriculture.dto.request.UsersRequestDTO;
import com.matheus.DigitalAgriculture.model.Fields;
import com.matheus.DigitalAgriculture.repository.FieldsRepository;
import com.matheus.DigitalAgriculture.services.AuthenticationUsersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.CommandLineRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DigitalAgricultureApplicationTest {

    @Mock AuthenticationUsersService authenticationUsersService;
    @Mock FieldsRepository fieldsRepository;

    @Test
    void initDataBase_shouldRegisterUserAndSaveField() throws Exception {
        DigitalAgricultureApplication app = new DigitalAgricultureApplication();
        CommandLineRunner runner = app.initDataBase(authenticationUsersService, fieldsRepository);
        assertNotNull(runner);

        ArgumentCaptor<UsersRequestDTO> userCaptor = ArgumentCaptor.forClass(UsersRequestDTO.class);
        ArgumentCaptor<Fields> fieldCaptor = ArgumentCaptor.forClass(Fields.class);

        runner.run();

        verify(authenticationUsersService).register(userCaptor.capture());
        UsersRequestDTO dto = userCaptor.getValue();
        assertEquals("teste@teste.com1", dto.email());
        assertEquals("matheus", dto.name());
        assertEquals("123456", dto.password());

        verify(fieldsRepository).save(fieldCaptor.capture());
        Fields saved = fieldCaptor.getValue();
        assertEquals("Campo norte", saved.getName());
        assertEquals("soja", saved.getCrop());
        assertEquals(120, saved.getAreaHectares());
        assertNotNull(saved.getLatitude());
        assertNotNull(saved.getLongitude());
        assertNotNull(saved.getUsers());
        assertEquals(1L, saved.getUsers().getId());
        assertEquals("matheus", saved.getUsers().getName());
        assertEquals("teste@teste.com1", saved.getUsers().getEmail());
        assertEquals(-47.9287, saved.getLatitude().doubleValue(), 1e-6);
        assertEquals(-15.7801, saved.getLongitude().doubleValue(), 1e-6);
    }
}
