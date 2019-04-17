import java.util.Scanner;

public class Game {

    public static boolean isInteger( String input ) { //Pass in string
        try { //Try to make the input into an integer
            Integer.parseInt( input );
            return true; //Return true if it works
        }
        catch( Exception e ) {
            System.out.println("You should choose 1 or 2 or 3!");
            return false; //If it doesn't work return false
        }
    }

    public static void main (String [] arg) throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Hello ladies and gentlemen, welcome to the Battle ship!");
        System.out.println("Choose the mode of playing (1 - player vs computer, 2 - player vs player, 3 - player vs player online)");
        String ch = scan.nextLine();
        int choice;
        while (!isInteger(ch) && (ch != "1" || ch!= "2" || ch != "3")){
            ch = scan.nextLine();
        }
        choice = Integer.parseInt(ch);
        if (choice == 1) {
            PlayerVsBot player = new PlayerVsBot();
            player.play();
        }
        else if (choice == 2) {
            PlayeVsPlayer player = new PlayeVsPlayer();
            player.play();
        }
    }
}
