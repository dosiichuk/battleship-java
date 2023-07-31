package battleship;

import battleship.enums.ShotType;

import java.util.Scanner;

public class GameControlCenter {
    public static Scanner scanner = new Scanner(System.in);
    public Board board;
    public boolean isGameOn;

    public GameControlCenter() {
        this.startGame();
    }

    public void startGame() {
        this.initBoard();
        this.board.printBoardStatus(false);
        this.initShips(this.board);
        this.isGameOn = true;
        System.out.println("\nThe game starts!\n");
        this.board.printBoardStatus(true);
        while (this.isGameOn) {
            try {
                Shot shot = new Shot(scanner, this.board);
                if (shot.getShotType() == ShotType.HIT) {
                    Ship.updateShipsAfterHit(board.getShips(), shot);
                }
            } catch (Exception e) {

            }
        }
    }

    public void initBoard() {
        this.board = new Board(scanner);
    }

    public void initShips(Board board) {
        try {
            board.initShips();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void claimVictory() {
        System.out.println("You sank the last ship. You won. Congratulations!");
        System.exit(0);
    }
}
