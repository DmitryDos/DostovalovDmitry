package zoo.animals;

public class Horse extends Animal implements Herbivores, Overland {
    public String category = "horse";

    public Horse(String name) {
        super(name);
    }

    public void eatAction() {
        System.out.println(category + " called " + name + " can eat: " + foodHabits);
    }

    public void moveAction() {
        System.out.println(category + " called " + name + " can " + moveType);
    }

}
