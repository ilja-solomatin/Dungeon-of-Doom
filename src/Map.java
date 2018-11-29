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
            int randRow = randNumGenerator.nextInt((mapRows - 1 - 1) + 1) + 1;
            int randColumn = randNumGenerator.nextInt((mapColumns - 1 - 1) + 1) + 1;
            if(this.map[randRow][randColumn] != 'G'){
                canPlace = true;
                this.map[randRow][randColumn] = 'P';
            }
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
