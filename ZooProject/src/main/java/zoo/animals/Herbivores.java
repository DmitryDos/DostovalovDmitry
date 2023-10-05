package zoo.animals;

public interface Herbivores{ // Травоядные
    String foodHabits = "grass";
    default void eatAction(){
        System.out.println("This animal can eat: " + foodHabits);
    }
}
