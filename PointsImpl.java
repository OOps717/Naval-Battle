public class PointsImpl {

    private char x;
    private int y;
    private boolean check;

    public PointsImpl(int y, char x) {
        this.x = x;
        this.y = y;
        this.check = false;
    }

    public char getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean Attacking(PointsImpl p, PointsImpl[] points){
        boolean attacked = false;
        for (int i = 0; i < points.length; i++) {
            if (p.x == points[i].x && p.y == points[i].y){
                attacked = true;
                points[i].check = true;
            }
        }

        return attacked;
    }

    public boolean alreadyAttacked(PointsImpl p, PointsImpl[] points){
        boolean attacked = false;

        for (int i = 0; i < points.length; i++) {
            if ((p.x == points[i].x && p.y == points[i].y) && points[i].check) {
                System.out.println();
                attacked = true;
            }
        }

        return attacked;
    }

}
