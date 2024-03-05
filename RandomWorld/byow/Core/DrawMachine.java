package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class DrawMachine {
//    private Position initPos;
    /**
     * instance variables needed
     */
    private TETile[][] world;
    private int widthOfWorld;
    private int heightOfWorld;
    private Random random;
    private WeightedQuickUnionUF wqu;
    private HashMap<Integer, Position> coorToPosMap;
    private int virtualTopCoor;

    private Position user;

    public DrawMachine(TETile[][] te, int width, int height, Random random) {
//        this.initPos = p;
        this.world = te;
        this.heightOfWorld = height;
        this.widthOfWorld = width;
        this.random = random;
//        virtualTopCoor = width * height; Actually don't need.
        wqu = new WeightedQuickUnionUF(width * height);
        //The last one would be the virtual one to see if others connect
        coorToPosMap = new HashMap<>();

    }


    /**
     * methods to draw random world;
     *
     */
    public void initWorld(TETile[][] world) {
        this.world = world;
    }
    public TETile[][] drawRandomWorld() {
        fillWithNothing();
        drawRooms();
        drawRoomFlowers();
        drawHallways();
        drawWalls();
        userStartPosition();
        return world;
    }

    public void fillWithNothing() {
        for (int x = 0; x < widthOfWorld; x += 1) {
            for (int y = 0; y < heightOfWorld; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public void drawRooms() {
        int numOfRoom = random.nextInt(15, 25); //to randomly determine how many rooms we have.
        int randomWidth;
        int randomHeight;
        int randomX;
        int randomY;
        while (numOfRoom > 0) {
            randomWidth = random.nextInt(2,8);
            randomHeight = random.nextInt(3,8);
            randomX = random.nextInt(1, widthOfWorld - 1);
            randomY = random.nextInt(1, heightOfWorld - 1);
            Position thisPos = new Position(randomX, randomY, randomWidth, randomHeight, widthOfWorld, heightOfWorld);
            if (checkRoomValid(thisPos, randomWidth, randomHeight)) {
                drawOneRoom(thisPos, randomWidth, randomHeight);
                numOfRoom -= 1;
            }
        }
    }
    public void drawOneRoom(Position p, int widthOfRoom, int heightOfRoom) {
        int thisCoor = p.getCoor();
        int lastCoor = p.getCoor();
//        if (coorToPosMap.isEmpty()) {
//            wqu.connected(thisCoor, virtualTopCoor);
//        } Actually don't need
        // connect the position of the first room to virtual top, the we just need to check if other rooms connect to
        // the virtual top.
        coorToPosMap.put(thisCoor, p);

        for (int x = p.getX(); x < p.getX() + widthOfRoom; x += 1) {
            for (int y = p.getY(); y < p.getY() + heightOfRoom; y += 1) {
                world[x][y] = Tileset.FLOOR;
                thisCoor = xyToCoor(x, y);
                wqu.union(lastCoor, thisCoor);
                lastCoor = thisCoor;
            }
        }
    }

    public boolean checkRoomValid(Position p, int widthOfRoom, int heightOfRoom) {
        boolean condition = (p.getX() + widthOfRoom < widthOfWorld - 1) && (p.getY() + heightOfRoom < heightOfWorld -1)
                && (p.getX() > 0) && (p.getY() > 0);
        if (condition) {
            return checkRoomSurround(p, widthOfRoom, heightOfRoom);

        }
        return condition;

    }
    public boolean checkRoomSurround(Position p, int widthOfRoom, int heightOfRoom) {
        int x = p.getX();
        int y = p.getY();
        return (checkTileSurround(x,y) &&
                checkTileSurround(x+widthOfRoom, y) &&
                checkTileSurround(x, y + heightOfRoom) &&
                checkTileSurround(x + widthOfRoom, y + heightOfRoom));
    }

    public boolean checkTileSurround(int x, int y) {
        int numOfFloor = 0;
        if (world[x+1][y] == Tileset.FLOOR || world[x][y-1] == Tileset.FLOWER) {
            numOfFloor++;
        }
        if (world[x-1][y] == Tileset.FLOOR || world[x][y-1] == Tileset.FLOWER) {
            numOfFloor++;
        }
        if (world[x][y+1] == Tileset.FLOOR || world[x][y-1] == Tileset.FLOWER) {
            numOfFloor++;
        }
        if (world[x][y-1] == Tileset.FLOOR || world[x][y-1] == Tileset.FLOWER) {
            numOfFloor++;
        }
        if (numOfFloor == 2 ||  numOfFloor == 0) {
            return true;
        } else {
            return false;
        }
    }
    public void drawHallways() {
        //while condition: check if all the rooms have been connected
        //choose two rooms based on the Hashmap order
        //check if the two rooms have been connected
        //randomly generate two doors
        //connect: while x1 < x2, x1++, then while y1 < y2, y1++

        ArrayList<Integer> keys = new ArrayList<>(coorToPosMap.keySet());
        while (!fullyConnected()) {
            int a = random.nextInt(keys.size());
            int b = random.nextInt(keys.size());
            if (a != b && !wqu.connected(keys.get(a), keys.get(b))) {
                List<Integer> a1 = chooseDoor(coorToPosMap.get(keys.get(a)));
                List<Integer> b1 = chooseDoor(coorToPosMap.get(keys.get(b)));
                int xa = a1.get(0);
                int ya = a1.get(1);
                int xb = b1.get(0);
                int yb = b1.get(1);
                int move = 1;
                if (xa < xb) {
                    move = 1;
                } else {
                    move = -1;
                }
                while (xa != xb) {

                    world[xa + move][ya] = Tileset.FLOOR;
                    wqu.union(xyToCoor(xa, ya), xyToCoor(xa + move, ya));
                    xa += move;
                }
                if (ya < yb) {
                    move = 1;
                } else {
                    move = -1;
                }
                while (ya != yb) {
                    world[xa][ya + move] = Tileset.FLOOR;
                    wqu.union(xyToCoor(xa, ya), xyToCoor(xa, ya + move));
                    ya += move;
                }
                keys.remove(a);
            }
        }
    }

    public boolean fullyConnected() {
        ArrayList<Integer> keys = new ArrayList<>(coorToPosMap.keySet());
        for (int i = 0; i < keys.size() - 1; i++) {
            if (!wqu.connected(keys.get(i), keys.get(i + 1))) {
                return false;
            }
        }
        return true;
    }

    /**
     * In chooseDoor func, it returns a list with 3 elements, the first two is the door coordinate and
     * the last one represent which direction to go (0:south, 1:west, 2:north, 3:east)
     * @param p
     * @return
     */
    public List<Integer> chooseDoor(Position p) {
        List<Integer> xyList = new ArrayList<>();
        int width = p.getRoomWidth();
        int height = p.getRoomHeight();
        int sideOfDoor = random.nextInt(4);
        switch (sideOfDoor) {
            case 0: {
                xyList.add(p.getX() + random.nextInt(width));
                xyList.add(p.getY());
            }//south side
            case 1: {
                xyList.add(p.getX());
                xyList.add(p.getY() + random.nextInt(height));
            } // west side
            case 2: {
                xyList.add(p.getX() + random.nextInt(width));
                xyList.add(p.getY() + height - 1);
            } //north side
            case 3: {
                xyList.add(p.getX() + width - 1);
                xyList.add(p.getY() + random.nextInt(height));
            } //east side
        }
        xyList.add(sideOfDoor);
        return xyList;

    }

    public void drawWalls() {
        for (int i = 0; i < widthOfWorld; i++) {
            for (int j = 0; j < heightOfWorld; j++) {
                if (world[i][j] == Tileset.FLOOR || world[i][j] == Tileset.FLOWER) {
                    fillWithWall(i, j);
                }
            }
        }

    }
    public void fillWithWall(int x, int y) {
        if (world[x + 1][y] == Tileset.NOTHING) {
            world[x + 1][y] = Tileset.WALL;
        }
        if (world[x - 1][y] == Tileset.NOTHING) {
            world[x - 1][y] = Tileset.WALL;
        }
        if (world[x][y + 1] == Tileset.NOTHING) {
            world[x][y + 1] = Tileset.WALL;
        }
        if (world[x][y - 1] == Tileset.NOTHING) {
            world[x][y - 1] = Tileset.WALL;
        }

        if (world[x + 1][y + 1] == Tileset.NOTHING) {
            world[x + 1][y + 1] = Tileset.WALL;
        }
        if (world[x - 1][y - 1] == Tileset.NOTHING) {
            world[x - 1][y - 1] = Tileset.WALL;
        }
        if (world[x - 1][y + 1] == Tileset.NOTHING) {
            world[x - 1][y + 1] = Tileset.WALL;
        }
        if (world[x + 1][y - 1] == Tileset.NOTHING) {
            world[x + 1][y - 1] = Tileset.WALL;
        }
    }

    public void drawRoomFlowers() {
        int numsOFRoom = coorToPosMap.size();
        int numsOFFlowers = random.nextInt(5, numsOFRoom);
        ArrayList keys = new ArrayList<>(coorToPosMap.keySet());
        while (numsOFFlowers > 0) {
            int randomRoom = random.nextInt(numsOFRoom);
            Position pos = coorToPosMap.get(keys.get(randomRoom));
            world[pos.getX()][pos.getY()] = Tileset.FLOWER;
            world[pos.getX()][pos.getY() + pos.getRoomHeight() - 1] = Tileset.FLOWER;
            world[pos.getX() + pos.getRoomWidth() - 1][pos.getY()] = Tileset.FLOWER;
            world[pos.getX() + pos.getRoomWidth() - 1][pos.getY() + pos.getRoomHeight() - 1] = Tileset.FLOWER;
            numsOFFlowers--;
        }
    }

    public int xyToCoor(int x, int y) {
        return x + y * widthOfWorld;
    }

    public void userStartPosition() {
        ArrayList<Integer> keys = new ArrayList<>(coorToPosMap.keySet());
        int a = random.nextInt(keys.size());
        int k = keys.get(a);
        Position p = coorToPosMap.get(k); //position of room
        int x = random.nextInt(p.getRoomWidth()) + p.getX();
        int y = random.nextInt(p.getRoomHeight()) + p.getY();
        user = new Position(x, y);
        world[x][y] = Tileset.AVATAR; //initialize user position
    }

    public TETile[][] move(char dir) {
        //if touches wall, break;
        if (dir == 'W' || dir == 'w') {
            if (world[user.getX()][user.getY() + 1] != Tileset.WALL) {
                world[user.getX()][user.getY()] = Tileset.FLOOR;
                user.putY(1);
                world[user.getX()][user.getY()] = Tileset.AVATAR;
            }
//            if (world[user.getX()][user.getY() + 1] != Tileset.WALL &&
//                    world[user.getX()][user.getY() + 1] != Tileset.FLOWER) {
//                world[user.getX()][user.getY()] = Tileset.FLOOR;
//                user.putY(1);
//                world[user.getX()][user.getY()] = Tileset.AVATAR;
//            }
        }
        if (dir == 'S' || dir == 's') {
            if (world[user.getX()][user.getY() - 1] != Tileset.WALL) {
                world[user.getX()][user.getY()] = Tileset.FLOOR;
                user.putY(-1);
                world[user.getX()][user.getY()] = Tileset.AVATAR;
            }
        }
        if (dir == 'A' || dir == 'a') {
            if (world[user.getX() - 1][user.getY()] != Tileset.WALL) {
                world[user.getX()][user.getY()] = Tileset.FLOOR;
                user.putX(-1);
                world[user.getX()][user.getY()] = Tileset.AVATAR;
            }
        }
        if (dir == 'D' || dir == 'd') {
            if (world[user.getX() + 1][user.getY()] != Tileset.WALL) {
                world[user.getX()][user.getY()] = Tileset.FLOOR;
                user.putX(1);
                world[user.getX()][user.getY()] = Tileset.AVATAR;
            }
        }
        return world;
    }
    public TETile[][] replayMove(char dir) {
        //if touches wall, break;
        if (dir == 'W' || dir == 'w') {
            if (world[user.getX()][user.getY() + 1] != Tileset.WALL) {
                world[user.getX()][user.getY()] = Tileset.TRACE;
                user.putY(1);
                world[user.getX()][user.getY()] = Tileset.AVATAR;
            }
        }
        if (dir == 'S' || dir == 's') {
            if (world[user.getX()][user.getY() - 1] != Tileset.WALL) {
                world[user.getX()][user.getY()] = Tileset.TRACE;
                user.putY(-1);
                world[user.getX()][user.getY()] = Tileset.AVATAR;
            }
        }
        if (dir == 'A' || dir == 'a') {
            if (world[user.getX() - 1][user.getY()] != Tileset.WALL) {
                world[user.getX()][user.getY()] = Tileset.TRACE;
                user.putX(-1);
                world[user.getX()][user.getY()] = Tileset.AVATAR;
            }
        }
        if (dir == 'D' || dir == 'd') {
            if (world[user.getX() + 1][user.getY()] != Tileset.WALL) {
                world[user.getX()][user.getY()] = Tileset.TRACE;
                user.putX(1);
                world[user.getX()][user.getY()] = Tileset.AVATAR;
            }
        }
        return world;
    }


}
