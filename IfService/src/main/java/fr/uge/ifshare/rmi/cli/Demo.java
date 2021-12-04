package fr.uge.ifshare.rmi.cli;

import fr.uge.ifshare.rmi.common.IOnlineShop;
import fr.uge.ifshare.rmi.common.product.Product;
import fr.uge.ifshare.rmi.common.product.State;
import fr.uge.ifshare.rmi.common.user.IUserDatabase;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Demo {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        IOnlineShop shop = (IOnlineShop) Naming.lookup("onlineshop");
        IUserDatabase users = (IUserDatabase) Naming.lookup("userdata");

        Set<String> productSet = new HashSet<>(Arrays.asList("chair", "table", "plate", "spoon", "car"));
        for (String name : productSet) {
            try {
                shop.createAdvertising(
                  users.getRandomUser(),
                  new Product(name, State.randomState()), new Random().nextInt(20),
                  20,
                  "An excellent " + name + "."
                );
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        shop.getAdvertisings().forEach(System.out::println);
    }
}
