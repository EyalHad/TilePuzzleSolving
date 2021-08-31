package Node;

public class Location {

    public int x;
    public int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location(Location other) {
        this.x = other.x;
        this.y = other.y;
    }
}
