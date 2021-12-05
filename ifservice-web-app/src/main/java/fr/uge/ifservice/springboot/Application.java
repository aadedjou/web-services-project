package fr.uge.ifservice.springboot;

import fr.uge.ifshare.rmi.common.IOnlineShop;
import fr.uge.ifshare.rmi.common.user.IUserDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

@SpringBootApplication
public class Application {
    public static IUserDatabase users;
    public static IOnlineShop shop;

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        SpringApplication.run(Application.class, args);
        shop = (IOnlineShop) Naming.lookup("onlineshop");
        users = (IUserDatabase) Naming.lookup("userdata");
    }
}