import java.io.*;
import java.util.Scanner;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client
{
    private char[] split (String s) {
        char[] c = new char[10];
        for (int i = 0; i < s.length(); i++) {
            c[i] = s.charAt(i);
        }
        return c;
    }

    private static Socket socket;

    public void client()
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Give the key of the game:");
        int key = scan.nextInt();
        try
        {
            Socket socket = new Socket("localhost", key);
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("Connection established");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String msgin = "", msgout = "";
            while (!msgin.equals("bye")) {
                System.out.println("Give the position to attack");
                msgout = br.readLine();
                outputStream.writeUTF(msgout);
                msgin = inputStream.readUTF();
                char[] msg = split(msgin);
                System.out.println("Player 2 attacked: " + msg[0] + " " + msg[2]);
            }
        }
        catch (IOException e)
        {
            System.out.println("Exited, connection has been broken");
        }
        finally
        {
            //Closing the socket
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}