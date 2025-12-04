package main.enums;

public enum Direction {
    UP(-1,0),
    DOWN(1,0),
    LEFT(0,-1),
    RIGHT(0,1);

    public final int dRow;
    public final int dCol;

    Direction(int dRow, int dCol) {
        this.dRow = dRow;
        this.dCol = dCol;
    }

    public Direction getOpposite() {
        if (this == UP){
            return DOWN;
        }
        if (this == DOWN){ 
            return UP;
        }    
        if (this == LEFT){
            return RIGHT;
        } else{
        return LEFT;
        }
    }
}
