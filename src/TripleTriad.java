import java.util.ArrayList;
import java.util.Scanner;

public class TripleTriad {
    private static final int GRID_SIZE = 3;

    public void playGame() {
        // Initialize players and their hands
        Player player1 = new Player();
        player1.name = "Player 1";
        player1.hand = new ArrayList<>();
        player1.hand.add(new Card(1, 2, 3, 4));
        player1.hand.add(new Card(4, 3, 2, 1));
        player1.hand.add(new Card(2, 1, 4, 3));
        player1.hand.add(new Card(3, 4, 1, 2));
        player1.hand.add(new Card(3, 1, 5, 2));


        Player player2 = new Player();
        player2.name = "Player 2";
        player2.hand = new ArrayList<>();
        player2.hand.add(new Card(3, 4, 1, 2));
        player2.hand.add(new Card(2, 3, 4, 1));
        player2.hand.add(new Card(4, 1, 3, 2));
        player2.hand.add(new Card(1, 2, 3, 4));
        player2.hand.add(new Card(1, 3, 2, 5));


        // Initialize the grid
        Card[][] grid = new Card[GRID_SIZE][GRID_SIZE];

        // Initialize the current player
        Player currentPlayer = player1;

        Scanner scanner = new Scanner(System.in);

        // Game loop
        while (true) {
            displayGrid(grid);
            makeMove(currentPlayer, grid, scanner);
            if (gameOver(grid)) {
                break;
            }
            currentPlayer = switchPlayer(currentPlayer, player1, player2);
        }

        determineWinner(grid, player1, player2);
        scanner.close();
    }

    private void displayGrid(Card[][] grid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Card card = grid[i][j];
                if (card == null) {
                    System.out.print("- ");
                } else {
                    System.out.print(card.top + " ");
                }
            }
            System.out.println();
        }
    }


    private void makeMove(Player currentPlayer, Card[][] grid, Scanner scanner) {
        System.out.println(currentPlayer.name + ", make your move.");
        // display the player's card
        for (int i = 0; i < currentPlayer.hand.size(); i++) {
            Card card = currentPlayer.hand.get(i);
            System.out.println(i + ": " + card.top + " " + card.right + " " + card.bottom + " " + card.left);
        }
        System.out.println("Enter the row and column where you want to place your card:");
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        System.out.println("Enter the card you want to play:");
        int cardIndex = scanner.nextInt();
        Card cardToPlay = currentPlayer.hand.get(cardIndex);

        // Place the card on the grid
        grid[row][col] = cardToPlay;

        // Check for captures
        for (int i = -1; i <= 1; i++) {  // anyway to reduce runtime?
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int capturedRow = row + i;
                int capturedCol = col + j;
                if (capturedRow >= 0 && capturedRow < GRID_SIZE && capturedCol >= 0 && capturedCol < GRID_SIZE) {
                    Card capturedCard = grid[capturedRow][capturedCol];
                    if (capturedCard != null) {
                        int capturedCardValue = 0;
                        if (i == -1) {
                            capturedCardValue = capturedCard.top;
                        } else if (i == 1) {
                            capturedCardValue = capturedCard.bottom;
                        } else if (j == -1) {
                            capturedCardValue = capturedCard.left;
                        } else if (j == 1) {
                            capturedCardValue = capturedCard.right;
                        }
                        if (capturedCardValue < cardToPlay.bottom) {
                            // Capture the card
                            grid[capturedRow][capturedCol] = cardToPlay;
                        }
                    }
                }
            }
        }
    }

    private boolean gameOver(Card[][] grid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private Player switchPlayer(Player currentPlayer, Player player1, Player player2) {
        if (currentPlayer == player1) {
            return player2;
        } else {
            return player1;
        }
    }

    private void determineWinner(Card[][] grid, Player player1, Player player2) {
        int player1Score = 0;
        int player2Score = 0;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Card card = grid[i][j];
                if (card == player1.hand.get(0)) {
                    player1Score++;
                } else {
                    player2Score++;
                }
            }
        }
        if (player1Score > player2Score) {
            System.out.println(player1.name + " wins!");
        } else if (player2Score > player1Score) {
            System.out.println(player2.name + " wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    public static void main(String[] args) {
        TripleTriad game = new TripleTriad();
        game.playGame();
    }

}

