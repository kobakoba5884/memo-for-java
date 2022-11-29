package junit.whatis.mockito;

import java.util.AbstractList;
import java.util.Collection;

public class MockitoVerify extends AbstractList<String>{

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        return false;
    }

    @Override
    public String get(int index) {
        return null;
    }
    
}
