package battleship.enums;

public enum CellType {
    FOG("~"),
    SHIP("O"),
    HIT("X"),
    MISS("M");
    private String cellMark;

    CellType(String cellMark) {
        this.cellMark = cellMark;
    }

    public String getCellMark() {
        return cellMark;
    }
}
