import java.util.Arrays;
import java.util.Random;
import java.io.*;

/**
 * Reads and contains in memory the map of the game.
 */
class Map {

    /* Representation of the map */
    private char[][] map;

    /* Map name */
    private String mapName;

    /* Gold required for the human player to win */
    private int goldRequired;

    /* X and Y coordinates for both the player and the bot */
    private int playerPosX;
    private int playerPosY;
    private int botPosX;
    private int botPosY;

    /* Stores the character the players will be standing on, e.g 'G' for gold, or 'E' for exit */
    private char playerStandingOn;
    private char botStandingOn;

    /**
     * Default constructor, creates the default map "Very small Labyrinth of doom".
     */
    Map() {
        this.mapName = "Very small Labyrinth of Doom";
        this.goldRequired = 2;
        this.map = new char[][]{
                {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','.','.','.','.','.','.','G','.','.','.','.','.','.','.','.','.','E','.','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','.','.','E','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','G','.','.','.','.','.','.','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
        };
        // Calls methods to generate a random starting location for the player, followed by the bot
        this.placePlayer();
        this.placeBot();
    }

    /**
     * Method which will generate random indexes for the 2D array, and will place the player at that location
     */
    private void placePlayer(){
        Random randNumGenerator =  new Random();
        boolean canPlace = false; // a flag value to ensure that the generate coordinates are valid to spawn a player there
        int mapRows = this.map.length; // height of map
        int mapColumns = this.map[0].length; // width of map

        while(!canPlace){
            //Since mapRows is the total number of rows, we subtract 1 to have the last addressable element
            //We then subtract another 1 since we cannot let the player spawn on a wall, which is at the
            //last addressable element. This gives mapRows - 2. We add 1 which is the minimum value we want
            //to generate - excludes the top wall row (index 0). Finally we add 1 which again, is the minimum
            //value we want to generate.
            int randRow = randNumGenerator.nextInt((mapRows - 2 - 1) + 1) + 1;
            int randColumn = randNumGenerator.nextInt((mapColumns - 2 - 1) + 1) + 1;
            // if the generated coordinates are not the gold, or wall, then..
            if(this.map[randRow][randColumn] != 'G' && this.map[randRow][randColumn] != '#'){
                canPlace = true;
                this.setStandingOn(randColumn, randRow, 'P'); // set the player to stand at the generated coordinates
                this.map[randRow][randColumn] = 'P'; // write the player symbol on the map at the generated coordinates
                this.playerPosX = randColumn; // update values which store the players current coordinates
                this.playerPosY = randRow;
            }
        }
    }

    /**
     * Method which is almost identical to the one above (for the player), but will ensure the bot doesn't spawn
     * on top of the player.
     */
    private void placeBot(){
        Random randNumGenerator =  new Random();
        boolean canPlace = false;
        int mapRows = this.map.length;
        int mapColumns = this.map[0].length;

        while(!canPlace){
            int randRow = randNumGenerator.nextInt((mapRows - 2 - 1) + 1) + 1;
            int randColumn = randNumGenerator.nextInt((mapColumns - 2 - 1) + 1) + 1;
            if(this.map[randRow][randColumn] != 'G' && this.map[randRow][randColumn] != 'P' && this.map[randRow][randColumn] != '#'){
                canPlace = true;
                this.setStandingOn(randColumn, randRow, 'B');
                this.map[randRow][randColumn] = 'B';
                this.botPosX = randColumn;
                this.botPosY = randRow;
            }
        }
    }

    /**
     * Method which will update the player, or bots position on the map, given coordinates,
     * storing them in a variable.
     *
     * @param currentX : Stores the players current X coordinate.
     * @param currentY : Stores the players current Y coordinate
     * @param player : symbol which identifies the player which will have their position set
     *               e.g 'P' or 'B', for the player or bot respectively.
     */
    private void setStandingOn(int currentX, int currentY, char player){
        if(player == 'P') {
            this.playerStandingOn = this.map[currentY][currentX];
        }
        else{
            this.botStandingOn = this.map[currentY][currentX];
        }
    }

    /**
     * Method similar to the one above, but with one less parameter.
     * This method will set the players standing on symbol to the one supplied.
     *
     * @param symbol : char which the player is standing on
     * @param player : char to represent which players' standing on symbol to update, e.g 'P' or 'B'
     */
    void setStandingOn(char symbol, char player){
        if(player == 'P') {
            this.playerStandingOn = symbol;
        }
        else{
            this.botStandingOn = symbol;
        }
    }

    /**
     * Method which will get the symbol that the player is standing on.
     *
     * @param player : char representing which players standing on symbol to return, e.g 'P' or 'B'
     *
     * @return : the symbol that the player is standing on
     */
    char getStandingOn(char player){
        if(player == 'P') {
            return this.playerStandingOn;
        }
        else{
            return this.botStandingOn;
        }
    }

    /**
     * Method which will set the players X and Y coordinates
     *
     * @param newX : the X value to be updated to
     * @param newY : the Y value to be updated to
     */
    private void setPlayerPos(int newX, int newY){
        this.playerPosX = newX;
        this.playerPosY = newY;
        this.map[this.playerPosY][this.playerPosX] = 'P';
    }

    /**
     * Method to get the players X position
     * @return : the players X position
     */
    int getPlayerPosX(){
        return this.playerPosX;
    }

    /**
     * Method to get the players Y position
     * @return
     */
    int getPlayerPosY(){
        return this.playerPosY;
    }

    /**
     * Method to update the players position when supplied a direction to move towards.
     *
     * @param direction : char representing direction to move, e.g 'n' for north, etc.
     */
    void updatePlayerPos(char direction){
        /*
         * In each case, if the position the player wishes to move into is not a wall then:
         * The maps coordinates where the player is still standing, is set back to the original symbol.
         * For example, if the player was standing on 'G', but decided to move away from it, the symbol 'P' would
         * need to be set back to 'G'.
         *
         * Next, store the symbol that the player is about to move to, as it will be overwritten.
         * Then, update the players new position on the map, followed by a "success" or "fail" message.
         */
        switch(direction){
            case 'n':
                if(this.map[this.playerPosY - 1][this.playerPosX] != '#'){
                    this.map[this.playerPosY][this.playerPosX] = this.getStandingOn('P');
                    this.setStandingOn(this.playerPosX, this.playerPosY - 1, 'P');
                    setPlayerPos(this.playerPosX, this.playerPosY - 1);
                    System.out.println("Success");
                }
                else{
                    System.out.println("Fail");
                }
                break;
            case 'e':
                if(this.map[this.playerPosY][this.playerPosX + 1] != '#'){
                    this.map[this.playerPosY][this.playerPosX] = this.getStandingOn('P');
                    this.setStandingOn(this.playerPosX + 1, this.playerPosY, 'P');
                    setPlayerPos(this.playerPosX + 1, this.playerPosY);
                    System.out.println("Success");
                }
                else{
                    System.out.println("Fail");
                }
                break;
            case 's':
                if(this.map[this.playerPosY + 1][this.playerPosX] != '#'){
                    this.map[this.playerPosY][this.playerPosX] = this.getStandingOn('P');
                    this.setStandingOn(this.playerPosX, this.playerPosY + 1, 'P');
                    setPlayerPos(this.playerPosX, this.playerPosY + 1);
                    System.out.println("Success");
                }
                else{
                    System.out.println("Fail");
                }
                break;
            case 'w':
                if(this.map[this.playerPosY][this.playerPosX - 1] != '#'){
                    this.map[this.playerPosY][this.playerPosX] = this.getStandingOn('P');
                    this.setStandingOn(this.playerPosX - 1, this.playerPosY, 'P');
                    setPlayerPos(this.playerPosX - 1, this.playerPosY);
                    System.out.println("Success");
                }
                else{
                    System.out.println("Fail");
                }
                break;
            default:
                // If a non existent direction is supplied, e.g one that isn't 'n', 'e', 's', or 'w', print a fail message.
                System.out.println("Fail");
        }
    }

    /**
     * Method to set the bots X and Y coordinates.
     *
     * @param newX : the X value to be updated to
     * @param newY : the Y value to be updated to
     */
    private void setBotPos(int newX, int newY){
        this.botPosX = newX;
        this.botPosY = newY;
        this.map[this.botPosY][this.botPosX] = 'B';
    }

    /**
     * Method to get the bots X value.
     *
     * @return : integer which is the bots X position
     */
    int getBotPosX(){
        return this.botPosX;
    }

    /**
     * Method to get the bots Y value.
     *
     * @return : integer which is the bots Y position
     */
    int getBotPosY(){
        return this.botPosY;
    }

    /**
     * Method to update the bots position on the map, similar to the one above to update the players position.
     *
     * @param direction : char representing the direction to move to, e.g 'n' for north
     */
    void updateBotPos(char direction){
        switch(direction){
            case 'n':
                if(this.map[this.botPosY - 1][this.botPosX] != '#'){
                    this.map[this.botPosY][this.botPosX] = this.getStandingOn('B');
                    this.setStandingOn(this.botPosX, this.botPosY - 1, 'B');
                    setBotPos(this.botPosX, this.botPosY - 1);
                    System.out.println("Success");
                }
                else{
                    System.out.println("Fail");
                }
                break;
            case 'e':
                if(this.map[this.botPosY][this.botPosX + 1] != '#'){
                    this.map[this.botPosY][this.botPosX] = this.getStandingOn('B');
                    this.setStandingOn(this.botPosX + 1, this.botPosY, 'B');
                    setBotPos(this.botPosX + 1, this.botPosY);
                    System.out.println("Success");
                }
                else{
                    System.out.println("Fail");
                }
                break;
            case 's':
                if(this.map[this.botPosY + 1][this.botPosX] != '#'){
                    this.map[this.botPosY][this.botPosX] = this.getStandingOn('B');
                    this.setStandingOn(this.botPosX, this.botPosY + 1, 'B');
                    setBotPos(this.botPosX, this.botPosY + 1);
                    System.out.println("Success");
                }
                else{
                    System.out.println("Fail");
                }
                break;
            case 'w':
                if(this.map[this.botPosY][this.botPosX - 1] != '#'){
                    this.map[this.botPosY][this.botPosX] = this.getStandingOn('B');
                    this.setStandingOn(this.botPosX - 1, this.botPosY, 'P');
                    setBotPos(this.botPosX - 1, this.botPosY);
                    System.out.println("Success");
                }
                else{
                    System.out.println("Fail");
                }
                break;
            default:
                System.out.println("Fail");
        }
    }

    /**
     * Constructor that accepts a map to read in from, then calls the methods to generate a position for the player and bot.
     *
     * @param : The filename of the map file.
     */
    Map(String fileName) throws IOException {
        readMap(fileName);
        this.placePlayer();
        this.placeBot();
    }

    /**
     * @return : Gold required to exit the current map.
     */
    int getGoldRequired() {
        return this.goldRequired;
    }

    /**
     * @return : The map as stored in memory.
     */
    char[][] getMap() {
        return this.map;
    }

    /**
     * @return : The name of the current map.
     */
    String getMapName() {
        return this.mapName;
    }

    /**
     * Reads the map from file.
     *
     * @param : Name of the map's file.
     */
    private void readMap(String fileName) throws IOException {
        String workingDirectory = System.getProperty("user.dir"); // Get the directory the program is executing from
        File file = new File(workingDirectory + "\\" + fileName + ".txt");
        BufferedReader fileReader = new BufferedReader(new FileReader(file));

        this.mapName = fileReader.readLine().substring(5); // First 5 characters are 'name ' which are not needed, so take after it
        this.goldRequired = Integer.valueOf(fileReader.readLine().substring(4)); // First 4 characters are 'win '
        int mapRowCounter = 0; // Starting from the top row
        String line; // line from the file being read
        this.map = new char[0][]; // instantiating the map array with 0 elements
        while((line = fileReader.readLine()) != null){ // while the read line form the file isn't empty
            mapRowCounter++; // increase the number of rows in the map
            this.map = Arrays.copyOf(this.map, mapRowCounter); // update the map to be a copy of the previous map but with an extra row
            this.map[mapRowCounter - 1] = line.toCharArray(); // set the previous rows elements to those read from the file
        }
    }
}
