package fr.uge.ifshare.rmi.cli;

import fr.uge.ifshare.rmi.common.Advertising;
import fr.uge.ifshare.rmi.common.IOnlineShop;
import fr.uge.ifshare.rmi.common.controller.Choice;
import fr.uge.ifshare.rmi.common.controller.ConsoleController;
import fr.uge.ifshare.rmi.common.user.IUser;
import fr.uge.ifshare.rmi.common.user.IUserDatabase;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Objects;

public class IfShareClientRMI {
    private final ConsoleController controller;
    private final IOnlineShop shopPlatform;
    private final IUserDatabase users;
    private IUser sessionUser;

    private IfShareClientRMI(IOnlineShop shop, IUserDatabase users, ConsoleController controller) {
        this.controller = Objects.requireNonNull(controller);
        this.shopPlatform = Objects.requireNonNull(shop);
        this.users = Objects.requireNonNull(users);
    }

    // actions
    private void loginAs(IUser user) {
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

    // menus
    private void tryLogin() {
        final IUser[] user = new IUser[1];

        controller.inputString("Enter your IfService pseudo:",
          (p) -> {
              try {
                  user[0] = users.getUserById(p);
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
            loginAs(users.registerUser(firstName, lastName, password));
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
          new Choice("Do some shopping", this::doShopping),
          new Choice("Sell a product"),
          new Choice("My purchases"),
          new Choice("Disconnect from account", this::disconnectUser),
          new Choice("Exit", this::exitSession)
        );
    }

    private void doShopping() {
        Advertising ad = null;
        int qty = 1;

        try {
            ad = controller.displayMenu("Which of these products are you looking for ?   -   Your balance: ",
              // TODO: add balance bank
              shopPlatform.getAdvertisings());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Objects.requireNonNull(ad);
        if (ad.getQuantity() > 1) {
            qty = controller.inputInt(
              "How many of these are you planning to buy ?",
              (n) -> 0 <= n, "Can't buy negative amount."
            );
        }

        try {
            if (!shopPlatform.buyProduct(sessionUser, ad, qty)) {
            	/* Demander au client si il veut etre averti ou non
            	* Si oui, on crée un nouvel observer pour le client
            	* Puis, on appelle (get) la liste des observers du client (UserObservers), et on ajoute avec la methode addNew...
            	* Puis, on appelle la méthode d'enregistrement de l'observer du client : ad.register(<nouvel observer>);
            	* Si non, bah rien
            	*/
                System.out.println("There are not enough of this product for the moment; You will be notified when " +
                                     "it becomes available again. \n (" + ad.getObservers().size() + " users waiting)");
            }
            else if (qty > 1) {
                System.out.println("Successfully bought (" + qty + ") " + ad.getProduct().getName() + " from " + ad.getSellerPseudo());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        ConsoleController controller = Arrays.asList(args).contains("-c") ?
                                         ConsoleController.colored() :
                                         ConsoleController.standard();

        IOnlineShop shop = (IOnlineShop) Naming.lookup("onlineshop");
        IUserDatabase users = (IUserDatabase) Naming.lookup("userdata");

        IfShareClientRMI client = new IfShareClientRMI(shop, users, controller);

        client.sessionUser = users.getRandomUser();

        client.doShopping();
        client.displayMainMenu();
    }
}
