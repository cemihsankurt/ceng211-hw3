package main.hazard;

import main.ITerrainObjects;

public abstract class Hazard implements IHazards, ITerrainObjects {

    private String id;

    public Hazard(String id){
        this.id = id;
    }

    @Override
    public String getStringRepresentation(){
        return this.id;
    }
}
