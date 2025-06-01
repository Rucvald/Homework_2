package by.rucvald;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static java.util.Objects.nonNull;

public class UserService {
    private final UserDao userDao;
    private final Scanner scanner;

    public UserService(UserDao userDao, Scanner scanner) {
        this.userDao = userDao;
        this.scanner = scanner;
    }

    public void createUser () {
        System.out.print("Введите имя пользователя: ");
        String userName = scanner.nextLine();
        System.out.print("Введите Фамилию: ");
        String lastName = scanner.nextLine();
        System.out.print("Введите Имя: ");
        String firstName = scanner.nextLine();
        System.out.print("Введите дату рождения: ");
        LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine());
        int age = LocalDate.now().getYear() - dateOfBirth.getYear();
        User newUser  = new User(userName, lastName, firstName, dateOfBirth, age);
        userDao.create(newUser );
        System.out.println("Пользователь создан.");
    }

    public void getUserById() {
        System.out.print("Введите ID пользователя: ");
        long id = scanner.nextLong();
        User user = userDao.findById(id);
        if (nonNull(user)) {
            System.out.println("Пользователь: " + user.getLastName() + " " + user.getFirstName());
        } else {
            System.out.println("Пользователь не найден.");
        }
    }

    public void getAllUser () {
        List<User> users = userDao.findAll();
        for (User  u : users) {
            System.out.println(u.getId() + ": " + u.getLastName() + " " + u.getFirstName());
        }
    }

    public void updateUser () {
        System.out.print("Введите ID пользователя для обновления: ");
        long updateId = scanner.nextLong();
        User userToUpdate = userDao.findById(updateId);
        if (nonNull(userToUpdate)) {
            System.out.print("Введите новую Фамилию: ");
            String newName = scanner.next();
            userToUpdate.setLastName(newName);
            userDao.update(userToUpdate);
            System.out.println("Пользователь обновлен.");
        } else {
            System.out.println("Пользователь не найден.");
        }
    }

    public void deleteUser () {
        System.out.print("Введите ID пользователя для удаления: ");
        long deleteId = scanner.nextLong();
        userDao.delete(deleteId);
        System.out.println("Пользователь удален.");
    }
}
