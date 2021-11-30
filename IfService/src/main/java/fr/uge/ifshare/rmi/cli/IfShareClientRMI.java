package fr.uge.ifshare.rmi.cli;

import fr.uge.ifshare.rmi.common.IAdvertising;
import fr.uge.ifshare.rmi.common.IOnlineShop;
import fr.uge.ifshare.rmi.common.controller.Choice;
import fr.uge.ifshare.rmi.common.controller.ConsoleController;
import fr.uge.ifshare.rmi.common.product.Product;
import fr.uge.ifshare.rmi.common.product.State;
import fr.uge.ifshare.rmi.common.user.IUser;
import fr.uge.ifshare.rmi.common.user.IUserDatabase;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class IfShareClientRMI {
    private final ConsoleController controller;
    private final IUserDatabase users;
    private final IOnlineShop shop;
    private IUser sessionUser;

    private IfShareClientRMI(IOnlineShop shop, IUserDatabase users, ConsoleController controller) {
        this.controller = Objects.requireNonNull(controller);
        this.shop = Objects.requireNonNull(shop);
        this.users = Objects.requireNonNull(users);
    }

    // actions
    private void loginAs(IUser user) {
        sessionUser = Objects.requireNonNull(user);
        displayShopMenu();
    }

    private void disconnectUser() {
        controller.printMessage("(Successfully disconnected from " + sessionUser.getPseudo() + ")");
        sessionUser = null;
        displayLoginMenu();
    }

    private void exitSession() {
        System.exit(0);
    }

    private List<IAdvertising> notificationList() {
        List<IAdvertising> list = null;
        try {
            list = shop.getAdvertisings().stream()
              .filter(ad -> ad.firstOrderInWaitingList()
                .map(e -> e.getKey().equals(sessionUser))
                .orElse(false))
              .collect(Collectors.toList());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return list;
    }

    // menus
    private void displayLoginMenu() {
        controller.displayMenu(
          "Welcome to the IfShare Shop !\n" +
            "Please login or register to access our services.",
          new Choice("Login", this::tryLogin),
          new Choice("Create account", this::registerAndLogin),
          new Choice("Exit", this::exitSession)
        );
    }

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
        try {
            String firstName = controller.inputString("Enter your first name:");
            String lastName = controller.inputString("Now enter your last name:");
            String password = controller.inputString("Choose a password:");
            loginAs(users.registerUser(firstName, lastName, password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void displayShopMenu() {
        Objects.requireNonNull(sessionUser);
        controller.displayMenu(
          "Welcome back, " + sessionUser.getShortenFullName() + " !\n" +
            "What can we do for you ?",
          new Choice("Do some shopping", this::doShopping),
          new Choice("Sell a product", this::createAd),
          new Choice("Notifications" + (notificationList().isEmpty() ? "" : " (" + notificationList().size() + ")"),
            this::checkNotifications),
          new Choice("Rate my purchases", this::ratePurchases),
          new Choice("My sales", this::viewSales),
          new Choice("Disconnect from account", this::disconnectUser),
          new Choice("Exit", this::exitSession)
        );
    }

    private void doShopping() {
        try {
            // TODO: add balance bank
            IAdvertising ad = controller.displayMenu(
              "Which of these products are you looking for ?   -   Your balance: ", shop.getAdvertisings());
            int qty = 1;

            if (ad == null) return;
            if (ad.getQuantity() > 1) {
                qty = controller.inputPositiveInt("How many of these are you planning to buy ?");
            }

            if (!shop.buyProduct(sessionUser, ad, qty)) {
                controller.displayMenu(
                  "There are not enough of this product for the moment. \n" +
                    "Do you wish to be notified when '" + ad.getProduct().getName() + "' becomes available again ?",
                  new Choice("Yes", () -> controller.printMessage(
                    "You will receive a notification when the product will be back in the shop")),
                  new Choice("No thanks", ad::desistFirstUserFromWaitingList)
                );
            } else if (qty > 0) {
                controller.printMessage("Successfully bought (" + qty + ") " + ad.getProduct().getName() +
                                          (qty == 1 ? "" : "s") + " from " + ad.getSellerPseudo());
                sessionUser.addToHistory(ad);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        displayShopMenu();
    }

    private void createAd() {
        try {
            String name = controller.inputString("Enter a name for the product you wish to sell:");
            State state = controller.displayMenu("Currently, in which state is your product ?", State.values());
            String desc = controller.inputString("Enter a quick description for '" + name + "'.");
            int quantity = controller.inputPositiveInt("How many '" + name + "s' do you want to put up for sale ?");
            double price = controller.inputDouble(
              "Set a price (€) for your product. (1 unit)", p -> p > 0, "Value be positive."
            );
            shop.createAdvertising(sessionUser, new Product(name, state), quantity, price, desc);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        displayShopMenu();
    }

    private void checkNotifications() {
        IAdvertising ad = controller.displayMenu("Here are the products that you asked to be notified for.", notificationList());
        if (ad == null) return;

        int qty = ad.firstOrderInWaitingList().map(Map.Entry::getValue).orElse(0);
        controller.displayMenu("Do you still want this product ?",
          new Choice("Yes, I wanna buy " + qty + " " + ad.getProduct().getName() + (qty == 1 ? "" : "s") + " right now",
            () -> {
                try {
                    shop.buyProduct(sessionUser, ad, qty);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }),
          new Choice("No, I'm no longer interested.", ad::desistFirstUserFromWaitingList),
          new Choice("Do nothing")
        );
        displayShopMenu();
    }

    private void viewSales() {
        try {
            IAdvertising ad = controller.displayMenu(
              "Here are the products that you are currently selling. Pick an ad to update/delete it.",
              shop.getAdvertisings().stream()
                .filter(adv -> Objects.equals(adv.getSellerPseudo(), sessionUser.getPseudo()))
                .collect(Collectors.toList())
            );
            if (ad == null) return;

            final boolean[] updateChoice = {false};
            controller.displayMenu("What do you want to do with this ad ?",
              new Choice("Update quantity", () -> updateChoice[0] = true),
              new Choice("Delete this ad", () -> {
                  try {
                      shop.removeAd(ad);
                  } catch (RemoteException e) {
                      e.printStackTrace();
                  }
              }),
              new Choice("Do nothing")
            );

            if (updateChoice[0]) {
                int newQty = controller.inputPositiveInt(
                  "Provide the new quantity of '" + ad.getProduct().getName() + "s'"
                );
                ad.addQuantity(newQty);
                controller.printMessage(
                  "You are now selling " + newQty + " " + ad.getProduct().getName() +
                    (newQty == 1 ? "" : "s") + "."
                );
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        displayShopMenu();
    }

    private void ratePurchases() {
        try {
            IAdvertising ad = controller.displayMenu("Select one of your recent purchases to rate it:", sessionUser.getHistory());
            if (ad == null) return;
            double grade = controller.inputDouble("Assign a grade to " + ad.getSellerPseudo() + "'s product:",
              r -> 0 <= r && r <= 5, "Grade must be between 0 and 5.");
            shop.addRating(ad, sessionUser, grade);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        displayShopMenu();
    }

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        ConsoleController controller = Arrays.asList(args).contains("-c") ?
                                         ConsoleController.colored() :
                                         ConsoleController.standard();

        IOnlineShop shop = (IOnlineShop) Naming.lookup("onlineshop");
        IUserDatabase users = (IUserDatabase) Naming.lookup("userdata");
        IfShareClientRMI client = new IfShareClientRMI(shop, users, controller);

        client.displayLoginMenu();
    }
}
