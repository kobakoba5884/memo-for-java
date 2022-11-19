package junit.practice.whatis.mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

// @RunWith(MockitoJUnitRunner.class)
public class MockitoAnnotationTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    
    @Mock
    List<String> mockedList;

    @Spy
    List<String> spiedList = new ArrayList<>();

    @Test
    public void whenNotUseMockAnnotation_thenCorrect(){

        mockedList.add("one");
        Mockito.verify(mockedList).add("one");

        assertEquals(0, mockedList.size());

        Mockito.when(mockedList.size()).thenReturn(100);
        assertEquals(100, mockedList.size());
    }

    @Test
    public void whenNotUseSpyAnnotation_thenCorrect(){
        spiedList.add("one");
        spiedList.add("two");

        verify(spiedList).add("one");
        verify(spiedList).add("two");

        assertEquals(2, spiedList.size());

        doReturn(100).when(spiedList).size();
        assertEquals(100, spiedList.size());
    }

    
}
