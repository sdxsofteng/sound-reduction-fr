package INF2120.API;

import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Décrit le son d'un groupe de consonne en français.
 *
 * Cette description permet de contenir une ou deux consonnes afin de décrire le son des consonnes dans une syllabe.
 *
 * @see API_Consonne
 * @see SyllabeFrancais
 * @see <a href="https://fr.wiktionary.org/wiki/Annexe:Prononciation/fran%C3%A7ais">référence</a>
 */
public class ConsonneFrancais{

    /**
     * Calcule la distance entre les deux phonemes de consonnes ensemble
     * @param consonneComparee consonne comparee a this
     * @return distance totale de la consonne [0-12]
     */
    public int distancePhonemesConsonne(ConsonneFrancais consonneComparee){
        return distancePhonemeUnique(this.consonne1, consonneComparee.consonne1)
                + distancePhonemeUnique(this.consonne2, consonneComparee.consonne2);
    }

    /**
     * Calcule la distance entre deux phoneme de consonnes
     * @param phonemeOriginal phoneme du this
     * @param phonemeComparer phoneme de la consonne comparee
     * @return distance [0-6]
     */
    private int distancePhonemeUnique(API_Consonne phonemeOriginal, API_Consonne phonemeComparer){

        int distance;

        if (phonemeOriginal == null && phonemeComparer == null){
            distance = Constantes.DISTANCE_ZERO;
        }else if (phonemeOriginal == null ^ phonemeComparer == null){
            distance = Constantes.DISTANCE_MAXIMALE_PHONEME_CONSONNE;
        }else{
            distance = calculerDistanceBooleens(phonemeOriginal.estVocalique(), phonemeComparer.estVocalique())
                    + calculerDistanceBooleens(phonemeOriginal.estNasal(), phonemeComparer.estNasal())
                    + calculerDistanceBooleens(phonemeOriginal.estVoise(), phonemeComparer.estVoise())
                    + calculerDistanceBooleens(phonemeOriginal.estContinu(), phonemeComparer.estContinu())
                    + calculerDistanceBooleens(phonemeOriginal.estCompact(), phonemeComparer.estCompact())
                    + calculerDistanceBooleens(phonemeOriginal.estAigu(), phonemeComparer.estAigu());
        }
        return distance;
    }

    /**
     * Calcule la distance entre deux booleens
     * @param booleenOriginal booleen du this
     * @param booleenComparer booleen comparer
     * @return distance [0-1]
     */
    private int calculerDistanceBooleens(boolean booleenOriginal, boolean booleenComparer){

        int distance;

        if (booleenOriginal == booleenComparer){
            distance = 0;
        }else {
            distance = 1;
        }

        return distance;
    }

    /**
     * Methode permettant de verifier l'egalitee entre deux consonnes. Permet d'eviter les null pointer dans la methode
     * egaliteLettre
      * @param consonneAVerifier <-
     * @return si les deux consonnes sont egales
     */
    public boolean egaliteLettresConsonnes(ConsonneFrancais consonneAVerifier ) {

        boolean consonneOriginale;
        boolean consonneAComparer;
        boolean consonneCompleteEgale;

        //Permet de verifier si il n'y a pas de comparaison de null avec un non-null
        if (!((this.consonne1 == null ^ consonneAVerifier.consonne1 == null)
                || (this.consonne2 == null ^ consonneAVerifier.consonne2 == null))){
            consonneOriginale = egaliteeLettre( this.consonne1, consonneAVerifier.consonne1 );
            consonneAComparer = egaliteeLettre( this.consonne2, consonneAVerifier.consonne2 );
            consonneCompleteEgale = consonneOriginale && consonneAComparer;

        }else{
            consonneCompleteEgale = false;
        }

        return consonneCompleteEgale;
    }

    /**
     * Permet de verifier l'egalite entre deux API_Consonne
     * @param lettreOriginale lettre du this
     * @param lettreAVerifier lettre de la consonne comparee
     * @return valeur d'egalite entre les deux API_Consonne
     */
    private boolean egaliteeLettre(API_Consonne lettreOriginale, API_Consonne lettreAVerifier) {
        boolean lettreEgale;

        if (lettreOriginale == null && lettreAVerifier == null){
            lettreEgale = true;
        }else{
            lettreEgale = lettreOriginale.equals(lettreAVerifier);
        }

        return lettreEgale;
    }

    /**
     * La consonne de base du groupe de consonne.
     * Ne doit pas être {@code null}.
     */
    protected API_Consonne consonne1;

    /**
     * La consonne secondaire du groupe de consonne.
     * La valeur {@code null} est utilisé pour indiquer qu'elle n'est pas présente dans le groupe.
     */
    protected API_Consonne consonne2 = null;


    /**
     * Construit un groupe avec une seule consonne.
     *
     * @param consonne1 La consonne du groupe.  Elle est placé comme consonne de base.  Ne doit pas être {@code null}.
     */
    public ConsonneFrancais( API_Consonne consonne1 ) {
        this.consonne1 = consonne1;
    }


    /**
     * Construit un groupe avec deux consonnes.
     *
     * @param consonne1 La consonne de base du groupe.  Ne doit pas être {@code null}.
     * @param consonne2 La consonne secondaire du groupe.
     */
    public ConsonneFrancais( API_Consonne consonne1, API_Consonne consonne2 ) {
        this.consonne1 = consonne1;
        this.consonne2 = consonne2;
    }



    /**
     * Lit un groupe de consonnes dans le {@code Scanner}.
     *
     * Vérifie si le prochain caractère du {@code scanner} représente une consonne.  Si oui, alors cette consonne
     * deviendra la consonne de base du groupe retourné.
     * Ensuite, vérifie si le prochain caractère représente une consonne.  Si oui, alors cette consonne deviendra la
     * consonne secondaire du groupe retourné.
     *
     * @param scanner le {@code Scanner} dans lequel la lecture est effectué.
     * @return le groupe de consonne lu.
     * @exception NoSuchElementException s'il n'y a pas de {@code API_Consonne} valide.
     * @exception IllegalStateException si le {@code Scanner} est fermé.
     */
    public static ConsonneFrancais lire( Scanner scanner ) {
        ConsonneFrancais resultat = null;
        API_Consonne consonne1 = API_Consonne.lire( scanner );
        API_Consonne consonne2;

        try {
            consonne2 = API_Consonne.lire( scanner );
            resultat = new ConsonneFrancais( consonne1, consonne2 );
        } catch ( NoSuchElementException e ) {
            resultat = new ConsonneFrancais( consonne1 );
        }

        return resultat;
    }


    /**
     * retourne une chaîne de caractère composée des consonnes du groupe.
     *
     * @return la chaîne contenant les symboles des consonnes du groupe.
     */
    @Override
    public String toString() {
        return "" + consonne1 + ( null == consonne2 ? "" : consonne2 );
    }
}
