package junit.howto;

public class HowToInvokeMethods {
    public double publicSum(int a, double b) {
        return a + b;
    }

    public static double publicStaticMultiply(float a, long b) {
        return a * b;
    }

    @SuppressWarnings("unused")
    private boolean privateAnd(boolean a, boolean b) {
        return a && b;
    }

    protected int protectedMax(int a, int b) {
        return a > b ? a : b;
    }

    private static Integer doubleInteger(Integer input) {
        // this is dead code!!
        if (input == null) {
            return null;
        }
        return 2 * input;
    }

    public static Integer validateAndDouble(Integer input) {
        if (input == null) {
            throw new IllegalArgumentException("input should not be null");
        }
        return doubleInteger(input);
    }

    public void voidMethod(){
        System.out.println("void method");
    }
}
