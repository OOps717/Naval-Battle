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

//    public void putShot (PointsImpl p, char put){
//        board[p.getY() - 1][p.getX() - 97] = put;
//    }

    public void hide(char[][] board){
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
        PointsImpl[] p = new PointsImpl[20];
        int S4=1, S3=2, S2=3, S1=4;
        boolean  okay = true;
        int j = 0;

        while ( S1!=0 || S2!=0 || S3!=0 || S4!=0 ) {
            String chars = "1234";
            Random rnd = new Random();
            char ShipCell = chars.charAt(rnd.nextInt(chars.length()));
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

            int Vertdir = rnd.nextInt(11);
            if (Vertdir == 0) Vertdir ++;
            String hor = "abcdefghij";
            char H = hor.charAt((rnd.nextInt(hor.length())));
            int Horizdir = H - 96;

            if (ShipCell == '1'){
                if(board[Vertdir-1][Horizdir-1] == '#') S1++;
                else {
                    board[Vertdir - 1][Horizdir - 1] = '#';
                    p[j] = new PointsImpl(Vertdir, H);
                    j++;
                }
            }
            else{
                String direction = "lrud";
                char Dir = direction.charAt(rnd.nextInt(direction.length()));
                if (Dir == 'l'){
                    if((Horizdir <= 3 && ShipCell == '4') || (Horizdir <= 2 && ShipCell == '3') || (Horizdir <= 1 && ShipCell == '2')){
                        if (ShipCell == '2') S2++;
                        if (ShipCell == '3') S3++;
                        if (ShipCell == '4') S4++;
                    }
                    else {
                        for (int i = Horizdir - 1; i > Horizdir - Character.getNumericValue(ShipCell) - 1; i--) {
                            if (board[Vertdir - 1][i] == '#') {
                                okay = false;
                            }
                        }
                        if (okay){
                            for (int i = Horizdir - 1; i > Horizdir - Character.getNumericValue(ShipCell) - 1; i--) {
                                board[Vertdir - 1][i] = '#';
                                p[j] = new PointsImpl(Vertdir, H);
                                H--;
                                j++;
                            }
                        }
                        else{
                            if (ShipCell == '2') S2++;
                            if (ShipCell == '3') S3++;
                            if (ShipCell == '4') S4++;
                            okay = true;
                        }
                    }
                }
                if (Dir == 'r'){
                    if ((Horizdir >= 8 && ShipCell == '4') || (Horizdir >= 9 && ShipCell == '3') || (Horizdir >= 10 && ShipCell == '2')){
                        if (ShipCell == '2') S2++;
                        if (ShipCell == '3') S3++;
                        if (ShipCell == '4') S4++;
                    }
                    else{
                        for (int i = Horizdir - 1; i < Horizdir + Character.getNumericValue(ShipCell) - 1; i++) {
                            if (board[Vertdir - 1][i] == '#') {
                                okay = false;
                            }
                        }
                        if (okay){
                            for (int i = Horizdir - 1; i < Horizdir + Character.getNumericValue(ShipCell) - 1; i++) {
                                board[Vertdir - 1][i] = '#';
                                p[j] = new PointsImpl(Vertdir, H);
                                j++;
                                H++;
                            }
                        }
                        else{
                            if (ShipCell == '2') S2++;
                            if (ShipCell == '3') S3++;
                            if (ShipCell == '4') S4++;
                            okay = true;
                        }
                    }
                }
                if (Dir == 'd'){
                    if ((Vertdir >= 8 && ShipCell == '4') || (Vertdir >= 9 && ShipCell == '3') || (Vertdir == 10 && ShipCell == '2')){
                        if (ShipCell == '2') S2++;
                        if (ShipCell == '3') S3++;
                        if (ShipCell == '4') S4++;
                    }
                    else {

                        for (int i = Vertdir - 1; i < Vertdir - 1 + Character.getNumericValue(ShipCell); i++) {
                            if (board[i][Horizdir - 1] == '#') {
                                okay = false;
                            }
                        }
                        if(okay){
                            for (int i = Vertdir - 1; i < Vertdir - 1 + Character.getNumericValue(ShipCell); i++) {
                                board[i][Horizdir - 1] = '#';
                                p[j] = new PointsImpl(i+1,H);
                                j++;
                            }
                        }
                        else{
                            if (ShipCell == '2') S2++;
                            if (ShipCell == '3') S3++;
                            if (ShipCell == '4') S4++;
                            okay = true;
                        }
                    }
                }

                if (Dir == 'u'){
                    if ((Vertdir <= 3 && ShipCell == '4') || (Vertdir <= 2 && ShipCell == '3') || (Vertdir <= 1 && ShipCell == '2')){
                        if (ShipCell == '2') S2++;
                        if (ShipCell == '3') S3++;
                        if (ShipCell == '4') S4++;
                    }
                    else {
                        for (int i = Vertdir - 1; i > Vertdir - 1 - Character.getNumericValue(ShipCell); i--) {
                            if (board[i][Horizdir - 1] == '#') okay = false;
                        }
                        if (okay) {
                            for (int i = Vertdir - 1; i > Vertdir - 1 - Character.getNumericValue(ShipCell); i--) {
                                board[i][Horizdir - 1] = '#';
                                p[j] = new PointsImpl(i+1, H);
                                j++;
                            }
                        }
                        else{
                            if (ShipCell == '2') S2++;
                            if (ShipCell == '3') S3++;
                            if (ShipCell == '4') S4++;
                            okay = true;
                        }
                    }
                }
            }
        }


        for (int i = 0; i < p.length; i++){
            System.out.println(p[i].getX() + " " + p[i].getY());
        }

        return board;
    }

}
