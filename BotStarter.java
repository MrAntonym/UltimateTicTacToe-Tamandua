// // Copyright 2016 theaigames.com (developers@theaigames.com)

//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at

//        http://www.apache.org/licenses/LICENSE-2.0

//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//
//    For the full copyright and license information, please view the LICENSE
//    file that was distributed with this source code.

package bot;
import java.util.ArrayList;
import java.util.Random;

/**
 * BotStarter class
 * 
 * Magic happens here. You should edit this file, or more specifically
 * the makeTurn() method to make your bot do more than random moves.
 * 
 * @author Jim van Eeden <jim@starapple.nl>
 */

public class BotStarter {
    
    Random r = new Random();

    /**
     * Makes a turn. Edit this method to make your bot smarter.
     * Currently does only random moves.
     *
     * @return The column where the turn was made.
     */
	public Move makeTurn(Field field, int id) {
        int myID = id;
		ArrayList<Move> possibleMoves = field.getAvailableMoves();
        Move move;
        /* Test for winning moves */
        for (Move m : possibleMoves) {
            if (winning(field, m)) {
                return m;
            }
        }
        
        
		move = moves.get(r.nextInt(moves.size())); /* get random move from available moves */
		
		return move;
	}
    
    public boolean winning(Field field, Move move) {
        /* Check the macroBoard to see if winning this board will result in a win. */
        int[][] macro = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                macro[i][j] = field.getMacro(i, j);
            }
        }
        /* This move must be on a board which hasn't been won and therefore it would be a valid move on the macro board. */
        if (!boardWinning(((int) move.getX() / 3), ((int) move.getY() / 3), macro)) {
            return false;
        }
        int[][] micro = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                micro[i][j] = field.getPlayerID(i, j);
            }
        }
        if (boardWinning(move.getX(), move.getY(), micro)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Returns whether a certain move will result in winning a given board.
     * The board should be given as a 3x3 array with values 0, 1, or 2 where
     * 0 is unclaimed, 1 is the player playing (x, y), and 2 is the other player.
     *
     * This method assumes that the board has not already been won by either player.
     */
    public boolean boardWinning(int x, int y, int[][] board) {
        /* Technically unneccessary to create a new array, but it allows for easier changes if different forms of containers must be used in the future. */
        int[][] postMove = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                postMove[i][j] = board[i][j];
            }
        }
        postMove[x][y] = 1;
        if (x == y && postMove[0][0] == 1 && postMove[1][1] == 1 && postMove[2][2] == 1) {
            return true;
        } else if (x == 2 - y && postMove[0][2] == 1 && postMove[1][1] == 1 && postMove[2][0] == 1) {
            return true;
        } else if (postMove[x][0] == 1 && postMove[x][1] == 1 && postMove[x][2] == 1) {
            return true;
        } else if (postMove[0][y] == 1 && postMove[1][y] == 1 && postMove [2][y] == 1) {
            return true;
        } else {
            return false;
        }
    }


	public static void main(String[] args) {
		BotParser parser = new BotParser(new BotStarter());
		parser.run();
	}
}

