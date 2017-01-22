package kjw.kr.bunobuno;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by kjwook on 2017. 1. 18..
 */
@RunWith(MockitoJUnitRunner.class)
public class CalcTest {
    @Mock
    private Calc calc;

    @Test
    public void sum() throws Exception {
        when(calc.sum(anyInt(), anyInt())).thenCallRealMethod();
        assertThat(calc.sum(1,2), is(3));
    }

    @Test
    public void multiply() throws Exception {
        when(calc.multiply(2,3)).thenReturn(6);
        assertThat(calc.multiply(2, 3), is(6));
    }
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(calc);
    }

    @After
    public void tearDown() throws Exception {


    }


}