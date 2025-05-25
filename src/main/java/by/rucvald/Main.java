package by.rucvald;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import static java.util.Objects.nonNull;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Создать пользователя");
            System.out.println("2. Получить пользователя по ID");
            System.out.println("3. Получить всех пользователей");
            System.out.println("4. Обновить пользователя");
            System.out.println("5. Удалить пользователя");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Введите имя пользователя: ");
                    String userName = scanner.nextLine();
                    System.out.print("Введите Фамилию: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Введите Имя: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Введите дату рождения: ");
                    LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine());
                    int age = LocalDate.now().getYear() - dateOfBirth.getYear();
                    User newUser = new User(userName, lastName, firstName, dateOfBirth, age);
                    userDao.create(newUser);
                    System.out.println("Пользователь создан.");
                    break;
                case 2:
                    System.out.print("Введите ID пользователя: ");
                    long id = scanner.nextLong();
                    User user = userDao.findById(id);
                    if (nonNull(user)) {
                        System.out.println("Пользователь: " + user.getLastName() + " " + user.getFirstName());
                    } else {
                        System.out.println("Пользователь не найден.");
                    }
                    break;
                case 3:
                    List<User> users = userDao.findAll();
                    for (User u : users) {
                        System.out.println(u.getId() + ": " + u.getLastName() + " " + u.getFirstName());
                    }
                    break;
                case 4:
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
                    break;
                case 5:
                    System.out.print("Введите ID пользователя для удаления: ");
                    long deleteId = scanner.nextLong();
                    userDao.delete(deleteId);
                    System.out.println("Пользователь удален.");
                    break;
                case 0:
                    System.out.println("Выход из программы.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}