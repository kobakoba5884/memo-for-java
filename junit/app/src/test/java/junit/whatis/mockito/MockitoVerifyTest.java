package junit.whatis.mockito;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

// https://salumarine.com/how-to-test-how-many-times-method-is-called-using-mockito/

public class MockitoVerifyTest {
    @Rule
    public MockitoRule initRule = MockitoJUnit.rule();
    
    @Mock
    List<String> mockedList = mock(MockitoVerify.class);

    @Test
    public void verifySimpleInvocation(){
        System.out.println(mockedList.size());
        verify(mockedList).size();
    }

    @Test
    public void verifyOneTime(){
        mockedList.size();
        verify(mockedList, times(1)).size();
    }

    @Test
    public void verifyInteraction(){
        verify(mockedList, times(0)).size();
        // mockedList.size();
        // mockedList.clear();
        verifyNoInteractions(mockedList);
    }

    // verify order invoking method
    @Test
    public void verifyOrderInteractions(){
        mockedList.size();
        mockedList.add("a parameter");
        mockedList.clear();

        InOrder inOrder = inOrder(mockedList);
        inOrder.verify(mockedList).size();
        inOrder.verify(mockedList).add("a parameter");
        inOrder.verify(mockedList).clear();
    }

    @Test
    public void interactionHasNotOccurd(){
        verify(mockedList, never()).clear();
        verify(mockedList, times(0)).add("0");
    }

    
}
