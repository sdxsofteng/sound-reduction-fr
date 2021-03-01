package INF2120.API;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;


/**
 * Une classe pour contenir une suite de syllabe.
 *
 * Cette classe permet la gestion d'une suite de syllabe.
 *
 * @see SyllabeFrancais
 */
public class TexteSonore extends ArrayList< SyllabeFrancais > {

    /**
     * Permet de modifier le texte en le reduisant k fois selon les regles enonces dans le TP
     * 1)Trouver les syllabes avec la plus petite distances entre elles
     * 2)Remplacer la syllabe la moins occurent par la syllabe la plus occurente
     * @param k nombre de fois que l'on doit reduire le texte
     */
    public void modifierTexteKFois(int k) {
        int nombreDeSyllabes = syllabesUniques.size();

        DistanceSonore syllabesAChanger;
        SyllabeFrancais syllabeARemplacer;
        SyllabeFrancais syllabeDeRemplacement;

        while (nombreDeSyllabes > k) {

            syllabesAChanger = plusPetiteDistanceSonore(distancesSonores);
            syllabeARemplacer = syllabeMoinsOccurente(syllabesAChanger);
            syllabeDeRemplacement = syllabeRemplacement(syllabeARemplacer, syllabesAChanger);
            remplacerSyllabe(syllabeARemplacer, syllabeDeRemplacement);
            supprimerDistancesReliees(syllabeARemplacer);

            nombreDeSyllabes--;
        }
    }

    /**
     * Permet de updater la liste des DistanceSonore et de supprimer les distances qui contiennent la syllabe qui a ete
     * remplacee
     * @param syllabeRemplacee syllabe remplacee
     */
    private void supprimerDistancesReliees(SyllabeFrancais syllabeRemplacee){

        for (int i = 0; i < distancesSonores.size(); i++){
            if (distancesSonores.get(i).syllabe1.egaliteSyllabes(syllabeRemplacee)
                    || distancesSonores.get(i).syllabe2.egaliteSyllabes(syllabeRemplacee)) {

                distancesSonores.remove(i);
                i--;
            }
        }
    }

    /**
     * Determine quelle sera la syllabe de remplacement
     * @param syllabeARemplacer syllabeARemplacer que l'on connait deja
     * @param distanceSonore Objet DistanceSonore contenant la syllabeDeRemplacement
     * @return syllabe de remplacement
     */
    private SyllabeFrancais syllabeRemplacement(SyllabeFrancais syllabeARemplacer,
                                                DistanceSonore distanceSonore){
        SyllabeFrancais syllabeDeRemplacement;

        if (distanceSonore.syllabe1.egaliteSyllabes(syllabeARemplacer)){
            syllabeDeRemplacement = distanceSonore.syllabe2;
        }else {
            syllabeDeRemplacement = distanceSonore.syllabe1;
        }

        return syllabeDeRemplacement;
    }

    /**
     * remplace la syllabe dans le texte sonore
     * @param syllabeARemplacer syllabe que l'on doit remplacer
     * @param syllabeDeRemplacement syllabe que l'on doit mettre a la place de la syllabeARemplacer
     */
    private void remplacerSyllabe(SyllabeFrancais syllabeARemplacer, SyllabeFrancais syllabeDeRemplacement){
        for (int i = 0; i < this.size(); i++){
            if (this.get(i).egaliteSyllabes(syllabeARemplacer)){
                this.set(i, syllabeDeRemplacement);
            }
        }
    }

    /**
     * Prend les syllabes avec la distance sonore la moins grande et determine celle qui occure le moins souvent
     * Si jamais, les deux occurences sont egales on prend la deuxieme comme syllabe la moins occurente.
     * On ajuste ensuite les nombres d'occurence sur la syllabe qui sera gardee
     * @param syllabesAVerifier distance sonore a verifier
     * @return la syllabe la moins occurente
     */
    private SyllabeFrancais syllabeMoinsOccurente(DistanceSonore syllabesAVerifier){
        SyllabeFrancais syllabeMoinsOccurente;

        if (syllabesAVerifier.getSyllabe1().occurences < syllabesAVerifier.getSyllabe2().occurences){

            syllabeMoinsOccurente = syllabesAVerifier.getSyllabe1();

            syllabesAVerifier.getSyllabe2().occurences = syllabesAVerifier.getSyllabe2().occurences
                    + syllabeMoinsOccurente.occurences;
        }else {

            syllabeMoinsOccurente = syllabesAVerifier.getSyllabe2();

            syllabesAVerifier.getSyllabe1().occurences = syllabesAVerifier.getSyllabe1().occurences
                    + syllabeMoinsOccurente.occurences;
        }

        return  syllabeMoinsOccurente;
    }

    /**
     * Permet de trouver l'Objet DistanceSonore ayant la distance la plus petite
     * @param distancesSyllabes arraylist de distance sonores entre toutes les syllabes uniques
     * @return Objet DistanceSonore avec la distance sonore la plus petite
     */
    private DistanceSonore plusPetiteDistanceSonore(ArrayList<DistanceSonore> distancesSyllabes){

        DistanceSonore distancePlusPetite = null;
        int plusPetiteDistance = 0;

        for (DistanceSonore distanceSyllabe : distancesSyllabes){
            if (distanceSyllabe.equals(distancesSyllabes.get(0))){
                plusPetiteDistance = distanceSyllabe.getDistanceSyllabes();
                distancePlusPetite = distanceSyllabe;
            }else {
                if (plusPetiteDistance >= distanceSyllabe.getDistanceSyllabes()){
                    plusPetiteDistance = distanceSyllabe.getDistanceSyllabes();
                    distancePlusPetite = distanceSyllabe;
                }
            }
        }
        distancesSyllabes.remove(distancePlusPetite);

        return distancePlusPetite;
    }


    /**
     * ArrayList de distances sonores entre syllabes qui nous permettrons de reduire notre texte
     */
    public ArrayList<DistanceSonore> distancesSonores = new ArrayList<DistanceSonore>();

    /**
     * Permet de calculer toutes les combinaisons possibles de syllabes et la distance entre chaque.
     * L'algorithme pour trouver toutes les combinaisons sans dedoublement fonctionne comme ceci.
     * 1 x x x
     * - 2 x x
     * - - 3 x
     * les x sont les syllabes avec lesquelles des distanceSonore seront crees
     */
    public void distanceSonore(){

        for (int i = 0; i < syllabesUniques.size() - 1; i++){
            for (int j = i + 1; j < syllabesUniques.size(); j++){
                distancesSonores.add(new DistanceSonore(syllabesUniques.get(i), syllabesUniques.get(j)));
            }
        }
    }

    /**
     * Permet de contenir les syllabes uniques ainsi que leurs occurences dans la classe Texte Sonore
     */
    public ArrayList<SyllabeFrancais> syllabesUniques = new ArrayList<SyllabeFrancais>();

    /**
     * Permet de trouver toutes les syllabes unique contenues dans le TexteSonore, utilise des fonction d'egalite et
     * compte leurs occurences dans le TexteSonore et ajoute les syllabes determinees uniques (avec leur occurences)
     * dans le ArrayList syllabesUniques
     */
    public void trouverSyllabesUniques(){

        int occurences;

        for (SyllabeFrancais syllabeTexte : this){

            if (syllabeTexte.nonTrouvee(syllabesUniques)){
                occurences = compterOccurenceSyllabeTxt(syllabeTexte);

                syllabesUniques.add(new SyllabeFrancais(syllabeTexte.attaque, syllabeTexte.noyau,
                        syllabeTexte.coda, occurences));
            }
        }
    }

    /**
     * Compte les occurences de la syllabe en parametre dans le texteSonore this
     * @param syllabe syllabe a compter les occurences
     * @return le nombre d'occurence
     */
    private int compterOccurenceSyllabeTxt(SyllabeFrancais syllabe){
        int occurences = 0;

        for ( SyllabeFrancais syllabeText : this ){
            if (syllabeText.egaliteSyllabes(syllabe)){
                occurences++;
            }
        }

        return occurences;
    }

    /**
     * Le caractère utilisé pour séparé les syllabes lors de la lecture et de l'écriture.
     */
    public static final String SEPARATEUR = ".";

    /**
     * Construit une suite de syllabe vide.
     */
    public TexteSonore() {}

    /**
     * Construit une suite de syllabes à partir du contenu d'un fichier.
     *
     * @param nomFichier Le nom du fichier qui contient la suite de syllabes.
     */
    public TexteSonore( String nomFichier ) {
        File fichier = new File( nomFichier );
        Scanner scanner = null;

        try{
            scanner = new Scanner( fichier );
        } catch( FileNotFoundException e ) {
            Erreur.FICHIER_INEXISTANT.lancer( "\"" + nomFichier +"\"" );
        }

        scanner.useDelimiter( "" );
        lire( scanner );
        scanner.close();
    }


    /**
     * Lit une suite de syllabe dans le {@code Scanner}.
     *
     * Consulte le {@code Scanner} pour lire une suite de syllabe séparé par le caractère {@code SEPARATEUR}.
     *
     * @param scanner le {@code Scanner} dans lequel la lecture est effectué.
     * @return le groupe de consonne lu.
     * @exception NoSuchElementException s'il n'y a pas de {@code API_Consonne} valide.
     * @exception IllegalStateException si le {@code Scanner} est fermé.
     */
    private void lire( Scanner scanner ) {
        try{
            while( scanner.hasNext() ) {
                add( SyllabeFrancais.lire( scanner ) );
                scanner.next( SEPARATEUR );
            }
        } catch( NoSuchElementException e ) {
        }
    }


    /**
     * Construit une chaîne de caractères contenant la suite de syllabe représenté par les symboles de l'API.
     *
     * @return la chaîne construite.  S'il n'y a pas de syllabe dans la suite, alors la chaîne sera vide.
     */
    @Override
    public String toString() {
        return stream().map( SyllabeFrancais::toString ).collect( Collectors.joining( SEPARATEUR ) );
    }
}
