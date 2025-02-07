import GLOOP.*;

public class Main {
    GLHimmel himmel;
    GLBoden boden;
    GLKamera kamera;
    GLTastatur input;
    GLMaus maus;
    GLLicht licht;

    byte kameraAbstand;
    byte kameraHoehe;

    GLWuerfel wurfel;
    double geschwindigkeit = 0;
    double beschleunigung = 0.5;
    double hochstgeschwindigkeit = 10;
    double bremskraft = 1;
    double reibung = 0.2;
    double drehwinkel = 0;
    double drehgeschwindigkeit = 5;

    double radian;
    double deltaX;
    double deltaZ;

//    public static void main(String[] args) {
//        Main main = new Main();
//        System.setProperty("sun.java2d.opengl", "True");
//        main.szene();
//    }

    public void szene() {
        himmel = new GLHimmel("assets/sunflowers_puresky.jpg");
        boden = new GLBoden("assets/aerial_grass_rock_diff_2k.jpg");
        licht = new GLLicht();

        kamera = new GLKamera(1200, 900);
        kameraAbstand = 35;
        kameraHoehe = 15;

        input = new GLTastatur();
        maus = new GLMaus();

        wurfel = new GLWuerfel(0, 0, 0, 15);
        deltaX = 0;
        deltaZ = 0;

        while (!input.esc()) {
            kamera.setzeBlickpunkt(wurfel.gibX(), wurfel.gibY(), wurfel.gibZ());
            kamera.setzePosition(wurfel.gibX(), wurfel.gibY() + kameraHoehe, wurfel.gibZ() + kameraAbstand);
            //kamera.rotiere(drehwinkel, wurfel.gibX(), wurfel.gibY(), wurfel.gibZ(), 0, 25, 50);
            Sys.warte(15);


            if (input.oben()) {
                kameraHoehe += 1;
                if (kameraHoehe > 0) {
                    kameraHoehe = 0;
                }
            } else if (input.unten()) {
                kameraHoehe -= 1;
                if (kameraHoehe > 0) {
                    kameraHoehe = 0;
                }
            }

            if (input.links()) {
                kameraAbstand -= 1;
                if (kameraAbstand < 0) {
                    kameraAbstand = 0;
                }
            } else if (input.rechts()) {
                kameraAbstand += 1;
                if (kameraAbstand < 0) {
                    kameraAbstand = 0;
                }
            }


            if (input.istGedrueckt('w')) {
                geschwindigkeit += beschleunigung;
                if (geschwindigkeit > hochstgeschwindigkeit) {
                    geschwindigkeit = hochstgeschwindigkeit;
                }
            } else if (input.istGedrueckt('s')) {
                geschwindigkeit -= bremskraft;
                if (geschwindigkeit < -hochstgeschwindigkeit) {
                    geschwindigkeit = -hochstgeschwindigkeit;
                }
            } else {
                if (geschwindigkeit > 0) {
                    geschwindigkeit -= reibung;
                    if (geschwindigkeit < 0) {
                        geschwindigkeit = 0;
                    }
                } else if (geschwindigkeit < 0) {
                    geschwindigkeit += reibung;
                    if (geschwindigkeit > 0) {
                        geschwindigkeit = 0;
                    }
                }
            }

            if (input.istGedrueckt('a')) {
                drehwinkel -= drehgeschwindigkeit;
            }
            if (input.istGedrueckt('d')) {
                drehwinkel += drehgeschwindigkeit;
            }

            radian = Math.toRadians(drehwinkel);
            deltaX = geschwindigkeit * Math.sin(radian);
            deltaZ = geschwindigkeit * Math.cos(radian);

            wurfel.verschiebe(deltaX, 0, deltaZ);
            wurfel.setzeDrehung(0, drehwinkel, 0);


        }

        Sys.beenden();
    }
}