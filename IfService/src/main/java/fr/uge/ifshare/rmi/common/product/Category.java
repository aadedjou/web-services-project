package fr.uge.ifshare.rmi.common.product;

public enum Category {
    VEHICLES("cars", "motorcycles", "equipment"),
    HOBBIES("sports", "art", "music", "toys"),
    MULTIMEDIA("movies", "music", "pictures"),
    FURNITURE("decoration", "electronic", "garden"),
    FOOD("fresh", "stocks", "wine"),
    ;

    private final String[] sub;

    Category(String... subcategories) {
        this.sub = subcategories;
    }
}
