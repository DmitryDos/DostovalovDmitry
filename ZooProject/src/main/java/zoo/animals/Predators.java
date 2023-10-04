package zoo.animals;

public interface Predators{ //Хищники
    String foodHabits = "meat";
    default void eatAction(){
        System.out.println("This animal can eat: " + foodHabits);
    }
}
