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
    //Need to start the discord bot up to listen for commands.
    static {
        try {
            System.out.println("starting bot...");
            bot = JDABot.startbot();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Variables.intArrayLists(); //easier than defining the list lol
        bootUP("args"); //start the Discord crowd control.

    }

    public static void bootUP(String args) throws IOException, InterruptedException {
        Runtime rt = Runtime.getRuntime();
        //Create a runtime and try to close any instance of open goal.

        // If you change this variable the game will try more or less to close itself on reboot.
        //A lower number means restarts would be faster.
        //A Higher number means restarts would be more consistant.
        int closeAmount = 6;

        for (int i = 0; i < closeAmount; i++) {
            rt.exec("taskkill /F /IM goalc.exe");
            rt.exec("taskkill /F /IM gk.exe");
            rt.exec("taskkill /F /IM cmd.exe");
            TimeUnit.SECONDS.sleep(1);
        }



        // directory from where the program was launched
        String dir = System.getProperty("user.dir");
        String root = dir.replace("\\JavaCode","");

        File file = new File(root +"\\goalc.exe");
        Desktop.getDesktop().open(file);
        file = new File(root +"\\launchgame.bat");
        Desktop.getDesktop().open(file);
        //Wait a certain amount of time for goalc.exe
        // to be ready before trying to connect to it
        TimeUnit.SECONDS.sleep(3);
        clientSocket = new Socket("127.0.0.1", 8181);
       // TimeUnit.SECONDS.sleep(5); <-- don't think this is needed.
        runCommand("(lt)");
        runCommand("(mi)");

        //Play something visually to the user to let them know they are connected.
        runCommand("(send-event *target* 'get-pickup (pickup-type eco-red) 5.0)");
        runCommand("(dotimes (i 1) (sound-play-by-name (static-sound-name \"cell-prize\") (new-sound-id) 1024 0 0 (sound-group sfx) #t))");

        //Do "startup" commands here can be anything, for example we disable debug mode no stinky cheaters here
        runCommand("(set! *cheat-mode* #f)");
        runCommand("(set! *debug-segment* #f)");

    }

    public static void runCommand(String args) {

        try {

            if (!clientSocket.isConnected()){
                System.out.println("ERROR: We are not connected to the REPL");
            };

            //create output stream attached to socket
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            //create input stream attached to socket
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String replCommand = args;

            //send line to server
            byte[] data = replCommand.getBytes("UTF-8");
            ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + data.length);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            // this is the fix
            buffer.putInt(data.length);
            buffer.putInt(10);
            buffer.put(data);
            outToServer.write(buffer.array());


            System.out.println("Sent " + replCommand + " to REPL server");
            if(Commands.event != null) {
                Commands.event.getChannel().sendMessage("Sent ```lisp\n " + replCommand + "``` to REPL server by `" + Commands.event.getMember().getEffectiveName()+"`").queue();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
