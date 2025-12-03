package main;

import main.enums.Direction;
import main.enums.FoodType;
import main.hazard.*;
import main.penguin.*;

import java.util.ArrayList;
import java.util.Random;

public class TerrainGrid {

    private ArrayList<ArrayList<ITerrainObjects>> map;
    private final int GRID_SIZE = 10;
    private Random random = new Random();

    private ArrayList<Penguin> penguins;

    public TerrainGrid() {
        this.map = new ArrayList<>();
        this.penguins = new ArrayList<>();

        initializeEmtpyMap();
        generatePenguins();
        generateHazards();
        generateFoodItems();
    }

    public void printMap() {
        String horizontalLine = "-------------------------------------------------------------";

        System.out.println(horizontalLine);

        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print("|");

            for (int j = 0; j < GRID_SIZE; j++) {
                ITerrainObjects item = map.get(i).get(j);
                String content;

                if (item == null) {
                    content = "    ";
                } else {
                    content = String.format("%-4s", item.getStringRepresentation());
                }
                System.out.print(" " + content + "|");
            }

            System.out.println();
            System.out.println(horizontalLine);
        }
    }

    public void movePenguin(Penguin p, Direction dir, int maxSteps) {

        int currentRow = -1, currentCol = -1;

        for(int i=0; i<GRID_SIZE; i++) {
            for(int j=0; j<GRID_SIZE; j++) {
                if(map.get(i).get(j) == p) {
                    currentRow = i;
                    currentCol = j;
                    break;
                }
            }
        }

        if (currentRow == -1){
            return;
        }

        System.out.println(p.getStringRepresentation() + " " + "is moving to " + dir);

        int stepsTaken = 0;
        boolean keepMoving = true;

        while (keepMoving) {
            int nextRow = currentRow + dir.dRow;
            int nextCol = currentCol + dir.dCol;

            if (nextRow < 0 || nextRow >= GRID_SIZE || nextCol < 0 || nextCol >= GRID_SIZE) {
                map.get(currentRow).set(currentCol, null);
                p.setEliminated(true);
                System.out.println(p.getStringRepresentation() + "fell into water and eliminated");
                return;
            }

            ITerrainObjects targetCell = map.get(nextRow).get(nextCol);

            if (targetCell == null) {

                map.get(nextRow).set(nextCol, p);
                map.get(currentRow).set(currentCol, null);

                currentRow = nextRow;
                currentCol = nextCol;
                stepsTaken++;

                if (maxSteps != -1 && stepsTaken >= maxSteps) {
                    System.out.println("Unique Action Used, Penguin Stopped.");
                    keepMoving = false;
                }

            } else {
                handleCollision(p, targetCell, nextRow, nextCol);
                keepMoving = false;
            }
        }
    }



    private void initializeEmtpyMap(){
        for(int i = 0; i < GRID_SIZE; i++){
            ArrayList<ITerrainObjects> row = new ArrayList<>();
            for(int j = 0; j < GRID_SIZE; j++){
                row.add(null);
            }
            map.add(row);
        }
    }

    private void generatePenguins(){
        String [] names = {"P1","P2","P3"};

        for(String name : names) {
            Penguin p = createRandomPenguin(name);
            penguins.add(p);

            boolean placed = false;
            while(!placed){
                int row = random.nextInt(GRID_SIZE);
                int col = random.nextInt(GRID_SIZE);

                boolean isEdge = (row == 0 || row == GRID_SIZE - 1 || col == 0 || col == GRID_SIZE - 1);

                if(isEdge && map.get(row).get(col) == null){
                    map.get(row).set(col, p);
                    placed = true;
                }
            }

        }
    }

    private void generateHazards(){
        for(int i = 0; i < 15; i++){
            Hazard h = createRandomHazard();
            placeItemRandomly(h);
        }
    }

    private void generateFoodItems(){
        for(int i = 0; i < 20; i++){
            FoodType type = FoodType.values()[random.nextInt(FoodType.values().length)];
            int weight = random.nextInt(5) + 1;

            FoodItem f = new  FoodItem(type, weight);
            placeItemRandomly(f);
        }
    }

    private void placeItemRandomly(ITerrainObjects item){
        boolean placed = false;

        while(!placed){
            int row =  random.nextInt(GRID_SIZE);
            int col = random.nextInt(GRID_SIZE);

            if(map.get(row).get(col) == null){
                map.get(row).set(col, item);
                placed = true;
            }

        }

    }

    private Penguin createRandomPenguin(String name){
        int type = random.nextInt(4);
        switch (type){
            case 0:
                return new KingPenguin(name);
            case 1:
                return new EmperorPenguin(name);
            case 2:
                return new RoyalPenguin(name);
            default:
                return new RockhopperPenguin(name);
        }
    }

    private Hazard createRandomHazard(){
        int type = random.nextInt(4);
        switch (type){
            case 0:
                return new LightIceBlock();
            case 1:
                return new HeavyIceBlock();
            case 2:
                return new SeaLion();
            default:
                return new HoleInIce();
        }
    }

    private void handleCollision(Penguin p, ITerrainObjects target, int row, int col){

        if(target.getClass().equals(FoodItem.class)){
            FoodItem f = (FoodItem)target;
            p.addFood(f.getWeight());
            System.out.println("Food ate: " + f.getStringRepresentation());
        }
    }




}
