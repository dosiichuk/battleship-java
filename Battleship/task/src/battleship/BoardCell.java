package battleship;
import battleship.enums.CellType;
import battleship.enums.Row;

public class BoardCell {
    private int rowNum;
    private int colNum;
    private CellType cellType;

    public BoardCell(int rowNum, int colNum, CellType cellType) {
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.cellType = cellType;
    }

    public BoardCell(String cellSignature, CellType cellType) throws Exception {
        boolean isCellSignatureValid = checkIfCellSignatureIsValid(cellSignature);
        if (isCellSignatureValid) {
            try {
                int[] rowAndColumnNumber = parseCellSignatureIntoRowAndColNumbers(cellSignature);
                this.rowNum = rowAndColumnNumber[0];
                this.colNum = rowAndColumnNumber[1];
            } catch (Exception e) {
                throw new Exception("Input cell is invalid");
            }
        } else {
            throw new Exception("Input cell is invalid.");
        }
        this.cellType = cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    public CellType getCellType() {
        return cellType;
    }

    public boolean checkIfBoardCellIsFree() {
        return this.cellType.equals(cellType.FOG);
    }

    public static int[] parseCellSignatureIntoRowAndColNumbers(String cellSignatureString) throws Exception {
        StringBuilder cellRowAndColumnString = new StringBuilder(cellSignatureString);
        int[] result = new int[2];
        result[0] = Row.getRowNumberFromRowLetter(cellRowAndColumnString.substring(0, 1));
        result[1] = Integer.parseInt(cellRowAndColumnString.substring(1));
        return result;
    }

    public static boolean checkIfCellSignatureIsValid(String cellSignatureString) {
        boolean isRowValid = false;
        boolean isColumnValid = false;
        StringBuilder cellSignatureStringBuilder = new StringBuilder(cellSignatureString);
        int [] cellRowAndColumnCharacters;
        try {
            cellRowAndColumnCharacters = parseCellSignatureIntoRowAndColNumbers(cellSignatureString);
        } catch (Exception e) {
            return false;
        }
        int colNumber = cellRowAndColumnCharacters[1];
        for (Row row: Row.values()) {
            if (row.getLetter().equals(cellSignatureStringBuilder.substring(0, 1))) {
                isRowValid = true;
            }
        }
        for (int i = 1; i < 11; i++) {
            if (i == colNumber) {
                isColumnValid = true;
            }
        }
        return isRowValid && isColumnValid;
    }

    public static double calculateDistanceBetweenTwoCells(BoardCell cellOne, BoardCell cellTwo) {
//        double distanceBetweenTwoCells = Math.sqrt(Math.pow(cellTwo.getColNum() -
//                cellOne.getColNum(), 2) + Math.pow(cellTwo.getRowNum()
//                - cellOne.getRowNum(), 2)) + 1;
        double distanceBetweenTwoCells = Math.max(Math.abs(cellTwo.getColNum() - cellOne.getColNum()), Math.abs(cellTwo.getRowNum() - cellOne.getRowNum())) + 1;
//        System.out.println(cellTwo.getColNum() + cellOne.getColNum() +" row " + cellTwo.getRowNum() + cellOne.getRowNum() + " " + distanceBetweenTwoCells);
        return distanceBetweenTwoCells;
    }

}
