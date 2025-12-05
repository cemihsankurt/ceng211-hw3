package main;

import main.enums.Direction;
import main.penguin.Penguin;
import main.penguin.RoyalPenguin;
import main.penguin.RockhopperPenguin;
import main.hazard.HoleInIce;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class IcyTerrain {
    private final TerrainGrid terrainGrid;
    private final Scanner scanner;
    private Penguin playerPenguin;
    private final List<Penguin> allPenguins;
    private int currentTurn = 1;
    private static final int MAX_TURNS = 4;
    private final Random random = new Random();
    
    public IcyTerrain() {
        this.terrainGrid = new TerrainGrid();
        this.scanner = new Scanner(System.in);
        this.allPenguins = terrainGrid.getPenguins();
        
        this.playerPenguin = allPenguins.get(random.nextInt(allPenguins.size()));
        
        // Örnek Çıktı Başlangıcı
        System.out.println("Welcome to Sliding Penguins Puzzle Game App. An 10x10 icy terrain grid is being generated.");
        System.out.println("Penguins, Hazards, and Food items are also being generated. The initial icy terrain grid:");
        
        terrainGrid.printMap(); 
        printPenguinStatuses();
        
        startGameLoop();
    }
    
    private void printPenguinStatuses() {
        System.out.println("\nThese are the penguins on the icy terrain:");
        for (Penguin p : allPenguins) {
            String type = p.getClass().getSimpleName();
            String playerStatus = (p == playerPenguin) ? " ---> YOUR PENGUIN" : "";
            // P1, P2, P3 yazdırılırken P1'den 1'i almak için substring kullanılır.
            System.out.printf("- Penguin %s (%s): %s%s\n", 
                p.getStringRepresentation().substring(1), 
                p.getStringRepresentation(), 
                type, 
                playerStatus);
        }
    }

    private void startGameLoop() {
        
        while (currentTurn <= MAX_TURNS) {
            System.out.println("\n*** Turn " + currentTurn);
            
            for (Penguin penguin : allPenguins) {
                if (penguin.isEliminated() || penguin.getTurnsLeft() == 0) continue; 
                
                if (penguin.isStunned()) {
                    System.out.println(penguin.getStringRepresentation() + " is stunned and skips the turn.");
                    penguin.setStunned(false); 
                    continue;
                }

                System.out.println("\n--- " + penguin.getStringRepresentation() + (penguin == playerPenguin ? " (Your Penguin)" : ""));
                
                if (penguin == playerPenguin) {
                    handlePlayerTurn(penguin);
                } else {
                    handleAITurn(penguin);
                }
                
                penguin.decrementTurnsLeft();
                
            }
            currentTurn++;
        }
        endGame();
    }
    
    private void handlePlayerTurn(Penguin penguin) {
        Direction direction;

        if (penguin.isSingleUseAvailable()) {
            String prompt = "Will " + penguin.getStringRepresentation() + " use its special action? Answer with Y or N -->";
            String actionChoice = getYNInput(prompt);
            
            if (actionChoice.equalsIgnoreCase("Y")) {
                 penguin.useUniqueAction(); 
                 
                 if (penguin instanceof RoyalPenguin) {
                     
                 }
            }
        }

        String promptDir = "Which direction will " + penguin.getStringRepresentation() + " move? Answer with U (Up), D (Down), L (Left), R (Right) -->";
        direction = getDirectionInput(promptDir);
        
        terrainGrid.moveObject(penguin, direction);
    }
    
    private void handleAITurn(Penguin penguin) {
        Direction chosenDir;

        if (penguin.isSingleUseAvailable() && !(penguin instanceof RockhopperPenguin) && random.nextDouble() < 0.30) {
            penguin.useUniqueAction();
            
            if (penguin instanceof RoyalPenguin) {
                
            }
        }

        chosenDir = determineAIDirection(penguin); 
        
        if (penguin instanceof RockhopperPenguin && penguin.isSingleUseAvailable()) {
            if (isDirectionTowardHazard(penguin, chosenDir, HoleInIce.class)) {
                penguin.useUniqueAction(); 
                penguin.setSingleUseAvailable(false);
            }
        }

        terrainGrid.moveObject(penguin, chosenDir);
    }

    // Y/N tekrar sorma mantığı
    private String getYNInput(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt + " ");
            input = scanner.nextLine().trim().toUpperCase();
            
            if (input.equals("Y") || input.equals("N")) {
                return input;
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        }
    }
    
    // Yön tekrar sorma mantığı
    private Direction getDirectionInput(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt + " ");
            input = scanner.nextLine().trim().toUpperCase();
            
            switch (input) {
                case "U": return Direction.UP;
                case "D": return Direction.DOWN;
                case "L": return Direction.LEFT;
                case "R": return Direction.RIGHT;
                default: 
                    System.out.println("Invalid input. Please enter U, D, L, or R.");
            }
        }
    }
    
    private Direction determineAIDirection(Penguin p) {
        List<Direction> possibleDirs = new ArrayList<>();
        List<Direction> safeToFoodDirs = new ArrayList<>();
        List<Direction> safeToHazardDirs = new ArrayList<>();
        
        for (Direction dir : Direction.values()) {
            if (isSafeAndLeadsToFood(p, dir)) {
                safeToFoodDirs.add(dir);
            } else if (isSafeAndLeadsToHazard(p, dir)) {
                
            }
            possibleDirs.add(dir);
        }

        if (!safeToFoodDirs.isEmpty()) {
            return safeToFoodDirs.get(random.nextInt(safeToFoodDirs.size()));
        }

        if (!safeToHazardDirs.isEmpty()) {
            return safeToHazardDirs.get(random.nextInt(safeToHazardDirs.size()));
        }

        return Direction.values()[random.nextInt(Direction.values().length)];
    }

    private boolean isSafeAndLeadsToFood(Penguin p, Direction dir) {
        return false;
    }
    private boolean isSafeAndLeadsToHazard(Penguin p, Direction dir) {
        return false;
    }
    private boolean isDirectionTowardHazard(Penguin p, Direction dir, Class<?> excludeHazard) {
        return false;
    }
    
    private void endGame() {
        System.out.println("\n***** GAME OVER *****");
        List<Penguin> leaderboard = new ArrayList<>(allPenguins);
        leaderboard.sort(Comparator.comparingInt(Penguin::getTotalFoodWeight).reversed());
        
        for (int i = 0; i < leaderboard.size(); i++) {
            Penguin p = leaderboard.get(i);
            String position = (i == 0) ? "1st" : (i == 1) ? "2nd" : "3rd";
            System.out.printf("%s place: %s (%s)\n", position, p.getStringRepresentation(), (p == playerPenguin ? "Your Penguin" : p.getClass().getSimpleName()));
            System.out.printf("--> Total weight: %d units\n", p.getTotalFoodWeight());
        }
    }
}