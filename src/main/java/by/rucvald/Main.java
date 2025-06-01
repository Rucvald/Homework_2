package by.rucvald;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService(new UserDao(), new Scanner(System.in));
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
                    userService.createUser();
                    break;
                case 2:
                    userService.getUserById();
                    break;
                case 3:
                    userService.getAllUser();
                    break;
                case 4:
                    userService.updateUser();
                    break;
                case 5:
                    userService.deleteUser();
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