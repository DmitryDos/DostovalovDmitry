package zoo.animals;

public class Dolphin extends Animal implements Predators, Waterfowl {
    public String foodHabits = "fish";
    public String category = "dolphin";

    public Dolphin(String name) {
        super(name);
    }
    public void eatAction(){
        System.out.println(category + " called " + name + " can eat: " + foodHabits);
    }
    public void moveAction(){
        System.out.println(category + " called " + name + " can " + moveType);
    }

}
