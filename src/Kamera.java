import GLOOP.*;

public class Kamera {
    GLKamera kamera;
    Auto auto;

    private byte kameraAbstand;
    private byte kameraHohe;
    private double rotationsWinkel;

    Kamera(int pB, int pH, Auto pAuto) {
        auto = pAuto;
        kamera = new GLKamera(1200, 900);
        kameraAbstand = 35;
        kameraHohe = 15;
    }

    public void aktualisiere() {
        double radian = Math.toRadians(rotationsWinkel);
        double kameraX = auto.gibX() - kameraAbstand * Math.sin(auto.getRadians() + radian);
        double kameraZ = auto.gibZ() - kameraAbstand * Math.cos(auto.getRadians() + radian);

        kamera.setzePosition(kameraX, auto.gibY() + kameraHohe, kameraZ);
        kamera.setzeBlickpunkt(auto.gibX(), auto.gibY(), auto.gibZ());
    }

    public void erhoeheHoheKamera(int pX) {
        kameraHohe += pX;
        if (kameraHohe < 0) {
            kameraHohe = 127;
        }
    }

    public void verringereHoheKamera(int pX) {
        kameraHohe -= pX;
        if (kameraHohe < 0) {
            kameraHohe = 0;
        }
    }

    public void erhoeheAbstandKamera(int pX) {
        kameraAbstand += pX;
        if (kameraAbstand < 0) {
            kameraAbstand = 127;
        }
    }

    public void verringereAbstandKamera(int pX) {
        kameraAbstand -= pX;
        if (kameraAbstand < 0) {
            kameraAbstand = 0;
        }
    }

    public void rotiere(int pW) {
        rotationsWinkel += pW;
        if (rotationsWinkel >= 360) {
            rotationsWinkel -= 360;
        } else if (rotationsWinkel < 0) {
            rotationsWinkel += 360;
        }
    }


}
