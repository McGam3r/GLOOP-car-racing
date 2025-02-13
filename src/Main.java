import GLOOP.*;

public class Main {
    GLHimmel himmel;
    GLBoden boden;
    Kamera kamera;
    GLTastatur tastatur;
    GLLicht licht;

    Auto auto;

    Hindernis[] hindernisse;


    public static void main(String[] args) {
        Main main = new Main();
        System.setProperty("sun.java2d.opengl", "True");
        main.szene();
    }

    public void szene() {
        // Himmel, Boden, Licht, Auto und Hindernisse werden erstellt
        himmel = new GLHimmel("assets/sunflowers_puresky.jpg");
        boden = new GLBoden("assets/aerial_grass_rock_diff_2k.jpg");
        licht = new GLLicht();

        auto = new Auto(0, 0, 0);

        hindernisse = new Hindernis[20];
        for (int i = 0; i < hindernisse.length; i++) {
            int randomX, randomZ;
            boolean kollision;
            do {
                // Zufällige Position für Hindernis
                randomX = (int) (Math.random() * 501) - 250;
                randomZ = (int) (Math.random() * 501) - 250;
                kollision = false;
                for (int j = 0; j < i; j++) {
                    // Kollisionserkennung: Wenn das Hindernis mit einem anderen Hindernis oder dem Auto kollidiert, wird ein neues Hindernis generiert "+10" und "-10" sind die Toleranzwerte/Abstand
                    if (randomX + 10 > hindernisse[j].gibX() - hindernisse[j].gibBreite() / 2 && randomX - 10 < hindernisse[j].gibX() + hindernisse[j].gibBreite() / 2 &&
                            randomZ + 10 > hindernisse[j].gibZ() - hindernisse[j].gibTiefe() / 2 && randomZ - 10 < hindernisse[j].gibZ() + hindernisse[j].gibTiefe() / 2
                            || hindernisse[j].gibX() + (double) hindernisse[j].gibBreite() / 2 > auto.gibX() - 10 && hindernisse[j].gibX() - (double) hindernisse[j].gibBreite() / 2 < auto.gibX() + 10
                            && hindernisse[j].gibZ() + (double) hindernisse[j].gibTiefe() / 2 > auto.gibZ() - 10 && hindernisse[j].gibZ() - (double) hindernisse[j].gibTiefe() / 2 < auto.gibZ() + 10
                            || randomX + (double) hindernisse[j].gibBreite() / 2 > auto.gibX() - 10 && randomX - (double) hindernisse[j].gibBreite() / 2 < auto.gibX() + 10
                            && randomZ + (double) hindernisse[j].gibTiefe() / 2 > auto.gibZ() - 10 && randomZ - (double) hindernisse[j].gibTiefe() / 2 < auto.gibZ() + 10) {
                        kollision = true;
                        break;
                    }
                }
            } while (kollision);
            hindernisse[i] = new Hindernis(randomX, randomZ, 20, 25, 20);


        }

        kamera = new Kamera(1200, 900, auto);

        tastatur = new GLTastatur();

        int kameraGeschwindigkeit = 1;

        while (!tastatur.esc()) {
            // Kamera wird aktualisiert und bewegt
            kamera.aktualisiere();

            // Kamera steuerung
            if (tastatur.oben()) {
                kamera.erhoeheHoheKamera(kameraGeschwindigkeit);
            } else if (tastatur.unten()) {
                kamera.verringereHoheKamera(kameraGeschwindigkeit);
            }

            if (tastatur.links()) {
                kamera.verringereAbstandKamera(kameraGeschwindigkeit);
            } else if (tastatur.rechts()) {
                kamera.erhoeheAbstandKamera(kameraGeschwindigkeit);
            }

            if (tastatur.istGedrueckt('q')) {
                kamera.rotiere(-kameraGeschwindigkeit * 2);
            }

            if (tastatur.istGedrueckt('e')) {
                kamera.rotiere(kameraGeschwindigkeit * 2);
            }

            if (tastatur.istGedrueckt('r')) {
                kamera.reset();
            }

            // Auto steuerung
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

            // Auto wird bewegt/aktualisiert + Kollisionserkennung
            auto.bewege(hindernisse);

            Sys.warte(16);
        }

        Sys.beenden();
    }
}