import GLOOP.*;

public class Auto {
    GLWuerfel wurfel;

    private double geschwindigkeit = 0;
    private double beschleunigung = 0.5;
    private double hochstgeschwindigkeit = 10;
    private double bremskraft = 1;
    private double reibung = 0.2;
    private double drehwinkel = 0;
    private double drehgeschwindigkeit = 5;

    private double radian;
    private double deltaX;
    private double deltaZ;

    Auto(int pX, int pY, int pZ) {
        wurfel = new GLWuerfel(pX, pY, pZ, 15);
        deltaX = 0;
        deltaZ = 0;
    }

    public void beschleunigen() {
        geschwindigkeit += beschleunigung;
        if (geschwindigkeit > hochstgeschwindigkeit) {
            geschwindigkeit = hochstgeschwindigkeit;
        }
    }

    public void bremsen() {
        geschwindigkeit -= bremskraft;
        if (geschwindigkeit < -hochstgeschwindigkeit) {
            geschwindigkeit = -hochstgeschwindigkeit;
        }
    }

    public void reibung() {
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

    public void bewege() {
        radian = Math.toRadians(drehwinkel);
        deltaX = Math.sin(radian) * geschwindigkeit;
        deltaZ = Math.cos(radian) * geschwindigkeit;

        wurfel.verschiebe(deltaX, 0, deltaZ);
        wurfel.setzeDrehung(0, drehwinkel, 0);
    }

    public void lenkeLinks() {
        if (geschwindigkeit != 0) {
            drehwinkel += drehgeschwindigkeit;
            if(drehwinkel >= 360){
                drehwinkel = 0;
            }
        }
    }

    public void lenkeRechts() {
        if (geschwindigkeit != 0) {
            drehwinkel -= drehgeschwindigkeit;
            if(drehwinkel >= -360){
                drehwinkel = 0;
            }
        }
    }

    public double gibX() {
        return wurfel.gibX();
    }

    public double gibY() {
        return wurfel.gibY();
    }

    public double gibZ() {
        return wurfel.gibZ();
    }

    public double getRadians() {
        return radian;
    }


}