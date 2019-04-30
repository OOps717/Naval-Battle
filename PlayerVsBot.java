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
        int xPos = -1, yPos = -1;
        Random rnd = new Random();
        Scanner scan = new Scanner(System.in);
        System.out.println("Hey there! Give me your beautiful name");
        String name = scan.next();

        player_board.printBoard(name);

        p = player_board.place(name);
        System.out.println("Are you ready to play?(y/n)");
        if (Character.toLowerCase(scan.next().charAt(0)) == 'y') {
            b = board.randomPlacement();
            System.out.println("Choose the level of bot (e - easy, h - hard)");
            char level = Character.toLowerCase(scan.next().charAt(0));
            while (level != 'e' && level != 'h'){
                System.out.println("Please, choose the right level e or h for easy and hard respectively");
                level = Character.toLowerCase(scan.next().charAt(0));
            }
            System.out.println();

            boardToShow.printBoard();
            if (level == 'e') { //Random cell attacking
                for (int i = 0; i < 200; i++) {
                    while (yourTurn) {  //Players turn
                        System.out.println("Your turn to attack. Give the position to attack");
                        System.out.println("On vertical axis (1-10):");
                        String yAx = scan.next();
                        while (!player_board.isInteger(yAx)) {
                            yAx = scan.next();
                        }
                        int y = Integer.parseInt(yAx) - 1;
                        System.out.println("On horizontal axis (a-j):");
                        int x = Character.toLowerCase(scan.next().charAt(0)) - 97;

                        if (y > 9 || y < 0 || x > 9 || x < 0) {
                            System.out.println("Out of bounds, try again");
                            i--;
                            continue;
                        } else if (b[y][x] == '#' && bToShow[y][x] != 'X') { //User hits. Continue hitting
                            System.out.println("Hit! Continue in the same way.");
                            bToShow[y][x] = 'X';
                            boardToShow.printBoard();
                            shipsLeft--;
                            if (shipsLeft == 0) {
                                System.out.println("You have won congrats");
                                i = 200;
                                break;
                            }
                        } else if (b[y][x] == '-' && bToShow[y][x] != 'O') { //Miss, turn goes to bot
                            System.out.println("Miss :(");
                            bToShow[y][x] = 'O';
                            boardToShow.printBoard();
                            yourTurn = false;
                        } else if (bToShow[y][x] == 'O' || bToShow[y][x] == 'X') {
                            System.out.println("You already attacked this point, try again");
                            boardToShow.printBoard();
                            i--;
                        }

                        player_board.printBoard(name);
                    }
                    while (!yourTurn) {
                        System.out.println("Bot's turn to attack ...");
                        TimeUnit.SECONDS.sleep(2);
                        String hor = "abcdefghij";
                        int x = hor.charAt((rnd.nextInt(hor.length()))) - 97;
                        int y = rnd.nextInt(10);
                        if (p[y][x] == '#' && p[y][x] != 'X') {
                            p[y][x] = 'X';
                            player_board.printBoard(name);
                            playersShipsLeft--;
                        } else if (p[y][x] == '-' && p[y][x] != 'O') {
                            p[y][x] = 'O';
                            player_board.printBoard(name);
                            yourTurn = true;
                        } else if (p[y][x] == 'O' || p[y][x] == 'X') {
                            i--;
                            continue;
                        }
                        boardToShow.printBoard();
                        if (playersShipsLeft == 0) {
                            System.out.println("Game over. Better luck next time");
                            i = 200;
                            break;
                        }
                    }
                }
            }else { //Consequent cell attacking
                for (int i = 0; i < 200; i++) {
                    while (yourTurn) {
                        System.out.println("Your turn to attack. Give the position to attack");
                        System.out.println("On vertical axis");
                        String yAx = scan.next();
                        while (!player_board.isInteger(yAx)) {
                            yAx = scan.next();
                        }
                        int y = Integer.parseInt(yAx) - 1;
                        System.out.println("On horizontal axis (a-j)");
                        int x = Character.toLowerCase(scan.next().charAt(0)) - 97;

                        if (y > 9 || y < 0 || x > 9 || x < 0) {
                            System.out.println("Out of bounds, try again");
                            i--;
                            continue;
                        } else if (b[y][x] == '#' && bToShow[y][x] != 'X') {
                            System.out.println("Hit! Continue in the same way.");
                            bToShow[y][x] = 'X';
                            boardToShow.printBoard();
                            shipsLeft--;
                            if (shipsLeft == 0) {
                                System.out.println("You have won congrats");
                                i = 200;
                                break;
                            }
                        } else if (b[y][x] == '-' && bToShow[y][x] != 'O') {
                            System.out.println("Miss :(");
                            bToShow[y][x] = 'O';
                            boardToShow.printBoard();
                            yourTurn = false;
                        } else if (bToShow[y][x] == 'O' || bToShow[y][x] == 'X') {
                            System.out.println("You already attacked this point, try again");
                            boardToShow.printBoard();
                            i--;
                        }

                        player_board.printBoard(name);
                    }
                    while (!yourTurn) {
                        if (yPos != -1 && xPos != -1) {
                            TimeUnit.SECONDS.sleep(1);
                            if (yPos != 9 && p[yPos + 1][xPos] != 'O' && p[yPos + 1][xPos] != 'X' && p[yPos + 1][xPos] == '#') {
                                yPos = yPos + 1;
                                p[yPos][xPos] = 'X';
                                playersShipsLeft--;
                                player_board.printBoard(name);
                            } else if (yPos != 9 && xPos != 9 && p[yPos + 1][xPos + 1] != 'O' && p[yPos + 1][xPos + 1] != 'X' && p[yPos + 1][xPos + 1] == '#') {
                                yPos = yPos + 1;
                                xPos = xPos + 1;
                                p[yPos][xPos] = 'X';
                                playersShipsLeft--;
                                player_board.printBoard(name);
                            } else if (xPos != 9 && p[yPos][xPos + 1] != 'O' && p[yPos][xPos + 1] != 'X' && p[yPos][xPos + 1] == '#') {
                                xPos = xPos + 1;
                                p[yPos][xPos] = 'X';
                                playersShipsLeft--;
                                player_board.printBoard(name);
                            } else if (yPos != 0 && xPos != 9 && p[yPos - 1][xPos + 1] != 'O' && p[yPos - 1][xPos + 1] != 'X' && p[yPos - 1][xPos + 1] == '#') {
                                yPos = yPos - 1;
                                xPos = xPos + 1;
                                p[yPos][xPos] = 'X';
                                playersShipsLeft--;
                                player_board.printBoard(name);
                            } else if (yPos != 9 && xPos != 0 && p[yPos + 1][xPos - 1] != 'O' && p[yPos + 1][xPos - 1] != 'X' && p[yPos + 1][xPos - 1] == '#') {
                                yPos = yPos + 1;
                                xPos = xPos - 1;
                                p[yPos][xPos] = 'X';
                                playersShipsLeft--;
                                player_board.printBoard(name);
                            } else if (yPos != 0 && xPos != 0 && p[yPos - 1][xPos - 1] != 'O' && p[yPos - 1][xPos - 1] != 'X' && p[yPos - 1][xPos - 1] == '#') {
                                yPos = yPos - 1;
                                xPos = xPos - 1;
                                p[yPos][xPos] = 'X';
                                playersShipsLeft--;
                                player_board.printBoard(name);
                            } else if (xPos != 0 && p[yPos][xPos - 1] != 'O' && p[yPos][xPos - 1] != 'X' && p[yPos][xPos - 1] == '#') {
                                xPos = xPos - 1;
                                p[yPos][xPos] = 'X';
                                playersShipsLeft--;
                                player_board.printBoard(name);
                            } else if (yPos != 0 && p[yPos - 1][xPos] != 'O' && p[yPos - 1][xPos] != 'X' && p[yPos - 1][xPos] == '#') {
                                yPos = yPos - 1;
                                p[yPos][xPos] = 'X';
                                playersShipsLeft--;
                                player_board.printBoard(name);
                            } else {
                                xPos = -1;
                                yPos = -1;
                            }
                        } else {
                            System.out.println("Bot's turn to attack ...");
                            TimeUnit.SECONDS.sleep(1);
                            String hor = "abcdefghij";
                            int x = hor.charAt((rnd.nextInt(hor.length()))) - 97;
                            int y = rnd.nextInt(10);
                            if (p[y][x] == '#' && p[y][x] != 'X') {
                                p[y][x] = 'X';
                                yPos = y;
                                xPos = x;
                                player_board.printBoard(name);
                                playersShipsLeft--;
                            } else if (p[y][x] == '-' && p[y][x] != 'O') {
                                p[y][x] = 'O';
                                player_board.printBoard(name);
                                yourTurn = true;
                            } else if (p[y][x] == 'O' || p[y][x] == 'X') {
                                i--;
                                continue;
                            }
                        }
                        boardToShow.printBoard();
                        if (playersShipsLeft == 0) {
                            System.out.println("Game over. Better luck next time");
                            i = 200;
                            break;
                        }
                    }
                }
            }
        }else System.exit(0);
    }

}
