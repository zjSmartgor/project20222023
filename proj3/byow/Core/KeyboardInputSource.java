package byow.Core;


import byow.InputDemo.InputSource;
import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class KeyboardInputSource implements InputSource {
    private String input;//The form L......:Q;
    private TETile[][] world;
//    private static final boolean PRINT_TYPED_KEYS = false;
    public KeyboardInputSource(String input) {
        this.input = input;
//        StdDraw.text(0.3, 0.3, "press m to moo, q to quit");
    }
    public KeyboardInputSource() {

    }
    public KeyboardInputSource(TETile[][] world) {
        this.world = world;

    }

    public char getNextKey() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toUpperCase(StdDraw.nextKeyTyped());
//                if (PRINT_TYPED_KEYS) {
//                    System.out.print(c);
//                }
                return c;
            }
        }
    }
    public char getNextKey2() {
        while (true) {
            showSet();
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toUpperCase(StdDraw.nextKeyTyped());
//                if (PRINT_TYPED_KEYS) {
//                    System.out.print(c);
//                }
                return c;
            }
        }
    }

    public boolean possibleNextInput() {
        return true;
    }

    public void showSet() {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
//        System.out.println(x);
//        System.out.println(y);
        //ter.renderFrame(world);
        if (x >= 0 && x < 80 && y >= 5 && y < 35) {
            drawType(world[x][y - 5].description());
        }
    }

    public void drawType(String s) {
        /* Take the input string S and display it at the upper right of the screen,
         * with the pen settings given below. */
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(fontBig);
        StdDraw.text(70, 38, s);
        StdDraw.show();
        StdDraw.pause(100);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(70, 38, 5, 1);
        // StdDraw.text(70, 35, "████████████");
    }
}
