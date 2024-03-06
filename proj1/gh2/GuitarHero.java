package gh2;
import deque.ArrayDeque;
import deque.Deque;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;


public class GuitarHero {
    private static final double CONCERT_A = 440.0;
    private static final int NUMBEROFNOTES = 37;
    private static final int ONETIMENUM1 = 24;
    private static final double ONETIMENUM2 = 12.0;

    private static double frequencyCalculation(int i) {
        return CONCERT_A * Math.pow(2, (i - ONETIMENUM1) / ONETIMENUM2);

    }

    public static void main(String[] args) {
        Deque<GuitarString> noteList = new ArrayDeque<>();
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        double sample;

        for (int i = 0; i < NUMBEROFNOTES; i++) {
            double frequencyUse = GuitarHero.frequencyCalculation(i);
            GuitarString noteUse = new GuitarString(frequencyUse);
            noteList.addLast(noteUse);
        }
        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (keyboard.indexOf(key) != -1) {
                    noteList.get(keyboard.indexOf(key)).pluck();
                } else {
                    continue;
                }


            }
            sample = 0.0;
            for (int i = 0; i < NUMBEROFNOTES; i++) {
                sample += noteList.get(i).sample();
                noteList.get(i).tic();
            }

            StdAudio.play(sample);
        }

    }
}
