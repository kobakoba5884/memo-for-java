package junit.practice.whatis.reflection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Test;

import junit.practice.whatis.reflection.models.Animal;
import junit.practice.whatis.reflection.models.Goat;
import junit.practice.whatis.reflection.models.Person;

public class WhatIsReflectionTest {
    private Class<?> goatClass = null;
    private Class<?> animalClass = null;

    public WhatIsReflectionTest() throws ClassNotFoundException{
        this.goatClass = Class.forName("junit.practice.whatis.reflection.models.Goat");
        this.animalClass = Class.forName("junit.practice.whatis.reflection.models.Animal");
    }
    
    @BeforeClass
    public static void beforeClassTesting() throws ClassNotFoundException{
        
    }

    @Test
    public void givenObject_whenGetsFieldNamesAtRuntime_thenCorrect() {
        Person person = new Person();
        Field[] fields = person.getClass().getDeclaredFields();

        List<String> actualFieldNames = Arrays.stream(fields).map(field -> field.getName()).collect(Collectors.toList());

        assertTrue(Arrays.asList("name", "age").containsAll(actualFieldNames));
    }

    // get basic information of class
    @Test
    public void givenObject_whenGetsClassName_thenCorrect() throws ClassNotFoundException {
        Animal goat = new Goat("goat");

        Arrays.asList(goat.getClass(), goatClass).stream().forEach(clazz -> {
            assertEquals("Goat", clazz.getSimpleName());
            assertEquals("junit.practice.whatis.reflection.models.Goat", clazz.getName());
            assertEquals("junit.practice.whatis.reflection.models.Goat", clazz.getCanonicalName());
        });
    }

    // get the modifier used in a class
    @Test
    public void givenClass_whenRecognisesModifiers_thenCorrect() throws ClassNotFoundException {

        int goatMods = goatClass.getModifiers();
        int animalMods = animalClass.getModifiers();

        assertTrue(Modifier.isPublic(goatMods));
        assertTrue(Modifier.isAbstract(animalMods));
        assertTrue(Modifier.isPublic(animalMods));
    }

    // get the package name
    @Test
    public void givenClass_whenGetsPackageInfo_thenCorrect() throws ClassNotFoundException {
        Package pkg = goatClass.getPackage();

        assertEquals("junit.practice.whatis.reflection.models", pkg.getName());
    }

    // get the super class information
    @Test
    public void givenClass_whenGetsSuperClass_thenCorrect() throws ClassNotFoundException {
        String str = "any string";
        Class<?> goatSuperClass = goatClass.getSuperclass();

        assertEquals("Animal", goatSuperClass.getSimpleName());
        assertEquals("Object", str.getClass().getSuperclass().getSimpleName());
    }

    // get the implemened interfaces information
    @Test
    public void givenClass_whenGetsImplementedInterfaces_thenCorrect() throws ClassNotFoundException{

        Class<?>[] goatInterfaces = goatClass.getInterfaces();
        Class<?>[] animalInterfaces = animalClass.getInterfaces();

        assertEquals(1, goatInterfaces.length);
        assertEquals(1, animalInterfaces.length);
        assertEquals("Locomotion", goatInterfaces[0].getSimpleName());
        assertEquals("Eating", animalInterfaces[0].getSimpleName());
    }

    // get constructor info
    @Test
    public void givenClass_whenGetsConstructor_thenCorrect(){
        Constructor<?>[] constructors = goatClass.getConstructors();

        assertEquals(1, constructors.length);
        assertEquals("junit.practice.whatis.reflection.models.Goat", constructors[0].getName());
    }

    // get fields for animal class
    @Test
    public void givenClass_whenGetsFields_thenCorrect(){
        Field[] fields = animalClass.getDeclaredFields();

        List<String> actualFields = Arrays.stream(fields).map(field -> field.getName()).collect(Collectors.toList());

        assertEquals(2, actualFields.size());
        assertTrue(actualFields.containsAll(Arrays.asList("name", "CATEGORY")));
    }

    // get methods for animal class
    @Test
    public void givenClass_whenGetsMethods_thenCorrect(){
        Method[] methods = animalClass.getDeclaredMethods();
        List<String> actualMethods = Arrays.stream(methods).map(method -> method.getName()).collect(Collectors.toList());

        assertEquals(7, actualMethods.size());
        assertTrue(actualMethods.containsAll(Arrays.asList("getName", "setName", "getSound")));
    }
}
