package battleship;

import battleship.enums.ShipType;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private final ShipType shipType;
    private final BoardCell shipStartCell;
    private final BoardCell shipEndCell;
    private final List<BoardCell> shipCells;
    private final int shipRowStart;
    private final int shipRowEnd;
    private final int shipColStart;
    private final int shipColEnd;
    private final Board board;
    private final int shipLength;
    private int shipUnscathedLength;
    private final boolean isPossibleToCreateShip;

    public Ship(ShipType shipType, BoardCell shipStartCell, BoardCell shipEndCell, Board board) throws Exception {
        this.shipType = shipType;
        this.shipStartCell = shipStartCell;
        this.shipEndCell = shipEndCell;
        this.shipRowStart = Math.min(this.shipStartCell.getRowNum(), this.shipEndCell.getRowNum());
        this.shipRowEnd = Math.max(this.shipStartCell.getRowNum(), this.shipEndCell.getRowNum());
        this.shipColStart = Math.min(this.shipStartCell.getColNum(), this.shipEndCell.getColNum());
        this.shipColEnd = Math.max(this.shipStartCell.getColNum(), this.shipEndCell.getColNum());
        this.shipLength = shipType.getNumberOfCells();
        this.shipUnscathedLength = this.shipLength;
        this.board = board;
        this.shipCells = this.collectShipCells(this.board);
        try {
            this.isPossibleToCreateShip = this.isPossibleToCreateShip();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public boolean isPossibleToCreateShip() throws Exception {
        boolean areCellsOnTheSameAxis = areCellsOnTheSameAxis();
        boolean isDistanceBetweenCellsEqualToShipLength = isDistanceBetweenCellsEqualToShipLength();
        boolean isShipTooCloseFromAnotherOne = this.isShipTooCloseToAnotherOne(this.board);
        if (!areCellsOnTheSameAxis) {
            throw new Exception("Error! Wrong ship location! Try again:");
        }
        if (!isDistanceBetweenCellsEqualToShipLength) {
            throw new Exception(String.format("Error! Wrong length of the %s! Try again:", this.shipType.getName()));
        }
        if (isShipTooCloseFromAnotherOne) {
            throw new Exception("Error! You placed it too close to another one. Try again:");
        }
        return areCellsOnTheSameAxis && isDistanceBetweenCellsEqualToShipLength &&
                !isShipTooCloseFromAnotherOne;
    }

    public boolean areCellsOnTheSameAxis() {
        return this.shipStartCell.getColNum() == this.shipEndCell.getColNum() ||
                this.shipStartCell.getRowNum() == this.shipEndCell.getRowNum();
    }

    public boolean isDistanceBetweenCellsEqualToShipLength(){
        double distanceBetweenCells = BoardCell.calculateDistanceBetweenTwoCells(this.shipStartCell, this.shipEndCell);
        return (int) distanceBetweenCells == this.shipType.getNumberOfCells();
    }

    public boolean isShipTooCloseToAnotherOne(Board board) {
        if (board.getShips().size() == 0) {
            return false;
        }
        for (Ship ship: board.getShips()) {
          List<BoardCell> listOfCellsOfComparedShip = ship.getShipCells();
          for (BoardCell cellOfThisShip: this.getShipCells()) {
              for (BoardCell cellOfComparedShip: listOfCellsOfComparedShip) {
//                  System.out.println("Distance"+ BoardCell.calculateDistanceBetweenTwoCells(cellOfThisShip, cellOfComparedShip));
                  if (BoardCell.calculateDistanceBetweenTwoCells(cellOfThisShip, cellOfComparedShip) <= 2) {
                      return true;
                  }
              }
          }
        }
        return false;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public BoardCell getShipStartCell() {
        return shipStartCell;
    }

    public BoardCell getShipEndCell() {
        return shipEndCell;
    }

    public Board getBoard() {
        return board;
    }

    public int getShipRowStart() {
        return shipRowStart;
    }

    public int getShipRowEnd() {
        return shipRowEnd;
    }

    public int getShipColStart() {
        return shipColStart;
    }

    public int getShipColEnd() {
        return shipColEnd;
    }

    public List<BoardCell> collectShipCells(Board board) {
        List<BoardCell> listOfShipCells = new ArrayList<BoardCell>();
        for (int i = this.shipRowStart; i <= this.shipRowEnd; i++) {
            for (int j = this.shipColStart; j <= this.shipColEnd; j++) {
                listOfShipCells.add(board.getBoardStatus()[i - 1][j - 1]);
            }
        }
        return listOfShipCells;
    }

    public static void updateShipsAfterHit(List<Ship> ships, Shot shot) {
        BoardCell hitCell = shot.getShotCell();
        for (Ship ship: ships) {
            for (BoardCell boardCell: ship.getShipCells()) {
                if (boardCell.equals(hitCell)) {
                    ship.setShipUnscathedLength(ship.getShipUnscathedLength() - 1);
                }
            }
            if (ship.getShipUnscathedLength() == 0) {
                removeShipFromShipList(ships, ship);
            }
            if (ships.size() == 0) {
                GameControlCenter.claimVictory();
            }
        }
    }

    public static void removeShipFromShipList(List<Ship> ships, Ship ship) {
        ships.remove(ship);
    }

    public List<BoardCell> getShipCells() {
        return shipCells;
    }

    public void setShipUnscathedLength(int shipUnscathedLength) {
        this.shipUnscathedLength = shipUnscathedLength;
    }

    public int getShipLength() {
        return shipLength;
    }

    public int getShipUnscathedLength() {
        return shipUnscathedLength;
    }
}
