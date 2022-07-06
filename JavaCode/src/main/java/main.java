import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class main {
    ;
    public static JDA bot;
    public static Dotenv dotenv = Dotenv.load();
    public static BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    //create client socket, connect to server
    public static Socket clientSocket;

    static {

        try {
            System.out.println("starting bot...");
            bot = JDABot.startbot();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        Variables.intArrayLists();
        bootUP("args");


    }

    public static void bootUP(String args) throws IOException, InterruptedException {
        Runtime rt = Runtime.getRuntime();



        for (int i = 0; i < 50; i++) {
            rt.exec("taskkill /F /IM goalc.exe");
            rt.exec("taskkill /F /IM gk.exe");
        }
        TimeUnit.SECONDS.sleep(3);
        //rt.exec("cmd.exe cd C:\\Users\\NinjaPC\\Desktop\\opengoal\\unmodified releases\\newputty\\jak-project && task repl");

        // directory from where the program was launched
        String dir = System.getProperty("user.dir");
        dir = dir.replace("\\JavaCode","");

        File file = new File(dir +"\\goalc.exe");
        Desktop.getDesktop().open(file);
        file = new File(dir +"\\launchgame.bat");
        Desktop.getDesktop().open(file);

        TimeUnit.SECONDS.sleep(3);
        clientSocket = new Socket("127.0.0.1", 8181);
        TimeUnit.SECONDS.sleep(5);
        main.runCommand("(lt)");
        // TimeUnit.SECONDS.sleep(15);
        main.runCommand("(mi)");
        // TimeUnit.SECONDS.sleep(10);


        runCommand("(lt)");
        runCommand("(mi)");
        runCommand("(send-event *target* 'get-pickup (pickup-type eco-red) 5.0)");
        runCommand("(set! *cheat-mode* #f)");
        runCommand("(set! *debug-segment* #f)");

    }

    public static void runCommand(String args) {
        String temp;
        String displayBytes;
        try {
            //create input stream


            //create output stream attached to socket
            DataOutputStream outToServer =
                    new DataOutputStream(clientSocket.getOutputStream());


            //create input stream attached to socket
            BufferedReader inFromServer =
                    new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // temp = inFromUser.readLine();
            String replCommand = "(set! (-> *TARGET-bank* wheel-flip-dist) (meters 100))";
            replCommand = args;

            //send line to server

            byte[] data = replCommand.getBytes("UTF-8");
            ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + data.length);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            // this is the fix
            buffer.putInt(data.length);
            buffer.putInt(10);

            buffer.put(data);
            System.out.println(buffer.array());
            outToServer.write(buffer.array());

            System.out.println("sent this thing");


            System.out.println(clientSocket.isConnected());


            System.out.println("Sent " + replCommand + " to Repl server");
            if(Commands.event != null) {
                Commands.event.getChannel().sendMessage("Sent " + replCommand + " to Repl server").queue();
            }
            //read line from server
            String modifiedSentence = "";
         //   while ((modifiedSentence = inFromServer.readLine()) != null) {
          //      System.out.println("FROM SERVER: " + modifiedSentence);
           //     modifiedSentence=null;
          //  }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
