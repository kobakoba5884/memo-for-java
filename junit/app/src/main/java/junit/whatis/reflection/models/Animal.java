package junit.whatis.reflection.models;

import lombok.Data;

@Data
public abstract class Animal implements Eating{
    public static String CATEGORY = "domestic";
    private String name;

    protected abstract String getSound();

    public Animal(){

    }
}
