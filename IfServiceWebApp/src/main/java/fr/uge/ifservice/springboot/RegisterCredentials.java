package fr.uge.ifservice.springboot;

import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RegisterCredentials {
    @NonNull
    @NotBlank(message = "Missing field")
    private String password = "";
    @NonNull
    @NotBlank(message = "Missing field")
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    private String firstName = "";
    @NonNull
    @NotBlank(message = "Missing field")
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    private String lastName = "";

    public RegisterCredentials() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
