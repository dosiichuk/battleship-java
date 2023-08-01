package battleship;

import battleship.enums.CellType;
import battleship.enums.ShipType;
import battleship.enums.ShotType;

import java.util.List;
import java.util.Scanner;

public class Shot {
    public final Scanner scanner;
    public final Board board;
    public final Player player;
    public final List<Player> playerList;
    public BoardCell shotCell;
    public boolean isValidShot = false;
    public boolean isShipSunk = false;
    public ShotType shotType;

    public Shot(Scanner scanner, Board board, Player player, List<Player> playerList) {
        this.scanner = scanner;
        this.board = board;
        this.player = player;
        this.playerList = playerList;
        while (!this.isValidShot) {
            try {
                this.shotCell = this.identifyTargetBoardCell();
                boolean isValidShot = this.isShotValid(shotCell);
                if (isValidShot) {
                    this.processShotAndMarkCell(shotCell);
                    this.isValidShot = true;
                }
            } catch (Exception e) {
                System.out.println("\n" + e.getMessage() + "\n");
            }
        }
    }

    public String takeUserInput() {
        String userInput = this.scanner.nextLine();
        return userInput;
    }

    public boolean isShotValid(BoardCell shotCell) throws Exception {
        try {
            boolean isShotValid = shotCell.getCellType().equals(CellType.FOG) ||
                    shotCell.getCellType().equals(CellType.SHIP) ||
                    shotCell.getCellType().equals(CellType.HIT) ||
                    shotCell.getCellType().equals(CellType.MISS);
            if (isShotValid) {
                return true;
            } else {
                throw new Exception("Error! You entered the wrong coordinates! Try again:");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void processShotAndMarkCell(BoardCell shotCell) {
        if (shotCell.getCellType().equals(CellType.FOG) || shotCell.getCellType().equals(CellType.MISS)) {
            shotCell.setCellType(CellType.MISS);
            this.setShotType(ShotType.MISS);
//            this.board.printBoardStatus(true);
            System.out.println("You missed.");
//            this.board.printBoardStatus(false);
        } else if (shotCell.getCellType().equals(CellType.SHIP) || shotCell.getCellType().equals(CellType.HIT)) {
            shotCell.setCellType(CellType.HIT);
            this.setShotType(ShotType.HIT);

            Ship.updateShipsAfterHit(Player.getOpponent(this.player, this.playerList).getBoard().getShips(), this);
            if (this.isShipSunk) {
                System.out.println("You sank a ship!");
            } else {
                System.out.println("You hit a ship!");
            }
        }
    }

    public BoardCell identifyTargetBoardCell() throws Exception {
        String cellSignature = this.takeUserInput();
        boolean isCellSignatureValid = BoardCell.checkIfCellSignatureIsValid(cellSignature);
        if (!isCellSignatureValid) {
            throw new Exception("Error! You entered the wrong coordinates! Try again:");
        }
        int[] cellCoordinates = BoardCell.parseCellSignatureIntoRowAndColNumbers(cellSignature);
        BoardCell[][] boardCells = this.board.getBoardStatus();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (boardCells[i][j].getRowNum() == cellCoordinates[0] - 1 && boardCells[i][j].getColNum() == cellCoordinates[1] - 1) {
                    return boardCells[i][j];
                } else {
                    continue;
                }
            }
        }
        throw new Exception("Error! You entered the wrong coordinates! Try again:");
    }

    public void setShotType(ShotType shotType) {
        this.shotType = shotType;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public Board getBoard() {
        return board;
    }

    public BoardCell getShotCell() {
        return shotCell;
    }

    public boolean isValidShot() {
        return isValidShot;
    }

    public ShotType getShotType() {
        return shotType;
    }

    public void setShotCell(BoardCell shotCell) {
        this.shotCell = shotCell;
    }

    public void setValidShot(boolean validShot) {
        isValidShot = validShot;
    }

    public void setShipSunk(boolean shipSunk) {
        isShipSunk = shipSunk;
    }
}
