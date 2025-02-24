import GLOOP.GLMaterial;
import GLOOP.GLObjekt;
import GLOOP.GLTorus;
import GLOOP.GLTafel;

public class Ziel {
    GLTorus ziel;
    GLTafel zeitAnzeige;
    private final int x;
    private final int z;
    private final int winkel;

    Ziel(int pX, int pZ, int pW) {
        x = pX;
        z = pZ;
        winkel = pW;

        ziel = new GLTorus(x, 0, z, 25, 5);
        ziel.setzeMaterial(GLMaterial.SMARAGD);
        ziel.setzeSkalierung(1, 2, 1);
        ziel.setzeDrehung(0, winkel, 0);

        zeitAnzeige = new GLTafel(x, 50, z, 50, 25);
        zeitAnzeige.setzeDrehung(0, winkel + 180, 0);
    }

    public double gibDistanz(GLObjekt objekt) {
        return ziel.gibDistanz(objekt);
    }

    public double gibDistanz(int pX, int pZ) {
        // Berechnung: Distanz zwischen Ziel und Punkt -> wurzel((pX - x)^2 + (pZ - z)^2)
        return Math.sqrt(Math.pow(pX - x, 2) + Math.pow(pZ - z, 2));
    }

    public boolean zielErreicht(Auto auto) {
        return x + 15 * Math.sin(Math.toRadians(winkel)) < auto.gibX()
                && z + 15 * Math.cos(Math.toRadians(winkel)) > auto.gibX()
                && x - 15 * Math.sin(Math.toRadians(winkel)) < auto.gibZ()
                && z - 15 * Math.cos(Math.toRadians(winkel)) > auto.gibZ();
    }

    public void setzeZeitAnzeige(String text) {
        zeitAnzeige.setzeText(text, 16);
    }
}