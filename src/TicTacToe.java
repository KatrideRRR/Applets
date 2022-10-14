import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;

public class TicTacToe extends Applet implements ActionListener {

    Button squares[][];
    Button newGameButton;
    Label score;
    int emptySquaresLeft = 9;
    int winRate;
    int loseRate;
    Label rate;
    Label rate2;


    public void init() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.CYAN);

        Font appletFont = new Font("Monospased", Font.BOLD, 20);
        this.setFont(appletFont);

        GridLayout gr = new GridLayout(1, 3);

        rate = new Label("Win " + winRate);
        rate2 = new Label("Lose " + loseRate);


        newGameButton = new Button("New Game");
        newGameButton.addActionListener(this);

        JPanel p1 = new JPanel();
        p1.setLayout(gr);
        p1.add(rate);
        p1.add(newGameButton);
        p1.add(rate2);
        this.add(p1, "North");

        Panel centerPanel = new Panel();
        centerPanel.setLayout(new GridLayout(3, 3));
        this.add(centerPanel, "Center");

        score = new Label("Your turn!");
        this.add(score, "South");

        squares = new Button[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                squares[i][j] = new Button();
                squares[i][j].addActionListener(this);
                squares[i][j].setBackground(Color.ORANGE);
                centerPanel.add(squares[i][j]);
            }
        }

    }


    public void actionPerformed(ActionEvent e) {

        Button theButton = (Button) e.getSource();
        if (theButton == newGameButton) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    squares[i][j].setEnabled(true);
                    squares[i][j].setLabel("");
                    squares[i][j].setBackground(Color.ORANGE);
                }
            }

            emptySquaresLeft = 9;
            score.setText("Your turn!");
            newGameButton.setEnabled(false);
            return;
        }

        String winner = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (theButton == squares[i][j] & !squares[i][j].getLabel().equals("O")) {
                    squares[i][j].setLabel("X");
                    winner = lookForWinner();
                    if (!"".equals(winner)) {
                        endTheGame();
                    } else {
                        computerMove();
                        winner = lookForWinner();
                        if (!"".equals(winner)) {
                            endTheGame();
                        }
                    }
                    break;
                }
            }
        }

        if (winner.equals("X")) {
            score.setText("You won!");
            winRate++;
            rate.setText("Win " + winRate);
        } else if (winner.equals("O")) {
            score.setText("You lose!");
            loseRate++;
            rate2.setText("Lose " + loseRate);
        } else if (winner.equals("T")) {
            score.setText("It's a tie!");
        }

    }

    String lookForWinner() {
        String theWinner = "";
        emptySquaresLeft--;
        if (emptySquaresLeft == 0) {
            return "T";
        }


        if (!squares[0][0].getLabel().equals("") &&
                squares[0][0].getLabel().equals(squares[0][1].getLabel()) &&
                squares[0][0].getLabel().equals(squares[0][2].getLabel())) {
            theWinner = squares[0][0].getLabel();
            highlightWinner(0, 0, 0, 1, 0, 2);
        } else if (!squares[1][0].getLabel().equals("") &&
                squares[1][0].getLabel().equals(squares[1][1].getLabel()) &&
                squares[1][0].getLabel().equals(squares[1][2].getLabel())) {
            theWinner = squares[1][0].getLabel();
            highlightWinner(1, 0, 1, 1, 1, 2);
        } else if (!squares[2][0].getLabel().equals("") &&
                squares[2][0].getLabel().equals(squares[2][1].getLabel()) &&
                squares[2][0].getLabel().equals(squares[2][2].getLabel())) {
            theWinner = squares[2][0].getLabel();
            highlightWinner(2, 0, 2, 1, 2, 2);
        } else if (!squares[0][0].getLabel().equals("") &&
                squares[0][0].getLabel().equals(squares[1][0].getLabel()) &&
                squares[0][0].getLabel().equals(squares[2][0].getLabel())) {
            theWinner = squares[0][0].getLabel();
            highlightWinner(0, 0, 1, 0, 2, 0);
        } else if (!squares[0][1].getLabel().equals("") &&
                squares[0][1].getLabel().equals(squares[1][1].getLabel()) &&
                squares[0][1].getLabel().equals(squares[2][1].getLabel())) {
            theWinner = squares[0][1].getLabel();
            highlightWinner(0, 1, 1, 1, 2, 1);
        } else if (!squares[0][2].getLabel().equals("") &&
                squares[0][2].getLabel().equals(squares[1][2].getLabel()) &&
                squares[0][2].getLabel().equals(squares[2][2].getLabel())) {
            theWinner = squares[0][2].getLabel();
            highlightWinner(0, 2, 1, 2, 2, 2);
        } else if (!squares[0][0].getLabel().equals("") &&
                squares[0][0].getLabel().equals(squares[1][1].getLabel()) &&
                squares[0][0].getLabel().equals(squares[2][2].getLabel())) {
            theWinner = squares[0][0].getLabel();
            highlightWinner(0, 0, 1, 1, 2, 2);
        } else if (!squares[0][2].getLabel().equals("") &&
                squares[0][2].getLabel().equals(squares[1][1].getLabel()) &&
                squares[0][2].getLabel().equals(squares[2][0].getLabel())) {
            theWinner = squares[0][2].getLabel();
            highlightWinner(0, 2, 1, 1, 2, 0);
        }
        return theWinner;
    }


    void computerMove() {
        int[] selectedSquare = new int[2];

        selectedSquare = findEmptySquare("O");

        if (selectedSquare[0] == -1) {
            selectedSquare = findEmptySquare("X");
        }
        if ((selectedSquare[0] == -1) && (squares[1][1].getLabel().equals(""))) {
            selectedSquare[0] = 1;
            selectedSquare[1] = 1;


        }
        if (selectedSquare[0] == -1) {
            selectedSquare = getRandomSquare();
        }
        squares[selectedSquare[0]][selectedSquare[1]].setLabel("O");
        squares[selectedSquare[0]][selectedSquare[1]].setEnabled(false);
    }


    int[] findEmptySquare(String player) {
        int res[] = new int[2];

        int weight[][] = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (squares[i][j].getLabel().equals("O"))
                    weight[i][j] = -1;
                else if (squares[i][j].getLabel().equals("X"))
                    weight[i][j] = 1;
                else
                    weight[i][j] = 0;
            }
        }
        int twoWeights = player.equals("O") ? -2 : 2;

        if (weight[0][0] + weight[0][1] + weight[0][2] == twoWeights) {
            if (weight[0][0] == 0) {
                res[0] = 0;
                res[1] = 0;
                return res;
            } else if (weight[0][1] == 0) {
                res[0] = 0;
                res[1] = 0;
                return res;
            } else {
                res[0] = 0;
                res[1] = 0;
                return res;
            }
        }
        if (weight[1][0] + weight[1][1] + weight[1][2] == twoWeights) {
            if (weight[1][0] == 0) {
                res[0] = 1;
                res[1] = 0;
                return res;
            } else if (weight[1][1] == 0) {
                res[0] = 1;
                res[1] = 1;
                return res;
            } else {
                res[0] = 1;
                res[1] = 2;
                return res;
            }
        }
        if (weight[2][0] + weight[2][1] + weight[2][2] == twoWeights) {
            if (weight[2][0] == 0) {
                res[0] = 2;
                res[1] = 0;
                return res;
            } else if (weight[2][1] == 0) {
                res[0] = 2;
                res[1] = 1;
                return res;
            } else {
                res[0] = 2;
                res[1] = 2;
                return res;
            }
        }
        if (weight[0][0] + weight[1][0] + weight[2][0] == twoWeights) {
            if (weight[0][0] == 0) {
                res[0] = 0;
                res[1] = 0;
                return res;
            } else if (weight[1][0] == 0) {
                res[0] = 1;
                res[1] = 0;
                return res;
            } else {
                res[0] = 2;
                res[1] = 0;
                return res;
            }
        }
        if (weight[0][1] + weight[1][1] + weight[2][1] == twoWeights) {
            if (weight[0][1] == 0) {
                res[0] = 0;
                res[1] = 1;
                return res;
            } else if (weight[1][1] == 0) {
                res[0] = 1;
                res[1] = 1;
                return res;
            } else {
                res[0] = 2;
                res[1] = 1;
                return res;
            }
        }
        if (weight[0][2] + weight[1][2] + weight[2][2] == twoWeights) {
            if (weight[0][2] == 0) {
                res[0] = 0;
                res[1] = 2;
                return res;
            } else if (weight[1][2] == 0) {
                res[0] = 1;
                res[1] = 2;
                return res;
            } else {
                res[0] = 2;
                res[1] = 2;
                return res;
            }
        }

        if (weight[0][0] + weight[1][1] + weight[2][2] == twoWeights) {
            if (weight[0][0] == 0) {
                res[0] = 0;
                res[1] = 0;
                return res;
            } else if (weight[1][1] == 0) {
                res[0] = 1;
                res[1] = 1;
                return res;
            } else {
                res[0] = 2;
                res[1] = 2;
                return res;
            }
        }
        if (weight[0][2] + weight[1][1] + weight[2][0] == twoWeights) {
            if (weight[0][2] == 0) {
                res[0] = 0;
                res[1] = 2;
                return res;
            } else if (weight[1][1] == 0) {
                res[0] = 1;
                res[1] = 1;
                return res;
            } else {
                res[0] = 2;
                res[1] = 0;
                return res;
            }
        }
        res[0] = -1;
        res[1] = -1;
        return res;
    }


    int[] getRandomSquare() {
        boolean gotEmptySquare = false;
        int selectedSquareX = -1;
        int selectedSquareY = -1;
        int res[] = new int[2];
        do {
            selectedSquareX = (int) (Math.random() * 2);
            selectedSquareY = (int) (Math.random() * 2);

            if (squares[selectedSquareX][selectedSquareY].getLabel().equals("")) {
                gotEmptySquare = true;
            }
        } while (!gotEmptySquare);
        res[0] = selectedSquareX;
        res[1] = selectedSquareY;
        return res;
    }

    void highlightWinner(int win1, int win2, int win3, int win4, int win5, int win6) {

        squares[win1][win2].setBackground(Color.CYAN);
        squares[win3][win4].setBackground(Color.CYAN);
        squares[win5][win6].setBackground(Color.CYAN);
    }

    void endTheGame() {
        newGameButton.setEnabled(true);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                squares[i][j].setEnabled(false);
            }
        }
    }
    public static void main(String[] args){
        Applet tictac=new TicTacToe();
        JFrame frame=new JFrame("Крестики и нолики");
        frame.getContentPane().add(tictac);
        frame.setSize(200,300);
        tictac.init();
        tictac.start();
        frame.setVisible(true);
    }


}
