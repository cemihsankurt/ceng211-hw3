package main.penguin;

public class RoyalPenguin extends Penguin {

    public RoyalPenguin(String id){
        super(id);
    }


    @Override
    public void useUniqueAction() {
        if(singleUseAvailable){
            this.setSingleUseAvailable(false);
        }
    }
}
