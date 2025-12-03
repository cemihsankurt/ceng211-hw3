package main.penguin;

import main.ITerrainObjects;

public abstract class Penguin implements ITerrainObjects{

    private String id;
    private int totalFoodWeight;
    private boolean isEliminated;

    public Penguin(String id){
        this.id = id;
        this.totalFoodWeight = 0;
        this.isEliminated = false;
    }

    public abstract void useUniqueAction();

    public void slide(String direction){
        if(isEliminated){
            return;
        }

        System.out.println(id + " " + "is sliding to " + direction);
    }

    public void addFood(int weight){
        totalFoodWeight += weight;
    }

    @Override
    public String getStringRepresentation() {
        return this.id;
    }

    public int getTotalFoodWeight() { return totalFoodWeight; }
    public boolean isEliminated() { return isEliminated; }
    public void setEliminated(boolean eliminated) { isEliminated = eliminated; }

}
