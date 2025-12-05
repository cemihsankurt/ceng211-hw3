package main;

import main.enums.*;
import main.hazard.*;
import main.penguin.*;
import main.food.FoodItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TerrainGrid {
    private static final int GRID_SIZE = 10;
    private final List<List<AbstractTerrainObject>> map; 
    private final List<Penguin> penguins = new ArrayList<>();
    private final Random random = new Random();

    public TerrainGrid() {
        this.map = new ArrayList<>();
        initializeEmptyMap();
        
        generatePenguins();
        generateHazards();
        generateFoodItems();
    }

    private void initializeEmptyMap() {
        for (int i = 0; i < GRID_SIZE; i++) {
            List<AbstractTerrainObject> row = new ArrayList<>();
            for (int j = 0; j < GRID_SIZE; j++) {
                row.add(null);
            }
            map.add(row);
        }
    }

    private void generatePenguins() {
        String[] names = {"P1", "P2", "P3"};
        for (String name : names) {
            Penguin p = createRandomPenguin(name);
            penguins.add(p);
            placeItemRandomly(p, true);
        }
    }
    
    private void generateHazards() {
        for (int i = 0; i < 15; i++) {
            Hazard h = createRandomHazard();
            placeItemRandomly(h, false);
        }
    }
    
    private void generateFoodItems() {
        for (int i = 0; i < 20; i++) {
            FoodItem f = new FoodItem();
            placeItemRandomly(f, false);
        }
    }

    private void placeItemRandomly(AbstractTerrainObject item, boolean edgeOnly) {
        boolean placed = false;
        while (!placed) {
            int row = random.nextInt(GRID_SIZE);
            int col = random.nextInt(GRID_SIZE);
            
            boolean isEdge = (row == 0 || row == GRID_SIZE - 1 || col == 0 || col == GRID_SIZE - 1);
            if (edgeOnly && !isEdge) continue;

            AbstractTerrainObject existing = map.get(row).get(col);
            
            boolean collisionConflict = false;
            
            if (existing != null) {
                if (item instanceof Penguin || existing instanceof Penguin) {
                    if (existing instanceof Penguin || existing instanceof Hazard) collisionConflict = true;
                }
                
                if (item instanceof Hazard && existing instanceof FoodItem) collisionConflict = true;
                if (existing instanceof Hazard && item instanceof FoodItem) collisionConflict = true;
                if (item instanceof Hazard && existing instanceof Hazard) collisionConflict = true;
            }

            if (!collisionConflict) {
                map.get(row).set(col, item);
                item.setRow(row);
                item.setCol(col);
                placed = true;
            }
        }
    }

    private Penguin createRandomPenguin(String name) {
        int type = random.nextInt(4);
        switch (type) {
            case 0: return new KingPenguin(name);
            case 1: return new EmperorPenguin(name);
            case 2: return new RoyalPenguin(name);
            default: return new RockhopperPenguin(name);
        }
    }
    
    private Hazard createRandomHazard() {
        int type = random.nextInt(4);
        switch (type) {
            case 0: return new LightIceBlock();
            case 1: return new HeavyIceBlock();
            case 2: return new SeaLion();
            default: return new HoleInIce();
        }
    }

    public void moveObject(AbstractTerrainObject obj, Direction dir) {
        
    }

    public void handleCollision(Penguin p, AbstractTerrainObject target, int nextR, int nextC, Direction dir) {
        
    }
    
    public boolean performSingleStep(Penguin p, int nextR, int nextC) {
        return true; 
    }

    public boolean isValidLocation(int row, int col) {
        return row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE;
    }
    public AbstractTerrainObject getObjectAt(int row, int col) {
        return isValidLocation(row, col) ? map.get(row).get(col) : null;
    }
    public List<Penguin> getPenguins() {
        return penguins;
    }

    public void printMap() {
        String horizontalLine = "-------------------------------------------------";
        System.out.println(horizontalLine);
        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print("|");
            for (int j = 0; j < GRID_SIZE; j++) {
                AbstractTerrainObject item = map.get(i).get(j);
                String content = (item == null) ? " " : item.getStringRepresentation();
                System.out.print(String.format(" %-3s|", content));
            }
            System.out.println();
            System.out.println(horizontalLine);
        }
    }
}