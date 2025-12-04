package main.food;

import java.util.Random;
import main.AbstractTerrainObject;
import main.enums.FoodType;


public class FoodItem  extends AbstractTerrainObject{

    private FoodType foodType;
    private int weight;

    public FoodItem() {
        Random rand = new Random();
        FoodType[] types = FoodType.values();
        FoodType chosenType = types[rand.nextInt(types.length)];
        
        this.weight = rand.nextInt(5) + 1; 
        super(chosenType.getShortName()); 
        this.foodType = chosenType;
    }
    

    public int getWeight() {
        return weight;
    }

    public FoodType getFoodType(){
        return foodType;
    }

    @Override
    public String getStringRepresentation() {
        return foodType.getShortName();
    }
}
