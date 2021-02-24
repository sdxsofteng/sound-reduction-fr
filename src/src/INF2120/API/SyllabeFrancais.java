package INF2120.API;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Représente l'unité de base pour la prononciation en français.
 *
 * Une syllabe est composé d'un groupe de syllabe (le noyau).
 * optionnelement, elle peu avoir deux groupes de consonne.
 * Le groupe de consonne avant le noyau est l'attaque de la syllabe.
 * Le groupe de consonne après le noyau est le coda de la syllabe.
 *
 * @see ConsonneFrancais
 * @see VoyelleFrancais
 * @see <a href="https://fr.wiktionary.org/wiki/Annexe:Prononciation/fran%C3%A7ais">référence</a>
 */
public class SyllabeFrancais {

    /**
     * Calcule la distance entre deux syllabes
     * @param syllabe2 syllabe pour laquelle nous devons trouver la distance avec le this
     * @return distance totale entre deux syllabes [0,42]
     */
    public int distanceSyllabes(SyllabeFrancais syllabe2){
        return distanceConsonne(this.attaque, syllabe2.attaque) + (distanceVoyelle(this.noyau, syllabe2.noyau) * 2)
                + distanceConsonne(this.coda, syllabe2.coda);
    }

    /**
     * Retourne la distance entre deux consonne
     * @param consonneOriginale consonne du this
     * @param consonneComparee consonne de la syllabe pour laquelle nous voulons trouver la distance avec this
     * @return distance totale entre deux consonnes [0,12]
     */
    private int distanceConsonne(ConsonneFrancais consonneOriginale, ConsonneFrancais consonneComparee){
        int distance;

        if (consonneOriginale == null && consonneComparee == null){
            distance = Constantes.DISTANCE_ZERO;
        }else if (consonneOriginale == null ^ consonneComparee == null){
            distance = Constantes.DISTANCE_MAXIMALE_CONSONNE;
        }else {
            distance = consonneOriginale.distancePhonemesConsonne(consonneComparee);
        }
        return distance;
    }

    /**
     * Calcule la distance entre deux groupes de voyelles
     * @param voyelleOriginale groupe de voyelles du this
     * @param voyelleComparee groupe de voyelles a comparee
     * @return distance [0-9]
     */
    private int distanceVoyelle(VoyelleFrancais voyelleOriginale, VoyelleFrancais voyelleComparee){

        int distance;

        if (voyelleOriginale == null && voyelleComparee == null){
            distance = Constantes.DISTANCE_ZERO;
        }else if (voyelleOriginale == null ^ voyelleComparee == null){
            distance = Constantes.DISTANCE_MAXIMALE_VOYELLE;
        }else {
            distance = voyelleOriginale.distancePhonemesVoyelles(voyelleComparee);
        }
        return distance;
    }

    /**
     * Verifier le arrayList de syllabes passer en parametre pour savoir si celui ci contient la syllabe this
     * @param syllabesUniques ArrayList de syllabes uniques
     * @return une valeur booleene
     */
    public boolean nonTrouvee (ArrayList<SyllabeFrancais> syllabesUniques){

        boolean nonTrouvee = true;

        if (!syllabesUniques.isEmpty()){

            for (SyllabeFrancais syllabeUnique : syllabesUniques){

                if (this.egaliteSyllabes(syllabeUnique)){
                    nonTrouvee = false;
                }
            }
        }

        return nonTrouvee;
    }

    /**
     * Permet de rattacher le nombre d'occurence a this
     */
    public int occurences;

    /**
     * Permet de creer une nouvelle syllabe avec un noyau, une attaque, un coda et une occurence
     * @param attaque contient l'attaque
     * @param noyau contient le noyau
     * @param coda contient le coda
     * @param occurences contient le nombre d'occurence de la syllabe
     */
    SyllabeFrancais(ConsonneFrancais attaque, VoyelleFrancais noyau, ConsonneFrancais coda, int occurences){
        this.attaque = attaque;
        this.noyau = noyau;
        this.coda = coda;
        this.occurences = occurences;
    }

    /**
     * Verifie l'egalite entre deux syllabes. Compare this a la syllabeAVerifier
     * @param syllabeAVerifier syllabe a verifier
     * @return si les deux syllabes sont egales
     */
    public boolean egaliteSyllabes (SyllabeFrancais syllabeAVerifier) {

        boolean attaqueEgales;
        boolean noyauEgaux;
        boolean codaEgaux;
        boolean syllabesEgales;

        if (verifierComposantes(syllabeAVerifier)){
            attaqueEgales = egaliteeConsonne( this.attaque, syllabeAVerifier.attaque );
            noyauEgaux = egaliteeVoyelle( this.noyau, syllabeAVerifier.noyau );
            codaEgaux = egaliteeConsonne( this.coda, syllabeAVerifier.coda );

            syllabesEgales = attaqueEgales && noyauEgaux && codaEgaux;
        }
        else {
            syllabesEgales = false;
        }

        return syllabesEgales;
    }

    /**
     * Verifie l'egalite entre deux consonnes. Si les deux sont nulls c'est necessairement vrai.
     * Si une consonne est null et l'autre non-null retourne faux. Sinon procede a la verification de chaque lettre
     * dans la consonne
     * @param consonneOriginale consonne du this
     * @param consonneAVerifier consonne de la syllabe a verifier
     * @return valeur de verite si les consonnes sont egales
     */
    private boolean egaliteeConsonne(ConsonneFrancais consonneOriginale,
                                     ConsonneFrancais consonneAVerifier ) {
        boolean consonneEgales;

        if (consonneOriginale == null && consonneAVerifier == null){
            consonneEgales = true;
        }
        else if (consonneOriginale == null ^ consonneAVerifier == null){
            consonneEgales = false;
        }
        else {
            consonneEgales = consonneOriginale.egaliteLettresConsonnes(consonneAVerifier);
        }

        return consonneEgales;
    }

    /**
     * Verifie l'egalite entre deux voyelles. Si les deux sont nulls c'est necessairement vrai.
     * Si une voyelle est null et l'autre non-null retourne faux. Sinon procede a la verification de chaque lettre
     * dans la voyelle
     * @param voyelleOriginale consonne du this
     * @param voyelleAVerifier consonne de la syllabe a verifier
     * @return valeur de verite si les consonnes sont egales
     */
    private boolean egaliteeVoyelle(VoyelleFrancais voyelleOriginale, VoyelleFrancais voyelleAVerifier ) {

        boolean voyellesEgales;

        if (voyelleOriginale == null && voyelleAVerifier == null){
            voyellesEgales = true;
        }
        else if (voyelleOriginale == null ^ voyelleAVerifier == null){
            voyellesEgales = false;
        }
        else {
            voyellesEgales = voyelleOriginale.egaliteLettresVoyelles(voyelleAVerifier);
        }

        return voyellesEgales;
    }

    /**
     * Verifie si les attaques(noyau et coda) sont soit null et null ou non-null et non-null. Si ce n'est pas le cas
     * on sait tous de suite que les syllabes ne sont pas egales
     * @param syllabeAVerifier syllabe a verifier contre this
     * @return valeur de verite si les composantes sont null et null ou non-null et non-null
     */
    private boolean verifierComposantes( SyllabeFrancais syllabeAVerifier ) {
        return !( ( this.attaque == null ^ syllabeAVerifier.attaque == null )
                && ( this.noyau == null ^ syllabeAVerifier.noyau == null )
                && ( this.coda == null ^ syllabeAVerifier.coda == null ));
    }



























    /**
     * Le groupe de consonne pour l'attaque de la syllabe.  S'il n'est pas présent, alors la valeur est à {@code null}.
     */
    protected ConsonneFrancais attaque = null;

    /**
     * Le groupe de voyelle pour la syllabe.  Ne doit pas être {@code null}.
     */
    protected VoyelleFrancais noyau;

    /**
     * Le groupe de consonne pour le code de la syllabe.  S'il n'est pas présent, alors la valeur est à {@code null}.
     */
    protected ConsonneFrancais coda = null;


    /**
     * Construit une syllabe avec un noyau seulement.
     *
     * @param noyau le groupe de voyelle utilisé pour la syllabe.  Ne doit pas être {@code null}.
     */
    public SyllabeFrancais( VoyelleFrancais noyau) {
        this.noyau = noyau;
    }


    /**
     * Construit une syllabe avec une attaque et un noyau.
     *
     * @param attaque le groupe de consonne utilisé pour la syllabe.
     * @param noyau le groupe de voyelle utilisé pour la syllabe.  Ne doit pas être {@code null}.
     */
    public SyllabeFrancais( ConsonneFrancais attaque, VoyelleFrancais noyau) {
        this.attaque = attaque;
        this.noyau = noyau;
    }


    /**
     * Construit une syllabe avec une attaque, un noyau et un coda.
     *
     * @param attaque le groupe de consonne utilisé pour la syllabe.
     * @param noyau le groupe de voyelle utilisé pour la syllabe.  Ne doit pas être {@code null}.
     * @param coda le groupe de consonne utilisé pour la syllabe.
     */
    public SyllabeFrancais(ConsonneFrancais attaque, VoyelleFrancais noyau, ConsonneFrancais coda ) {
        this.attaque = attaque;
        this.noyau = noyau;
        this.coda = coda;
    }


    /**
     * Construit une syllabe avec un noyau et un coda.
     *
     * @param noyau le groupe de voyelle utilisé pour la syllabe.  Ne doit pas être {@code null}.
     * @param coda le groupe de consonne utilisé pour la syllabe.
     */
    public SyllabeFrancais(VoyelleFrancais noyau, ConsonneFrancais coda ) {
        this.noyau = noyau;
        this.coda = coda;
    }


    /**
     * Lit une syllabe dans le {@code Scanner}.
     *
     * Cherche possiblement un groupe de consonne qui servira d'attaque, ensuite un groupe de voyelle qui
     * servira de noyau et finalement un autre groupe de consonne pour le coda.
     *
     * @param scanner le {@code Scanner} dans lequel la lecture est effectué.
     * @return la voyelle lu.
     * @exception NoSuchElementException s'il n'y a pas de {@code SyllabeFrancais} valide.
     * @exception IllegalStateException si le {@code Scanner} est fermé.
     */
    public static SyllabeFrancais lire( Scanner scanner ) {
        ConsonneFrancais attaque = null;
        VoyelleFrancais noyau;
        ConsonneFrancais coda = null;

        try {
            attaque = ConsonneFrancais.lire( scanner );
        } catch ( NoSuchElementException e ) {
        }

        noyau = VoyelleFrancais.lire( scanner );

        try {
            coda = ConsonneFrancais.lire( scanner );
        } catch ( NoSuchElementException e ) {
        }


        return new SyllabeFrancais( attaque, noyau, coda );
    }


    /**
     * retourne une chaîne de caractère composée des phonèmes de la syllabe.
     *
     * @return la chaîne contenant les symboles des phonèmes de la syllabe.
     */
    @Override
    public String toString() {
        return "" + ( null == attaque ? "" : attaque )
                + noyau
                + ( null == coda ? "" : coda );
    }
}
