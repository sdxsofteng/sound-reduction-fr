package INF2120.API;

public class DistanceSonore {

    /**
     * Variables d'instances contenant les deux syllabes et leurs distances
     */
    public SyllabeFrancais syllabe1;
    public SyllabeFrancais syllabe2;
    public int distanceSyllabes;

    /**
     * Constructeur qui calcule la distances entre les deux syllabes automatiquement a la construction
     * @param syllabe1 syllabes 1
     * @param syllabe2 syllabes 2
     */
    public DistanceSonore(SyllabeFrancais syllabe1, SyllabeFrancais syllabe2) {
        this.syllabe1 = syllabe1;
        this.syllabe2 = syllabe2;
        this.distanceSyllabes = syllabe1.distanceSyllabes(syllabe2);
    }

    public SyllabeFrancais getSyllabe1() {
        return syllabe1;
    }

    public SyllabeFrancais getSyllabe2() {
        return syllabe2;
    }

    public int getDistanceSyllabes() {
        return distanceSyllabes;
    }

    @Override
    public String toString() {
        return "DistanceSonore{" +
                "syllabe1=" + syllabe1 +
                ", syllabe2=" + syllabe2 +
                ", distanceSyllabes=" + distanceSyllabes +
                '}';
    }
}
