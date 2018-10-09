package abitsmarter;
import java.awt.*;
import java.awt.event.InputEvent;
public class MouseBot
{
    private Board board;
    private Robot robot;
    public MouseBot(Board board) throws AWTException 
    {
        this.board = board;
        this.robot = new Robot();
    }
    public void play (String move)
    {
        String[] array = move.split("");
        Square s1 = this.board.getSquareByName(array[0]+array[1]);
        Square s2 = this.board.getSquareByName(array[2]+array[3]);
        int x1 = s1.getxCoordinate()+31;
        int y1 = s1.getyCoordinate()+31;
        int x2 = s2.getxCoordinate()+31;
        int y2 = s2.getyCoordinate()+31;
        this.robot.mouseMove(x1, y1);
        this.robot.mousePress(InputEvent.BUTTON1_MASK);
        this.robot.mouseMove(x2, y2);
        this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
        if(array.length==5)
        {
            String promote = array[4];
            switch (promote)
            {
                case "q":
                    this.robot.mousePress(InputEvent.BUTTON1_MASK);
                    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    break;
                case "n":
                    this.robot.mouseMove(x2, y2+64);
                    this.robot.mousePress(InputEvent.BUTTON1_MASK);
                    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    break;
                case "r":
                    this.robot.mouseMove(x2, y2+128);
                    this.robot.mousePress(InputEvent.BUTTON1_MASK);
                    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    break;
                case "b":
                    this.robot.mouseMove(x2, y2+192);
                    this.robot.mousePress(InputEvent.BUTTON1_MASK);
                    this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    break;
            }
        }
    }
}