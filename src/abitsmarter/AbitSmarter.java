package abitsmarter;
import java.awt.AWTException;
import java.io.IOException;
public class AbitSmarter
{
    public static void main(String[] args) throws AWTException, IOException
    {
        Listener listener = new Listener("lichess");
        while (true)
        {
            listener.moveListener();
        }
    }  
}
