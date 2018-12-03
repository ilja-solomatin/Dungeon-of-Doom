import java.util.Random;

/**
 * Reads and contains in memory the map of the game.
 *
 */
public class Map {

    /* Representation of the map */
    private char[][] map;

    /* Map name */
    private String mapName;

    /* Gold required for the human player to win */
    private int goldRequired;

    private int playerPosX;
    private int playerPosY;
    private int botPosX;
    private int botPosY;

    private char playerStandingOn;
    private char botStandingOn;

    /**
     * Default constructor, creates the default map "Very small Labyrinth of doom".
     */
    public Map() {
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
        this.placePlayer();
        this.placeBot();
    }

    private void placePlayer(){
        Random randNumGenerator =  new Random();
        boolean canPlace = false;
        int mapRows = this.map.length;
        int mapColumns = this.map[0].length;

        while(!canPlace){
            //Since mapRows is the total number of rows, we subtract 1 to have the last addressable element
            //We then subtract another 1 since we cannot let the player spawn on a wall, which is at the
            //last addressable element. This gives mapRows - 2. We add 1 which is the minimum value we want
            //to generate - excludes the top wall row (index 0). Finally we add 1 which again, is the minimum
            //value we want to generate.
            int randRow = randNumGenerator.nextInt((mapRows - 2 - 1) + 1) + 1;
            int randColumn = randNumGenerator.nextInt((mapColumns - 2 - 1) + 1) + 1;
            if(this.map[randRow][randColumn] != 'G'){
                canPlace = true;
                this.setStandingOn(randColumn, randRow, 'P');
                this.map[randRow][randColumn] = 'P';
                this.playerPosX = randColumn;
                this.playerPosY = randRow;
            }
        }
    }

    private void placeBot(){
        Random randNumGenerator =  new Random();
        boolean canPlace = false;
        int mapRows = this.map.length;
        int mapColumns = this.map[0].length;

        while(!canPlace){
            int randRow = randNumGenerator.nextInt((mapRows - 2 - 1) + 1) + 1;
            int randColumn = randNumGenerator.nextInt((mapColumns - 2 - 1) + 1) + 1;
            if(this.map[randRow][randColumn] != 'G' && this.map[randRow][randColumn] != 'P'){
                canPlace = true;
                this.setStandingOn(randColumn, randRow, 'B');
                this.map[randRow][randColumn] = 'B';
                this.botPosX = randColumn;
                this.botPosY = randRow;
            }
        }
    }

    public void setStandingOn(int currentX, int currentY, char player){
        if(player == 'P') {
            this.playerStandingOn = this.map[currentY][currentX];
        }
        else{
            this.botStandingOn = this.map[currentY][currentX];
        }
    }

    public void setStandingOn(char symbol, char player){
        if(player == 'P') {
            this.playerStandingOn = symbol;
        }
        else{
            this.botStandingOn = symbol;
        }
    }

    public char getStandingOn(char player){
        if(player == 'P') {
            return this.playerStandingOn;
        }
        else{
            return this.botStandingOn;
        }
    }

    public void setPlayerPos(int newX, int newY){
        this.playerPosX = newX;
        this.playerPosY = newY;
        this.map[this.playerPosY][this.playerPosX] = 'P';
    }

    public int getPlayerPosX(){
        return this.playerPosX;
    }

    public int getPlayerPosY(){
        return this.playerPosY;
    }

    public void updatePlayerPos(char direction){
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
                System.out.println("Fail");
        }
    }

    public void setBotPos(int newX, int newY){
        this.botPosX = newX;
        this.botPosY = newY;
        this.map[this.botPosY][this.botPosX] = 'B';
    }

    public int getBotPosX(){
        return this.botPosX;
    }

    public int getBotPosY(){
        return this.botPosY;
    }

    public void updateBotPos(char direction){
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
     * Constructor that accepts a map to read in from.
     *
     * @param : The filename of the map file.
     */
    public Map(String fileName) {
        readMap(fileName);
    }

    /**
     * @return : Gold required to exit the current map.
     */
    protected int getGoldRequired() {
        return this.goldRequired;
    }

    /**
     * @return : The map as stored in memory.
     */
    protected char[][] getMap() {
        return this.map;
    }


    /**
     * @return : The name of the current map.
     */
    protected String getMapName() {
        return this.mapName;
    }


    /**
     * Reads the map from file.
     *
     * @param : Name of the map's file.
     */
    protected void readMap(String fileName) {
    }


}
