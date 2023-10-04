package zoo.animals;

public interface Flying { //Летающие
    String moveType = "fly";

    default void moveAction() {
        System.out.println("This animal can " + moveType);
    }
}
