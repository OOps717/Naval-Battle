import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class PlayerVsBot {

    private char[][] b;
    private char[][] bToShow;
    private char[][] p;
    private int shipsLeft = 20;
    private int playersShipsLeft = 20;
    private boolean yourTurn = true;
    PlayerImpl player_board;
    AIplaceImpl board;
    AIplaceImpl boardToShow;

    public PlayerVsBot() {
        b = new char[10][10];
        bToShow = new char[10][10];
        p = new char [10][10];
        player_board = new PlayerImpl(p);
        board = new AIplaceImpl(b);
        boardToShow = new AIplaceImpl(bToShow);
    }

    public void play() throws InterruptedException {
        Random rnd = new Random();
        Scanner scan = new Scanner(System.in);
        System.out.println("Hey there! Give me your beautiful name");
        String name = scan.nextLine();

        player_board.printBoard("Sanan");
        PlayerImpl player_board = new PlayerImpl(p);// player board
        AIplaceImpl board = new AIplaceImpl(b);// computer board real
        AIplaceImpl boardToShow = new AIplaceImpl(bToShow);


        p = player_board.Play(name);
        System.out.println("Are you ready to play?(y/n)");
        if (Character.toLowerCase(scan.next().charAt(0)) == 'y') {
            b = board.randomPlacement();

            System.out.println();

            boardToShow.printBoard();

            for (int i = 0; i < 200; i++){
                while(yourTurn) {
                    System.out.println("Your turn to attack. Give the position to attack");
                    System.out.println("On vertical axis (1-10):");
                    int y = scan.nextInt() - 1;
                    System.out.println("On horizontal axis (a-j):");
                    int x = Character.toLowerCase(scan.next().charAt(0)) - 97;

                    if (y > 9 || y < 0 || x > 9 || x < 0) {
                        System.out.println("Out of bounds, try again");
                        i--;
                        yourTurn = true;
                        continue;
                    } else if (b[y][x] == '#' && bToShow[y][x] != 'X') {
                        System.out.println("Hit! Continue in the same way.");
                        bToShow[y][x] = 'X';
                        boardToShow.printBoard();
                        shipsLeft--;
                        if (shipsLeft == 0) {
                            System.out.println(" You win congrats");
                            break;
                        }
                        yourTurn = true;
                    } else if (b[y][x] == '-' && bToShow[y][x] != 'O') {
                        System.out.println("Miss :(");
                        bToShow[y][x] = 'O';
                        boardToShow.printBoard();
                        yourTurn = false;
                    } else if (b[y][x] == 'O' || b[y][x] == 'X') {
                        System.out.println("You already attacked this point, try again");
                        boardToShow.printBoard();
                        i--;
                        yourTurn = true;
                    }

                    player_board.printBoard(name);
                }
                while(!yourTurn){
                    System.out.println("Bot's turn to attack ...");
                    TimeUnit.SECONDS.sleep(2);
                    String hor = "abcdefghij";
                    int x = hor.charAt((rnd.nextInt(hor.length()))) - 97;
                    int y = rnd.nextInt(10);
                    if (p[y][x] == '#' && p[y][x] != 'X') {
                        p[y][x] = 'X';
                        player_board.printBoard(name);
                        shipsLeft--;
                        if (playersShipsLeft == 0) {
                            System.out.println("Game over. Better luck next time");
                            break;
                        }
                        yourTurn = false;
                    } else if (p[y][x] == '-' && p[y][x] != 'O') {
                        p[y][x] = 'O';
                        player_board.printBoard(name);
                        yourTurn = true;
                    } else if (p[y][x] == 'O' || p[y][x] == 'X') {
                        i--;
                        yourTurn = false;
                    }

                    boardToShow.printBoard();
                }
            }
        }

    }
}
