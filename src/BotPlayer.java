import java.util.Random;
/**
 * Runs the game with a bot player and contains code needed to read inputs.
 *
 */
class BotPlayer {
    private Random randNumGenerator =  new Random(); // used to generate a random number
    private int randCommand;
    private char[] randDirection = {'n', 'e', 's', 'w'};

    /**
     * Method to return a command, "look" or "move" with a generated random direction.
     * The chance for the look command is 25%, and for move its 75%.
     *
     * @return : String "look" or "move" with a given direction.
     */
    String getCommand(){
        randCommand = randNumGenerator.nextInt(4) + 1; //random number from 1 and 4 inclusive.
        if(randCommand == 1){
            return "look";
        }
        else{
            return ("move " + randDirection[randCommand - 1]);
        }
    }
}