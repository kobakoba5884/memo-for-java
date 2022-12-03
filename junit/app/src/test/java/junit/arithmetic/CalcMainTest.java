package junit.arithmetic;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class CalcMainTest {

    @Before
    public void beforeTesting(){

    }

    @After
    public void afterTesting(){
        
    }

    @Test
    public void testCalc(){
        CalcMain target = new CalcMain();

        int op = 1;
        int valL = 5;
        int valR = 3;

        int result = target.calc(op, valL, valR);

        assertEquals(8, result);
    }

    @Test
    @Parameters(method = "testCalc_Parameters")
    public void testCalc(int op, int valL, int valR, int expected){

        int result = new CalcMain().calc(op, valL, valR);

        assertEquals(expected, result);
    }

    @SuppressWarnings("unused")
    private static int[][] testCalc_Parameters(){
        return new int[][]{
            {1, 5, 3, 8},
            {2, 9, 5, 4},
            {3, 7, 6, 42}
        };
    }

    
}
