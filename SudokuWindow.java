import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SudokuWindow implements ActionListener
{
    private JFrame frame;
    private JPanel panel;
    private JButton start;
    private SudokuPanel sudokuPanel;
    private SudokuPanel solutionPanel;
    private int dimension;
    private int [][]matrix;
    private JTextField alert;
    private JButton anotherSudoku;
    private JButton restart;
    private JButton okButton;
    
    public SudokuWindow()
    {
        panel = new JPanel();
        panel.setLayout(new GridLayout(1,1));
        frame = new JFrame("Sudoku");
        frame.setSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(panel);
    }

    private void setVisible(boolean isVisible)
    {
        frame.setVisible(isVisible);
    }

    private void createButton(String text, Font font)
    {
        this.start = new JButton(text);
        this.start.setFont(font);
        start.addActionListener(this);
        this.panel.add(start);
    }

    public void actionPerformed(ActionEvent e)
    {
        dimension = 9;
        JOptionPane.showMessageDialog(this.frame, "Please enter your sudoku");
        panel.removeAll();
        sudokuPanel = new SudokuPanel(dimension);
        panel.add(sudokuPanel);
        panel.revalidate();
        panel.repaint();
        for (int i = 0; i < dimension; ++i)
        {
            for (int j = 0; j < dimension; ++j)
            {
                sudokuPanel.getMatrix()[i][j].setText(String.valueOf(0));
            }
        }

        okButton = new JButton("OK");
        okButton.setFont(new Font("serif", Font.PLAIN, 24));
        okButton.addActionListener(new EneterMatrixListener());
        panel.setLayout(new GridLayout(2,1));
        panel.add(okButton);
        panel.revalidate();
        panel.repaint();
    }

    private class EneterMatrixListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            matrix = new int [dimension][dimension];
            for (int i = 0; i < dimension; ++i)
            {
                for (int j = 0; j < dimension; ++j)
                {
                    matrix[i][j] = Integer.parseInt(sudokuPanel.getMatrix()[i][j].getText());
                }
            }

            Sudoku sudoku = new Sudoku(matrix);
            if (!sudoku.isValid())
            {
                JOptionPane.showMessageDialog(SudokuWindow.this.frame, "This is not a valid sudoku");
                System.exit(-1);
            }
            boolean solvable = sudoku.solve();
            panel.removeAll();
            if (solvable)
            {
                solutionPanel = new SudokuPanel(new Sudoku(sudoku.getSolution()));
                panel.add(solutionPanel);
            }
            else
            {
                alert = new JTextField("This sudoku is not solvable. ");
                alert.setFont(new Font("serif", Font.PLAIN, 24));
                panel.add(alert);
            }

            JButton another = new JButton("Try another one");
            another.setFont(new Font("serif", Font.PLAIN, 24));
            another.addActionListener(new anotherSudokuListener());
            panel.add(another);
            panel.setLayout(new GridLayout(2,1));
            panel.revalidate();
            panel.repaint();
        }
    }

    private class anotherSudokuListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            SudokuWindow.this.panel.removeAll();
            SudokuWindow.this.restart = new JButton("Restart");
            restart.setFont(new Font("serif", Font.PLAIN, 24));
            restart.addActionListener(SudokuWindow.this);
            SudokuWindow.this.panel.setLayout(new GridLayout(1,1));
            SudokuWindow.this.panel.add(restart);
            SudokuWindow.this.panel.revalidate();
            SudokuWindow.this.panel.repaint();
        }
    }

    public static void main(String []args)
    {
        SudokuWindow window = new SudokuWindow();
        window.createButton("Start", new Font("serif", Font.PLAIN, 24));
        window.setVisible(true);
    }
}
