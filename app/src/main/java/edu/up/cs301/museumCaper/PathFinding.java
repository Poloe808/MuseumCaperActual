package edu.up.cs301.museumCaper;
import  java.util.*;
import java.util.List;

/*
    in this class i will implement a search algorithm so that the ai bots arent moving
    aimlessly and can have a good goal so that they can move more interact with the user.
*/
public class PathFinding {
    public static int[] getMoveNext(MuseumCaperState state, int row, int col, int goalRow, int goalCol){
        // get the board and have it implemented here
        List<List<MapTile>> board = state.getBoard();
        int boardRow = board.size();
        int boardCol = board.get(0).size();

        // use a HashMap instead of int[][][] so {0,0} is never confused with "unvisited"
        // Key is "row,col", value is the parent int[]{row,col}
        Map<String, int[]> parent = new HashMap<>();
        String startKey = row + "," + col;
        parent.put(startKey, null); // null means this is the start node

        // now we use queue here to keep adding of the previous path its gone
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{row,col});

        // now we create a 2d array for directions this will make it easier to navigate
        // and know where were at so up down left right
        int[][] directions = {
                {-1,0}, // up
                {1,0}, // down
                {0,-1}, // left
                {0,1} // right
        };

        boolean found = false;

        // now we have a while loop to keep making the path move forward so we keep adding the new
        // direction until there is not more
        while(!queue.isEmpty()){
            int[] current = queue.poll();
            int currRow = current[0];
            int currCol = current[1];

            if(currRow == goalRow && currCol == goalCol){
                found = true;
            }

            // have a for each loop to navigate the 4 directions
            for(int[] dir : directions){
                int r = currRow + dir[0];
                int c = currCol + dir[1];

                String neighborKey = r + "," + c;

                if(r >= 0 && r < boardRow && c >= 0 && c < boardCol && !parent.containsKey(neighborKey)){
                    // have it check the walls
                    boolean blocked = false;
                    if(dir[0] == -1){ // up
                        blocked = board.get(currRow).get(currCol).getTopWall();
                    }else if(dir[0] == 1){// down
                        blocked = board.get(r).get(c).getTopWall();
                    }else if(dir[1] == -1){// left
                        blocked = board.get(currRow).get(currCol).getLeftWall();
                    }else if(dir[1] == 1){// right
                        blocked = board.get(r).get(c).getLeftWall();
                    }
                    if(!blocked){
                        parent.put(neighborKey, new int[]{currRow, currCol});
                        queue.add(new int[]{r,c});
                    }
                }
            }
        }

        // if we never found the goal, return null so the caller knows there's no path
        if (!found) {
            return null;
        }

        // here we will have the path be reconstructed
        List<int[]> path = new ArrayList<>();
        int[] curr = {goalRow, goalCol};

        // walk back through the HashMap until we hit the start (null parent)
        while(curr != null){
            path.add(0, curr);
            curr = parent.get(curr[0] + "," + curr[1]);
        }

        // have it convert the direction we just made and make it compatible with the state
        if(path.size() >= 2){
            int[] next = path.get(1);
            int row2 = next[0] - row;
            int col2 = next[1] - col;

            // actual conversion
            int convertCol = col2;
            int convertRow;

            if(row2 == -1){
                convertRow = 1; //up
            }else if(row2 == 1){
                convertRow = -1; // down
            }else{
                convertRow = 0;
            }
            return new int[]{convertCol, convertRow};
        }

        return null;
    }
}