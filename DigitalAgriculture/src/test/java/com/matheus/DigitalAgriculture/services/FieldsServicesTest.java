package com.matheus.DigitalAgriculture.services;

import com.matheus.DigitalAgriculture.dto.mapper.FieldsMapper;
import com.matheus.DigitalAgriculture.dto.request.FieldsRequestDTO;
import com.matheus.DigitalAgriculture.dto.response.FieldDetailsResponseDTO;
import com.matheus.DigitalAgriculture.dto.response.FieldsResponseDTO;
import com.matheus.DigitalAgriculture.dto.response.PaginationResponseDTO;
import com.matheus.DigitalAgriculture.exception.RegisterNotFound;
import com.matheus.DigitalAgriculture.model.Fields;
import com.matheus.DigitalAgriculture.model.Users;
import com.matheus.DigitalAgriculture.repository.FieldsRepository;
import com.matheus.DigitalAgriculture.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FieldsServicesTest {

    @Mock FieldsRepository fieldsRepository;
    @Mock UsersRepository usersRepository;
    @Mock FieldsMapper fieldsMapper;

    @InjectMocks FieldsServices service;

    private Users persistentUser(Long id) {
        Users exampleUser = new Users();
        exampleUser.setId(id);
        exampleUser.setName("User");
        exampleUser.setEmail("user@example.com");
        return exampleUser;
    }

    private Fields persistenFields(Long id, Users owner) {
        Fields exampleField = new Fields();
        try {
            var m = Fields.class.getMethod("setId", Long.class);
            m.invoke(exampleField, id);
        } catch (Exception ignore) {}
        return exampleField;
    }

    private FieldsRequestDTO aFieldsRequestDto() {
        return new FieldsRequestDTO(
                null,
                "Field A",
                "Soy",
                100,
                new BigDecimal("12.345678"),
                new BigDecimal("98.765432")
        );
    }

    @Test
    void findFieldsByUserId_shouldReturnPaginatedDtos_whenRepositoryReturnsPage() {
        long userId = 7L;
        int page = 1;
        int size = 2;

        Users owner = persistentUser(userId);
        Fields field1 = persistenFields(1L, owner);
        Fields field2 = persistenFields(2L, owner);
        Page<Fields> pageResult = new PageImpl<>(List.of(field1, field2), PageRequest.of(page, size), 10);

        when(fieldsRepository.findByUsersId(eq(userId), eq(PageRequest.of(page, size))))
                .thenReturn(pageResult);

        FieldsResponseDTO dto1 = mock(FieldsResponseDTO.class, Answers.RETURNS_DEFAULTS);
        FieldsResponseDTO dto2 = mock(FieldsResponseDTO.class, Answers.RETURNS_DEFAULTS);
        when(fieldsMapper.toDto(field1)).thenReturn(dto1);
        when(fieldsMapper.toDto(field2)).thenReturn(dto2);

        PaginationResponseDTO<FieldsResponseDTO> resp =
                service.findFieldsByUserId(page, size, userId);

        assertNotNull(resp);
        assertEquals(10, resp.totalElements());
        assertEquals(pageResult.getTotalPages(), resp.totalPages());
        assertEquals(2, resp.elements().size());
        assertSame(dto1, resp.elements().get(0));
        assertSame(dto2, resp.elements().get(1));


        verify(fieldsRepository).findByUsersId(userId, PageRequest.of(page, size));
        verify(fieldsMapper).toDto(field1);
        verify(fieldsMapper).toDto(field2);
    }

    @Test
    void findFieldsByUserId_shouldHandleEmptyPage() {
        long userId = 42L;
        int page = 0;
        int size = 10;

        Page<Fields> empty = new PageImpl<>(List.of(), PageRequest.of(page, size), 0);
        when(fieldsRepository.findByUsersId(eq(userId), eq(PageRequest.of(page, size))))
                .thenReturn(empty);

        PaginationResponseDTO<FieldsResponseDTO> resp =
                service.findFieldsByUserId(page, size, userId);

        assertNotNull(resp);
        assertEquals(0, resp.totalElements());
        assertEquals(0, resp.totalPages());
        assertTrue(resp.elements().isEmpty());

        verify(fieldsRepository).findByUsersId(userId, PageRequest.of(page, size));
        verifyNoInteractions(fieldsMapper);
    }

    @Test
    void insertFields_shouldSaveEntity_whenUserExists() {
        long userId = 77L;
        Users user = persistentUser(userId);

        FieldsRequestDTO dto = aFieldsRequestDto();
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

        Fields entity = new Fields();
        when(fieldsMapper.toEntity(dto, user)).thenReturn(entity);
        when(fieldsRepository.save(entity)).thenReturn(entity);

        service.insertFields(dto, userId);

        verify(usersRepository).findById(userId);
        verify(fieldsMapper).toEntity(dto, user);
        verify(fieldsRepository).save(entity);
    }

    @Test
    void insertFields_shouldThrowRegisterNotFound_whenUserDoesNotExist() {
        long userId = 999L;
        FieldsRequestDTO dto = aFieldsRequestDto();
        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        RegisterNotFound ex = assertThrows(RegisterNotFound.class, () -> service.insertFields(dto, userId));
        assertEquals("Usuário não encontrado", ex.getMessage());

        verify(usersRepository).findById(userId);
        verify(fieldsMapper, never()).toEntity(any(), any());
        verify(fieldsRepository, never()).save(any());
    }

    @Test
    void findFindWithDetailsById_shouldReturnDetailsDto_whenFieldExists() {
        long fieldId = 5L;
        Users owner = persistentUser(1L);
        Fields field = persistenFields(fieldId, owner);

        when(fieldsRepository.findFindWithDetailsById(fieldId)).thenReturn(Optional.of(field));

        FieldDetailsResponseDTO detailsDto = mock(FieldDetailsResponseDTO.class, Answers.RETURNS_DEFAULTS);
        when(fieldsMapper.detailsToDto(field)).thenReturn(detailsDto);

        FieldDetailsResponseDTO result = service.findFindWithDetailsById(fieldId);

        assertNotNull(result);
        assertSame(detailsDto, result);
        verify(fieldsRepository).findFindWithDetailsById(fieldId);
        verify(fieldsMapper).detailsToDto(field);
    }

    @Test
    void findFindWithDetailsById_shouldThrowRegisterNotFound_whenFieldDoesNotExist() {
        long fieldId = 12345L;
        when(fieldsRepository.findFindWithDetailsById(fieldId)).thenReturn(Optional.empty());

        RegisterNotFound ex = assertThrows(RegisterNotFound.class,
                () -> service.findFindWithDetailsById(fieldId));
        assertEquals("Talhão não encontrado!", ex.getMessage());

        verify(fieldsRepository).findFindWithDetailsById(fieldId);
        verifyNoInteractions(fieldsMapper);
    }
}
