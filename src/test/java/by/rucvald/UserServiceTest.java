package by.rucvald;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserDao userDao;
    private Scanner scanner;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userDao = mock(UserDao.class);
        scanner = mock(Scanner.class);
        userService = new UserService(userDao, scanner);
    }

    @Test
    public void testCreateUser () {
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        when(scanner.nextLine()).thenReturn("username", "lastname", "firstname", birthDate.toString());

        userService.createUser ();

        int expectedAge = LocalDate.now().getYear() - birthDate.getYear();
        User expectedUser  = new User("username", "lastname", "firstname", birthDate, expectedAge);

        verify(userDao).create(expectedUser );
    }


    @Test
    public void testGetUserByIdUserFound() {
        when(scanner.nextLong()).thenReturn(1L);
        User user = new User("username", "lastname", "firstname", LocalDate.now(), 0);
        when(userDao.findById(1L)).thenReturn(user);

        userService.getUserById();

        verify(userDao).findById(1L);
    }

    @Test
    public void testGetUserByIdUserNotFound() {
        when(scanner.nextLong()).thenReturn(2L);
        when(userDao.findById(2L)).thenReturn(null);

        userService.getUserById();

        verify(userDao).findById(2L);
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User("username1", "lastname1", "firstname1", LocalDate.now(), 0);
        User user2 = new User("username2", "lastname2", "firstname2", LocalDate.now(), 0);
        when(userDao.findAll()).thenReturn(Arrays.asList(user1, user2));

        userService.getAllUser ();

        verify(userDao).findAll();
    }

    @Test
    public void testUpdateUserUserFound() {
        when(scanner.nextLong()).thenReturn(1L);
        when(userDao.findById(1L)).thenReturn(new User("username", "lastname", "firstname", LocalDate.now(), 0));
        when(scanner.next()).thenReturn("newLastname");

        userService.updateUser ();

        verify(userDao).update(any(User.class));
    }

    @Test
    public void testUpdateUserUserNotFound() {
        when(scanner.nextLong()).thenReturn(2L);
        when(userDao.findById(2L)).thenReturn(null);

        userService.updateUser ();

        verify(userDao).findById(2L);
    }

    @Test
    public void testDeleteUser () {
        when(scanner.nextLong()).thenReturn(1L);

        userService.deleteUser ();

        verify(userDao).delete(1L);
    }
}
