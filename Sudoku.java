import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Sudoku
{
    private int dimension;
    private int [][]matrix;

    public Sudoku()
    {
        dimension = 9;
        matrix = new int [dimension][dimension];
        for (int i = 0; i < dimension; ++i)
        {
            for (int j = 0; j < dimension; ++j)
            {
                matrix[i][j] = 0;
            }
        }
    }

    public Sudoku(int [][]matrix)
    {
        if (matrix.length != 9 || matrix[0].length != 9)
        {
            throw new IllegalArgumentException("Only 9*9 matrix is accepted. ");
        }
        dimension = matrix.length;
        this.matrix = new int [dimension][dimension];
        for (int i = 0; i < dimension; ++i)
        {
            for (int j = 0; j < dimension; ++j)
            {
                this.matrix[i][j] = matrix[i][j];
            }
        }
    }

    public int getDimension()
    {
        return this.dimension;
    }

    public int get(int i, int j)
    {
        if (i < 0 || i >= dimension || j < 0 || j >= dimension)
        {
            throw new IllegalArgumentException("Index out of bounds. ");
        }
        return matrix[i][j];
    }

    public void print()
    {
        for (int i = 0; i < dimension; ++i)
        {
            for (int j = 0; j < dimension; ++j)
            {
                System.out.print(matrix[i][j] + "  ");
            }
            System.out.println();
        }
    }

    private boolean valueInRow(int value, int row)
    {
        if (row < 0 || row >= dimension)
        {
            throw new IndexOutOfBoundsException("Error in valueInRow. ");
        }
        for (int col = 0; col < dimension; ++col)
        {
            if (matrix[row][col] == value)
                return true;
        }
        return false;
    }

    private boolean valueInCol(int value, int col)
    {
        if (col < 0 || col >= dimension)
        {
            throw new IndexOutOfBoundsException("Error in valueInCol. ");
        }
        for (int row = 0; row < dimension; ++row)
        {
            if (matrix[row][col] == value)
                return true;
        }
        return false;
    }

    private ArrayList<ArrayList<Integer>> getZoneCornerIndices(int row, int col)
    {
        if (row < 0 || row >= dimension)
        {
            throw new IndexOutOfBoundsException("Error in getZoneCornerIndices. ");
        }
        if (col < 0 || col >= dimension)
        {
            throw new IndexOutOfBoundsException("Error in getZoneCornerIndices. ");
        }
        ArrayList<ArrayList<Integer>> indices = new ArrayList<ArrayList<Integer>> ();
        int rowStart = 0;
        int rowEnd = 0;
        int colStart = 0; 
        int colEnd = 0;
        for (int i = 0; i < 3; ++i)
        {
            if (row%3 == i)
            {
                rowStart = row - i;
                rowEnd = rowStart + 2;
                break;
            }
        }
        for (int i = 0; i < 3; ++i)
        {
            if (col%3 == i)
            {
                colStart = col - i;
                colEnd = colStart + 2;
                break;
            }
        }

        ArrayList<Integer> rowList = new ArrayList<Integer>();
        ArrayList<Integer> colList = new ArrayList<Integer>();
        rowList.add(rowStart);
        rowList.add(rowEnd);
        indices.add(rowList);
        colList.add(colStart);
        colList.add(colEnd);
        indices.add(colList);
        return indices;
    }

    private boolean valueInZone(int value, int row, int col)
    {
        if (row < 0 || row >= dimension || col < 0 || col >= dimension)
        {
            throw new IndexOutOfBoundsException("Index out of range in valueInZone. ");
        }

        ArrayList<ArrayList<Integer>> cornerIndices = this.getZoneCornerIndices(row, col);
        int rowStart = cornerIndices.get(0).get(0);
        int rowEnd = cornerIndices.get(0).get(1);
        int colStart = cornerIndices.get(1).get(0);
        int colEnd = cornerIndices.get(1).get(1);
        for (int i = rowStart; i <= rowEnd; ++i)
        {
            for (int j = colStart; j <= colEnd; ++j)
            {
                if (value == matrix[i][j])
                    return true;
            }
        }
        return false;
    }

    private boolean isSafe(int value, int row, int col)
    {
        if (value == 0)
            return true;
        if (!valueInRow(value, row) && !valueInCol(value, col) && !valueInZone(value, row, col))
            return true;
        return false;
    }

    private ArrayList<Integer> findUnassigned()
    {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int row = 0; row < dimension; ++row)
        {
            for (int col = 0; col < dimension; ++col)
            {
                if (matrix[row][col] == 0)
                {
                    result.add(row);
                    result.add(col);
                    return result;
                }
            }
        }
        result.add(-1);
        result.add(-1);
        return result;
    }

    public boolean isValid()
    {
        for (int i = 0; i < dimension; ++i)
        {
            for (int j = 0; j < dimension; ++j)
            {
                if (!isSafe(matrix[i][j], i, j))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean solve()
    {
        int row = 0;
        int col = 0;
        ArrayList<Integer> unassignedIndices = this.findUnassigned();
        row = unassignedIndices.get(0);
        col = unassignedIndices.get(1);
        if (row == -1 && col == -1)
        {
            return true;
        }
        int value = 1;
        while(value <= 9)
        {
            if (isSafe(value, row, col))
            {
                matrix[row][col] = value;
                if (this.solve())
                    return true;
                else
                    matrix[row][col] = 0;
            }
            value++;
        }
        return false;
    }

    public int [][] getSolution()
    {
        if (this.solve())
        {
            return this.matrix;
        }
        else
        {
            int [][] nullMatrix = new int [dimension][dimension];
            for (int i = 0; i < dimension; ++i)
            {
                for (int j = 0; j < dimension; ++j)
                {
                    nullMatrix[i][j] = 0;
                }
            }
            return nullMatrix;
        }
    }

    public int [][] getMatrix()
    {
        return this.matrix;
    }

    public static void main(String []args)
    {
        int dimension = 9;
        int [][]matrix = new int [dimension][dimension];
        for (int i = 0; i < dimension; ++i)
        {
            for (int j = 0; j < dimension; ++j)
            {
                matrix[i][j] = 0;
            }
        }
        /*try
        {
            Scanner scanner = new Scanner(new File("sudoku_1.txt"));
            while(scanner.hasNextInt())
            {
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                int element = scanner.nextInt();
                matrix[row][col] = element;
            }
            scanner.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }*/
        matrix[0][0] = 1;
        matrix[0][1] = 1;
        Sudoku sudoku = new Sudoku(matrix);
        System.out.println("Sudoku: ");
        sudoku.print();
        if (!sudoku.isValid())
        {
            System.out.println("This is not a valid sudoku. ");
            System.exit(-1);
        }
        Sudoku solution = new Sudoku(sudoku.getSolution());
        System.out.println("Solution: ");
        solution.print();
    }
}
