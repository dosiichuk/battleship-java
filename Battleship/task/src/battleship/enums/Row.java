package battleship.enums;

public enum Row {
    A("A", 1),
    B("B", 2),
    C("C", 3),
    D("D", 4),
    E("E", 5),
    F("F", 6),
    G("G", 7),
    H("H", 8),
    I("I",9),
    J("J", 10);
    private String Letter;
    private int number;

    Row(String letter, int number) {
        Letter = letter;
        this.number = number;
    }

    public String getLetter() {
        return Letter;
    }

    public int getNumber() {
        return number;
    }

    public static String getRowLetterFromRowNumber(int rowNumber) {
        String rowLetter = "A";
        for (Row row : Row.values()) {
            if (row.getNumber() == rowNumber) {
                rowLetter = row.getLetter();
                return row.getLetter();
            }
        }
        return rowLetter;
    }

    public static int getRowNumberFromRowLetter(String rowLetter) {
        int rowNumber = 0;
        for (Row row : Row.values()) {
            if (row.getLetter().equals(rowLetter)) {
                rowNumber = row.getNumber();
                return rowNumber;
            }
        }
        return rowNumber;
    }

}
