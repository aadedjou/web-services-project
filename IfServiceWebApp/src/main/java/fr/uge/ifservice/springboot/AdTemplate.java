package fr.uge.ifservice.springboot;

import fr.uge.ifshare.rmi.common.product.State;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class AdTemplate {
    @NonNull
    @NotBlank(message = "Missing field")
    private String name = "";
    private String desc = "";
    @Positive(message = "Field must be positive")
    private int quantity;
    @Positive(message = "Field must be positive")
    private double price;

    public AdTemplate() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
