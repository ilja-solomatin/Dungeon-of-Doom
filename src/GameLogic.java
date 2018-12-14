import java.io.IOException;
import java.util.Scanner;

/**
 * Contains the main logic part of the game, as it processes.
 */
public class GameLogic {
    //Declaring objects for the game
    private Map map;
    private HumanPlayer player;
    private BotPlayer bot;

    private int playerGold;
    private String playerCommand;

    /**
     * Default constructor
     */
    private GameLogic() {
        map = new Map();
        player = new HumanPlayer();
        bot = new BotPlayer();
        this.playerGold = 0;
    }

    /**
     * Overloaded constructor with a file name.
     *
     * @param fileName : the name of the file to be read
     * @throws IOException : possible error when reading from a file
     */
    private GameLogic(String fileName) throws IOException{
        map = new Map(fileName);
        player = new HumanPlayer();
        bot = new BotPlayer();
        this.playerGold = 0;
    }

    /**
     * Returns the gold required to win.
     *
     * @return : Gold required to win.
     */
    private String hello() {
        return Integer.toString(map.getGoldRequired());
    }

    /**
     * Returns the gold currently owned by the player.
     *
     * @return : Gold currently owned.
     */
    private String gold() {
        return Integer.toString(this.playerGold);
    }

    /**
     * Checks if movement is legal and updates player's location on the map.
     *
     * @param direction : The direction of the movement.
     * @param player : Char representing the player who is attempting to move.
     */
    private void move(char direction, char player){
        if(player == 'P') {
            this.map.updatePlayerPos(direction);
        }
        else{
            this.map.updateBotPos(direction);
        }
    }

    /**
     * Method to display a 5x5 grid of the map around the player.
     *
     * @param player : The player using the look command, to display the grid around them.
     */
    private void look(char player) {
        int playerRow;
        int playerColumn;

        if(player == 'P') {
            playerRow = this.map.getPlayerPosY(); // The row the player is standing in
            playerColumn = this.map.getPlayerPosX(); // The column the player is standing in
        }
        else{
            playerRow = this.map.getBotPosY();
            playerColumn = this.map.getBotPosX();
        }

        char[][] map = this.map.getMap();
        for(int row = playerRow - 2; row <= playerRow + 2; row++){ // From 2 rows above the player, to 2 rows below the player
            for(int column = playerColumn - 2; column <= playerColumn + 2; column++){ // 2 columns above player to 2 columns below
                if(row >= 0 && row < map.length && column >= 0 && column < map[0].length){ // If the row and columns are within bounds of map
                    System.out.print(map[row][column]);
                    System.out.print("\t");
                }
            }
            System.out.println();
        }
    }

    /**
     * Processes the player's pickup command, updating the map and the player's gold amount.
     */
    private void pickup() {
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
    private void quitGame() {
        if(this.map.getStandingOn('P') == 'E' && this.playerGold >= this.map.getGoldRequired()){
            System.out.println("WIN, congratulations!");
            System.exit(0);
        }
        else{
            System.out.println("LOSE.");
            System.exit(0);
        }
    }

    /**
     * Check if the player has been caught by the bot.
     *
     * @return : boolean value indicating if the bot is standing on the players location.
     */
    private boolean playerIsCaught(){
        if(this.map.getStandingOn('P') == 'B' || this.map.getStandingOn('B') == 'P'){
            return true;
        }
        else{
            return false;
        }
    }

    public static void main(String[] args) {
        GameLogic logic;
        //Prompt to load a file
        System.out.println("Would you like to load a map? (Y/N)");
        Scanner reader = new Scanner(System.in);
        if(reader.nextLine().toUpperCase().equals("Y")) {
            System.out.println("Enter the file name: ");
            String fileName = reader.nextLine();
            try{
                // if a file has been supplied, attempt to call the constructor with the file name.
                logic = new GameLogic(fileName);
            }
            catch(IOException e){
                System.out.println("Error opening file, using default map.");
                logic = new GameLogic();
            }
        }
        else{
            System.out.println("Using default map.");
            logic = new GameLogic();
        }
        System.out.println("Currently loaded map: " + logic.map.getMapName());

        // Take turns indefinitely, or until the player is caught, or the player wins the game
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

                //Check if the player has been caught after making a move
                if(logic.playerIsCaught()){
                    System.out.println("You have been caught by the bot!");
                    logic.quitGame();
                }
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

            // Performs the bot turn, which will only be moving or looking.
            System.out.println("\nBot's turn.");
            logic.playerCommand = logic.bot.getCommand();
            if(logic.playerCommand.startsWith("move")){
                logic.move(logic.playerCommand.charAt(logic.playerCommand.length() - 1), 'B');
                if(logic.playerIsCaught()){
                    System.out.println("You have been caught by the bot!");
                    logic.quitGame();
                }
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