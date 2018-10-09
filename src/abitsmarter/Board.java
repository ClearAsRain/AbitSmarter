package abitsmarter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
public class Board
{
    private static final String[] horizontal = {"a","b","c","d","e","f","g","h"};
    private static final String[] vertical =  {"1","2","3","4","5","6","7","8"};
    private int xCoordinate;
    private int yCoordinate;
    private int width;
    private int squareWidth;
    private int orientation;
    private Color lightSquare;
    private Color darkSquare;
    private Color lightMoveSquare;
    private Color darkMoveSquare;
    private ArrayList<Piece> whitePieces;
    private ArrayList<Piece> blackPieces;
    private ArrayList<Square> squares;
    private int[] blackPieceIdentifier;
    private int[] whitePieceIdentifier;
    private BufferedImage img;
    private Robot robot;
    public Board(String boardName) throws AWTException
    {
        switch (boardName)
        {
            case "lichess":
                this.width = 512;
                this.squareWidth = 64;
                this.darkSquare = new Color(181,136,99);
                this.lightSquare = new Color(240,217,181);
                this.darkMoveSquare = new Color(170,162,58);
                this.lightMoveSquare = new Color(205,210,106);
                this.blackPieceIdentifier = new int[] {2325,2132,2372,2661,2179,2758};
                this.whitePieceIdentifier = new int[] {2325,2109,2374,2661,2179,2758};
                break;
        }
        this.robot = new Robot();
        this.blackPieces = new ArrayList<>();
        this.whitePieces = new ArrayList<>();
        findBoard();
        setOrientation(boardName);
        setSquares();
        setBlackPieces();
        setWhitePieces();
    }
    private boolean match(int x, int y, Color c)
    {
        return this.img.getRGB(x, y) == c.getRGB();
    }
    private void findBoard ()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.img = this.robot.createScreenCapture(new Rectangle(0, 0, screenSize.width, screenSize.height));
        for(int x = 0; x < screenSize.width; x++)
        {
            for(int y = 0; y < screenSize.height; y++)
            {
                if(match(x,y,this.lightSquare))
                {
                    this.xCoordinate = x-this.width;
                    this.yCoordinate = y-this.width;
                }
            }
        }
    }
    private void setOrientation(String boardName)
    {
        switch (boardName)
        {
            case "lichess":
                int orientation;
                int w = this.squareWidth;
                int x = this.xCoordinate+2;
                int y = this.yCoordinate+2;
                int count = 0;
                for(int a = 0; a < w-2; a++)
                {
                    for(int b = 0; b < w-2; b++)
                    {
                        if(match(x+b,y+a,this.lightSquare))
                        {
                            count = count+1;
                        }
                    }
                }
                if(count == 2374)
                {
                    orientation = 1;
                }
                else
                {
                    orientation = 2;
                }
                this.orientation = orientation;
                break;
        }

    }
    private void setWhitePieces()
    {
        for(int i=0;i<8;i++)
        {
            this.whitePieces.add(new Piece(1,this.whitePieceIdentifier[5],"pawn",getSquareByName(horizontal[i]+"2")));
        }
        this.whitePieces.add(new Piece(1,this.whitePieceIdentifier[0],"king",getSquareByName("e1")));
        this.whitePieces.add(new Piece(1,this.whitePieceIdentifier[1],"queen",getSquareByName("d1")));
        this.whitePieces.add(new Piece(1,this.whitePieceIdentifier[2],"rook",getSquareByName("a1")));
        this.whitePieces.add(new Piece(1,this.whitePieceIdentifier[2],"rook",getSquareByName("h1")));
        this.whitePieces.add(new Piece(1,this.whitePieceIdentifier[3],"bishop",getSquareByName("c1")));
        this.whitePieces.add(new Piece(1,this.whitePieceIdentifier[3],"bishop",getSquareByName("f1")));
        this.whitePieces.add(new Piece(1,this.whitePieceIdentifier[4],"knight",getSquareByName("g1")));
        this.whitePieces.add(new Piece(1,this.whitePieceIdentifier[4],"knight",getSquareByName("b1")));

    }
    private void setBlackPieces()
    {
        for(int i=0;i<8;i++)
        {
            this.blackPieces.add(new Piece(2,this.blackPieceIdentifier[5],"pawn",getSquareByName(horizontal[i]+"7")));
        }
        this.blackPieces.add(new Piece(2,this.blackPieceIdentifier[0],"king",getSquareByName("e8")));
        this.blackPieces.add(new Piece(2,this.blackPieceIdentifier[1],"queen",getSquareByName("d8")));
        this.blackPieces.add(new Piece(2,this.blackPieceIdentifier[2],"rook",getSquareByName("a8")));
        this.blackPieces.add(new Piece(2,this.blackPieceIdentifier[2],"rook",getSquareByName("h8")));
        this.blackPieces.add(new Piece(2,this.blackPieceIdentifier[3],"bishop",getSquareByName("c8")));
        this.blackPieces.add(new Piece(2,this.blackPieceIdentifier[3],"bishop",getSquareByName("f8")));
        this.blackPieces.add(new Piece(2,this.blackPieceIdentifier[4],"knight",getSquareByName("g8")));
        this.blackPieces.add(new Piece(2,this.blackPieceIdentifier[4],"knight",getSquareByName("b8")));
    }
    private void setSquares ()
    {
        ArrayList<Square> squares = new ArrayList<>();
        Square square = null;
        int[] x = new int[8];
        int[] y = new int[8];
        for(int l = 0;l<8;l++)
        {
            x[l]=this.xCoordinate+(l*squareWidth);
            y[l]=this.yCoordinate+(l*squareWidth);
        }
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(this.orientation == 1)
                {
                    square = new Square(x[i],y[7-j],horizontal[i],vertical[j]);
                }
                else if(this.orientation == 2)
                {
                    square = new Square(x[i],y[j],horizontal[7-i],vertical[j]);
                }
                squares.add(square);
            }
        }
        this.squares = squares;
    }
     Square getSquareByName(String name)
    {
        Square square = null;
        for (Square lSquare : this.squares) {
            if (lSquare.getName().equals(name)) {
                square = lSquare;
            }
        }
        return square;
    }

    public int getxCoordinate()
    {
        return xCoordinate;
    }

    public int getyCoordinate()
    {
        return yCoordinate;
    }

    public int getWidth()
    {
        return width;
    }

    public int getSquareWidth()
    {
        return squareWidth;
    }

    public int getOrientation()
    {
        return orientation;
    }

    public Color getLightSquare()
    {
        return lightSquare;
    }

    public Color getDarkSquare()
    {
        return darkSquare;
    }

    public Color getLightMoveSquare()
    {
        return lightMoveSquare;
    }

    public Color getDarkMoveSquare()
    {
        return darkMoveSquare;
    }

    public ArrayList<Piece> getBlackPieces()
    {
        return blackPieces;
    }

    public ArrayList<Piece> getWhitePieces()
    {
        return whitePieces;
    }

    public ArrayList<Square> getSquares()
    {
        return squares;
    }

    public int[] getBlackPieceIdentifier()
    {
        return blackPieceIdentifier;
    }

    public int[] getWhitePieceIdentifier()
    {
        return whitePieceIdentifier;
    }
    public void removePiece (int color,Piece piece)
    {
        if(color ==1)
        {
            this.whitePieces.remove(piece);
        }
        else
        {
            this.blackPieces.remove(piece);
        }
    }
}