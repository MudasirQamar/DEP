package java_Example;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Choose an operation:");
            System.out.println("1. Add User");
            System.out.println("2. View Users");
            System.out.println("3. Update User");
            System.out.println("4. Delete User");
            System.out.println("5. Exit");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.next();
                    System.out.print("Enter email: ");
                    String email = scanner.next();
                    System.out.print("Enter country: ");
                    String country = scanner.next();
                    userDAO.addUser(name, email, country);
                    break;
                case 2:
                    List<User> users = userDAO.getUsers();
                    for (User user : users) {
                        System.out.println("ID: " + user.getId() + ", Name: " + user.getName() +
                                ", Email: " + user.getEmail() + ", Country: " + user.getCountry());
                    }
                    break;
                case 3:
                    System.out.print("Enter user ID to update: ");
                    int idToUpdate = scanner.nextInt();
                    System.out.print("Enter new name: ");
                    String newName = scanner.next();
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.next();
                    System.out.print("Enter new country: ");
                    String newCountry = scanner.next();
                    userDAO.updateUser(idToUpdate, newName, newEmail, newCountry);
                    break;
                case 4:
                    System.out.print("Enter user ID to delete: ");
                    int idToDelete = scanner.nextInt();
                    userDAO.deleteUser(idToDelete);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 5);

        scanner.close();
    }
}
