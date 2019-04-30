import java.io.*;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server
{
    private char[][] p1, p2;
    private int player2ShipsLeft = 20;
    private PlayerImpl player1_board, player2_board;

    public Server() {
        p1 = new char[10][10];
        p2 = new char[10][10];
        player1_board = new PlayerImpl(p1);
        player2_board = new PlayerImpl(p2);
    }

    private char[] split (String s) {
        char[] c = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            c[i] = s.charAt(i);
        }
        return c;
    }

    private char[][] splitToBoard (String s) {
        char[][] c = new char[10][10];
        int k = 0;
        for (int i = 0; i < 10; i++) {
            for (int j =0; j < 10; j++){
                c[i][j] = s.charAt(k);
                k++;
            }
        }
        return c;
    }

    private static Socket socket;

    public void server() throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        int shipsLeft = 20, yourShipsLeft = 20;
        boolean check = true;

        System.out.println("Nice choice! Waiting for someone to connect...");

        try {
            ServerSocket server = new ServerSocket(25000);
            socket = server.accept();
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("Someone has connected, game starts");
            String msgin = "", msgout = "";
            System.out.println("Give me your name svp:");
            String name = scan.next();
            System.out.println("Well, for now, place your ships");
            player1_board.printBoard(name);
            p1 = player1_board.place(name);
            System.out.println("Need to wait until another player finishes the placement");

            //Sending and getting boards ----------------------------------------
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    msgout += p1[i][j];
                }
            }
            outputStream.writeUTF(msgout);
            outputStream.flush();
            msgin = inputStream.readUTF();
            char[][] p2Real = splitToBoard(msgin);
            //--------------------------------------------------------------------

            TimeUnit.SECONDS.sleep(1);
            System.out.println("Game starts");
            TimeUnit.SECONDS.sleep(2);

            while (true) {
                System.out.println("Waiting for player 2 to attack");

                msgin = inputStream.readUTF();
                char[] msg = split(msgin);

                if (msg[1] == '0') {
                    System.out.println("Player 2 attacked: " + msg[0] + msg[1] + " " + msg[3]); //if Vertical position is 10
                    if (p1[10][msg[3] - 97] == '#') { //if Player 2 hit
                        System.out.println("Ouch");
                        p1[10][msg[3] - 97] = 'X';
                        player1_board.printBoard(name);
                        yourShipsLeft--;
                        player2_board.printBoard("2");
                        if (player2ShipsLeft == 0) {
                            System.out.println("Game over, better luck next time");
                            break;
                        }
                        check = false;
                    } else if (p1[10][msg[3] - 97] == '-') { //if miss
                        System.out.println("Lucky!");
                        p1[10][msg[3] - 97] = 'O';
                        player1_board.printBoard(name);
                        player2_board.printBoard("2");
                        check = true;
                    }
                } else {
                    System.out.println("Player 2 attacked: " + msg[0] + " " + msg[2]); //Same if vertical direction <10
                    if (p1[msg[0] - '1'][msg[2] - 97] == '#') {
                        System.out.println("Ouch");
                        p1[msg[0] - '1'][msg[2] - 97] = 'X';
                        player1_board.printBoard(name);
                        yourShipsLeft--;
                        player2_board.printBoard("2");
                        if (player2ShipsLeft == 0) System.out.println("Player 2 has won, congrats");
                        check = false;
                    } else if (p1[msg[0] - '1'][msg[2] - 97] == '-') {
                        System.out.println("Lucky!");
                        p1[msg[0] - '1'][msg[2] - 97] = 'O';
                        player1_board.printBoard(name);
                        player2_board.printBoard("2");
                        check = true;
                    }
                }
                if (yourShipsLeft == 0){ //Case of loosing
                    System.out.println("Game over, better luck next time!");
                    break;
                }


                while (check) {
                    System.out.println("Give the position to attack on Vertical axis (or looser for exiting)");//Checking coordinates after
                    String yAx = scan.next();
                    while (!player1_board.isInteger(yAx)) {
                        yAx = scan.next();
                    }

                    int y = Integer.parseInt(yAx) - 1;
                    System.out.println("And the position on horizontal (a-j)");
                    char xAx = scan.next().charAt(0);
                    int x = Character.toLowerCase(xAx - 97);

                    if (y > 9 || y < 0 || x > 9 || x < 0) {
                        System.out.println("Out of bounds, try again");
                        continue;
                    }  //Attacking cells and checks if hit or not
                    else if (p2[y][x] == 'X' || p2[y][x] == 'O') {
                        System.out.println("You've already check this cell, try again");
                        player2_board.printBoard("2");
                        continue;
                    } else if (p2Real[y][x] == '-' && p2[y][x] != 'O') {
                        System.out.println("Miss :(");
                        p2[y][x] = 'O';
                        player2_board.printBoard("2");
                        check = false;
                    } else if (p2Real[y][x] == '#' && p2[y][x] != 'X') {
                        System.out.println("Hit! Continue in the same way.");
                        p2[y][x] = 'X';
                        player2_board.printBoard("2");
                        shipsLeft--;
                    }

                    player1_board.printBoard(name);
                    msgout = yAx + " " + xAx;
                    outputStream.writeUTF(msgout);
                    outputStream.flush();
                }

                if (shipsLeft == 0) {  //Case if player 1 hit all the ships
                    System.out.println("You won! Congrats!");
                    break;
                }
            }
        }catch (IOException e){
            System.out.println("Exited");
        } finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e){}
        }
    }
}
