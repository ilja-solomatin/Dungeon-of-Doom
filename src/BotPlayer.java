import java.util.Random;

public class BotPlayer {
    Random randNumGenerator =  new Random();
    private int randCommand;
    private char[] randDirection = {'n', 'e', 's', 'w'};

    public String getCommand(){
        randCommand = randNumGenerator.nextInt(4) + 1; //random number between 4 and 1.
        if(randCommand == 1){
            return "look";
        }
        else{
            return ("move " + randDirection[randCommand - 1]);
        }
    }
}