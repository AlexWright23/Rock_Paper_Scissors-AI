//GUI and the AI

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RockPaperScissorsFrame extends JFrame {
    private JButton rockButton, paperButton, scissorsButton, quitButton;
    private JTextArea resultArea;
    private JScrollPane scrollPane;
    private JTextField playerWinsField, computerWinsField, tiesField;
    private int playerWins = 0, computerWins = 0, ties = 0;
    private String lastPlayerMove = null;
    private int[] moveCounts = {0, 0, 0}; // rps move counts

    private Strategy strategy = new ComputerMoveStrategy();  //AI

    public RockPaperScissorsFrame() {
        setTitle("Rock Paper Scissors");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBorder(BorderFactory.createTitledBorder("Results"));

        resultArea = new JTextArea(3, 30);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.BOLD, 18));
        scrollPane = new JScrollPane(resultArea);
        resultsPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Choose Your Move"));
        buttonPanel.setLayout(new GridLayout(1, 3, 20, 10));

        rockButton = createImageButton("rock.png", "Rock");
        paperButton = createImageButton("paper.png", "Paper");
        scissorsButton = createImageButton("scissors.png", "Scissors");

        buttonPanel.add(rockButton);
        buttonPanel.add(paperButton);
        buttonPanel.add(scissorsButton);

        JPanel statsPanel = new JPanel();
        statsPanel.setBorder(BorderFactory.createTitledBorder("Game History"));
        statsPanel.setLayout(new GridLayout(3, 2));

        statsPanel.add(new JLabel("Player Wins:"));
        playerWinsField = new JTextField("0", 5);
        playerWinsField.setEditable(false);
        statsPanel.add(playerWinsField);

        statsPanel.add(new JLabel("Computer Wins:"));
        computerWinsField = new JTextField("0", 5);
        computerWinsField.setEditable(false);
        statsPanel.add(computerWinsField);

        statsPanel.add(new JLabel("Ties:"));
        tiesField = new JTextField("0", 5);
        tiesField.setEditable(false);
        statsPanel.add(tiesField);

        JPanel quitPanel = new JPanel();
        quitButton = new JButton("Quit");
        quitButton.setPreferredSize(new Dimension(150, 50));
        quitButton.setBackground(Color.RED);
        quitButton.setForeground(Color.WHITE);
        quitButton.setFont(new Font("Arial", Font.BOLD, 18));
        quitButton.addActionListener(e -> System.exit(0));
        quitPanel.add(quitButton);

        add(resultsPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(statsPanel, BorderLayout.EAST);
        add(quitPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JButton createImageButton(String imageName, String move) {
        JButton button = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("/images/" + imageName))
                .getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        button.addActionListener(new GameActionListener(move));
        return button;
    }

    private class GameActionListener implements ActionListener {
        private String playerChoice;

        public GameActionListener(String choice) {
            this.playerChoice = choice;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (playerChoice) {
                case "Rock" -> moveCounts[0]++;
                case "Paper" -> moveCounts[1]++;
                case "Scissors" -> moveCounts[2]++;
            }

            String computerChoice = strategy.determineMove(lastPlayerMove, moveCounts);
            lastPlayerMove = playerChoice;

            String result = determineWinner(playerChoice, computerChoice);
            resultArea.append(playerChoice + " vs. " + computerChoice + " - " + result + "\n");

            updateStats();
        }
    }

    private String determineWinner(String player, String computer) {
        if (player.equals(computer)) {
            ties++;
            return "It's a Tie!";
        } else if ((player.equals("Rock") && computer.equals("Scissors")) ||
                (player.equals("Paper") && computer.equals("Rock")) ||
                (player.equals("Scissors") && computer.equals("Paper"))) {
            playerWins++;
            return "Player Wins, GG!";
        } else {
            computerWins++;
            return "Computer Wins!";
        }
    }

    private void updateStats() {
        playerWinsField.setText(String.valueOf(playerWins));
        computerWinsField.setText(String.valueOf(computerWins));
        tiesField.setText(String.valueOf(ties));
    }
}
