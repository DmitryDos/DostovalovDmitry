package zoo.animals;

public interface Overland { //Сухопутные
    String moveType = "run";

    default void moveAction() {
        System.out.println("This animal can " + moveType);
    }
}
