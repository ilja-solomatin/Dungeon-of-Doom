Dungeon of Doom :
-----------------
This game was created as part of an end of semester one coursework, for first year of Computer Science.
The game consists of a map which the character can traverse through while looking for gold and eventually the exit,
while being chased by an enemy bot within.


Installation :
--------------
Create a "Dungeon of doom" folder and place the java files, along with any custom maps within there.
The necessary java files to run the game are:
- GameLogic.java
- Map.java
- HumanPlayer.java
- BotPlayer.java


Running the game :
------------------
When running the game for the first time, the java files will need to be compiled.
On the command line on linux find the directory where you installed the game.
Next, compile each java file using the command: javac GameLogic.java.
Repeat using the 'javac' command until all four .java files have been compiled.
Lastly, to run the game simply type "java GameLogic".


How to play :
-------------
The map is represented by various symbols:
- 'P' represents the player.
- 'B' represents the enemy bot.
- 'G' represents gold.
- 'E' represents exit.
- '#' represents a wall.
- '.' represents an empty floor tile.

An example map is shown below:
##########################  
#........................#  
#.....###........###.....#  
#......G..........G......#  
#........................#  
#...........E............#  
#......G.........G.......#  
#........G.....G.........#  
#..........###...........#  
#........................#  
##########################  

The commands available in the game:
- 'hello' - displays the total amount of gold required to be eligeble to win the game.
- 'gold' - displays the current gold owned by the player.
- 'move x' - moves the player one square in the direction x, which may be 'n', 'e', 's', or 'w', being
	north, east, south and west respectively.
- 'pickup' - picks up gold on the players current position.
- 'look' - displays a 5 by 5 grid of the map around the player.
- 'quit' - quits the game. If the player uses the command on the 'E' tile and owns enough gold to win,
	then the player will win the game. If the player doesn't have enough gold, then they will lose the game.
	After ending a game, all progress will be lost.

Using any command will consume the players turn. The player and the bot will be using commands in turns.
The bot's objective is to catch the player, and so will not be picking up gold in the dungeon.
The bot will traverse through the dungeon randomly, and if the bot uses the 'look' command and sees the player,
then the bot will attempt to chase the player by moving towards the location the bot spotted the player on.
The bot will remember the location it spotted the player on for 3 consecutive turns. After which it will need to use the 'look'
command again to see where the player has moved to in an attempt to escape. If the bot steps on the same location that the
player is on, then the player will lose, and the bot will be triumphant in catching the player.
