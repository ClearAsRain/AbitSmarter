package abitsmarter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Scanner;
public class Engine {
    private String bestMove;
    public String getBestMove(String moves) throws IOException {
        this.bestMove = null;
        String localDir = System.getProperty("user.dir");
        String movestring = "position startpos moves "+moves+"\n"+"go\n";
        Process exe = Runtime.getRuntime().exec(localDir+"\\stockfish_8_x64.exe");
        OutputStream stdin = exe.getOutputStream();
        stdin.write(movestring.getBytes());
        stdin.flush();
        new Thread(new Runnable() {
            public void run() {
                InputStreamReader reader = new InputStreamReader(exe.getInputStream());
                Scanner scan = new Scanner(reader);
                while (scan.hasNextLine()) {
                    String s = scan.nextLine();
                    if (s.contains("bestmove")) {
                        bestMove = s.split(" ")[1];
                        break;
                    }
                }
                while (exe.isAlive()) {
                    try {
                        scan.close();
                        exe.getInputStream().close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    exe.destroy();
                }
            }
        }).start();
        return this.bestMove;
    }
}