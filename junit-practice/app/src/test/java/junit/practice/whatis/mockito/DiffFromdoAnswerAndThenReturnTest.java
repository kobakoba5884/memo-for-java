package junit.practice.whatis.mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class DiffFromdoAnswerAndThenReturnTest {
    @Mock
    DiffFromdoAnswerAndThenReturn diffFromdoAnswerAndThenReturn;

    @Test
    public void testWhenThenReturn(){
        int retrunValue = 5;

        Mockito.when(diffFromdoAnswerAndThenReturn.getStringLength("dummy")).thenReturn(retrunValue);

        doReturn(retrunValue).when(diffFromdoAnswerAndThenReturn).getStringLength("dummy");

        assertEquals(5, diffFromdoAnswerAndThenReturn.getStringLength("dummy"));
    }

    @Test
    public void testThenAnswer() {
        Answer<Integer> answer = invocation -> {
            String string = (String) invocation.getArguments()[0];
            return string.length() * 5;
        };

        Mockito.when(diffFromdoAnswerAndThenReturn.getStringLength("dummy")).thenAnswer(answer);

        Mockito.doAnswer(answer).when(diffFromdoAnswerAndThenReturn).getStringLength("dummy");

        assertEquals(25, diffFromdoAnswerAndThenReturn.getStringLength("dummy"));
    }
}   
