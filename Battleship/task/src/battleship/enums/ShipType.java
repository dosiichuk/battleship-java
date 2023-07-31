package battleship.enums;

public enum ShipType {
    AIRCRAFT_CARRIER(5, 0, "Aircraft Carrier"),
    BATTLESHIP(4, 1, "Battleship"),
    SUBMARINE(3, 2, "Submarine"),
    CRUISER(3, 3, "Cruiser"),
    DESTROYER(2, 4, "Destroyer")
    ;
    private final int orderOfTakingPosition;
    private final int numberOfCells;
    private final String name;
    private ShipType(int numberOfCells, int orderOfTakingPosition, String name) {
        this.numberOfCells = numberOfCells;
        this.orderOfTakingPosition = orderOfTakingPosition;
        this.name = name;
    }

    public int getOrderOfTakingPosition() {
        return orderOfTakingPosition;
    }

    public int getNumberOfCells() {
        return numberOfCells;
    }

    public String getName() {
        return name;
    }
}
