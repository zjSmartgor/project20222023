package ngordnet.main;

import java.util.*;

public class Graph {
//    private HashMap<ArrayList<String>, ArrayList<String>> kk;fw
    private HashMap<Integer, ArrayList<Integer>> adjMap;
    private HashMap<Integer, ArrayList<String>> hMapIdToWord;
    private HashMap<String, ArrayList<Integer>> hMapWordToId;
    public Graph() {
        adjMap = new HashMap<>();
        hMapIdToWord = new HashMap<>();
        hMapWordToId = new HashMap<>();

    }

    public void putInGraph(int id, ArrayList<Integer> adjId) {
        if (!adjMap.containsKey(id)) {
            ArrayList<Integer> adjLst = new ArrayList<>();
            for (int i : adjId) {
                adjLst.add(i);
            }
            adjMap.put(id, adjLst);
        } else {
            ArrayList<Integer> adjLst = adjMap.get(id);
            for (int i : adjId) {
                adjLst.add(i);
            }
            adjMap.put(id, adjLst);

        }

    }

    public ArrayList<String> getInGraph(String word) {
        ArrayList idLst = getInMapWordToId(word);
        ArrayList wordLst = new ArrayList();
        if (idLst == null) {
            return wordLst;
        } else {
            return convertIdToWordsInOrder(getInGraphHelper(idLst));

        }


    }
    private ArrayList<Integer> combineTwoIntList(ArrayList<Integer> lst1, ArrayList<Integer> lst2) {
        HashSet<Integer> set1 = new HashSet<>(lst1);
        HashSet<Integer> set2  = new HashSet<>(lst2);
        set1.addAll(set2);
        ArrayList<Integer> result = new ArrayList<>(set1);
        return result;



    }
    private ArrayList<String> combineTwoStringList(ArrayList<String> lst1, ArrayList<String> lst2) {
        HashSet<String> set1 = new HashSet<>(lst1);
        HashSet<String> set2  = new HashSet<>(lst2);
        set1.addAll(set2);
        ArrayList<String> result = new ArrayList<>(set1);
        return result;







    }
    private ArrayList<Integer> getInGraphHelper(ArrayList<Integer> ids) {
        ArrayList result = ids;
        if (result == null) {
            return new ArrayList<>();
        } else {
            for (int i : ids) {
                result = combineTwoIntList(result,getInGraphHelper(adjMap.get(i)));
            }
            return result;
        }



    }
    private ArrayList<String> convertIdToWordsInOrder(ArrayList<Integer> ids) {
        ArrayList<String> result = new ArrayList();
        for (int i : ids) {
            result = combineTwoStringList(result, hMapIdToWord.get(i));
        }
        Collections.sort(result);
        return result;


    }

    public void putInMapIdToWord(int id, ArrayList<String> words) {
        hMapIdToWord.put(id, words);

    }

    public ArrayList getInMapIdToWord(int id) {
        return hMapIdToWord.get(id);

    }

    public void putInMapWordToId(String word, int id) {
        if (!hMapWordToId.containsKey(word)) {
            ArrayList<Integer> lst = new ArrayList<>();
            lst.add(id);
            hMapWordToId.put(word,lst);
        } else {
            hMapWordToId.get(word).add(id);
        }

    }

    public ArrayList getInMapWordToId(String word) {
        return hMapWordToId.get(word);

    }

}
