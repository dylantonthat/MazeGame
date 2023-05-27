import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Maze {

  public static void main(String[] args)
  {
    importMaze(mazeFile);

    if (movement(maze, startRow, startCol))
      System.out.println("Solved maze.");
    else
      System.out.println("Impossible to solve.");

    printMaze();
  }


  public static char[][] maze;

  // beginning inputs
  public static int startRow;
  public static int startCol;

  // inputs at dead end/finish line
  public static int rowEnd;
  public static int colEnd;

  // number of rows/columns
  public static int numRow;
  public static int numCol;

  public static File mazeFile; //== new File("mazeEasy.dat");

  public static String mazeString;


  //constructor
  

  public static void importMaze(File mF)
  {
    try
    {
      File m = mF;
      Scanner input = new Scanner(m);
      numRow = input.nextInt();
      numCol = input.nextInt();
      maze = new char[numRow][numCol];
      input.nextLine();

      for (int i = 0; i < numRow; i++)
      {
        String rowLine = input.nextLine(); // takes characters in the line of the maze
        for (int j = 0; j < numCol; j++)
        {
          maze[i][j] = rowLine.charAt(j); // finds specific character at index of line
        }
      }

      for (int i = 0; i < numRow; i++) // finds start point
      {
        for (int j = 0; j < numCol; j++)
        {
          if (maze[i][j] == '+')
          {
            startRow = i;
            startCol = j;
          }
        }
      }

      for (int i = 0; i < numRow; i++) // finds end point
      {
        for (int j = 0; j < numCol; j++)
        {
          if (maze[i][j] == '-')
          {
            rowEnd = i;
            colEnd = j;
          }
        }
      }

      input.close();
    }
    catch (FileNotFoundException error)
    {
      System.out.println("An error occurred.");
      error.printStackTrace();
    }
  }

  public static boolean canMove (char[][] m, int r, int c)
  {
    if (r > 0 && c >= 0 && (r < numRow && c < numCol))
    {
      if (m[r][c] == ' '|| ((r == startRow) && (c == startCol)))
      {
        return true;
      }
    }
    return false;
  }

  public static boolean movement(char[][] m, int row, int col) // inputs while moving across maze
  {

    if (row == rowEnd && col == colEnd) // if at finish line code stops running
        return true;

      maze[row][col] = '+'; // otherwise maze continues path

      if ((canMove(m,row,col + 1) && (m[row][col + 1] == ' ') && col + 1 < numCol) || m[row][col + 1] == '-') //east
      {
        if (movement(m,row, col + 1))
          return true;
        else
          m[row][col + 1] = '.';
      }
      if ((canMove(m,row + 1,col) && (m[row + 1][col] == ' ') && row + 1 < numRow) || m[row + 1][col] == '-') //south
      {
        if (movement(m,row + 1, col))
          return true;
        else
          m[row + 1][col] = '.';
      }
      if ((canMove(m,row,col - 1) && (m[row][col - 1] == ' ') && col - 1 < numCol) || m[row][col - 1] == '-') //west
      {
        if (movement(m,row, col - 1))
          return true;
        else
          m[row][col - 1] = '.';
      }
      if (((canMove(m,row - 1,col) && (m[row - 1][col] == ' ') && row - 1 < numRow) || m[row - 1][col] == '-')) //north
      {
        if (movement(m,row - 1, col))
          return true;
        else
          m[row - 1][col] = '.';
      }
    return false; // because code will go here if maze is not solved
  }




  public static void printMaze()
  {
    for (int i = 0; i < numRow; i++)
    {
      System.out.println();
      for (int j = 0; j < numCol; j++)
      {
        System.out.print(maze[i][j]);
      }
    }
  }

}
