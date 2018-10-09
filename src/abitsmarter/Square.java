package abitsmarter;
public class Square {
    private int xCoordinate;
    private int yCoordinate;
    private String name;
    private String vertical;
    private String horizontal;
    public Square(int xCoordinate, int yCoordinate, String vertical, String horizontal)
    {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.name = vertical+horizontal;
        this.vertical = vertical;
        this.horizontal = horizontal;
    }
    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public String getName() {
        return name;
    }

    public String getVertical() {
        return vertical;
    }

    public String getHorizontal() {
        return horizontal;
    }
}