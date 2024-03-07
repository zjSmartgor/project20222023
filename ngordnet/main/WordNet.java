package ngordnet.main;

import edu.princeton.cs.algs4.In;
import ngordnet.ngrams.NGramMap;

import java.util.*;

public class WordNet {
    private Graph graph;


    public WordNet(String synsetFile, String hyponymFile) {
        graph = new Graph();
        In sFile = new In(synsetFile);
        In hFile = new In(hyponymFile);

        while (hFile.hasNextLine()) {
            int id = -1;
            String item = hFile.readLine();
            String[] itemArray = item.split(",");
            ArrayList<Integer> lst = new ArrayList<>();
            for (String ele : itemArray) {
                int i = Integer.parseInt(ele);
                if (id == -1) {
                    id = i;
                } else {
                    lst.add(i);
                }

            }
            graph.putInGraph(id, lst);

        }
        while (sFile.hasNextLine()) {
            String item2 = sFile.readLine();
            String[] itemArray2 = item2.split(",");
            int id2 = Integer.parseInt(itemArray2[0]);
            String[] wordLst = itemArray2[1].split(" ");
            ArrayList<String> words = new ArrayList<>(Arrays.asList(wordLst));
            graph.putInMapIdToWord(id2, words);
            for (String w : words) {
                graph.putInMapWordToId(w, id2);
            }

        }
    }

    public ArrayList<String> hyponymsOneWord(String word) {
        ArrayList<String> lst = graph.getInGraph(word);
        return lst;
    }

    // We have the new func to deal with the list of words, so we actually don't need this func for one word;
    // can treat it as a helper func.
    public ArrayList<String> hyponymsWordLst(List<String> words) {
        int t = 0;
        ArrayList<String> lst = new ArrayList<>();
        for (String w : words) {
            if (t == 0) {
                lst = hyponymsOneWord(w);
                t += 1;
            } else {
                ArrayList<String> l = hyponymsOneWord(w);
                if (l.isEmpty()) {
                    return l;
                }
                lst.retainAll(l);
            }
        }
        Collections.sort(lst);

        return lst;
    }
}








