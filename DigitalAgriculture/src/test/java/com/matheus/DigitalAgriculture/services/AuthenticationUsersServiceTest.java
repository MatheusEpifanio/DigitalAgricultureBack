package com.matheus.DigitalAgriculture.services;

import com.matheus.DigitalAgriculture.dto.mapper.UsersMapper;
import com.matheus.DigitalAgriculture.dto.request.LoginRequestDTO;
import com.matheus.DigitalAgriculture.dto.request.UsersRequestDTO;
import com.matheus.DigitalAgriculture.dto.response.LoginResponseDTO;
import com.matheus.DigitalAgriculture.exception.AlreadyRegisteredException;
import com.matheus.DigitalAgriculture.exception.RegisterNotFound;
import com.matheus.DigitalAgriculture.model.Users;
import com.matheus.DigitalAgriculture.repository.UsersRepository;
import com.matheus.DigitalAgriculture.security.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationUsersServiceTest {

    @Mock UsersRepository usersRepository;
    @Mock TokenService tokenService;
    @Mock UsersMapper usersMapper;
    @Mock AuthenticationManager authenticationManager;

    @InjectMocks AuthenticationUsersService service;

    private UsersRequestDTO newUserDto() {

        return new UsersRequestDTO("Maria", "maria@example.com", "Senha@123");
    }

    private Users persistentUser() {
        Users exempleUser = new Users();
        exempleUser.setId(1L);
        exempleUser.setName("Maria");
        exempleUser.setEmail("maria@example.com");
        exempleUser.setPassword("$2a$10$hashExemplo");
        return exempleUser;
    }

    @Test
    void register_mustRegisterSuccessfully_whenEmailDoesNotExist() {
        UsersRequestDTO dto = newUserDto();

        when(usersRepository.findByEmail(dto.email())).thenReturn(null);


        Users entity = new Users();
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        entity.setPassword(dto.password());
        when(usersMapper.toEntity(dto)).thenReturn(entity);

        ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
        when(usersRepository.save(any(Users.class))).thenAnswer(inv -> {
            Users toSave = inv.getArgument(0, Users.class);
            toSave.setId(99L);
            return toSave;
        });

        service.register(dto);

        verify(usersRepository).findByEmail(dto.email());
        verify(usersMapper).toEntity(dto);
        verify(usersRepository).save(captor.capture());

        Users savedUser = captor.getValue();
        assertNotNull(savedUser);
        assertEquals(dto.email(), savedUser.getEmail());
        assertNotNull(savedUser.getPassword());
        assertNotEquals(dto.password(), savedUser.getPassword(), "senha não pode ser armazenada em texto puro");

        assertTrue(new BCryptPasswordEncoder().matches(dto.password(), savedUser.getPassword()));
    }

    @Test
    void register_deveLancarAlreadyRegistered_whenEmailJaExists() {
        UsersRequestDTO dto = newUserDto();

        when(usersRepository.findByEmail(dto.email())).thenReturn(persistentUser());

        assertThrows(AlreadyRegisteredException.class, () -> service.register(dto));

        verify(usersRepository, never()).save(any());
        verify(usersMapper, never()).toEntity(any());
    }

    @Test
    void login_mustAuthenticateAndGenerateToken() {
        LoginRequestDTO dto = new LoginRequestDTO("maria@example.com", "Senha@123");

        Users mainUser = persistentUser();

        Authentication authRetornado = new UsernamePasswordAuthenticationToken(
                mainUser, null, /* authorities */ null);

       ArgumentCaptor<Authentication> authCaptor = ArgumentCaptor.forClass(Authentication.class);

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authRetornado);
        when(tokenService.generateToken(mainUser)).thenReturn("jwt-token-gerado");

        LoginResponseDTO responseLogin = service.login(dto);

        assertNotNull(responseLogin);
        assertEquals("jwt-token-gerado", responseLogin.token());

        verify(authenticationManager).authenticate(authCaptor.capture());
        Authentication sent = authCaptor.getValue();
        assertTrue(sent instanceof UsernamePasswordAuthenticationToken);
        assertEquals(dto.email(), sent.getPrincipal());
        assertEquals(dto.password(), sent.getCredentials());

        verify(tokenService).generateToken(mainUser);
    }

    @Test
    void login_whenAuthenticateThorwException_shouldConvertToRegisterNotFound() {
        LoginRequestDTO dto = new LoginRequestDTO("maria@example.com", "senha_errada");

        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new BadCredentialsException("invalid"));

        RegisterNotFound ex = assertThrows(RegisterNotFound.class, () -> service.login(dto));
        assertEquals("Login inválido!", ex.getMessage());

        verify(tokenService, never()).generateToken(any());
    }
}
