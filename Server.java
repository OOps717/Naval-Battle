import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server
{

    private char[] split (String s) {
        char[] c = new char[10];
        for (int i = 0; i < s.length(); i++) {
            c[i] = s.charAt(i);
        }
        return c;
    }


    private static Socket socket;

    public void server()
    {
        //Scanner scan = new Scanner(System.in);
        Random rnd = new Random();
        int key = rnd.nextInt(25000);
        System.out.println("Key is " + key);
        try
        {
            ServerSocket server = new ServerSocket(key);
            Socket socket = server.accept();
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("Someone has connected");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String msgin = "", msgout = "";
            while (!msgin.equals("bye")) {
                msgin = inputStream.readUTF();
                char[] msg = split(msgin);
                System.out.println("Player 2 attacked: " + msg[0] + " " + msg[2]);

                System.out.println("Give the position to attack");
                msgout = br.readLine();
                outputStream.writeUTF(msgout);
                outputStream.flush();
            }
            //socket.close();
        }
        catch (IOException e)
        {
            System.out.println("Exited");
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e){}
        }
    }
}
