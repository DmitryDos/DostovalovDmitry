package zoo.animals;

public class Tiger extends Animal implements Predators, Overland {
    public String foodHabits = "beef";
    public String category = "tiger";

    public Tiger(String name) {
        super(name);
    }

    public void eatAction() {
        System.out.println(category + " called " + name + " can eat: " + foodHabits);
    }

    public void moveAction() {
        System.out.println(category + " called " + name + " can " + moveType);
    }
}
