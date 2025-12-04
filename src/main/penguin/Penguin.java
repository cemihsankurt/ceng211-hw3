package main.penguin;

import main.AbstractTerrainObject;
import main.IMovable;
import main.enums.Direction;
import main.food.FoodItem;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Penguin extends AbstractTerrainObject implements IMovable{
    private int turnsLeft = 4;
    private boolean isEliminated = false;
    private boolean isStunned = false; 
    private final List<FoodItem> collectedFood;
    
    protected boolean singleUseAvailable = true; 
    protected int specialStopDistance = -1; 
    protected boolean jumpPrepActivated = false;    

    public Penguin(String id) {
        super(id);
        this.collectedFood = new ArrayList<>();
    }

    public abstract void useUniqueAction();

    @Override
    public void slide(Direction direction) {
        
    }
    
    public void addFood(FoodItem food) {
        collectedFood.add(food);
    }
    
    public int getTotalFoodWeight() {
        return collectedFood.stream().mapToInt(FoodItem::getWeight).sum();
    }
    
    public FoodItem loseLightestFood() {
        if (collectedFood.isEmpty()) return null;
        
        FoodItem lightest = collectedFood.stream()
            .min(Comparator.comparingInt(FoodItem::getWeight))
            .orElse(null);
            
        if (lightest != null) {
            collectedFood.remove(lightest);
        }
        return lightest;
    }
    
    public boolean isEliminated() { 
        return isEliminated; 
    }

    public void setEliminated(boolean eliminated) { 
        this.isEliminated = eliminated; 
    }
    
    public int getTurnsLeft() { 
        return turnsLeft; 
    }
    
    public void decrementTurnsLeft() { 
        this.turnsLeft--; 
    }
    
    public boolean isStunned() { 
        return isStunned; 
    }
    
    public void setStunned(boolean stunned) { 
        isStunned = stunned; 
    }
    
    public boolean isSingleUseAvailable() { 
        return singleUseAvailable; 
    }
    
    public void setSingleUseAvailable(boolean available) { 
        this.singleUseAvailable = available; 
    }
    
    public int getSpecialStopDistance() { 
        return specialStopDistance; 
    }
    
    public void setSpecialStopDistance(int distance){
        this.specialStopDistance = distance;
    }

    public boolean isJumpPrepActivated() { 
        return jumpPrepActivated; 
    }
    
    public void setJumpPrepActivated(boolean activated) { 
        this.jumpPrepActivated = activated; 
    }

    public void setTurnsLeft(int turns) {
        this.turnsLeft = turns;
    }



}
