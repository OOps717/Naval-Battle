import java.util.Random;

public class AIplaceImpl {
    private char[][] board;

    public AIplaceImpl(char[][] board) {
        this.board = board;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                this.board[i][j] = '-';
            }
        }
    }

    public void printBoard (){
        System.out.println("Bot's board");

        char letter = 97;
        for (int r = 0; r < board.length + 1; r++) {
            for (int c = 0; c < board[0].length + 1; c++) {
                if (r == 0 && c == 0) System.out.print("  ");
                else if (r == 0 && c >= 0) {
                    System.out.print(letter + " ");
                    letter++;
                } else if (c == 0 && r >= 0) {
                    if (r == board.length) System.out.print(r);
                    else System.out.print(r + " ");
                } else System.out.print(board[r - 1][c - 1] + " ");
            }
            System.out.println("");
        }
    }

    public char[][] randomPlacement (){
        int S4=1, S3=2, S2=3, S1=4;
        boolean  okay = true;

        while ( S1!=0 || S2!=0 || S3!=0 || S4!=0 ) {
            String chars = "1234";
            Random rnd = new Random();
            char ShipCell = chars.charAt(rnd.nextInt(chars.length()));      //Randomly choose the ship
            if ((ShipCell=='1' && S1==0) || (ShipCell=='2' && S2==0) || (ShipCell=='3' && S3==0) || (ShipCell=='4' && S4==0)) continue;
            switch (ShipCell) {
                case '1':
                    S1--;
                    break;
                case '2':
                    S2--;
                    break;
                case '3':
                    S3--;
                    break;
                case '4':
                    S4--;
                    break;
            }

            int Vertdir = rnd.nextInt(10);      //Choosing random coordinates from 1-10 (0-9) and from a-j (0-9)
            String hor = "abcdefghij";
            char H = hor.charAt((rnd.nextInt(hor.length())));
            int Horizdir = H - 97;

            if (ShipCell == '1'){
                if(board[Vertdir][Horizdir] == '#') S1++;
                else {
                    board[Vertdir][Horizdir] = '#';
                }
            }
            else{
                String direction = "lrud";
                char Dir = direction.charAt(rnd.nextInt(direction.length()));       //Random choosing of direction
                if (Dir == 'l'){ //Putting in left direction and checking if everything is okay (like in PlayerImpl)
                    if((Horizdir <= 2 && ShipCell == '4') || (Horizdir <= 1 && ShipCell == '3') || (Horizdir <= 0 && ShipCell == '2')){
                        if (ShipCell == '2') S2++;
                        else if (ShipCell == '3') S3++;
                        else if (ShipCell == '4') S4++;
                    }
                    else {
                        for (int i = Horizdir; i > Horizdir - Character.getNumericValue(ShipCell); i--) {
                            if (board[Vertdir][i] == '#') okay = false;
                        }
                        if (okay){
                            for (int i = Horizdir; i > Horizdir - Character.getNumericValue(ShipCell); i--)
                                board[Vertdir][i] = '#';
                        }
                        else{
                            if (ShipCell == '2') S2++;
                            else if (ShipCell == '3') S3++;
                            else if (ShipCell == '4') S4++;
                            okay = true;
                        }
                    }
                }
                if (Dir == 'r'){ //Putting in right direction and checking if everything is okay (like in PlayerImpl)
                    if ((Horizdir >= 7 && ShipCell == '4') || (Horizdir >= 8 && ShipCell == '3') || (Horizdir >= 9 && ShipCell == '2')){
                        if (ShipCell == '2') S2++;
                        else if (ShipCell == '3') S3++;
                        else if (ShipCell == '4') S4++;
                    }
                    else{
                        for (int i = Horizdir; i < Horizdir + Character.getNumericValue(ShipCell); i++) {
                            if (board[Vertdir][i] == '#') okay = false;
                        }
                        if (okay){
                            for (int i = Horizdir; i < Horizdir + Character.getNumericValue(ShipCell); i++)
                                board[Vertdir][i] = '#';
                        }
                        else{
                            if (ShipCell == '2') S2++;
                            else if (ShipCell == '3') S3++;
                            else if (ShipCell == '4') S4++;
                            okay = true;
                        }
                    }
                }
                if (Dir == 'd'){ //Putting in down direction and checking if everything is okay (like in PlayerImpl)
                    if ((Vertdir >= 7 && ShipCell == '4') || (Vertdir >= 8 && ShipCell == '3') || (Vertdir == 9 && ShipCell == '2')){
                        if (ShipCell == '2') S2++;
                        else if (ShipCell == '3') S3++;
                        else if (ShipCell == '4') S4++;
                    }
                    else {

                        for (int i = Vertdir; i < Vertdir + Character.getNumericValue(ShipCell); i++) {
                            if (board[i][Horizdir] == '#') okay = false;
                        }
                        if(okay){
                            for (int i = Vertdir; i < Vertdir + Character.getNumericValue(ShipCell); i++)
                                board[i][Horizdir] = '#';
                        }
                        else{
                            if (ShipCell == '2') S2++;
                            else if (ShipCell == '3') S3++;
                            else if (ShipCell == '4') S4++;
                            okay = true;
                        }
                    }
                }

                if (Dir == 'u'){ //Putting in up direction and checking if everything is okay (like in PlayerImpl)
                    if ((Vertdir <= 2 && ShipCell == '4') || (Vertdir <= 1 && ShipCell == '3') || (Vertdir <= 0 && ShipCell == '2')){
                        if (ShipCell == '2') S2++;
                        else if (ShipCell == '3') S3++;
                        else if (ShipCell == '4') S4++;
                    }
                    else {
                        for (int i = Vertdir; i > Vertdir - Character.getNumericValue(ShipCell); i--)
                            if (board[i][Horizdir] == '#') okay = false;
                        if (okay) {
                            for (int i = Vertdir; i > Vertdir - Character.getNumericValue(ShipCell); i--) board[i][Horizdir] = '#';
                        }
                        else{
                            if (ShipCell == '2') S2++;
                            else if (ShipCell == '3') S3++;
                            else if (ShipCell == '4') S4++;
                            okay = true;
                        }
                    }
                }
            }
        }


        return board;
    }

}
