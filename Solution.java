import java.util.LinkedList;

public class Solution {

  // Board Values: Free Positions=0. Knights=1. King=2.
  public int[][] board;
  public int[][] knightMoves = {

    // first to the left, then vertical.
    {-2, -1}, // one left, two up
    {2, -1}, // one left, two down
    {-1, -2}, // two left, one up
    {1, -2}, // two left, one down

    // first to the right, then vertical.
    {-2, 1}, // one right, two up
    {2, 1}, // one right, two down
    {-1, 2}, // two right, one up
    {1, 2}, // two right, one down
  };

  /*
  By the problem design on binarysearch.com, we have to work
  around the given method 'public int solve(int[][] board)' so that the code
  can be run on the website. Even though the name 'solve' does not make
  a lot of sense, it is left as it is, so that the code can be run directly on the website,
  without any modifications.
  */
  public int solve(int[][] board) {
    this.board = board;
    return bfs_findMinNumberOfMoves();
  }

  /*
  Breadth First Search: move the king like a knight. The first encountered knight,
  is the knight that can capture the king with minumum number of moves.

  --------------------------------------------------------------------------------
  Side Note: Multi-Point Breadth First Search will also work but for this particular
             problem this approach is significantly slower.

             Multi-Source BFS is effective when both the exact source point
             and exact the destination point are NOT known.

  Example:   The farthest land point from water.
             We have multiple possibilities for water points next to the coast and
             multiple possibilities for points on land. But we still do not know
             the exact coordinates of these two points!!
  --------------------------------------------------------------------------------

  @return Minumum number of moves to capture the king by any of the knigts on the board.
          If the king can not be reached from the knights, it returns '-1'.
  */
  public int bfs_findMinNumberOfMoves() {

    boolean[][] visited = new boolean[board.length][board[0].length];
    LinkedList<Point> queue = new LinkedList<Point>();

    Point start = findKingPoint();
    queue.add(start);
    visited[start.row][start.column] = true;

    while (!queue.isEmpty()) {
      Point current = queue.removeFirst();
      int row = current.row;
      int column = current.column;

      if (board[row][column] == 1) {
        return current.numberOfMovesFromStart;
      }

      for (int i = 0; i < knightMoves.length; i++) {
        int new_r = row + knightMoves[i][0];
        int new_c = column + knightMoves[i][1];

        if (isInMatrix(new_r, new_c) && !visited[new_r][new_c]) {
          Point p = new Point(new_r, new_c);
          p.numberOfMovesFromStart = current.numberOfMovesFromStart + 1;
          queue.add(p);
          visited[new_r][new_c] = true;
        }
      }
    }

    return -1;
  }

  public Point findKingPoint() {

    Point kingPoint = null;

    outerloop:
    for (int r = 0; r < board.length; r++) {
      for (int c = 0; c < board.length; c++) {
        if (board[r][c] == 2) {
          kingPoint = new Point(r, c);
          break outerloop;
        }
      }
    }
    return kingPoint;
  }

  public boolean isInMatrix(int row, int column) {
    if (row < 0 || column < 0 || row > board.length - 1 || column > board[0].length - 1) {
      return false;
    }
    return true;
  }
}

class Point {
  int row;
  int column;
  int numberOfMovesFromStart;

  public Point(int row, int column) {
    this.row = row;
    this.column = column;
  }
}
