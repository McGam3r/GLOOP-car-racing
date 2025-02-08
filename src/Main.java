import GLOOP.*;

public class Main {
    GLHimmel himmel;
    GLBoden boden;
    GLKamera kamera;
    GLTastatur tastatur;
    GLMaus maus;
    GLLicht licht;

    Auto auto;

    byte kameraAbstand;
    byte kameraHohe;
    double kameraX;
    double kameraZ;


    public static void main(String[] args) {
        Main main = new Main();
        System.setProperty("sun.java2d.opengl", "True");
        main.szene();
    }

    public void szene() {
        himmel = new GLHimmel("assets/sunflowers_puresky.jpg");
        boden = new GLBoden("assets/aerial_grass_rock_diff_2k.jpg");
        licht = new GLLicht();

        auto = new Auto(0, 0, 0);

        kamera = new GLKamera(1200, 900);
        kameraAbstand = 35;
        kameraHohe = 15;

        tastatur = new GLTastatur();
        maus = new GLMaus();


        while (!tastatur.esc()) {
            kameraX = auto.gibX() - kameraAbstand * Math.sin(auto.getRadians());
            kameraZ = auto.gibZ() - kameraAbstand * Math.cos(auto.getRadians());

            kamera.setzePosition(kameraX, auto.gibY() + kameraHohe, kameraZ);
            kamera.setzeBlickpunkt(auto.gibX(), auto.gibY(), auto.gibZ());

            if (tastatur.oben()) {
                kameraHohe += 1;
                if (kameraHohe < 0) {
                    kameraHohe = 127;
                }
            } else if (tastatur.unten()) {
                kameraHohe -= 1;
                if (kameraHohe < 0) {
                    kameraHohe = 0;
                }
            }

            if (tastatur.links()) {
                kameraAbstand -= 1;
                if (kameraAbstand < 0) {
                    kameraAbstand = 0;
                }
            } else if (tastatur.rechts()) {
                kameraAbstand += 1;
                if (kameraAbstand < 0) {
                    kameraAbstand = 127;
                }
            }

            if (tastatur.istGedrueckt('q')) {
                kamera.rotiere(1, auto.gibX(), auto.gibY(), auto.gibZ(), 0, 25, 50);
            }

            if (tastatur.istGedrueckt('e')) {
                kamera.rotiere(-1, auto.gibX(), auto.gibY(), auto.gibZ(), 0, 25, 50);
            }

            if (tastatur.istGedrueckt('w')) {
                auto.beschleunigen();
            } else if (tastatur.istGedrueckt('s')) {
                auto.bremsen();
            } else {
                auto.reibung();
            }

            if (tastatur.istGedrueckt('a')) {
                auto.lenkeLinks();
            }
            if (tastatur.istGedrueckt('d')) {
                auto.lenkeRechts();
            }

            auto.bewege();

            Sys.warte(16);
        }

        Sys.beenden();
    }
}