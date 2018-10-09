package abitsmarter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
public class Listener
{
    private ArrayList<String> moves;
    private ArrayList<Piece> blackPieces;
    private ArrayList<Piece> whitePieces;
    private ArrayList<Square> squares;
    private int[] blackId;
    private int[] whiteId;
    private Robot robot;
    private BufferedImage img;
    private Board board;
    private Engine stockFish;
    private MouseBot player;
    public Listener(String board) throws AWTException
    {
        this.board = new Board(board);
        this.moves = new ArrayList<>();
        this.robot = new Robot();
        this.stockFish = new Engine();
        this.player = new MouseBot(this.board);
        this.blackPieces = this.board.getBlackPieces();
        this.whitePieces = this.board.getWhitePieces();
        this.squares = this.board.getSquares();
        this.blackId = this.board.getBlackPieceIdentifier();
        this.whiteId = this.board.getWhitePieceIdentifier();
    }
    private boolean match(int x, int y, Color c)
    {
        return this.img.getRGB(x, y) == c.getRGB();
    }
    private void takePicture()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.img = robot.createScreenCapture(new Rectangle(0, 0, screenSize.width, screenSize.height));
    }
    private Square getSquareByName(String name)
    {
        Square square = null;
        for (Square lSquare : this.board.getSquares()) {
            if (lSquare.getName().equals(name)) {
                square = lSquare;
            }
        }
        return square;
    }
    private String[] getPromotionPiece (Square square)
    {
        String[] squareName = new String[2];
        int orientation;
        int w = this.board.getSquareWidth();
        int x = square.getxCoordinate()+2;
        int y = square.getyCoordinate()+2;
        int count = 0;
        for(int a = 0; a < w-2; a++)
        {
            for(int b = 0; b < w-2; b++)
            {
                if(match(x+b,y+a,this.board.getLightMoveSquare()) || match(x+b,y+a,this.board.getDarkMoveSquare()))
                {
                    count = count+1;
                }
            }
        }
        if(count == this.blackId[1] || count == this.whiteId[1])
        {
            squareName[0] = "queen";
            squareName[1] = "q";

        }
        else if(count == this.blackId[2] || count == this.whiteId[2])
        {
            squareName[0] = "rook";
            squareName[1] = "r";

        }
        else if(count == this.blackId[3] || count == this.whiteId[3])
        {
            squareName[0] = "bishop";
            squareName[1] = "b";
        }
        else if(count == this.blackId[4] || count == this.whiteId[4])
        {
            squareName[0] = "knight";
            squareName[1] = "n";
        }
        return  squareName;
    }
    public void moveListener () throws IOException
    {
        int orientation = this.board.getOrientation();
        if(orientation == 1)
        {
            if(this.moves.size() % 2 ==0)
            {
                whiteMoveMaker();
            }
            else if (this.moves.size() % 2 ==1)
            {
                moveDetection(2);
            }
        }
        else if (orientation == 2)
        {
            if(this.moves.size() % 2 ==0)
            {
                moveDetection(1);
            }
            else if (this.moves.size() % 2 ==1)
            {
                blackMoveMaker();
            }
        }
    }
    private void whiteMoveMaker () throws IOException
    {
        String moves = getMoves();
        String move = this.stockFish.getBestMove(moves);
        if(move != null && !move.isEmpty())
        {
            String[] split = move.split("");
            String target = split[2]+split[3];
            Square square = getSquareByName(target);
            for (int i =0;i<this.blackPieces.size();i++)
            {
                if(this.blackPieces.get(i).getCurrentSquare().equals(square))
                {
                    this.blackPieces.remove(i);
                }
            }
            this.player.play(move);
            this.moves.add(move);
        }
    }
    private void blackMoveMaker () throws IOException
    {
        String moves = getMoves();
        String move = this.stockFish.getBestMove(moves);
        if(move != null && !move.isEmpty())
        {
            String[] split = move.split("");
            String target = split[2]+split[3];
            Square square = getSquareByName(target);
            for (int i =0;i<this.whitePieces.size();i++)
            {
                if(this.whitePieces.get(i).getCurrentSquare().equals(square))
                {
                    this.whitePieces.remove(i);
                }
            }
            this.player.play(move);
            this.moves.add(move);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public String getMoves ()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (String move:this.moves)
        {
           stringBuilder.append(move+" ");
        }
        return stringBuilder.toString();
    }


    private void moveDetection (int color)
    {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        takePicture();
        ArrayList<Piece> pieces;
        int[] ids;
        ArrayList<Piece> oppositePieces;
        ArrayList<Square> moveSquares = new ArrayList<>();
        ArrayList<Piece> movePieces = new ArrayList<>();
        String move = null;
        if(color == 1)
        {
             ids = this.whiteId;
             pieces = this.whitePieces;
             oppositePieces = this.blackPieces;
        }
        else
        {
             ids = this.blackId;
             pieces = this.blackPieces;
             oppositePieces = this.whitePieces;
        }
        for (Piece piece:pieces)
        {
            if(isMoveSquare(piece.getCurrentSquare()))
            {
                moveSquares.add(piece.getCurrentSquare());
                movePieces.add(piece);
            }
        }
        if(moveSquares.size()==2)
        {
            if(moveSquares.contains(getSquareByName("h1")))
            {
                move = "e1g1";
                if(movePieces.get(0).getCurrentSquare().getName().equals("e1"))
                {
                    movePieces.get(0).setCurrentSquare(getSquareByName("g1"));
                    movePieces.get(1).setCurrentSquare(getSquareByName("f1"));
                }
                else
                {
                    movePieces.get(1).setCurrentSquare(getSquareByName("g1"));
                    movePieces.get(0).setCurrentSquare(getSquareByName("f1"));
                }
                this.moves.add(move);
            }
            else if(moveSquares.contains(getSquareByName("h8")))
            {
                move = "e8g8";
                if(movePieces.get(0).getCurrentSquare().getName().equals("e8"))
                {
                    movePieces.get(0).setCurrentSquare(getSquareByName("g8"));
                    movePieces.get(1).setCurrentSquare(getSquareByName("f8"));
                }
                else
                {
                    movePieces.get(1).setCurrentSquare(getSquareByName("g8"));
                    movePieces.get(0).setCurrentSquare(getSquareByName("f8"));
                }
                this.moves.add(move);
            }
            else if(moveSquares.contains(getSquareByName("a1")))
            {
                move = "e1c1";
                if(movePieces.get(0).getCurrentSquare().getName().equals("e1"))
                {
                    movePieces.get(0).setCurrentSquare(getSquareByName("c1"));
                    movePieces.get(1).setCurrentSquare(getSquareByName("d1"));
                }
                else
                {
                    movePieces.get(1).setCurrentSquare(getSquareByName("c1"));
                    movePieces.get(0).setCurrentSquare(getSquareByName("d1"));
                }
                this.moves.add(move);
            }
            else if(moveSquares.contains(getSquareByName("a8")))
            {
                move = "e8c8";
                if(movePieces.get(0).getCurrentSquare().getName().equals("e8"))
                {
                    movePieces.get(0).setCurrentSquare(getSquareByName("c8"));
                    movePieces.get(1).setCurrentSquare(getSquareByName("d8"));
                }
                else
                {
                    movePieces.get(1).setCurrentSquare(getSquareByName("c8"));
                    movePieces.get(0).setCurrentSquare(getSquareByName("d8"));
                }
                this.moves.add(move);
            }
        }
        else if (moveSquares.size()==1)
        {
            Square exception = moveSquares.get(0);
            for (Square square:this.squares)
            {
                if(isMoveSquare(square)&&!square.equals(exception))
                {
                    moveSquares.add(square);
                }
            }
            if(moveSquares.size()==2)
            {

                movePieces.get(0).setCurrentSquare(moveSquares.get(1));
                if((moveSquares.get(1).getVertical().equals("8") && movePieces.get(0).getName().equals("pawn")) || (moveSquares.get(1).getVertical().equals("1") && movePieces.get(0).getName().equals("pawn")))
                {
                    String[] promoted = getPromotionPiece(moveSquares.get(1));
                    int i = 1;
                    switch (promoted[0])
                    {
                        case "queen":
                            i = 1;
                            break;
                        case "rook":
                            i = 2;
                            break;
                        case "bishop":
                            i = 3;
                            break;
                        case "knight":
                            i = 4;
                            break;
                    }
                    move = moveSquares.get(0).getName()+moveSquares.get(1).getName()+promoted[1];
                    pieces.remove(movePieces.get(0));
                    pieces.remove(movePieces.get(0));
                    pieces.add(new Piece(color,ids[i],promoted[0],moveSquares.get(1)));
                }
                else
                {
                    move = moveSquares.get(0).getName()+moveSquares.get(1).getName();
                }
                this.moves.add(move);
            }    
        }
    }
    private boolean isMoveSquare (Square square) {
        int w = this.board.getSquareWidth();
        int x = square.getxCoordinate()+2;
        int y = square.getyCoordinate()+2;
        boolean sts = false;
        if(match(x,y,this.board.getLightMoveSquare()) || match(x,y,this.board.getDarkMoveSquare()))
        {
            sts = true;
        }
        return sts;
    }
}
