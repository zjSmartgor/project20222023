package byow.Core;

public class Position {
    private int x;
    private int y;
    private int coor;
    private int widthOfWorld;
    private int heightOfWorld;
    private int widthOfRoom;
    private int heightOfRoom;


    public Position(int x, int y, int widthR, int heightR, int widthOfWorld, int heightOfWorld) {
        this.widthOfWorld = widthOfWorld;
        this.heightOfWorld = heightOfWorld;
        this.x = x;
        this.y = y;
        this.coor = x + widthOfWorld * y;
        this.widthOfRoom = widthR;
        this.heightOfRoom = heightR;

    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {return this.x;}
    public int getY() {return this.y;}

    public void putX(int move) {this.x += move;}
    public void putY(int move) {this.y += move;}
    public int getCoor() {return this.coor;}
    public int getRoomWidth() {return widthOfRoom;}
    public int getRoomHeight() {return heightOfRoom;}





}
