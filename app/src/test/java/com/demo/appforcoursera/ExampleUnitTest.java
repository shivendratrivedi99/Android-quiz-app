package com.demo.appforcoursera;

        import android.util.Log;

        import org.junit.Test;

        import java.util.HashMap;

        import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

        HashMap<Integer,Integer> arr = new HashMap<>();

        System.out.println(arr.get(2));
    }
}