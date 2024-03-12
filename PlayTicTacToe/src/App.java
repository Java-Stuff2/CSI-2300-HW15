import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {
    private Button[][] board = new Button[5][5];
    private boolean turnX = true;

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Button button = new Button(" ");
                button.setMinSize(90, 90);
                final int finalI = i;
                final int finalJ = j;
                button.setOnAction(e -> makeMove(finalI, finalJ));
                gridPane.add(button, j, i);
                board[i][j] = button;
            }
        }

        Scene scene = new Scene(gridPane, 450, 500);
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void makeMove(int i, int j) {
        if (!board[i][j].getText().equals(" "))
            return;
        board[i][j].setText(turnX ? "X" : "O");
        turnX = !turnX;
        // Add game logic here to check for a winner
        String winner = checkWinner();
        if (winner != null) {
            if (winner.equals("Tie")) {
                // Handle tie
                showTieMessage();
            } else {
                // Handle win
                showWinMessage(winner);
            }
        }
    }

    private String checkWinner() {
        // Check rows and columns
        for (int i = 0; i < 5; i++) {
            if (hasWinningCombination(board[i][0], board[i][1], board[i][2], board[i][3], board[i][4]))
                return board[i][0].getText();
            if (hasWinningCombination(board[0][i], board[1][i], board[2][i], board[3][i], board[4][i]))
                return board[0][i].getText();
        }

        // Check diagonals
        if (hasWinningCombination(board[0][0], board[1][1], board[2][2], board[3][3], board[4][4]))
            return board[0][0].getText();
        if (hasWinningCombination(board[0][4], board[1][3], board[2][2], board[3][1], board[4][0]))
            return board[0][4].getText();

        // Check for a tie
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board[i][j].getText().equals(" "))
                    return null; // Game is not over yet
            }
        }
        return "Tie"; // No winner and no empty cells
    }

    private boolean hasWinningCombination(Button... cells) {
        String firstCellText = cells[0].getText();
        if (firstCellText.equals(" "))
            return false;
        for (Button cell : cells) {
            if (!cell.getText().equals(firstCellText))
                return false;
        }
        return true;
    }

    // Method to show tie message
    private void showTieMessage() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("It's a tie!");
        alert.setContentText("No more moves left. Play again?");
        ButtonType restartButton0 = new ButtonType("Restart");
        alert.getButtonTypes().setAll(restartButton0);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == restartButton0) {
            restartGame();
        }

    }

    // Method to show win message
    private void showWinMessage(String winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(winner + " wins!");
        alert.setContentText("Congratulations! Play again?");
        // Add a button to restart the game
        ButtonType restartButton = new ButtonType("Restart");
        alert.getButtonTypes().setAll(restartButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == restartButton) {
            restartGame();
        }

    }

    private void restartGame() {
        // Clear the board and reset the turn
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].setText(" ");
            }
        }
        turnX = true; // Reset the turn to the first player
    }

    public static void main(String[] args) {
        launch(args);
    }
}
