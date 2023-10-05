package zoo.animals;

public interface Waterfowl { //Водоплавающие
    String moveType = "swim";

    default void moveAction() {
        System.out.println("This animal can " + moveType);
    }
}
