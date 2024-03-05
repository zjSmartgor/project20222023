package ngordnet.ngrams;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TestTimeSeries {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);


        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertEquals(expectedYears, totalPopulation.years());

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertEquals(expectedTotal.get(i), totalPopulation.data().get(i), 1E-10);
        }
    }

    @Test
    public void testDivideBy() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 50.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);
        catPopulation.put(1995, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries tsDivided = dogPopulation.dividedBy(catPopulation);
        for (int i : tsDivided.years()) {
            System.out.println(i);
        }
        for (int i : dogPopulation.years()) {
            System.out.println(i);
        }
        for (double i : tsDivided.data()) {
            System.out.println(i);
        }
        for (double i : dogPopulation.data()) {
            System.out.println(i);
        }


    }
    @Test
    public void testAnotherContrutor() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);
        TimeSeries a = new TimeSeries(catPopulation, 1989, 1992);
        System.out.print(a.toString());
    }
} 