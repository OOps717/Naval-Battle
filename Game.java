import java.io.IOException;
import java.util.Scanner;

public class Game {

    public static boolean isInteger( String input ) { //Pass in string
        try { //Try to make the input into an integer
            Integer.parseInt( input );
            return true; //Return true if it works
        }
        catch( Exception e ) {
            System.out.println("You should choose 1 or 2 or 3 or 4!");
            return false; //If it doesn't work return false
        }
    }

    public static void main (String [] arg) throws InterruptedException, IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Hello ladies and gentlemen, welcome to the Battle ship!");
        System.out.println("Choose the mode of playing (1 - player vs computer, 2 - player vs player, 3 - player vs player (locally), 4 - player vs player (online))");
        String ch = scan.nextLine();
        int choice;
        while (!isInteger(ch) && (!ch.equals("1") || !ch.equals("2") || !ch.equals("3") || !ch.equals("4"))){
            System.out.println("Only 1,2,3 or 4");
            ch = scan.nextLine();
        }
        choice = Integer.parseInt(ch);
        switch (choice){
            case 1:
                PlayerVsBot player1 = new PlayerVsBot();
                player1.play();
                break;
            case 2:
                PlayerVsPlayer player2 = new PlayerVsPlayer();
                player2.play();
                break;
            case 3:
                char pp;
                System.out.println("want to create a game or search for one? (s - to create port, c - to connect to the port)");
                pp = scan.next().charAt(0);
                while (pp != 's' && pp != 'c'){
                    System.out.println("Only c or s!");
                    pp = scan.next().charAt(0);
                }
                if (pp == 's'){
                    Server s = new Server();
                    s.server();
                }
                else{
                    Client c = new Client();
                    c.client("localhost");
                }
                break;
            case 4:
                char ppOnline;
                String host;
                System.out.println("want to create a game or search for one? (s - to create port, c - to connect to the port)");
                ppOnline = scan.next().charAt(0);
                while (ppOnline != 's' && ppOnline != 'c'){
                    System.out.println("Only c or s!");
                    ppOnline = scan.next().charAt(0);
                }
                if (ppOnline == 's'){
                    Server s = new Server();
                    s.server();
                }
                else{
                    System.out.println("Then give me the host to connect");
                    host = scan.next();
                    Client c = new Client();
                    c.client(host);
                }
                break;
        }
    }
}
