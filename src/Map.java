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

    private char standingOn;

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
                this.setStandingOn(randColumn, randRow);
                this.map[randRow][randColumn] = 'P';
                this.playerPosX = randColumn;
                this.playerPosY = randRow;
            }
        }
    }

    public void setStandingOn(int currentX, int currentY){
        this.standingOn = this.map[currentY][currentX];
    }

    public char getStandingOn(){
        return this.standingOn;
    }

    public void setPlayerPos(int newX, int newY){
        this.playerPosX = newX;
        this.playerPosY = newY;
        this.map[this.playerPosY][this.playerPosX] = 'P';
    }

    public void updatePlayerPos(char direction){
        switch(direction){
            case 'n':
                if(this.map[this.playerPosY - 1][this.playerPosX] != '#'){
                    this.map[this.playerPosY][this.playerPosX] = this.getStandingOn();
                    this.setStandingOn(this.playerPosX, this.playerPosY - 1);
                    setPlayerPos(this.playerPosX, this.playerPosY - 1);
                    System.out.println("Success");
                }
                else{
                    System.out.println("Fail");
                }
                break;
            case 'e':
                if(this.map[this.playerPosY][this.playerPosX + 1] != '#'){
                    this.map[this.playerPosY][this.playerPosX] = this.getStandingOn();
                    this.setStandingOn(this.playerPosX + 1, this.playerPosY);
                    setPlayerPos(this.playerPosX + 1, this.playerPosY);
                    System.out.println("Success");
                }
                else{
                    System.out.println("Fail");
                }
                break;
            case 's':
                if(this.map[this.playerPosY + 1][this.playerPosX] != '#'){
                    this.map[this.playerPosY][this.playerPosX] = this.getStandingOn();
                    this.setStandingOn(this.playerPosX, this.playerPosY + 1);
                    setPlayerPos(this.playerPosX, this.playerPosY + 1);
                    System.out.println("Success");
                }
                else{
                    System.out.println("Fail");
                }
                break;
            case 'w':
                if(this.map[this.playerPosY][this.playerPosX - 1] != '#'){
                    this.map[this.playerPosY][this.playerPosX] = this.getStandingOn();
                    this.setStandingOn(this.playerPosX - 1, this.playerPosY);
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
