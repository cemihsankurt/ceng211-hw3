package main.hazard;

import main.AbstractTerrainObject;
import main.IMovable;
import main.enums.Direction;

public abstract class Hazard extends AbstractTerrainObject implements IHazards, IMovable {

    public Hazard(String id){
        super(id);
    }

    @Override
    public void slide(Direction direction){
        
    }
}
