package zoo.animals;

public class Main {
    public static void main(String[] args) {
        Camel camelArt = new Camel("Art");
        camelArt.moveAction();
        camelArt.eatAction();

        Tiger tigerLeo = new Tiger("Leo");
        tigerLeo.moveAction();
        tigerLeo.eatAction();

        Dolphin dolphinAnton = new Dolphin("Anton");
        dolphinAnton.moveAction();
        dolphinAnton.eatAction();

        Eagle eagleYar = new Eagle("Yar");
        eagleYar.moveAction();
        eagleYar.eatAction();

        Horse horseDan = new Horse("Dan");
        horseDan.moveAction();
        horseDan.eatAction();
    }
}
