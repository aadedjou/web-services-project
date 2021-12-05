package fr.uge.ifservice.springboot;

import javax.validation.constraints.Positive;

public class SpringTruc {
    @Positive
    private int width;
    @Positive
    private int height;

    public int area() {
        return width * height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}