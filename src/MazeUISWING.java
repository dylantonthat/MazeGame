import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class MazeUISWING implements ActionListener
{
    private JFrame frame = new JFrame();

    private JButton easyButton;
    private JButton hardButton;
    private JButton mazeSolutionButton;

    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;
    

    private JLabel label = new JLabel("Choose a Maze Difficulty:", SwingConstants.CENTER);
    private JLabel instructions = new JLabel();

    private File mazeEasy;
    private JTextArea mazeEasyDisplay = new JTextArea(" ", 41, 41);

    private File mazeHard;
    private JTextArea mazeHardDisplay = new JTextArea(" ", 47, 131);

    private String mazeString;


    JPanel panel = new JPanel(new GridBagLayout());


    //constructor (beginning interface)
    public MazeUISWING()
    {
        instructions.setText("Start/Path = + | End of Maze = -");

        // the clickable buttons
        easyButton = new JButton("Easy");
        easyButton.addActionListener(this);
        easyButton.setBounds(50,100,160,60);


        hardButton = new JButton("Hard");
        hardButton.addActionListener(this);
        hardButton.setBounds(50,100,80,30);

        // Set up the constraints for label
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.anchor = GridBagConstraints.CENTER;

        labelConstraints.gridx = 0;
        labelConstraints.gridy = 0;
        labelConstraints.gridwidth = 2;
        labelConstraints.insets = new Insets(10, 0, 10, 0);

        // Set up the constraints for easyButton
        GridBagConstraints easyButtonConstraints = new GridBagConstraints();
        easyButtonConstraints.anchor = GridBagConstraints.CENTER;

        easyButtonConstraints.gridx = 0;
        easyButtonConstraints.gridy = 1;
        easyButtonConstraints.insets = new Insets(0, 0, 10, 5);

        // Set up the constraints for hardButton
        GridBagConstraints hardButtonConstraints = new GridBagConstraints();
        hardButtonConstraints.anchor = GridBagConstraints.CENTER;

        hardButtonConstraints.gridx = 1;
        hardButtonConstraints.gridy = 1;
        hardButtonConstraints.insets = new Insets(0, 5, 10, 0);


        // the panel with the button and text
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        frame.setLocationRelativeTo(null);

        // add label, easy, and hard buttons to the panel
        panel.add(label, labelConstraints);
        panel.add(easyButton, easyButtonConstraints);
        panel.add(hardButton, hardButtonConstraints);




        // set up the frame and display it
        frame.setTitle("Maze Game");
        frame.setLayout(new BorderLayout());

        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // process the button clicks
    public void actionPerformed(ActionEvent e)
    {

        if (e.getSource() == easyButton)
        {
            panel.remove(easyButton);
            panel.remove(hardButton);
            clear();
            frame.setSize(new Dimension(500, 800));

            // SET UP THE EASY MAZE DISPLAY
            GridBagConstraints mazeEasyConstraints = new GridBagConstraints();

            mazeEasyConstraints.gridx = 1;
            mazeEasyConstraints.gridy = 1;
            mazeEasyConstraints.insets = new Insets(0, 5, 10, 0);

            label.setText("Easy Maze:");

            mazeEasy = new File("mazeEasy.dat");
            Maze.importMaze(mazeEasy);

            mazeEasyDisplay.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 9));

            printMaze(mazeEasyDisplay);
            panel.add(mazeEasyDisplay, mazeEasyConstraints);


            GridBagConstraints instructionsConstraints = new GridBagConstraints();
            instructionsConstraints.gridx = 1;
            instructionsConstraints.gridy = 2;
            instructionsConstraints.insets = new Insets(0, 0, 10, 5);
            panel.add(instructions, instructionsConstraints);

            setUpSolutionButton();
        }
        else if (e.getSource() == hardButton)
        {
            panel.remove(easyButton);
            panel.remove(hardButton);
            clear();
            frame.setSize(new Dimension(800, 800));

            // SET UP THE HARD MAZE DISPLAY
            GridBagConstraints mazeHardConstraints = new GridBagConstraints();
            mazeHardConstraints.gridx = 1;
            mazeHardConstraints.gridy = 1;
            mazeHardConstraints.insets = new Insets(0, 5, 10, 0);


            label.setText("Hard Maze:");

            mazeHard = new File("mazeHard.dat");
            Maze.importMaze(mazeHard);

            mazeHardDisplay.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 9));

            printMaze(mazeHardDisplay);
            panel.add(mazeHardDisplay, mazeHardConstraints);

            GridBagConstraints instructionsConstraints = new GridBagConstraints();
            instructionsConstraints.gridx = 1;
            instructionsConstraints.gridy = 2;
            instructionsConstraints.insets = new Insets(0, 0, 10, 5);
            panel.add(instructions, instructionsConstraints);

            setUpSolutionButton();
        }
        else if (e.getSource() == mazeSolutionButton)
        {
            if (frame.getSize().equals(new Dimension(500, 800)))
                showSolution(mazeEasyDisplay);
            if (frame.getSize().equals(new Dimension(800, 800)))
                showSolution(mazeHardDisplay);
        }
    }

    public void setUpSolutionButton()
    {
        // SET UP MAZE SOLUTION BUTTON
        GridBagConstraints mazeSolutionButtonConstraints = new GridBagConstraints();
        mazeSolutionButtonConstraints.gridx = 1;
        mazeSolutionButtonConstraints.gridy = 3;
        mazeSolutionButtonConstraints.insets = new Insets(0, 0, 10, 10);

        mazeSolutionButton = new JButton("Give Up?");
        mazeSolutionButton.addActionListener(this);
        panel.add(mazeSolutionButton, mazeSolutionButtonConstraints);
    }
    public void clear()
    {
        panel.revalidate();
        panel.repaint();
    }

    public void printMaze(JTextArea m)
    {
        for (int i = 0; i < Maze.numRow; i++)
        {
            mazeString = "\n";
            m.setText(m.getText() + mazeString);
            for (int j = 0; j < Maze.numCol; j++)
            {
                mazeString = String.valueOf(Maze.maze[i][j]);
                m.setText(m.getText() + mazeString);
            }
        }
    }

    public void showSolution(JTextArea jT)
    {
        label.setText("Solution:");

        if(jT.equals(mazeEasyDisplay))
        {
            panel.remove(mazeSolutionButton);
            clear();
            mazeEasyDisplay.setText(null);

            Maze.importMaze(mazeEasy);
            Maze.movement(Maze.maze, Maze.startRow, Maze.startCol);
            printMaze(mazeEasyDisplay);
            mazeEasyDisplay.setEditable(false);
        }

        else if(jT.equals(mazeHardDisplay))
        {
            panel.remove(mazeSolutionButton);
            clear();
            mazeHardDisplay.setText(null);

            Maze.importMaze(mazeHard);
            Maze.movement(Maze.maze, Maze.startRow, Maze.startCol);
            printMaze(mazeHardDisplay);
            mazeEasyDisplay.setEditable(false);
    
        }
    }

    // create one Frame
    public static void main(String[] args)
    {
        new MazeUISWING();
    }
}


