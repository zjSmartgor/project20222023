package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Game {
    private TETile[][] world;
    private boolean gameOver = false;
    private DrawMachine dm;
    private TERenderer ter;
    private String input;
    private String name;


    public Game(TERenderer ter, TETile[][] world, DrawMachine dm, String input, String name) {
        this.ter = ter;
        this.world = world;
        this.dm = dm;
        this.input = input;
        this.name = name;

    }


    public void startGame() {
        char oldDir = ' ';
        drawGameFrame();
        KeyboardInputSource device = new KeyboardInputSource(world);
        while (!gameOver) {
            char dir = device.getNextKey2();
            // char dir4 = getNextKey();
            if (dir == 'Q' && oldDir == ':') {
                //save input*
                writeToFile(Character.toUpperCase(dir)); //save and load
                System.exit(0);
            } else {
                if (dir == ':') {
                    writeToFile(dir);
                } else {
                    writeToFile(Character.toUpperCase(dir));
                }
                //saveinput*
                world = dm.move(dir);
                // ter.renderFrame(world);
                drawGameFrame();
                oldDir = dir;
            }
            gameOver = checkFlowers();
        }
        displayCongrats();
    }
    public boolean checkFlowers() {
        for (int x = 0; x < world.length; x ++) {
            for (int y = 0; y < world[0].length; y ++) {
                if (world[x][y] == Tileset.FLOWER) {
                    return false;
                }
            }
        }
        return true;
    }
    public void displayCongrats() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 35);
        StdDraw.setFont(fontBig);
        StdDraw.text(40, 20, "Congratulations! You have found all the ❀!");
        StdDraw.show();
        StdDraw.pause(4000);
        System.exit(0);
    }

    public void replayGame() {

        StringInputDevice device = new StringInputDevice(input);
        while (device.possibleNextInput()) {
            char getSKey = device.getNextKey();
            if (getSKey == 'S') {
                break;
            }
        }
        while (device.possibleNextInput()) {
            char dir =device.getNextKey();
            if (dir == 'W' || dir == 'S' ||dir == 'A' || dir == 'D') {
                world = dm.replayMove(dir);
                drawGameFrame();
                StdDraw.pause(300);
            }

        }
    }

    /**
     * HUD: Text that describes the tile currently under the mouse pointer
     **/
    public void showSet() {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
//        System.out.println(x);
//        System.out.println(y);
        //ter.renderFrame(world);
        if (x >= 0 && x < 80 && y >= 5 && y < 36) {
            drawType(world[x][y - 5].description());
        }
    }

    public void drawType(String s) {
        /* Take the input string S and display it at the upper right of the screen,
         * with the pen settings given below. */
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(fontBig);
        StdDraw.text(70, 35, s);
        StdDraw.show();
    }

    public void drawGameFrame() {
        ter.renderFrame(world);
        StdDraw.setPenColor(Color.WHITE);
        Font fontSmall = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(fontSmall);
        StdDraw.textRight(40, 3, "Up: W | Down: S | Left: A | Right: D");
        StdDraw.textRight(70,3, "Player: " + name);//HUD

        StdDraw.show();
    }


    public void writeToFile(char key) {
        try(FileWriter fw = new FileWriter("InputFile.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.print(key);
        } catch (IOException e) {
        }

    }
}


