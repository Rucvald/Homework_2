package by.rucvald;

import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class UserDaoTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:14.5")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("passwordd");

    private UserDao userDao;

    @BeforeAll
    public static void beforeAll() {
        HibernateUtil.setSessionFactory(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        );
    }

    @AfterAll
    public static void afterAll() {
        HibernateUtil.shutdown();
    }

    @BeforeEach
    public void setUp() {
        userDao = new UserDao();
    }

    @AfterEach
    public void tearDown() {
        userDao.deleteAll();
    }


    @Test
    public void testCreateUser () {
        User user = new User("username", "lastname", "firstname", LocalDate.of(2000, 8, 10), 33);
        userDao.create(user);

        User foundUser  = userDao.findById(user.getId());
        assertNotNull(foundUser );
        assertEquals(user.getUserName(), foundUser .getUserName());
    }

    @Test
    public void testFindById() {
        User user = new User("username", "lastname", "firstname", LocalDate.of(2000, 8, 10), 33);
        userDao.create(user);

        User foundUser  = userDao.findById(user.getId());
        assertNotNull(foundUser );
        assertEquals(user.getId(), foundUser .getId());
    }

    @Test
    public void testUpdateUser () {
        User user = new User("username", "lastname", "firstname", LocalDate.of(2000, 8, 10), 33);
        userDao.create(user);

        user.setLastName("newLastName");
        userDao.update(user);

        User updatedUser  = userDao.findById(user.getId());
        assertEquals("newLastName", updatedUser .getLastName());
    }

    @Test
    public void testDeleteUser () {
        User user = new User("username", "lastname", "firstname", LocalDate.of(2000, 8, 10), 33);
        userDao.create(user);
        userDao.delete(user.getId());

        User foundUser  = userDao.findById(user.getId());
        assertNull(foundUser );
    }

    @Test
    public void testFindAllUsers() {
        User user1 = new User("username1", "lastname1", "firstname1", LocalDate.of(2000, 8, 10), 33);
        User user2 = new User("username2", "lastname2", "firstname2", LocalDate.of(2000, 8, 10), 33);
        userDao.create(user1);
        userDao.create(user2);

        List<User> users = userDao.findAll();
        assertEquals(2, users.size());
    }
}
