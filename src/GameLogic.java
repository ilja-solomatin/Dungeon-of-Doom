import java.io.IOException;

/**
 * Contains the main logic part of the game, as it processes.
 *
 */
public class GameLogic {

    private Map map;
    private HumanPlayer player;
    private int playerGold;
    private String playerCommand;

    /**
     * Default constructor
     */
    public GameLogic() {
        map = new Map();
        player = new HumanPlayer();
        this.playerGold = 0;
    }

    /**
     * Checks if the game is running
     *
     * @return if the game is running.
     */
    protected boolean gameRunning() {
        return false;
    }

    /**
     * Returns the gold required to win.
     *
     * @return : Gold required to win.
     */
    protected String hello() {
        return Integer.toString(map.getGoldRequired());
    }

    /**
     * Returns the gold currently owned by the player.
     *
     * @return : Gold currently owned.
     */
    protected String gold() {
        return Integer.toString(this.playerGold);
    }

    /**
     * Checks if movement is legal and updates player's location on the map.
     *
     * @param direction : The direction of the movement.
     */
    protected void move(char direction) {
        this.map.updatePlayerPos(direction);
    }

    /**
     * Converts the map from a 2D char array to a single string.
     *
     * @return : A String representation of the game map.
     */
    protected String look() {
        for(char[] row : this.map.getMap()){
            for (char i : row) {
                System.out.print(i);
                System.out.print("\t");
            }
            System.out.println();
        }
        return this.map.getMap().toString();
    }

    /**
     * Processes the player's pickup command, updating the map and the player's gold amount.
     *
     */
    protected void pickup() {
        if(this.map.getStandingOn() == 'G'){
            this.playerGold++;
            System.out.println("SUCCESS. " + this.gold());
        }
        else{
            System.out.println("FAIL. " + this.gold());
        }
    }

    /**
     * Quits the game, shutting down the application.
     */
    protected void quitGame() {
        if(this.map.getStandingOn() == 'E' && this.playerGold >= this.map.getGoldRequired()){
            System.out.println("WIN, congratulations!");
            System.exit(0);
        }
        else{
            System.out.println("LOSE.");
            System.exit(0);
        }
    }

    public static void printRow(char[] row){

    }

    public static void main(String[] args){
        GameLogic logic = new GameLogic();
        while(true){
            logic.playerCommand = logic.player.getInputFromConsole();
            if(logic.playerCommand.equals("hello")){
                System.out.println("Gold to win: " + logic.hello());
            }
            else if(logic.playerCommand.equals("gold")){
                System.out.println("Gold owned: " + logic.gold());
            }
            else if(logic.playerCommand.startsWith("move")){
                logic.move(logic.playerCommand.charAt(logic.playerCommand.length() - 1));
            }
            else if(logic.playerCommand.equals("pickup")){
                logic.pickup();
            }
            else if(logic.playerCommand.equals("look")){
                System.out.println(logic.look());
            }
            else if(logic.playerCommand.equals("quit")){
                logic.quitGame();
            }
            else{
                System.out.println("Fail");
            }
        }
    }
}