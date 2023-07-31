package battleship;

import battleship.enums.CellType;
import battleship.enums.Row;
import battleship.enums.ShipType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
    private final int numRows = 10;
    private final int numCols = 10;
    private BoardCell[][] boardStatus;
    private List<Ship> ships;
    private Scanner scanner;

    public Board(Scanner scanner) {
        this.boardStatus = initBoard();
        this.scanner = scanner;
        this.ships = new ArrayList<>();
    }

    public BoardCell[][] initBoard() {
        BoardCell[][] boardStatus = new BoardCell[this.numRows][this.numCols];
        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numCols; j++) {
                boardStatus[i][j] = new BoardCell(i, j, CellType.FOG);
            }
        }
        return boardStatus;
    }

    public void markCellsOccupiedByShip(Ship ship) {
        BoardCell shipStartCell = ship.getShipStartCell();
        BoardCell shipEndCell = ship.getShipEndCell();
        int startColumn = Math.min(shipStartCell.getColNum(), shipEndCell.getColNum());
        int endColumn = Math.max(shipStartCell.getColNum(), shipEndCell.getColNum());
        int startRow = Math.min(shipStartCell.getRowNum(), shipEndCell.getRowNum());
        int endRow = Math.max(shipStartCell.getRowNum(), shipEndCell.getRowNum());
        for (int i = startRow - 1; i <= endRow - 1; i++) {
            for (int j = startColumn - 1; j <= endColumn - 1; j++) {
                this.getBoardStatus()[i][j].setCellType(CellType.SHIP);
            }
        }
    }

    public void printBoardStatus(boolean masked) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < this.numRows; i++) {
            StringBuilder rowString = new StringBuilder(Row.getRowLetterFromRowNumber(i + 1));
            for (int j = 0; j < this.numCols; j++) {
                CellType cellType = this.boardStatus[i][j].getCellType();
                String cellMark = masked && cellType.getCellMark().equals("O") ? "~" : cellType.getCellMark();
                rowString.append(" " + cellMark);
            }
            System.out.println(rowString);
        }
    }

    public void initShips() throws Exception {
        for (ShipType shipType: ShipType.values()) {
            boolean isShipSuccessFullyGenerated = false;
            System.out.println(String.format("\nEnter the coordinates of the %s (%d cells):\n",
                    shipType.getName(), shipType.getNumberOfCells()));
            while (!isShipSuccessFullyGenerated) {
                String[] shipCoordinates = scanner.nextLine().split(" ");
                System.out.println("\n");
                BoardCell shipStartCell;
                BoardCell shipEndCell;
                try {
                    shipStartCell = new BoardCell(shipCoordinates[0], CellType.FOG);
                    shipEndCell = new BoardCell(shipCoordinates[1], CellType.FOG);
                    Ship ship = new Ship(shipType, shipStartCell, shipEndCell, this);
                    this.ships.add(ship);
                    this.markCellsOccupiedByShip(ship);
                    this.printBoardStatus(false);
                    isShipSuccessFullyGenerated = true;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public BoardCell[][] getBoardStatus() {
        return boardStatus;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public Scanner getScanner() {
        return scanner;
    }
}
