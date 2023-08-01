package battleship;

import java.util.List;
import java.util.Scanner;

public class Player {
    private final Board board;
    private final Scanner scanner;
    private final String name;
    private boolean isCurrentPlayer;

    public Player(String name, boolean isCurrentPlayer, Scanner scanner) {
        this.name = name;
        this.isCurrentPlayer = isCurrentPlayer;
        this.scanner = scanner;
        System.out.printf("%s, place your ships on the game field\n\n", this.name);
        this.board = new Board(scanner);
        this.board.printBoardStatus(false);
        try {
            this.board.initShips();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public Board getBoard() {
        return board;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public String getName() {
        return name;
    }

    public boolean isCurrentPlayer() {
        return isCurrentPlayer;
    }

    public void setCurrentPlayer(boolean currentPlayer) {
        isCurrentPlayer = currentPlayer;
    }

    public static void printBoardStatus(Player currentPlayer, List<Player> playerList) {
        Board currentPlayerBoard = currentPlayer.getBoard();
        Board opponentBoard = Player.getOpponent(currentPlayer, playerList).getBoard();
        opponentBoard.printBoardStatus(true);
        System.out.println("---------------------");
        currentPlayerBoard.printBoardStatus(false);
    }

    public static Player getOpponent(Player currentPlayer, List<Player> playerList) {
        return playerList.get(Math.abs(playerList.indexOf(currentPlayer) - 1));
    }
}
