package abitsmarter;
public class Piece
{
    private int color;
    private int identifier;
    private String name;
    private Square currentSquare;

    public Piece(int color, int identifier, String name, Square currentSquare) {
        this.color = color;
        this.identifier = identifier;
        this.name = name;
        this.currentSquare = currentSquare;
    }

    public int getColor() {
        return color;
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public Square getCurrentSquare() {
        return currentSquare;
    }

    public void setCurrentSquare(Square currentSquare) {
        this.currentSquare = currentSquare;
    }
}