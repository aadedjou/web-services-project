package fr.uge.ifservice.springboot;

import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;

public class LoginCredentials {
    @NonNull
    @NotBlank(message = "Missing field")
    private String pseudo = "";
    @NonNull
    @NotBlank(message = "Missing field")
    private String password = "";

    public LoginCredentials() {
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

