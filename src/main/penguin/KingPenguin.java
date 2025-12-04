package main.penguin;

public class KingPenguin extends Penguin {

    public KingPenguin(String id){
        super(id);
    }


    @Override
    public void useUniqueAction() {
        if(singleUseAvailable){
            this.specialStopDistance = 5;
            this.setSingleUseAvailable(false);
            System.out.println(getStringRepresentation() + " chooses to stop at the fifth square.");
        }
    }
}
