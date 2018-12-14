import java.util.Scanner;
/**
 * Runs the game with a human player and contains code needed to read inputs.
 *
 */
class HumanPlayer {

    /**
     * Reads player's input from the console.
     *
     * return : A string containing the input the player entered.
     */
    String getInputFromConsole(){
        Scanner reader = new Scanner(System.in);
        return reader.nextLine();
    }
}