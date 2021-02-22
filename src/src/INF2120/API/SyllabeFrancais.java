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

    public int calculerDistanceSyllabe(SyllabeFrancais syllabe2){

        return calculerConsonne(this.attaque, syllabe2.attaque) + (calculerVoyelle(this.noyau, syllabe2.noyau) * 2)
                + calculerConsonne(this.coda, syllabe2.coda);
    }

    private int calculerConsonne(ConsonneFrancais consonneOriginale, ConsonneFrancais consonneComparee){
        int distance;

        if (consonneOriginale == null && consonneComparee == null){
            distance = 0;
        }else if (consonneOriginale == null ^ consonneComparee == null){
            if (consonneOriginale == null){
                distance = consonneComparee.calculerDistanceLettres(consonneComparee.consonne1, null)
                + consonneComparee.calculerDistanceLettres(consonneComparee.consonne2, null);
            }else {
                distance = consonneOriginale.calculerDistanceLettres(consonneOriginale.consonne1, null)
                + consonneOriginale.calculerDistanceLettres(consonneOriginale.consonne2, null);
            }
        }else {
            distance = consonneOriginale.calculerDistanceConsonne(consonneComparee);
        }
        return distance;
    }

    private int calculerVoyelle(VoyelleFrancais voyelleOriginale, VoyelleFrancais voyelleComparee){

        int distance;

        if (voyelleOriginale == null && voyelleComparee == null){
            distance = 0;
        }else if (voyelleOriginale == null ^ voyelleComparee == null){
            if (voyelleOriginale == null){
                distance = voyelleComparee.calculerDistanceLettres(voyelleComparee.semiVoyelle, null)
                        + voyelleComparee.calculerDistanceLettres(voyelleComparee.voyelle, null);
            }else {
                distance = voyelleOriginale.calculerDistanceLettres(voyelleOriginale.semiVoyelle, null)
                        + voyelleOriginale.calculerDistanceLettres(voyelleOriginale.voyelle, null);
            }
        }else {
            distance = voyelleOriginale.calculerDistanceVoyelle(voyelleComparee);
        }
        return distance;
    }
































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

    public int occurences;

    SyllabeFrancais(ConsonneFrancais attaque, VoyelleFrancais noyau, ConsonneFrancais coda, int occurences){
        this.attaque = attaque;
        this.noyau = noyau;
        this.coda = coda;
        this.occurences = occurences;
    }







































    public boolean egaliteSyllabes ( SyllabeFrancais syllabeAVerifier ) {

        boolean attaqueEgales;
        boolean noyauEgaux;
        boolean codaEgaux;
        boolean syllabesEgales;

        if ( verifierComposantes(syllabeAVerifier) ){
            attaqueEgales = retournerEgaliteeConsonne( this.attaque, syllabeAVerifier.attaque );
            noyauEgaux = retournerEgaliteeVoyelle( this.noyau, syllabeAVerifier.noyau );
            codaEgaux = retournerEgaliteeConsonne( this.coda, syllabeAVerifier.coda );
            syllabesEgales = attaqueEgales && noyauEgaux && codaEgaux;
        }
        else {
            syllabesEgales = false;
        }

        return syllabesEgales;
    }

    private boolean retournerEgaliteeConsonne( ConsonneFrancais consonneOriginale,
                                               ConsonneFrancais consonneAVerifier ) {

        boolean consonneEgales;

        if ( consonneOriginale == null && consonneAVerifier == null){
            consonneEgales = true;
        }
        else if ( consonneOriginale == null || consonneAVerifier == null){
            consonneEgales = false;
        }
        else {
            consonneEgales = consonneOriginale.egaliteConsonne( consonneAVerifier );
        }

        return consonneEgales;
    }

    private boolean retournerEgaliteeVoyelle( VoyelleFrancais voyelleOriginale, VoyelleFrancais voyelleAVerifier ) {

        boolean voyellesEgales;

        if ( voyelleOriginale == null && voyelleAVerifier == null){
            voyellesEgales = true;
        }
        else if ( voyelleOriginale == null || voyelleAVerifier == null){
            voyellesEgales = false;
        }
        else {
            voyellesEgales = voyelleOriginale.egaliteVoyelle( voyelleAVerifier );
        }

        return voyellesEgales;
    }

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
