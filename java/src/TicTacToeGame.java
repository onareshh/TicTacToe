import java.util.Scanner;

public class TicTacToeGame {
    public String play(Scanner scanner, String player1Username, String player2Username) {
        char[][] board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }

        String currentPlayer = player1Username;
        char currentSymbol = 'X';

        for (int turn = 0; turn < 9; turn++) {
            printBoard(board);
            System.out.printf("Player %s (%c), enter your move (row and column): ", currentPlayer, currentSymbol);
            int row = scanner.nextInt() - 1;
            int col = scanner.nextInt() - 1;

            if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != ' ') {
                System.out.println("Invalid move. Try again.");
                turn--;
                continue;
            }

            board[row][col] = currentSymbol;
            if (checkWin(board, currentSymbol)) {
                printBoard(board);
                System.out.printf("Player %s (%c) wins!\n", currentPlayer, currentSymbol);
                return currentPlayer;
            }

            currentPlayer = currentPlayer.equals(player1Username) ? player2Username : player1Username;
            currentSymbol = currentSymbol == 'X' ? 'O' : 'X';
        }

        printBoard(board);
        System.out.println("It's a draw!");
        return "null";
    }

    private boolean checkWin(char[][] board, char symbol) {
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) ||
                    (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol)) {
                return true;
            }
        }
        return (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
                (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol);
    }

    private void printBoard(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] == ' ' ? "." : board[i][j]);
                if (j < 2) System.out.print("|");
            }
            System.out.println();
            if (i < 2) System.out.println("-----");
        }
    }
}
