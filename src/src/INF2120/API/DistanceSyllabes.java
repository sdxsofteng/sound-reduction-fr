package INF2120.API;

public class DistanceSyllabes {

    public SyllabeFrancais syllabe1;
    public SyllabeFrancais syllabe2;
    public int distanceSyllabes;

    public DistanceSyllabes(SyllabeFrancais syllabe1, SyllabeFrancais syllabe2) {
        this.syllabe1 = syllabe1;
        this.syllabe2 = syllabe2;
        this.distanceSyllabes = syllabe1.calculerDistanceSyllabe(syllabe2);
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
        return "DistanceSyllabes{" +
                "syllabe1=" + syllabe1 +
                ", syllabe2=" + syllabe2 +
                ", distanceSyllabes=" + distanceSyllabes +
                '}';
    }
}
