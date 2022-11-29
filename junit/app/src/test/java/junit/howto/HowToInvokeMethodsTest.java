package junit.practice.howto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Ignore;
import org.junit.Test;

import junit.howto.HowToInvokeMethods;

public class HowToInvokeMethodsTest {
    private Class<HowToInvokeMethods> targetClass = HowToInvokeMethods.class;
    private HowToInvokeMethods targetInstance = new HowToInvokeMethods();
    
    // getMethod()
    // find any public method of the class or any of its superclasses.
    @Test
    public void howToUseGetMethod() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        

        // --------------------instance method--------------------
        Method sumInstanceMethod = targetClass.getMethod("publicSum", int.class, double.class);
    

        Double resultOfSumInstanceMethod = (Double) sumInstanceMethod.invoke(targetInstance, 1, 3);

        assertEquals((Double) 4.0, resultOfSumInstanceMethod);

        // --------------------static method--------------------
        Method multiplyStaticMethod = targetClass.getMethod("publicStaticMultiply", float.class, long.class);

        Double resultOfMultiplyStaticMethod = (Double) multiplyStaticMethod.invoke(null, 3.5f, 2);

        assertEquals((Double) 7.0, resultOfMultiplyStaticMethod);
    }

    // In this way, private and protected methods can only be java 9 or higher
    @Test(expected = IllegalAccessException.class)
    public void givenObject_whenInvokePrivateMethod_thenFail() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException{
        Method andPrivateMethod = targetClass.getDeclaredMethod("privateAnd", boolean.class, boolean.class);

        Boolean result = (Boolean) andPrivateMethod.invoke(targetInstance, true, false);

        assertFalse(result);
    }

    @Test(expected = IllegalAccessException.class)
    @Ignore
    public void givenObject_whenInvokeProtectedMethod_thenFail() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Method maxProtectedMethod = targetClass.getDeclaredMethod("protectedMax", int.class, int.class);

        Integer result = (Integer) maxProtectedMethod.invoke(targetInstance, 2, 4);
        
        assertEquals((Integer) 4, result);
    }

    // ------------------how to assert Exception for junit version 4.12------------------
    @Test(expected = IllegalArgumentException.class)
    public void givenNull_WhenValidateAndDouble_ThenThrows() {
        // junit version 4.13
        // assertThrows(IllegalArgumentException.class, () -> HowToInvokeMethods.validateAndDouble(null));
        // junit version 4.12 -> https://stackoverflow.com/questions/156503/how-do-you-assert-that-a-certain-exception-is-thrown-in-junit-tests
        HowToInvokeMethods.validateAndDouble(null);
    }

    @Test
    public void givenANonNullInteger_WhenValidateAndDouble_ThenDoublesIt() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        // assertEquals((Integer) 4, HowToInvokeMethods.validateAndDouble(2));
        Method validateAndDoubleMethod = targetClass.getMethod("validateAndDouble", Integer.class);
        Integer result = (Integer) validateAndDoubleMethod.invoke(null, 2);

        assertEquals((Integer) 4, result);
    }

    // ------------------how to access private method------------------
    @Test
    public void givenNull_WhenDoubleInteger_ThenNull() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method doubleIntegerMethod = targetClass.getDeclaredMethod("doubleInteger", Integer.class);
        doubleIntegerMethod.setAccessible(true);
        
        assertEquals(null, doubleIntegerMethod.invoke(null, (Object) null));
    }

    @Test
    public void invokeVoidMethod() throws NoSuchMethodException, SecurityException{
        Method voidMethod = targetClass.getMethod("voidMethod");
        System.out.println(voidMethod);

    }

}
