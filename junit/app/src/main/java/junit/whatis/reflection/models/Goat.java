package junit.whatis.reflection.models;


public class Goat extends Animal implements Locomotion {

    public Goat(String name){
        setName(name);
    }

    @Override
    public String eats() {
        return "grass";
    }

    @Override
    public String getLocomotion() {
        return "walks";
    }

    @Override
    protected String getSound() {
        return "bleat";
    }
    
}
