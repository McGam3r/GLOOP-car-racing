import GLOOP.*;

public class Auto {
    private final GLQuader motorhaube;
    private final GLQuader karrosserie;
    private final GLZylinder[] reifen = new GLZylinder[4];
    private final int[] reifenPosX;
    private final int[] reifenPosZ;

    private double geschwindigkeit = 0;
    private final double hochstgeschwindigkeit = 10;
    private double drehwinkel = 0;
    private final double drehgeschwindigkeit = 5;

    private double radian;
    private double deltaX;
    private double deltaZ;

    Auto(int pX, int pY, int pZ) {
        motorhaube = new GLQuader(pX, pY, pZ + 7, 10, 5, 5);
        karrosserie = new GLQuader(pX, pY + 2, pZ, 10, 5, 10);
        karrosserie.setzeFarbe((double) 1 / 255, (double) 201 / 255, (double) 201 / 255);
        motorhaube.setzeFarbe((double) 155 / 255, (double) 155 / 255, (double) 155 / 255);

        reifenPosX = new int[]{-5, 5, -5, 5};
        reifenPosZ = new int[]{5, 5, -3, -3};

        for (int i = 0; i < reifen.length; i++) {
            reifen[i] = new GLZylinder(pX + reifenPosX[i], pY - 1, pZ + reifenPosZ[i], 1, 0.5);
        }
        for (GLZylinder zylinder : reifen) {
            zylinder.setzeDrehung(0, 90, 0);
            zylinder.setzeFarbe(0, 0, 0);
        }

        deltaX = 0;
        deltaZ = 0;
    }

    public void bewege(Hindernis[] hindernisse) {
        // Berechnung: Auto bewegungsrichtung
        radian = Math.toRadians(drehwinkel);
        deltaX = Math.sin(radian) * geschwindigkeit;
        deltaZ = Math.cos(radian) * geschwindigkeit;

        // Auto wird bewegt und gedreht
        karrosserie.verschiebe(deltaX, 0, deltaZ);
        karrosserie.setzeDrehung(0, drehwinkel, 0);

        motorhaube.setzePosition(karrosserie.gibX() + 7 * Math.sin(radian), karrosserie.gibY() - 2, karrosserie.gibZ() + 7 * Math.cos(radian));
        motorhaube.setzeDrehung(0, drehwinkel, 0);

        for (int i = 0; i < reifen.length; i++) {
            double reifenX = karrosserie.gibX() + reifenPosZ[i] * Math.sin(radian) + reifenPosX[i] * Math.cos(radian);
            double reifenZ = karrosserie.gibZ() + reifenPosZ[i] * Math.cos(radian) - reifenPosX[i] * Math.sin(radian);
            reifen[i].setzePosition(reifenX, karrosserie.gibY() - 1, reifenZ);
            reifen[i].setzeDrehung(0, drehwinkel + 90, 0);
        }

        // Kollisionsabfrage
        for (Hindernis hindernis : hindernisse) {
            if (kollisionAbfrage(hindernis)) {
                // Kollision erkannt, Bewegung rückgängig machen
                karrosserie.verschiebe(-deltaX, 0, -deltaZ);
                motorhaube.verschiebe(-deltaX, 0, -deltaZ);
                for (GLZylinder zylinder : reifen) {
                    zylinder.verschiebe(-deltaX, 0, -deltaZ);
                }
                geschwindigkeit = 0;
                break;
            }
        }
    }

    public void beschleunigen() {
        final double beschleunigung = 0.5;
        geschwindigkeit += beschleunigung;
        if (geschwindigkeit > hochstgeschwindigkeit) {
            geschwindigkeit = hochstgeschwindigkeit;
        }
    }

    public void bremsen() {
        final double bremskraft = 1;
        geschwindigkeit -= bremskraft;
        if (geschwindigkeit < -hochstgeschwindigkeit) {
            geschwindigkeit = -hochstgeschwindigkeit;
        }
    }

    public void reibung() {
        final double reibung = 0.2;
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

    public void lenkeLinks() {
        if (geschwindigkeit != 0) {
            drehwinkel += drehgeschwindigkeit;
            if (drehwinkel >= 360) {
                drehwinkel = 0;
            }
        }
    }

    public void lenkeRechts() {
        if (geschwindigkeit != 0) {
            drehwinkel -= drehgeschwindigkeit;
            if (drehwinkel <= -360) {
                drehwinkel = 0;
            }
        }
    }

    // Kollisionsabfrage
    public boolean kollisionAbfrage(Hindernis hindernis) {
        if (hindernis.gibX() + (double) hindernis.gibBreite() / 2 > gibX() - 5 && hindernis.gibX() - (double) hindernis.gibBreite() / 2 < gibX() + 5
                && hindernis.gibZ() + (double) hindernis.gibTiefe() / 2 > gibZ() - 5 && hindernis.gibZ() - (double) hindernis.gibTiefe() / 2 < gibZ() + 5
                || hindernis.gibX() + (double) hindernis.gibBreite() / 2 > motorhaube.gibX() - 5 && hindernis.gibX() - (double) hindernis.gibBreite() / 2 < motorhaube.gibX() + 5
                && hindernis.gibZ() + (double) hindernis.gibTiefe() / 2 > motorhaube.gibZ() - 5 && hindernis.gibZ() - (double) hindernis.gibTiefe() / 2 < motorhaube.gibZ() + 5) {
            return true;
        } else {
            return false;
        }
    }

    public double gibX() {
        return karrosserie.gibX();
    }

    public double gibY() {
        return karrosserie.gibY();
    }

    public double gibZ() {
        return karrosserie.gibZ();
    }

    public double getRadians() {
        return radian;
    }
}