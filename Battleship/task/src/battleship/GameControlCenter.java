package battleship;

import battleship.enums.ShotType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameControlCenter {
    private static Scanner scanner = new Scanner(System.in);
    private boolean isGameOn;
    private List<Player> playerList;
    private Player currentPlayer;

    public GameControlCenter() {
        this.playerList = new ArrayList<>();
        this.isGameOn = true;
        this.startGame();

    }

    public void startGame() {
        this.initPlayers();
        this.startBattle();
//        this.initBoard();
//        this.board.printBoardStatus(false);
//        this.initShips(this.board);
//        this.isGameOn = true;
//        System.out.println("\nThe game starts!\n");
//        this.board.printBoardStatus(true);
//        while (this.isGameOn) {
//            try {
//                Shot shot = new Shot(scanner, this.board);
//                if (shot.getShotType() == ShotType.HIT) {
//                    Ship.updateShipsAfterHit(board.getShips(), shot);
//                }
//            } catch (Exception e) {
//
//            }
//        }
    }

    public void initPlayers() {
        Player player1 = new Player("Player 1", false, scanner);
        playerList.add(player1);
        passMoveToAnotherPlayer(player1);
        Player player2 = new Player("Player 2", false, scanner);
        playerList.add(player2);
        passMoveToAnotherPlayer(player1);
    }

    public void startBattle() {
      while (isGameOn) {
          Player.printBoardStatus(this.currentPlayer, this.playerList);
          System.out.printf("\n%s, it's your turn:\n", this.currentPlayer.getName());
          try {
              Board opponentBoard = Player.getOpponent(this.currentPlayer, this.playerList).getBoard();
              Shot shot = new Shot(scanner, opponentBoard, currentPlayer, playerList);
//              if (shot.getShotType() == ShotType.HIT) {
//                  Ship.updateShipsAfterHit(opponentBoard.getShips(), shot);
//              }
            } catch (Exception e) {

            }
          passMoveToAnotherPlayer(Player.getOpponent(this.currentPlayer, this.playerList));
      }
    }

    public void passMoveToAnotherPlayer(Player nextPlayer) {
        if (playerList.size() == 1) {

        } else {
            this.currentPlayer = nextPlayer;
        }
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
    }

//    public void initBoard() {
//        this.board = new Board(scanner);
//    }

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
