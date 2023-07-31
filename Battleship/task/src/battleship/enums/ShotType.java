package battleship.enums;

public enum ShotType {
    HIT("HIT"),
    MISS("MISS");
    private final String shotTypeLabel;
    private ShotType(String shotTypeLabel) {
        this.shotTypeLabel = shotTypeLabel;
    }
}
