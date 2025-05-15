package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.pojos.User;
import at.htlkaindorf.clashtoolsbackend.repositories.UserRepository;
import at.htlkaindorf.clashtoolsbackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Create a test user
        testUser = User.builder()
                .id(1L)
                .username("testuser")
                .password("password")
                .mail("test@example.com")
                .build();
    }

    @Test
    void testFindUserByUsername_WhenUserExists() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        // Act
        Optional<User> result = userService.findUserByUsername("testuser");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testFindUserByUsername_WhenUserDoesNotExist() {
        // Arrange
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.findUserByUsername("nonexistent");

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByUsername("nonexistent");
    }

    @Test
    void testFindUserByMail_WhenUserExists() {
        // Arrange
        when(userRepository.findByMail("test@example.com")).thenReturn(Optional.of(testUser));

        // Act
        Optional<User> result = userService.findUserByMail("test@example.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getMail());
        verify(userRepository, times(1)).findByMail("test@example.com");
    }

    @Test
    void testFindUserByMail_WhenUserDoesNotExist() {
        // Arrange
        when(userRepository.findByMail("nonexistent@example.com")).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.findUserByMail("nonexistent@example.com");

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByMail("nonexistent@example.com");
    }

    @Test
    void testSaveUser() {
        // Arrange
        User userToSave = User.builder()
                .username("newuser")
                .password("password")
                .mail("new@example.com")
                .build();

        User savedUser = User.builder()
                .id(2L)
                .username("newuser")
                .password("password")
                .mail("new@example.com")
                .build();

        when(userRepository.save(userToSave)).thenReturn(savedUser);

        // Act
        User result = userService.saveUser(userToSave);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("newuser", result.getUsername());
        verify(userRepository, times(1)).save(userToSave);
    }
}
