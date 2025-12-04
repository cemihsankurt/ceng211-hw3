package main.penguin;

public class RockhopperPenguin extends Penguin {

    public RockhopperPenguin(String id){
        super(id);
    }


    @Override
    public void useUniqueAction() {
        if(singleUseAvailable){
            this.setJumpPrepActivated(true);
            System.out.println(getStringRepresentation() + " prepares to jump over one hazard.");
        }
    }
}
