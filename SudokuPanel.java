import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class SudokuPanel extends JPanel
{
    private int dimension;
    private JTextField [][]matrix;

    public SudokuPanel(int dimension)
    {
        this.dimension = dimension;
        if (dimension != 9)
            throw new IllegalArgumentException("Dimension must be 9. ");
        matrix = new JTextField [dimension][dimension];
        this.setLayout(new GridLayout(dimension, dimension));
        for (int i = 0; i < dimension; ++i)
        {
            for (int j = 0; j < dimension; ++j)
            {
                JTextField temp = new JTextField();
                temp.setColumns(2);
                temp.setFont(new Font("serif", Font.PLAIN, 24));
                matrix[i][j] = temp;
                this.add(temp);
            }
        }
    }

    public SudokuPanel(Sudoku argument)
    {
        dimension = argument.getDimension();
        if (dimension != 9)
        {
            throw new IllegalArgumentException("Matrix dimension wrong. ");
        }
        matrix = new JTextField[dimension][dimension];
        this.setLayout(new GridLayout(dimension, dimension));
        for (int i = 0; i < dimension; ++i)
        {
            for (int j = 0; j < dimension; ++j)
            {
                JTextField temp = new JTextField();
                temp.setColumns(5);
                temp.setFont(new Font("serif", Font.PLAIN, 24));
                temp.setText(Integer.toString(argument.get(i, j)));
                matrix[i][j] = temp;
                this.add(temp);
            }
        }
    }

    public JTextField [][] getMatrix()
    {
        return this.matrix;
    }
}
