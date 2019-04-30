import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class PlayerVsPlayer {

    private char[][] p1, p2, p1ToShow, p2ToShow;
    private int player1ShipsLeft = 20;
    private int player2ShipsLeft = 20;
    private boolean player1Turn = true; //To check whose turn
    private PlayerImpl player1_board, player2_board, player1_boardToShow, player2_boardToshow;


    public PlayerVsPlayer() {
        p1 = new char[10][10];
        p2 = new char[10][10];
        p1ToShow = new char[10][10];
        p2ToShow = new char[10][10];
        player1_board = new PlayerImpl(p1);
        player2_board = new PlayerImpl(p2);
        player1_boardToShow = new PlayerImpl(p1ToShow);
        player2_boardToshow = new PlayerImpl(p2ToShow);
    }


    public void play() throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Hello guys! Give me your names!");
        System.out.println("Let's start with player 1, give me your name and place the ships");

        String name1 = scan.nextLine();
        player1_board.printBoard(name1);
        p1 = player1_board.place(name1);

        for (int i = 0;i < 70; i++) System.out.println();
        System.out.println("\nNow with player 2, give me your name and place the ship");

        String name2 = scan.nextLine();
        player2_board.printBoard(name2);
        p2 = player2_board.place(name2);

        System.out.println("We are starting the game, just need 2 second (literally)");
        TimeUnit.SECONDS.sleep(2);

        for (int i = 0;i < 70; i++) System.out.println();
        for (int i = 0; i < 200; i++){
            while (player1Turn){
                System.out.println(name1 + "'s turn to attack. Give the position to attack");
                System.out.println("On vertical axis");
                String yAx = scan.nextLine();
                while (!player1_board.isInteger(yAx)){
                    yAx = scan.nextLine();
                }
                int y = Integer.parseInt(yAx) - 1;
                System.out.println("On horizontal axis (a-j)");
                int x = Character.toLowerCase(scan.next().charAt(0)) - 97;

                if (y > 9 || y < 0 || x > 9 || x < 0) {
                    System.out.println("Out of bounds, try again");
                    i--;
                    continue;
                } else if (p2[y][x] == '#' && p2ToShow[y][x] != 'X') {  //If everything is okay and Player hit
                    System.out.println("Hit! Continue in the same way.");
                    p2ToShow[y][x] = 'X';
                    player2_boardToshow.printBoard(name2);
                    player2ShipsLeft--;
                    player1_board.printBoard(name1);
                    if (player2ShipsLeft == 0) {
                        System.out.println(name1 + " has won, congrats");//If Player 2 has no ships left
                        i = 200;
                        break;
                    }
                } else if (p2[y][x] == '-' && p2ToShow[y][x] != 'O') { //If miss
                    System.out.println("Miss :(");
                    p2ToShow[y][x] = 'O';
                    player2_boardToshow.printBoard(name2);
                    player1Turn = false;
                    player1_board.printBoard(name1);
                    TimeUnit.SECONDS.sleep(3);
                    for(int j = 0; j < 70; j++) System.out.println(); //To hide the board in case if miss, change turns
                } else if (p2[y][x] == 'O' || p2[y][x] == 'X') {
                    System.out.println("You already attacked this point, try again");
                    player2_boardToshow.printBoard(name2);
                    i--;
                    player1_board.printBoard(name1);
                }
            }
            while (!player1Turn){ //Almost same actions for player 2
                System.out.println(name2 + "'s turn to attack. Give the position to attack");
                System.out.println("On vertical axis");
                String yAx = scan.nextLine();
                while (!player2_board.isInteger(yAx)){
                    yAx = scan.nextLine();
                }
                int y = Integer.parseInt(yAx) - 1;
                System.out.println("On horizontal axis (a-j)");
                int x = Character.toLowerCase(scan.next().charAt(0)) - 97;

                if (y > 9 || y < 0 || x > 9 || x < 0) {
                    System.out.println("Out of bounds, try again");
                    i--;
                    continue;
                } else if (p1[y][x] == '#' && p1ToShow[y][x] != 'X') {
                    System.out.println("Hit! Continue in the same way.");
                    p1ToShow[y][x] = 'X';
                    player1_boardToShow.printBoard(name1);
                    player1ShipsLeft--;
                    player2_board.printBoard(name2);
                    if (player1ShipsLeft == 0) {
                        System.out.println(name2 + " has won, congrats");
                        i=200;
                        break;
                    }
                } else if (p1[y][x] == '-' && p1ToShow[y][x] != 'O') {
                    System.out.println("Miss :(");
                    p1ToShow[y][x] = 'O';
                    player1_boardToShow.printBoard(name1);
                    player1Turn = true;
                    player2_board.printBoard(name2);
                    TimeUnit.SECONDS.sleep(3);
                    for(int j = 0; j < 70; j++) System.out.println();
                } else if (p1[y][x] == 'O' || p1[y][x] == 'X') {
                    System.out.println("You already attacked this point, try again");
                    player1_boardToShow.printBoard(name1);
                    i--;
                    player2_board.printBoard(name2);
                }
            }
        }
    }
}
