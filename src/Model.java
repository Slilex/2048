

import java.util.ArrayList;
import java.util.List;

public class Model {

    private static final int        FIELD_WIDTH     = 4;
    private Tile [][]               gameTiles       = new Tile[FIELD_WIDTH][FIELD_WIDTH];
    protected int                   score;
    protected int                   maxTile;

    public Model() {
        score           =   0;
        maxTile         =   0;
        resetGameTiles();
        addTile();

    }

    protected void resetGameTiles() {
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles[i].length; j++) {
                gameTiles[i][j] = new Tile();
            }
        }
        addTile();
        addTile();
    }

    private void addTile(){
        List<Tile> tileList = getEmptyTiles();
        if(tileList.isEmpty()) return;
       int n = (int)( tileList.size() * Math.random());
        tileList.get(n).value = Math.random() < 0.9 ? 2 : 4;
    }

    public void left(){
        if(rotation()) addTile();

    }

    public void up(){
        turn();
        turn();
        turn();
        if(rotation()) addTile();
        turn();
    }

    public void right(){
        turn();
        turn();
        if(rotation()) addTile();
        turn();
        turn();

    }

    public void down(){
        turn();
        if(rotation()) addTile();
        turn();
        turn();
        turn();
    }

    private void turn() {

        /*Поворачиваем массив против часовой стрелки */
        Tile[][] turnArg = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles.length; j++) {
                turnArg[i][FIELD_WIDTH - 1 - j] = gameTiles[j][i];
            }
        }
        gameTiles = turnArg;
    }

    private List<Tile> getEmptyTiles(){
        List <Tile> list = new ArrayList<>(FIELD_WIDTH*FIELD_WIDTH);
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles[i].length; j++) {
                if(gameTiles[i][j].value == 0){
                    list.add(gameTiles[i][j]);
                }
            }
        }
        return list;
    }

    private boolean rotation() {
        boolean change = false;
        for (int i = 0; i < gameTiles.length; i++) {
            boolean mer = mergeTiles(gameTiles[i]);
            boolean com = compressTiles(gameTiles[i]);
            if (mer || com) {
                change = true;
            }
        }
        return change;
    }

    private boolean compressTiles(Tile[] tiles){
        boolean edit = false;

        for (int i = tiles.length-1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (tiles[j].value == 0) {
                    int a = tiles[j].value;
                    tiles[j].value = tiles[j + 1].value;
                    if(tiles[j+1].value!=0){
                        edit = true;
                    }
                    tiles[j + 1].value = a;
                }
            }
        }
        return edit;

    }

    private boolean mergeTiles(Tile[] tiles){
        boolean edit = false;
        for (int i = 0; i < tiles.length-1; i++) {
            if((tiles[i].value == tiles[i+1].value)&&(tiles[i].value!=0)){
                edit = true;
                tiles[i].value = tiles[i].value*2;
                tiles[i+1].value = 0;
                score = score + tiles[i].value;
                if(tiles[i].value>maxTile){
                    maxTile = tiles[i].value;
                }
            }
        }
        return edit;
    }

    public boolean canMove(){
        boolean change = false;
        for (int i = 0; i < gameTiles.length ; i++) {
            for (int j = 0; j < gameTiles.length-1; j++) {
                if( (gameTiles[i][j].value == gameTiles[i][j+1].value) || (gameTiles[i][j].value == 0)){
                    return true;
                }
                if(gameTiles[j][i].value == gameTiles[j+1][i].value){
                    return true;
                }

            }
        }

        return change;

    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }
}
