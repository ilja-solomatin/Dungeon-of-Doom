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
     * If the bot can see the player, then the direction to move will not be random
     * It will be determined by comparing the positions of both the player and bot.
     *
     * @param playerX : Players X coordinate
     * @param playerY  : Players Y coordinate
     * @param botX : Bots X coordinate
     * @param botY : Bots Y coordinate
     *
     * @return : String "look" or "move" with a given direction.
     */
    String getCommand(boolean canSeePlayer, int playerX, int playerY, int botX, int botY){
        if(canSeePlayer){
            if(botY > playerY){
                return "move n";
            }
            else if(botY < playerY){
                return "move s";
            }
            else if(botX > playerX){
                return "move w";
            }
            else if(botX < botY){
                return "move e";
            }
        }
        else{ // else cant see player, so move randomly
            randCommand = randNumGenerator.nextInt(4) + 1; //random number from 1 and 4 inclusive.
            if(randCommand == 1){
                return "look";
            }
            else{
                return ("move " + randDirection[randCommand - 1]);
            }
        }
        return "move";
    }
}