package fr.uge.ifshare.rmi.cli;

import fr.uge.ifshare.rmi.common.IOnlineShop;
import fr.uge.ifshare.rmi.common.User;
import fr.uge.ifshare.rmi.common.controller.Choice;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Objects;

import static fr.uge.ifshare.rmi.common.controller.ConsoleController.displayMenu;
import static fr.uge.ifshare.rmi.common.controller.ConsoleController.inputString;

public class IfServiceClientRMI {
    private final IOnlineShop shopPlatform;
    private User sessionUser;

    private IfServiceClientRMI(IOnlineShop shop) {
        this.shopPlatform = shop;
    }

    private void loginAs(User user) {
        sessionUser = Objects.requireNonNull(user);
        displayShopMenu();
    }

    private void disconnectUser() {
        System.out.println("(Successfully disconnected from " + sessionUser.getPseudo() + ")");
        sessionUser = null;
        displayMainMenu();
    }

    private void exitSession() {
        System.exit(0);
    }

    private void tryLogin() {
        final User[] user = new User[1];

        inputString("Enter your IfService pseudo:",
          (p) -> {
              try {
                  user[0] = shopPlatform.getUserById(p);
              } catch (RemoteException e) {
                  e.printStackTrace();
              }
              return user[0] != null;
          },
          "No user with this pseudo was found. Please retry."
        );

        inputString("Enter your password:",
          (p) -> user[0].getPassword().equals(p),
          "Wrong password for '" + user[0].getPseudo() + "'"
        );
        loginAs(user[0]);
    }

    private void registerAndLogin() {
        String firstName = inputString("Enter your first name:");
        String lastName = inputString("Now enter your last name:");
        String password = inputString("Choose a password:");
        try {
            loginAs(shopPlatform.registerUser(firstName, lastName, password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void displayMainMenu() {
        displayMenu(
          "Welcome to the IfShare Shop !\n" +
            "Please login or register to access our services.",
          new Choice("Login", this::tryLogin),
          new Choice("Create account", this::registerAndLogin),
          new Choice("Exit", this::exitSession)
        );
    }

    private void displayShopMenu() {
        Objects.requireNonNull(sessionUser);
        displayMenu(
          "Welcome back, " + sessionUser.getShortenFullName() + " !\n" +
            "What can we do for you ?",
          new Choice("Do some shopping"),
          new Choice("Sell a product"),
          new Choice("Disconnect from account", this::disconnectUser),
          new Choice("Exit", this::exitSession)
        );
    }

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        IfServiceClientRMI client = new IfServiceClientRMI((IOnlineShop) Naming.lookup("onlineshop"));
        client.displayMainMenu();
    }
}
