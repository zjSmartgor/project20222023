package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static Random RANDOM;
    private TETile[][] world;
    private String input = "";
    private DrawMachine dm;
    private String name;
    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */

    public void interactWithKeyboard() throws IOException {
        world = new TETile[WIDTH][HEIGHT];
        ter.initialize(WIDTH, HEIGHT + 10, 0, 5);
        drawMainFrame();
        KeyboardInputSource device = new KeyboardInputSource();
        char key = ' ';
        while (true) {
            key = device.getNextKey();
            if (key == 'N'){
                //clear the file
                //add N to input and save the input for every step
                input += key; //input :N
                writeToFileNextLine(Character.toUpperCase(key));
                drawSeedFrame();
                name = drawNameFrame();
//                    input += getSeed(); //N --> N1234S
                world = interactWithInputString(input);
                Game game = new Game(ter, world, dm, input, name);
                game.startGame();

            } else if (key == 'L') {
                //read the input from the file and keep going
                /**
                 * load input
                 */
                writeToFile(Character.toUpperCase(key));
                input = loadLastInput();
                world = interactWithInputString(input);
                name = readName();
                Game game = new Game(ter, world, dm, input, name);
                game.startGame();
            } else if (key == 'Q') {
                writeToFile(key);
                System.exit(0);
            } else if (key == 'R') {
                writeToFile(key);
                replayGame();
            }
        }
    }
    public void replayGame() throws IOException {
        String replayInput = loadLastInput();
        StringInputDevice device = new StringInputDevice(replayInput);
        String seedString = "";
        while (device.possibleNextInput()) {
                char key = device.getNextKey();
                if (key == 'S') {
                    break;
                } else if (key == 'N'){

                } else {
                    seedString += key;
                }
            }
        RANDOM = new Random(Long.parseLong(seedString));
        dm = new DrawMachine(world, WIDTH, HEIGHT, RANDOM);
        world = dm.drawRandomWorld();
        name = readName();

        Game game = new Game(ter, world, dm, replayInput, name);
        drawCenterTextFrame("Directing To Replaying ..");
        StdDraw.pause(1000);
        drawCenterTextFrame("Directing To Replaying ....");
        StdDraw.pause(1000);
        drawCenterTextFrame("Directing To Replaying ......");
        StdDraw.pause(1000);
        game.replayGame();
        drawCenterTextFrame("Returning To Menu ..");
        StdDraw.pause(1000);
        drawCenterTextFrame("Returning To Menu ....");
        StdDraw.pause(1000);
        drawCenterTextFrame("Returning To Menu ......");
        StdDraw.pause(1000);
        drawMainFrame();
    }

    public String readName() throws IOException {
        BufferedReader input = new BufferedReader(new FileReader("NameFile.txt"));
        String last = null, line;
        while ((line = input.readLine()) != null && !line.isBlank() ) {
            last = line;
        }
        return last;
    }
    public void writeToNameFile(char key) {
        try(FileWriter fw = new FileWriter("NameFile.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.print(key);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    public void writeToNameFileNextLine() {
        try(FileWriter fw = new FileWriter("NameFile.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println();
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    public void writeToFile(char key) {
        try(FileWriter fw = new FileWriter("InputFile.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.print(key);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    public void writeToFileNextLine(char key) {
        try(FileWriter fw = new FileWriter("InputFile.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println();
            out.print(key);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    public String loadLastInput() throws IOException {
        BufferedReader input = new BufferedReader(new FileReader("InputFile.txt"));
        String last = null, line;
        while ((line = input.readLine()) != null) {
            last = line;
        }
        return last;
    }
    public void drawCenterTextFrame(String text) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTH / 2, (HEIGHT + 20) / 2, text);
        StdDraw.show();
    }

    public void drawMainFrame() {
        StdDraw.setCanvasSize(WIDTH * 16, (HEIGHT + 10) * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT + 10);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 35);
        StdDraw.setFont(fontBig);
        StdDraw.text(40, 30, "CS61B: STRIKE GAME");

        Font fontMed = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(fontMed);

        //StdDraw.text(3, HEIGHT - 2, "text");
        StdDraw.text(40, 23, "New Game (N)");
        StdDraw.text(40, 20, "Load Game (L)");
        StdDraw.text(40, 17, "Replay (R)");
        StdDraw.text(40, 14, "Quit (Q)"); //WIDTH / 2, (HEIGHT + 10) / 2 + 2
        StdDraw.text(70, 3, "By Jeffery  & Tina");
        StdDraw.show();
    }

    public void drawSeedFrame() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTH / 2, (HEIGHT + 20) / 2, "Please Enter Seed(end with S) : ");
        StdDraw.show();
        String s = "";
        KeyboardInputSource device = new KeyboardInputSource();
        while (true) {
            char key = device.getNextKey();
            if (key == 'S') {
                input += key;
                writeToFile(Character.toUpperCase(key));
                break;
            }
            s += key; // char
            input += key;
            writeToFile(Character.toUpperCase(key));
            drawFrame(s);
        }

    }
    public String drawNameFrame() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTH / 2, (HEIGHT + 20) / 2, "Please Enter Name(end with .) : ");
        StdDraw.show();
        String s = "";
        KeyboardInputSource device = new KeyboardInputSource();
        while (true) {
            char key = device.getNextKey();
            if (key == '.') {
                break;
            }
            s += key; // char
            writeToNameFile(key);
            drawFrame2(s);
        }
        writeToNameFileNextLine();
        return s;

    }
    public void drawFrame2(String s) {
        /* Take the input string S and display it at the center of the screen,
         * with the pen settings given below. */
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTH / 2, (HEIGHT + 20) / 2, "Please Enter Name(end with .) : ");
        StdDraw.text(WIDTH / 2, (HEIGHT + 10) / 2, s);
        StdDraw.show();
    }

    public void drawFrame(String s) {
        /* Take the input string S and display it at the center of the screen,
         * with the pen settings given below. */
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTH / 2, (HEIGHT + 20) / 2, "Please Enter Seed(end with S) : ");
        StdDraw.text(WIDTH / 2, (HEIGHT + 10) / 2, s);
        StdDraw.show();
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, running both of these:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) throws IOException {
        // N######S
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        StringInputDevice device = new StringInputDevice(input);
        TETile[][] finalWorldFrame = null;
        char firstKey = device.getNextKey();
// N13523S
        if (firstKey == 'N') {
            writeToFileNextLine(firstKey);
            String seedString = "";
            while (device.possibleNextInput()) {
                char key = device.getNextKey();
                writeToFile(key);
                if (key == 'S') {
                    break;
                } else {
                    seedString += key;
                }
            }
            RANDOM = new Random(Long.parseLong(seedString));
            finalWorldFrame = new TETile[WIDTH][HEIGHT];
            dm = new DrawMachine(finalWorldFrame, WIDTH, HEIGHT, RANDOM);
            finalWorldFrame = dm.drawRandomWorld();
            while (device.possibleNextInput()) {
                char dir = device.getNextKey();
                writeToFile(dir);
                finalWorldFrame = dm.move(dir);
            }

        } else if (firstKey == 'L') {
            String lastInput = loadLastInput();
            writeToFile(firstKey);
            finalWorldFrame = interactWithInputString(lastInput);
            while (device.possibleNextInput()) {
                char dir = device.getNextKey();

                writeToFile(dir);
                finalWorldFrame = dm.move(dir);
            }
        }
        this.world = finalWorldFrame;
        return finalWorldFrame;
    }

    /**
     * add a toString method so that we can print engine.toString(world) in Main and shou it on terminal.
     *
     */
    @Override
    public String toString() {
        int width = world.length;
        int height = world[0].length;
        StringBuilder sb = new StringBuilder();

        for (int y = height - 1; y >= 0; y -= 1) {
            for (int x = 0; x < width; x += 1) {
                if (world[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                sb.append(world[x][y].character());
            }
            sb.append('\n');
        }
        return sb.toString();
    }

}
