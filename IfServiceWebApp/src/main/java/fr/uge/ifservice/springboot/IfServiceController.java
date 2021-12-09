package fr.uge.ifservice.springboot;

import fr.uge.ifshare.rmi.common.IOnlineShop;
import fr.uge.ifshare.rmi.common.product.Product;
import fr.uge.ifshare.rmi.common.product.State;
import fr.uge.ifshare.rmi.common.user.IUser;
import fr.uge.ifshare.rmi.common.user.IUserDatabase;
import fr.uge.ifshare.rmi.common.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

import static fr.uge.ifservice.springboot.Application.shop;
import static fr.uge.ifservice.springboot.Application.users;

@Controller
@SessionAttributes({"states", "shop", "users", "adv", "user", "regCredentials", "loginCredentials", "pageErrorMessage"})
public class IfServiceController {
    @ModelAttribute("states")
    public List<State> populateAccommodationTypes() {
        return Arrays.asList(State.values());
    }

    @ModelAttribute("shop")
    public IOnlineShop shop() {
        return shop;
    }

    @ModelAttribute("users")
    public IUserDatabase users() {
        return users;
    }

    @ModelAttribute("adv")
    public AdTemplate adv() {
        return new AdTemplate();
    }

    @ModelAttribute("user")
    public User user() {
        return new User();
    }

    @ModelAttribute("loginCredentials")
    public LoginCredentials loginCredentials() {
        return new LoginCredentials();
    }

    @ModelAttribute("regCredentials")
    public RegisterCredentials regCredentials() {
        return new RegisterCredentials();
    }

    @ModelAttribute("pageErrorMessage")
    public String pageErrorMessage() {
        return "";
    }

    @GetMapping("/")
    public String redirectRoot() {
        return "redirect:/ifservice/home";
    }

    @GetMapping("{path:[a-z]+}")
    public String redirectPath(@PathVariable("path") String path) {
        return "ifservice".equals(path) ? redirectRoot() : "redirect:/ifservice/" + path;
    }

    @GetMapping("/ifservice/login")
    public String login(Model model, HttpSession session) {
        model.addAttribute("loginCredentials", new LoginCredentials());
        model.addAttribute("pageErrorMessage", "");
        session.setAttribute("SESSION_USER", null);
        return "ifservice/login";
    }

    @PostMapping("/ifservice/login")
    public String processLoginForm(@Valid LoginCredentials credentials,
                                   BindingResult bindingResult,
                                   Model model,
                                   HttpServletRequest request) throws RemoteException {
        if (bindingResult.hasErrors()) {
            return "ifservice/login";
        }

        IUser user = users.getUserById(credentials.getPseudo());
        if (user == null) {
            model.addAttribute("pageErrorMessage",
              "No user with pseudo '" + credentials.getPseudo() + "' was found. Please " + "retry.");
            return "ifservice/login";
        }
        if (!user.getPassword().equals(credentials.getPassword())) {
            model.addAttribute("pageErrorMessage",
              "Wrong password for '" + credentials.getPseudo() + "'.");
            return "ifservice/login";
        }

        request.getSession().setAttribute("SESSION_USER", user);
        model.addAttribute("user", request.getSession().getAttribute("SESSION_USER"));
        return "ifservice/home";
    }

    @GetMapping("/ifservice/register")
    public String register(Model model, HttpSession session) {
        model.addAttribute("regCredentials", new RegisterCredentials());
        model.addAttribute("pageErrorMessage", "");
        session.setAttribute("SESSION_USER", null);
        return "ifservice/register";
    }

    @PostMapping("/ifservice/register")
    public String processRegisterForm(@Valid RegisterCredentials credentials,
                                      BindingResult bindingResult,
                                      Model model,
                                      HttpServletRequest request) throws RemoteException {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(System.out::println);
            return "ifservice/register";
        }

        request.getSession().setAttribute("SESSION_USER",
          users.registerUser(credentials.getFirstName(), credentials.getLastName(), credentials.getPassword())
        );
        model.addAttribute("user", request.getSession().getAttribute("SESSION_USER"));
        return "ifservice/home";
    }

    @GetMapping("/ifservice/home")
    public String shopHome(Model model, HttpSession session) {

        if (session.getAttribute("SESSION_USER") == null) {
            return "redirect:/ifservice/login";
        }
        model.addAttribute("user", session.getAttribute("SESSION_USER"));
        return "ifservice/home";
    }

    @GetMapping("/ifservice/sell")
    public String sell(Model model, HttpSession session) {
        if (session.getAttribute("SESSION_USER") == null) {
            return "redirect:/ifservice/login";
        }
        model.addAttribute("user", session.getAttribute("SESSION_USER"));
        return "ifservice/sell";
    }

    @PostMapping("/ifservice/sell")
    public String processSellForm(@Valid AdTemplate ad,
                                  BindingResult bindingResult,
                                  Model model) throws RemoteException {
        if (bindingResult.hasErrors()) {
            return "ifservice/sell";
        }

        IOnlineShop shop = (IOnlineShop) model.getAttribute("shop");
        IUser user = (IUser) model.getAttribute("user");

        if (shop == null) return redirectRoot();

        shop.createAdvertising(
          user, new Product(ad.getName(), State.NEW), ad.getQuantity(), ad.getPrice(), ad.getDesc()
        );
        return "ifservice/home";
    }

    @GetMapping("/ifservice/disconnect")
    @SuppressWarnings("unused")
    public String disconnect(Model model, HttpSession session) {
        session.setAttribute("SESSION_USER", null);
        return "redirect:/ifservice/login";
    }
}

