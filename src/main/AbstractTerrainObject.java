package main;

public abstract class AbstractTerrainObject implements ITerrainObjects {
    private int row;
    private int col;
    protected final String id;

    public AbstractTerrainObject(String id) {
        this.id = id;
    }

    @Override
    public int getRow(){ 
        return row; 
    }
    
    @Override
    public int getCol(){ 
        return col; 
    }
    
    @Override
    public void setRow(int row){ 
        this.row = row; 
    }
    
    @Override
    public void setCol(int col){ 
        this.col = col; 
    }

    @Override
    public String getStringRepresentation(){
        return id;
    }
}
