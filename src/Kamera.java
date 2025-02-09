import GLOOP.*;

public class Kamera {
    GLKamera kamera;
    Auto auto;

    private byte kameraAbstand;
    private byte kameraHohe;
    private double kameraX;
    private double kameraZ;

    private double rotationWinkel;
    private double rotationX;
    private double rotationZ;

    Kamera(int pB, int pH, Auto pAuto) {
        auto = pAuto;
        kamera = new GLKamera(1200, 900);
        kameraAbstand = 35;
        kameraHohe = 15;
    }

    public void aktualisiere() {
        kameraX = auto.gibX() - kameraAbstand * Math.sin(auto.getRadians());
        kameraZ = auto.gibZ() - kameraAbstand * Math.cos(auto.getRadians());

        //rotationX = kameraAbstand * Math.sin(Math.toRadians(rotationWinkel));
        //SrotationZ = kameraAbstand * Math.cos(Math.toRadians(rotationWinkel));

        kamera.setzePosition(kameraX - rotationX, auto.gibY() + kameraHohe, kameraZ - rotationZ);
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
        //kamera.rotiere(pW, auto.gibX(), auto.gibY(), auto.gibZ(), 0, 25, 50);
        rotationWinkel += pW;
    }


}
