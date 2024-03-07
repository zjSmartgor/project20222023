package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class NGramMap {
    private TimeSeries yearToTotalCount;
    private HashMap<String, TimeSeries> wordToYearToCount;
    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    public NGramMap(String wordsFilename, String countsFilename) {
        yearToTotalCount = new TimeSeries();
        wordToYearToCount = new HashMap<>();
        In wordsFile = new In(wordsFilename);
        In countsFile = new In(countsFilename);
        while (wordsFile.hasNextLine() && !wordsFile.isEmpty()) {
            String itemWord = wordsFile.readString();
            int itemYear = wordsFile.readInt();
            double itemCount = (double) wordsFile.readInt();
            wordsFile.readInt();
            if (wordToYearToCount.containsKey(itemWord)) {
                wordToYearToCount.get(itemWord).put(itemYear, itemCount);
            } else {
                TimeSeries yearToCount = new TimeSeries();
                yearToCount.put(itemYear, itemCount);
                wordToYearToCount.put(itemWord, yearToCount);
            }


        }

        while (countsFile.hasNextLine()) {
            String item = countsFile.readLine();
            String[] itemArray = item.split(",");
            int itemYearTotal = Integer.parseInt(itemArray[0]);
            double itemCountTotal = Double.parseDouble(itemArray[1]);
            yearToTotalCount.put(itemYearTotal, itemCountTotal);
        }

    }

    /** Provides the history of WORD. The returned TimeSeries should be a copy,
     *  not a link to this NGramMap's TimeSeries. In other words, changes made
     *  to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word) {
        if (wordToYearToCount.get(word) != null) {
            TimeSeries tsNeed = wordToYearToCount.get(word);
            TimeSeries tsReturn = new TimeSeries();
            List key = tsNeed.years();
            for (Object i : key) {
                tsReturn.put((int) i, tsNeed.get(i));
            }
            return tsReturn;

        }
        return null;

    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     *  returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     *  changes made to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries tsNeed = wordToYearToCount.get(word);
        TimeSeries tsReturn = new TimeSeries(tsNeed, startYear, endYear);
        return tsReturn;

    }

    /** Returns a defensive copy of the total number of words recorded per year in all volumes. */
    public TimeSeries totalCountHistory() {
        TimeSeries tsReturn = new TimeSeries();
        List key = yearToTotalCount.years();
        for (Object i : key) {
            tsReturn.put((int) i, yearToTotalCount.get(i));
        }
        return tsReturn;
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD compared to
     *  all words recorded in that year. */
    public TimeSeries weightHistory(String word) {
        TimeSeries weightHistoryTs = wordToYearToCount.get(word).dividedBy(yearToTotalCount);
        return weightHistoryTs;


    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     *  and ENDYEAR, inclusive of both ends. */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries tsWord = wordToYearToCount.get(word);
        TimeSeries weightHistoryWithYears = new TimeSeries(tsWord, startYear, endYear);
        return weightHistoryWithYears.dividedBy(yearToTotalCount);
    }

    /** Returns the summed relative frequency per year of all words in WORDS. */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        Iterator iter = words.iterator();
        TimeSeries summedWeightHistory = weightHistory((String) iter.next());
        for (Iterator it = iter; it.hasNext(); ) {
            String i = (String) it.next();
            summedWeightHistory = summedWeightHistory.plus(weightHistory(i));
        }
        return summedWeightHistory;
    }

    /** Provides the summed relative frequency per year of all words in WORDS
     *  between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     *  this time frame, ignore it rather than throwing an exception. */
    public TimeSeries summedWeightHistory(Collection<String> words,
                              int startYear, int endYear) {
        Iterator iter = words.iterator();
        TimeSeries summedWeightHistoryWithYear = weightHistory((String) iter.next(), startYear, endYear);
        for (Iterator it = iter; it.hasNext(); ) {
            String i = (String) it.next();
            summedWeightHistoryWithYear = summedWeightHistoryWithYear.plus(weightHistory(i, startYear, endYear));
        }
        return summedWeightHistoryWithYear;
        /**another way to loop
         *         int k = 1;
         *         TimeSeries summedWeightHistoryWithYear = null;
         *         for (String i : words) {
         *             if (k == 1) {
         *                 summedWeightHistoryWithYear = weightHistory(i, startYear, endYear);
         *                 k += 1;
         *             } else {
         *                 TimeSeries ts = weightHistory(i, startYear, endYear);
         *                 summedWeightHistoryWithYear = summedWeightHistoryWithYear.plus(ts);
         *             }
         *
         *         }
         */




    }



}
