package ngordnet.ngrams;

import java.util.*;

/** An object for mapping a year number (e.g. 1996) to numerical data. Provides
 *  utility methods useful for data analysis.
 *  @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {
    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        super();
    }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     *  inclusive of both end points. */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        List<Integer> yearSet = ts.years();
        for (int i : yearSet) {
            if (i >= startYear && i <= endYear) {
                this.put(i, ts.get(i));
            }
        }
    }

    /** Returns all years for this TimeSeries (in any order). */
    public List<Integer> years() {
        List<Integer> yearList = new ArrayList<>();
        if (this.size() != 0) {
            for (int i : this.keySet()) {
                yearList.add(i);
            }
        }
        return yearList;

    }

    /** Returns all data for this TimeSeries (in any order).
     *  Must be in the same order as years(). */
    public List<Double> data() {
        List<Double> dataList = new ArrayList<>();
        if (this.size() != 0) {
            for (int i : this.keySet()) {
                dataList.add(this.get(i));
            }
        }
        return dataList;
    }

    /** Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     *  each year, sum the data from this TimeSeries with the data from TS. Should return a
     *  new TimeSeries (does not modify this TimeSeries). */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries returnTs = new TimeSeries();
        for (int i : ts.years()) {
            if (!this.containsKey(i)) {
                returnTs.put(i, ts.get(i));
            } else {
                returnTs.put(i, ts.get(i) + this.get(i));
            }
        }
        for (int j : this.years()) {
            if (!ts.containsKey(j)) {
                returnTs.put(j, this.get(j));
            }
        }
        return returnTs;
    }

    /** Returns the quotient of the value for each year this TimeSeries divided by the
     *  value for the same year in TS. If TS is missing a year that exists in this TimeSeries,
     *  throw an IllegalArgumentException. If TS has a year that is not in this TimeSeries, ignore it.
     *  Should return a new TimeSeries (does not modify this TimeSeries). */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries returnTs = new TimeSeries();
        for (int i : this.years()) {
            if (!ts.containsKey(i)) {
                throw new IllegalArgumentException();
            } else {
                returnTs.put(i, this.get(i) / ts.get(i));
            }
        }
        return returnTs;

    }
    


}
