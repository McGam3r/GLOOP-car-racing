import GLOOP.GLQuader;

public class Hindernis {
    private GLQuader hindernis;
    private int breite;
    private int hohe;
    private int tiefe;

    Hindernis(int pX, int pY, int pZ, int pBreite, int pHohe, int pTiefe) {
        breite = pBreite;
        hohe = pHohe;
        tiefe = pTiefe;

        hindernis = new GLQuader(pX, pY, pZ, pBreite, pHohe, pTiefe);
        hindernis.setzeTextur("assets/red_brick_diff_4k.jpg");
    }

    Hindernis(int pX, int pZ, int pBreite, int pHohe, int pTiefe) {
        breite = pBreite;
        hohe = pHohe;
        tiefe = pTiefe;

        hindernis = new GLQuader(pX, 0, pZ, pBreite, pHohe, pTiefe);
        hindernis.setzeTextur("assets/red_brick_diff_4k.jpg");

    }

    public double gibX() {
        return hindernis.gibX();
    }

    public double gibY() {
        return hindernis.gibY();
    }

    public double gibZ() {
        return hindernis.gibZ();
    }

    public int gibBreite() {
        return breite;
    }

    public int gibHohe() {
        return hohe;
    }

    public int gibTiefe() {
        return tiefe;
    }
}
