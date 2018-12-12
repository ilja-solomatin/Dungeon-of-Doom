import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Contains the main logic part of the game, as it processes.
 *
 */
public class GameLogic {

    private Map map;
    private HumanPlayer player;
    private BotPlayer bot;
    private int playerGold;
    private String playerCommand;

    /**
     * Default constructor
     */
    public GameLogic() {
        map = new Map();
        player = new HumanPlayer();
        bot = new BotPlayer();
        this.playerGold = 0;
    }

    public GameLogic(String fileName) throws IOException{
        map = new Map(fileName);
        player = new HumanPlayer();
        bot = new BotPlayer();
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
    protected void move(char direction, char player){
        if(player == 'P') {
            this.map.updatePlayerPos(direction);
        }
        else{
            this.map.updateBotPos(direction);
        }
    }

    /**
     * Converts the map from a 2D char array to a single string.
     *
     * @return : A String representation of the game map.
     */
    protected void look(char player) {
        int playerRow;
        int playerColumn;

        if(player == 'P') {
            playerRow = this.map.getPlayerPosY();
            playerColumn = this.map.getPlayerPosX();
        }
        else{
            playerRow = this.map.getBotPosY();
            playerColumn = this.map.getBotPosX();
        }

        char[][] map = this.map.getMap();
        for(int row = playerRow - 2; row <= playerRow + 2; row++){
            for(int column = playerColumn - 2; column <= playerColumn + 2; column++){
                if(row >= 0 && row < map.length && column >= 0 && column < map[0].length){
                    System.out.print(map[row][column]);
                    System.out.print("\t");
                }
            }
            System.out.println();
        }
    }

    /**
     * Processes the player's pickup command, updating the map and the player's gold amount.
     *
     */
    protected void pickup() {
        if(this.map.getStandingOn('P') == 'G'){
            this.playerGold++;
            this.map.setStandingOn('.', 'P');
            System.out.println("SUCCESS. Gold owned: " + this.gold());
        }
        else{
            System.out.println("FAIL. Gold owned: " + this.gold());
        }
    }

    /**
     * Quits the game, shutting down the application.
     */
    protected void quitGame() {
        if(this.map.getStandingOn('P') == 'E' && this.playerGold >= this.map.getGoldRequired()){
            System.out.println("WIN, congratulations!");
            System.exit(0);
        }
        else{
            System.out.println("LOSE.");
            System.exit(0);
        }
    }

    protected boolean playerIsCaught(){
        if(this.map.getStandingOn('P') == 'B' || this.map.getStandingOn('B') == 'P'){
            return true;
        }
        else{
            return false;
        }
    }

    public static void main(String[] args) {
        GameLogic logic;
        System.out.println("Would you like to load a map? (Y/N)");
        Scanner reader = new Scanner(System.in);
        if(reader.nextLine().toUpperCase().equals("Y")) {
            System.out.println("Enter the file name: ");
            String fileName = reader.nextLine();
            try{
                logic = new GameLogic(fileName);
            }
            catch(Exception e){
                System.out.println("Error opening file, using default map.");
                logic = new GameLogic();
            }
        }
        else{
            System.out.println("Using default map.");
            logic = new GameLogic();
        }
        System.out.println("Currently loaded map: " + logic.map.getMapName());
        while(true){
            System.out.println("\nYour turn.");
            logic.playerCommand = logic.player.getInputFromConsole();
            if(logic.playerCommand.equals("hello")){
                System.out.println("Gold to win: " + logic.hello());
            }
            else if(logic.playerCommand.equals("gold")){
                System.out.println("Gold owned: " + logic.gold());
            }
            else if(logic.playerCommand.startsWith("move")){
                logic.move(logic.playerCommand.charAt(logic.playerCommand.length() - 1), 'P');
            }
            else if(logic.playerCommand.equals("pickup")){
                logic.pickup();
            }
            else if(logic.playerCommand.equals("look")){
                logic.look('P');
            }
            else if(logic.playerCommand.equals("quit")){
                logic.quitGame();
            }
            else{
                System.out.println("Fail");
            }

            if(logic.playerIsCaught()){
                System.out.println("You have been caught by the bot!");
                logic.quitGame();
            }

            System.out.println("\nBot's turn.");
            logic.playerCommand = logic.bot.getCommand();
            if(logic.playerCommand.startsWith("move")){
                logic.move(logic.playerCommand.charAt(logic.playerCommand.length() - 1), 'B');
            }
            else{
                logic.look('B');
            }

            if(logic.playerIsCaught()){
                System.out.println("You have been caught by the bot!");
                logic.quitGame();
            }
        }
    }
}