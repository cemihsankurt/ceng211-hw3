package main.penguin;

public class EmperorPenguin extends Penguin{

    public EmperorPenguin(String id){
        super(id);
    }


    @Override
    public void useUniqueAction() {
        if(singleUseAvailable){
            this.specialStopDistance = 3;
            this.setSingleUseAvailable(false);
            System.out.println(getStringRepresentation() + " chooses to stop at the third square.");
        }
    }
}
