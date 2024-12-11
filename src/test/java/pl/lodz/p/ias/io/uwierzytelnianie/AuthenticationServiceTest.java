//package pl.lodz.p.ias.io.uwierzytelnianie;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
//import pl.lodz.p.ias.io.uwierzytelnianie.repositories.UserRepository;
//import pl.lodz.p.ias.io.uwierzytelnianie.services.AuthenticationService;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class AuthenticationServiceTest {
//
//    @Autowired
//    private AuthenticationService authenticationService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @BeforeEach
//    public void setUp() {
//        // Upewnij się, że baza danych jest pusta przed każdym testem
//        userRepository.deleteAll();
//    }
//
//    @Test
//    public void testRegisterUser_Success() {
//        Account newUser = authenticationService.register("username", "password", "John", "Doe", "ROLE_WOLONTARIUSZ");
//
//        // Sprawdzenie, czy użytkownik został zapisany w bazie
//        assertNotNull(newUser);
//        assertEquals("username", newUser.getUsername());
//        assertEquals("John", newUser.getFirstName());
//        assertEquals("Doe", newUser.getLastName());
//        assertEquals("ROLE_WOLONTARIUSZ", newUser.getRole().getRoleName());
//
//        // Sprawdzenie, czy hasło zostało zahashowane
//        assertNotEquals("password", newUser.getPasswordHash());
//    }
//
//    @Test
//    public void testRegisterUser_UsernameAlreadyExists() {
//        // Rejestracja pierwszego użytkownika
//        authenticationService.register("username", "password", "John", "Doe", "ROLE_WOLONTARIUSZ");
//
//        // Próba rejestracji z tym samym użytkownikiem
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
//                authenticationService.register("username", "password", "Jane", "Doe", "ROLE_WOLONTARIUSZ")
//        );
//        assertEquals("Username already exists!", exception.getMessage());
//    }
//
//    @Test
//    public void testRegisterUser_InvalidRole() {
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
//                authenticationService.register("username", "password", "John", "Doe", "INVALID_ROLE")
//        );
//        assertEquals("Invalid role name!", exception.getMessage());
//    }
//}
