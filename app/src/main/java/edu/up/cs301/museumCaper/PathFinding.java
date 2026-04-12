package edu.up.cs301.museumCaper;
import  java.util.*;
import java.util.List;

/*
    in this class i will implement a search algorithm so that the ai bots arent moving
    aimlessly and can have a good goal so that they can move more interact with the user.
*/
public class PathFinding {
    private MuseumCaperState state;
    public PathFinding(MuseumCaperState state){
        this.state = state;
    }
    public static int[] getMoveNext(MuseumCaperState state, int row, int col, int goalRow, int goalCol){
        // get the board and have it implemented here
        List<List<MapTile>> board = state.getBoard();
        int boardRow = board.size();
        int boardCol = board.get(0).size();

        // create a boolean to check that we have visited that place
        boolean[][] visit = new boolean[boardRow][boardCol];
        // have a int that keeps track of the previous or where its gone in the board
        int[][][] parent = new int[boardRow][boardCol][2];

        // now we use queue here to keep adding of the previous path its gone
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{row,col});
        visit[row][col] = true;

        // now we create a 2d array for directions this will make it easier to navigate
        // and know where were at so up down left right
        int[][] directions = {
                {-1,0}, // up
                {1,0}, // down
                {0,-1}, // left
                {0,1} // right
        };
        // now we have a while loop to keep making the path move forward so we keep adding the new
        // direction until there is not more
        while(!queue.isEmpty()){
          int[] current = queue.poll();
          int currRow = current[0];
          int currCol = current [1];

          // here we will have the path be reconstructed if the guards have made it to the goal already
          if(currRow == goalRow && currCol == goalCol){
              List<int[]> path = new ArrayList<>();
              int[] curr = {goalRow,goalCol};
              while (curr[0] != row || curr[1] != col){
                  path.add(0,curr);
                  curr = parent[curr[0]][curr[1]];
              }
              path.add(0, new int[]{row,col});

              // have a it convert the direction we just made and make it compatible with the state
              // we need to do this because the logic is different and if not done it will get confused
              // its our up and down that are a little different the left and right work fine
              if(path.size() >= 2){
                  int[] next = path.get(1);
                  int row2 = next[0] - row;
                  int col2 = next[1] - col;

                  // acutal conversion
                  int convertCol = col2;
                  int convertRow;

                  if(row2 == -1){
                      convertRow = 1; //up
                  }else if(row2 == 1){
                      convertRow = -1;
                  }else{
                      convertRow = 0;
                  }
                    return new int[]{convertCol,convertRow};
              }
          }
          // have a for each loop to navigate the 4 directions
          for(int[] dir : directions){
              int r = currRow + dir[0];
              int c = currCol + dir[1];

              if(r >= 0 && r < boardRow && c >= 0 && c < boardCol && !visit[r][c]){
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
                      visit[r][c] = true;
                      parent[r][c] = new int[]{currRow,currCol};
                      queue.add(new int[]{r,c});
                  }
              }
          }
        }
        return new int[]{0,0};

    }
}
