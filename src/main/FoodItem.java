package main;

import main.enums.FoodType;

public class FoodItem implements ITerrainObjects{

    private FoodType foodType;
    private int weight;

    public FoodItem(FoodType foodType, int weight) {
        this.foodType = foodType;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String getStringRepresentation() {
        return foodType.getShortName();
    }
}
