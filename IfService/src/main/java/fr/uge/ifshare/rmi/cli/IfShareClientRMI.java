package fr.uge.ifshare.rmi.cli;

import fr.uge.ifshare.rmi.common.IOnlineShop;
import fr.uge.ifshare.rmi.common.User;
import fr.uge.ifshare.rmi.common.controller.Choice;
import fr.uge.ifshare.rmi.common.controller.ConsoleController;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Objects;

public class IfShareClientRMI {
    private final ConsoleController controller;
    private final IOnlineShop shopPlatform;
    private User sessionUser;

    private IfShareClientRMI(IOnlineShop shop, ConsoleController controller) {
        this.controller = controller;
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

        controller.inputString("Enter your IfService pseudo:",
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

        controller.inputString("Enter your password:",
          (p) -> user[0].getPassword().equals(p),
          "Wrong password for '" + user[0].getPseudo() + "'"
        );
        loginAs(user[0]);
    }

    private void registerAndLogin() {
        String firstName = controller.inputString("Enter your first name:");
        String lastName = controller.inputString("Now enter your last name:");
        String password = controller.inputString("Choose a password:");
        try {
            loginAs(shopPlatform.registerUser(firstName, lastName, password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void displayMainMenu() {
        controller.displayMenu(
          "Welcome to the IfShare Shop !\n" +
            "Please login or register to access our services.",
          new Choice("Login", this::tryLogin),
          new Choice("Create account", this::registerAndLogin),
          new Choice("Exit", this::exitSession)
        );
    }

    private void displayShopMenu() {
        Objects.requireNonNull(sessionUser);
        controller.displayMenu(
          "Welcome back, " + sessionUser.getShortenFullName() + " !\n" +
            "What can we do for you ?",
          new Choice("Do some shopping"),
          new Choice("Sell a product"),
          new Choice("Disconnect from account", this::disconnectUser),
          new Choice("Exit", this::exitSession)
        );
    }

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        ConsoleController controller = Arrays.asList(args).contains("-c") ?
                                         ConsoleController.colored() :
                                         ConsoleController.standard();
        IfShareClientRMI client = new IfShareClientRMI((IOnlineShop) Naming.lookup("onlineshop"), controller);
        client.displayMainMenu();
    }
}
